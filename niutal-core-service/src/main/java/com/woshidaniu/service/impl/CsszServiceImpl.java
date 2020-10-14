package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.enhanced.context.event.EventAspect;
import org.springframework.stereotype.Service;

import com.woshidaniu.api.event.XtcsUpdateEvent;
import com.woshidaniu.api.event.handler.PropsUpdateEventHandler;
import com.woshidaniu.basicutils.ArrayUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.ICsszDao;
import com.woshidaniu.dao.entities.CsszModel;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.service.svcinterface.ICsszService;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：参数设置
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年10月18日下午4:16:23
 */
@Service
@SuppressWarnings("unchecked")
public class CsszServiceImpl extends BaseServiceImpl<CsszModel, ICsszDao> implements ICsszService {
	
	@Resource
	private ICsszDao dao;
	private static final String sjly_database_begin = "database:";
	private static final String sjly_basedata_begin = "basedata:";
	private static final String sjly_fixed_begin = "fixed:";
	
	protected static final String DEFAULT_CACHE_NAME = "niutal_XTSZ";
	protected static final String DEFAULT_CACHE_KEY = "niutal_XTSZ_KEY";
	protected String cacheKey = DEFAULT_CACHE_KEY;
	protected CsszModel CSSZ_MODEL = new CsszModel();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.common.service.BaseServiceImpl#getModelList(java.lang.Object)
	 */
	public List<CsszModel> getModelList(CsszModel model){
		
		List<CsszModel> list = dao.getModelList(model);
		
		if (list == null || list.isEmpty()){
			return null;
		}
		 //解析参数字段数据来源
		 List<PairModel> zdsjList = null; 
			 
		 for (CsszModel xtszModel : list) {
			 
			 //指定数据来源
			 String zdly = xtszModel.getZdly();
			 if (StringUtils.isNull(zdly)){
				 continue;
			 }
			 
			 if(zdly.startsWith(sjly_database_begin)){
				//数据查询SQL
				String sql = zdly.substring(sjly_database_begin.length());
				
				if(StringUtils.isBlank(sql)){
					throw new NullPointerException("数据来源字段配置不正确！");
				}
				zdsjList =  dao.getZdsjList(sql);
			 }  else if(zdly.startsWith(sjly_basedata_begin)){
				 //基础数据
				 String id = zdly.substring(sjly_basedata_begin.length());
				 if(StringUtils.isBlank(id)){
					 throw new NullPointerException("数据来源字段配置不正确！");
				 }
				 List<HashMap<String,String>> dataList = BaseDataReader.getCachedOptionList(id);
				 
				 if(dataList != null && !dataList.isEmpty()){
					 zdsjList = new ArrayList<PairModel>();
					 for (HashMap<String,String> data : dataList) {
						 zdsjList.add(new PairModel(data.get("key"), data.get("value")));
					 }
				 }
			 }else if(zdly.startsWith(sjly_fixed_begin)){
				 //固定选项
				 String content = zdly.substring(sjly_fixed_begin.length());
				 zdsjList = getFixedList(content,":");
			 } else {
				 
			 }
			 xtszModel.setZdsjList(zdsjList);
			 //清空字段来源信息：防止页面看到
			 xtszModel.setZdly(null);
		 }
		 return list;
	}

	private  List<PairModel> getFixedList(String content,String split){
		List<PairModel> field_data = new ArrayList<PairModel>();
		if(!StringUtils.isBlank(content)){
			for (String key : content.split(",")) {
				if(key.indexOf(split) > 0){
					String[] arrStrings = key.split(split);
					field_data.add(new PairModel(arrStrings[0],arrStrings[1]));
				}else{
					field_data.add(new PairModel(key,key));
				}
			}
		}
		return field_data;
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.ICsszService#getValue(java.lang.String)
	 */
	@Override
	public String getValue(String zdm) {
		if(isCacheSupport()){
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(getCacheKey());
			if(valueWrapper != null){
				List<CsszModel> list = (List<CsszModel>) valueWrapper.get();
				if(list == null || list.isEmpty()){
					return dao.getValue(zdm);
				}
				for (CsszModel csszModel : list) {
					if(csszModel.getZdm().equals(zdm)){
						return csszModel.getZdz();
					}
				}
				return dao.getValue(zdm);
			}else{
				return dao.getValue(zdm);
			}
		}else{
			return dao.getValue(zdm);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.ICsszService#getCssz()
	 */
	@Override
	public Map<String, String> getValues(String ssmk) {
		if(isCacheSupport()){
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(getCacheKey());
			if(valueWrapper != null){
				List<CsszModel> list = (List<CsszModel>) valueWrapper.get();
				if(list == null || list.isEmpty()){
					list = dao.getModelList(CSSZ_MODEL);
					cache.put(getCacheKey(), list);
				}
				return toMap(list, ssmk);
			} else {
				List<CsszModel> list = dao.getModelList(CSSZ_MODEL);
				cache.put(getCacheKey(), list);
				return toMap(list, ssmk);
			}
		}else{
			return toMap(dao.getModelList(CSSZ_MODEL), ssmk);
		}
	}
	
	protected Map<String,String> toMap(List<CsszModel> list,String ssmk){
		Map<String,String> map = new HashMap<String, String>();
		for (CsszModel model : list){
			if(ssmk != null){
				//仅仅当所属模块标记相同才取值
				if(ssmk.equals(model.getSsmk())){
					map.put(model.getZdm(), model.getZdz());
				}
				continue;
			}
			map.put(model.getZdm(), model.getZdz());
		}
		return map;
	}

	@Override
	@EventAspect( handler = PropsUpdateEventHandler.class)
	public boolean saveCssz(CsszModel model, Map<String, String[]> parameters) {
		List<CsszModel> itemList = getModelList(model);
		for (CsszModel cssz : itemList){
			if (Integer.valueOf(cssz.getZdlx()) == 4){
				//多选
				String[] zdzArr = parameters.get(cssz.getZdm());
				CsszModel csszModel = new CsszModel();
				csszModel.setZdm(cssz.getZdm());
				String zdz = ArrayUtils.toString(zdzArr);
				if (zdz.length() > 2){
					csszModel.setZdz(zdz.substring(1, zdz.length()-1));
					update(csszModel);
				}
			} else {
				String zdz = parameters.get(cssz.getZdm())[0];
				CsszModel csszModel = new CsszModel();
				csszModel.setZdm(cssz.getZdm());
				csszModel.setZdz(zdz);
				update(csszModel);
			}
		}
		
		//刷新缓存设
		refreshCache();
		
		//推送系统参数更新事件
		
		//第一步：对修改过的参数进行整合处理
		Map<String, String> tempMap = new HashMap<String, String>();
		for (CsszModel cssz : itemList){
			if (Integer.valueOf(cssz.getZdlx()) == 4){
				//多选
				String[] zdzArr = parameters.get(cssz.getZdm());
				String zdz = ArrayUtils.toString(zdzArr);
				if (zdz.length() > 2){
					tempMap.put(cssz.getZdm(), zdz.substring(1, zdz.length()-1));
				}
			} else {
				tempMap.put(cssz.getZdm(), parameters.get(cssz.getZdm())[0]);
			}
		}
		
		getContext().publishEvent(new XtcsUpdateEvent( this , tempMap ));
		
		return true;
	} 
	
	
	@Override
	public void setValue(String key, String value) {
		CsszModel csszModel = new CsszModel();
		csszModel.setZdm(key);
		csszModel.setZdz(value);
		update(csszModel);
		//刷新缓存
		refreshCache();
	}
	
	@Override
	public void refreshCache() {
		//缓存设置
		if(isCacheSupport()){
			Cache cache = this.getCache();
			//系统参数缓存
			cache.evict(getCacheKey());
			cache.put(getCacheKey(), dao.getModelList(CSSZ_MODEL));
		}
	}

	@Override
	public String getCacheName() {
		return DEFAULT_CACHE_NAME;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	
	@Override
	public boolean isCacheSupport() {
		return true;
	}
	
}
