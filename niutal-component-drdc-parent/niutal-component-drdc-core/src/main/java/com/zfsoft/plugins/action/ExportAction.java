package com.woshidaniu.plugins.action;


import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IDocExportService;
import com.woshidaniu.export.service.utils.ExportComparator;
/**
 * 自定义导出
 * @author Penghui.Qu
 *
 */
public class ExportAction extends BaseAction implements ModelDriven<ExportModel>{

	private static final long serialVersionUID = 1L;
	private static final String EXPORT_CONFIG = "exportConfig";
	
	private ExportModel model = new ExportModel();
	private IDocExportService exportService;

	public void setExportService(IDocExportService exportService) {
		this.exportService = exportService;
	}

	public ExportModel getModel() {
		return model;
	}
	
	/**
	 * 获取导出配置信息
	 * @return
	 */
	public String exportConfig(){
		
		User user = getUser();
		if(user.getYhlybid()==null||"".equals(user.getYhlybid())){
			getValueStack().set("message","用户"+user.getYhm()+"的数据来源表中无该用户数据，不是正常用户无法进行导出！");
			return EXPORT_CONFIG;
		}
		model.setZgh(user.getYhlybid());
		//查询导出列
		List<ExportConfigModel> configList = exportService.getConfigList(model);
		//根据显示顺序排序
		Collections.sort(configList,new ExportComparator());
		
		getValueStack().set("configList", configList);
		return EXPORT_CONFIG;
	}
	
	
	/**
	 * 保存自定义导出配置
	 * @return
	 */
	public String saveCustomConfig(){
		try {
			User user = getUser();
			model.setZgh(user.getYhlybid());
			model.setSelectZd(URLDecoder.decode(model.getSelectZd(), "UTF-8"));
			model.setUnselectZd(URLDecoder.decode(model.getUnselectZd(), "UTF-8"));
			boolean isSuccess = exportService.saveExportConfig(model);
			getValueStack().set(DATA, isSuccess);
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, false);
		}
		return DATA;
	}
	
	/**
	 *@描述：初始化页面上的配置
	 *@创建人:"huangrz"
	 *@创建时间:2014-12-12上午09:08:24
	 *@return
	 */
	public String exportInitDcpz(){
		try {
			exportService.exportInitDcpz(model);
		} catch (Exception e) {
			logStackException(e);
		}
		return null;
	}
}
