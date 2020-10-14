package com.woshidaniu.export.service.impl;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.exception.ServiceException;
import com.woshidaniu.dao.daointerface.IDocExportDao;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IDocExportService;

/**
 * 公用导出实现 
 * @author Penghui.Qu
 *
 */
public abstract class AbstractDocExportService implements IDocExportService {
	
	
	protected IDocExportDao exportDao;
	protected static final transient Logger log = LoggerFactory.getLogger(IDocExportService.class);
	
	//用于注入
	public void setExportDao(IDocExportDao exportDao) {
		this.exportDao = exportDao;
	}
	
	
	/**
	 * 创建导出文件
	 * @throws Exception 
	 */
	public abstract File getExportFile(ExportModel model) throws Exception;

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IExportService#getConfigList(com.woshidaniu.dao.entities.ExportModel)
	 */
	public List<ExportConfigModel> getConfigList(ExportModel model) {
		
		if (null == model || StringUtils.isEmpty(model.getDcclbh()) || StringUtils.isEmpty(model.getZgh())){
			throw new ServiceException("Nonlicet params !");
		}
		
		List<ExportConfigModel> configList = null;
		
		String[] selectZd = model.getSelectCol();
		if(!BlankUtils.isBlank(selectZd)){
			for (int i = 0; i < selectZd.length; i++) {
				try {
					selectZd[i] = URLDecoder.decode(selectZd[i], "UTF-8");
				} catch (Exception e) {
				}
			}
		}
		
		if (null != selectZd && selectZd.length > 0){
			
			configList = new ArrayList<ExportConfigModel>();
			
			for (int i = 0 ; i < selectZd.length ; i++){
				ExportConfigModel configModel = new ExportConfigModel();
				configModel.setZd(selectZd[i].split("@")[0]);
				configModel.setZdmc(selectZd[i].split("@")[1]);
				configModel.setZt(SELECT_ZT);
				configList.add(configModel);
			}
			
		} else {
			configList = exportDao.getExportConfig(model);
			//若根据用户名获取到的导出配置为空则获取公用配置
			for (int i = 0; null == configList || configList.size() == 0; i++){
				if (i > 0){
					log.error("Export blank info:ID("+model.getDcclbh()+"),User("+model.getZgh()+")");
				}
				//防止死循环，只判断两次
				if(i>=2){
					break;
				}
				//若根据用户名获取到的导出配置为空则获取公用配置
				model.setZgh(PUBLIC_CONFIG);
				configList = exportDao.getExportConfig(model);
			}
		}
		return configList;
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.export.service.svcinterface.IExportService#saveExportConfig(com.woshidaniu.dao.entities.ExportModel)
	 */
	public boolean saveExportConfig(ExportModel model) {
		
		if (null == model || StringUtils.isEmpty(model.getDcclbh()) || StringUtils.isEmpty(model.getZgh())){
			throw new ServiceException("Nonlicet params !");
		}
		
		String selectZd = model.getSelectZd();
		String unselectZd = model.getUnselectZd();
		List<HashMap<String,String>> configList = new ArrayList<HashMap<String,String>>();
		
		//选中字段数据处理
		if (!StringUtils.isEmpty(selectZd)){
			
			String[] selectCol = selectZd.split(",");
			
			for (int i = 0 ; i < selectCol.length ; i++){
				HashMap<String,String> map = new HashMap<String, String>();
				map.put("zt", SELECT_ZT);
				map.put("zgh", model.getZgh());
				map.put("dcclbh", model.getDcclbh());
				map.put("zd", selectCol[i].split("@")[0]);
				map.put("zdmc", selectCol[i].split("@")[1]);
				map.put("xssx", String.valueOf(i));
				configList.add(map);
			}
		}
		
		//未选中字段数据处理
		if (!StringUtils.isEmpty(unselectZd)){
			String[] unselectCol = unselectZd.split(",");
			
			for (int i = 0 ; i < unselectCol.length ; i++){
				HashMap<String,String> map = new HashMap<String, String>();
				map.put("zt", UNSELECT_ZT);
				map.put("zgh", model.getZgh());
				map.put("dcclbh", model.getDcclbh());
				map.put("zd", unselectCol[i].split("@")[0]);
				map.put("zdmc", unselectCol[i].split("@")[1]);
				map.put("xssx", String.valueOf(i));
				configList.add(map);
			}
		}else{
			//若用户选择的配置和默认配置一致则删除该用户的配置
			exportDao.deleteConfig(model);
			return true;
		}
		
		List<ExportConfigModel> defaultConfigList = exportDao.getExportConfig(model);
		
		//存在该用户对应配置执行更新，不存在则插入
		if (null != defaultConfigList && defaultConfigList.size() > 0){
			exportDao.deleteConfig(model);
		} 
		
		return exportDao.insertConfig(configList) == configList.size();
	}
	
	/**
	 *@描述：初始化页面上的配置
	 *@创建人:"huangrz"
	 *@创建时间:2014-12-12上午08:45:14
	 *@param model
	 */
	public void exportInitDcpz(ExportModel model){
		List<ExportConfigModel> colConfig = model.getColConfig();
		//防止错误数据
		if(colConfig==null || colConfig.size() == 0){
			return;
		}
		
		model.setZgh(PUBLIC_CONFIG);
		//数据库里已经有的数据
		List<ExportConfigModel> config = exportDao.getExportConfig(model);
		
		boolean flag = true;
		//比较页面是否和数据库一样
		if(config != null && config.size() == colConfig.size()){
			for(int i=0;i<config.size();i++){
				ExportConfigModel db = config.get(i);     //数据库里的一列配置
				ExportConfigModel col = colConfig.get(i); //页面上的一列配置
				//字段和字段名是否都一样
				if(!db.getZd().equals(col.getZd()) || !db.getZdmc().equals(col.getZdmc())){
					flag = false;
					break;
				}
			}
		}else{
			flag = false;
		}
		
		//如果数据库里的配置和页面上的配置不一致 就更新数据库
		if(!flag){
			exportDao.updateCshpz(model);
		}
	}

}
