package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.ISjbzDao;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.service.svcinterface.ISjbzService;
import com.woshidaniu.taglibs.data.TagDataProvider;
import com.woshidaniu.util.base.MessageUtil;

import net.sf.json.JSONObject;

/**
 * 标准数据获取实现
 * @author Penghui.Qu
 *
 */
@Service("sjbzService")
public class SjbzServiceImpl extends BaseServiceImpl<SjbzModel, ISjbzDao> implements ISjbzService,TagDataProvider {

	@Resource
	private ISjbzDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.ISjbzService#getYwsjList(String)}
	 */
	@Override
	public List<SjbzModel> getYwsjList(String lx) throws Exception {
		
		if (!StringUtils.isEmpty(lx)){
			return dao.getYwsjList(lx);
		}else {
			throw new IllegalArgumentException("lx不能为空!");
		}
		
	}
	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.ISjbzService#getYwsjList(String,String)}
	 */
	@Override
	public List<SjbzModel> getYwsjList(String lx, String zt) throws Exception {
		SjbzModel queryModel = new SjbzModel();
		queryModel.setLx(lx);
		
		if (!StringUtils.isEmpty(lx)){
			if(StringUtils.isEmpty(zt)){
				queryModel.setZt(SjbzModel.ZT_1);
			}else{
				queryModel.setZt(zt);
			}
			return dao.getYwsjListByLxAndZt(queryModel);
		}else {
			throw new IllegalArgumentException("lx不能为空!");
		}
		
	}
	
	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.ISjbzService#getBzsjList(String)}
	 */
	@Override
	@Cacheable(value=CacheName.CACHE_BASIC,key="#tableName + 'getBzsjList'")
	public List<SjbzModel> getBzsjList(String tableName)  {
		
		SjbzModel model = new SjbzModel();
		model.setTableName(tableName);
		
		return dao.getBzsjList(model);
	}
	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.ISjbzService#getBzsjList(String)}
	 */
	@Override
	public List<Map<String, String>> getBzsjListForAutoCom(String tableName) throws Exception {
		
		SjbzModel model = new SjbzModel();
		
		if (!StringUtils.isEmpty(tableName)){
			model.setTableName(tableName);
			return dao.getBzsjListForAutoCom(model);
		}else {
			throw new IllegalArgumentException("tableName不能为空!");
		}
	}



	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.ISjbzService#getMcByDm(com.woshidaniu.dao.entities.SjbzModel)
	 */
	@Override
	public SjbzModel getMcByDm(String tableName,String dm) {
		SjbzModel sjbzModel = new SjbzModel();
		sjbzModel.setTableName(tableName);
		sjbzModel.setDm(dm);
		return dao.getMcByDm(sjbzModel);
	}


	@Override
	public List<Map<String, String>> getJcsjList(String lx) {
		
		return dao.getJcsjList(lx);
	}
	
	/**
	 * 获取基础代码列表根据类型
	 * @param lx基础数据类型代码，配置文件中的key
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getTjListByLx(String lx){
		if(StringUtils.isEmpty(lx)){
			return null;
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//获取对应代码的基础数据
		List<Map<String,String>> tjlist = dao.getJcsjList(MessageUtil.getText(lx));
		if(null!=tjlist&&tjlist.size()>0){
			for(int i=0;i<tjlist.size();i++){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("dm", tjlist.get(i).get("DM"));
				param.put("mc", tjlist.get(i).get("MC"));
				list.add(param);
			}
		}
		
		return list;
	}

	@Override
	public List<?> getDataList(JSONObject json) {
		String tableName = json.getString("tableName");
		return getBzsjList(tableName);
	}
	
}
