package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.woshidaniu.api.event.XtcsUpdateEvent;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.TimeUtils;
import com.woshidaniu.cache.core.annotation.CacheExpire;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.constant.GlobalCacheKeyConstant;
import com.woshidaniu.daointerface.IXtszDao;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.XtszModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IXtszService;
import com.woshidaniu.service.utils.FixedStringUtils;
import com.woshidaniu.util.xml.BaseData;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 *@类名称:XtszServiceImpl.java
 *@类描述：系统设置 实现
 *@创建人：wangjiayong
 *@创建时间：2015-1-16 下午04:58:54
 *@版本号:v1.0
 *@修改人：kangzhidong
 *@修改备注：优化系统参数取值规则和方式，更改页面数据解析方式
 */
@After
@Logger(model="N01",business="N000001")
@SuppressWarnings("unchecked")
@Service
public class XtszServiceImpl extends CommonBaseServiceImpl<XtszModel, IXtszDao> implements IXtszService {
	
	@Resource
	private IXtszDao dao;
	
	protected static final String DEFAULT_CACHE_NAME = "niutal_XTSZ";
	protected static final String DEFAULT_CACHE_KEY = "niutal_XTSZ_KEY";
	protected String cacheKey = DEFAULT_CACHE_KEY;
	protected XtszModel XTSZ_MODEL = new XtszModel();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	/**
	 * @see  {@link com.woshidaniu.comp.xtgl.xtsz.service.XtszService#cxNdlb()}.
	 */
	public List cxNdlb(String[][] params) {
		List list = new ArrayList();
		long year = Long.parseLong(TimeUtils.getYear()) ;
		for (int i = -5; i < 5; i++) {
			Map param = new HashMap();

			String text = (year + i) + "";
			String value = text;
			param.put("text", text);
			param.put("value", value);
			list.add(param);
		}

		return list;
	}

	/**
	 * @see  {@link com.woshidaniu.comp.xtgl.xtsz.service.XtszService#cxXnlb()}.
	 */
	public List cxXnlb(String[][] params)
			{
		List list = new ArrayList();
		long year = Long.parseLong(TimeUtils.getYear()) ;
		
		for (int i = -5; i < 5; i++) {
			Map param = new HashMap();
			
			String text = (year + i) + "-" + (year + i + 1);
			String value = text;
			param.put("text", text);
			param.put("value", value);
			list.add(param);
		}

		return list;
	}
	
	/**
	 * 
	 *@描述：匹配设置项
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-20下午02:45:30
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param list
	 *@param zs
	 *@return
	 */
	private List<XtszModel> matchList(List<XtszModel> list,String zs){
		if(!BlankUtils.isBlank(list) && !BlankUtils.isBlank(zs)){
			List<XtszModel> tmpList = new ArrayList<XtszModel>();
			for (XtszModel xtszModel : list) {
				if(xtszModel.getZs().contains(zs)){
					tmpList.add(xtszModel);
				}
			}
			return tmpList;
		}else{
			return list;
		}
		
	}
	
	public List<XtszModel> getModelList(XtszModel model){
		//缓存服务器开启
		List<XtszModel> list = dao.getModelList(model);
		if(!BlankUtils.isBlank(list)){
			//匹配设置项
			list = matchList(list,model.getZs());
/*			//排序
			Collections.sort(list,comparator);
*/			
			 //解析参数字段数据来源
			 List<PairModel> zdsjList = null; 
			 String zdly = null;
			 for (XtszModel xtszModel : list) {
				 zdly = xtszModel.getZdly();
				 //指定数据来源
				 if(!BlankUtils.isBlank(zdly)){
					 if(zdly.startsWith("database:")){
						//数据查询SQL
						zdly = zdly.substring("database:".length());
						if(!BlankUtils.isBlank(zdly)){
							zdsjList =  dao.getZdsjList(zdly);
						}
					 }else if(zdly.startsWith("basedata:")){
						 //基础数据
						 zdly = zdly.substring("basedata:".length());
						 if(!BlankUtils.isBlank(zdly)){
							 List<BaseData> baseData = BaseDataReader.getCachedBaseDataList(zdly);
							 if(!BlankUtils.isBlank(baseData)){
								 zdsjList = new ArrayList<PairModel>();
								 for (BaseData baseData2 : baseData) {
									 zdsjList.add(new PairModel(baseData2.getKey(), baseData2.getValue()));
								 }
							 }
						 }
					 }else if(zdly.startsWith("fixed:")){
						 //固定选项
						 zdly = zdly.substring("fixed:".length());
						 zdsjList = FixedStringUtils.getFixedList(zdly,":");
					 }
					 xtszModel.setZdsjList(zdsjList);
					 //清空字段来源信息：防止页面看到
					 xtszModel.setZdly(null);
				 }
			 }
			 return list;
		 }
		 return new ArrayList<XtszModel>();
	}

	@Comment(model="N0105",business="N010510",recordSQL=true)
	public boolean insert(XtszModel model) {
		return dao.insert(model)>0?true:false;
	}
	
	@Comment(model="N0105",business="N010510",recordSQL=true)
	public boolean xgXxxxsz(XtszModel model) {
		return dao.xgXxxxsz(model)>0?true:false;
	}
	
	@Comment(recordSQL=true,recordDesc=false)
	@CacheExpire(keys={GlobalCacheKeyConstant.XTSZ_CACHEKEY,GlobalCacheKeyConstant.CURRENT_XNXQ,GlobalCacheKeyConstant.CJLR_XNXQ,
			GlobalCacheKeyConstant.BKCJLR_XNXQ})
	public void updateXtsz(XtszModel model){
		
		List<PairModel> zdsjList = model.getZdsjList();
		
		//日志记录需要参数组织
		List<String> disc = new ArrayList<String>();
		if(!BlankUtils.isBlank(zdsjList)){
			for (PairModel pairModel : zdsjList) {
				if ("DQXNM".equals(pairModel.getKey())) {
					model.setXnm(pairModel.getValue());
				} 
				
				if ("DQXQM".equals(pairModel.getKey())) {
					model.setXqm(pairModel.getValue());
				}
			}

			for (PairModel pairModel : zdsjList) {
				disc.add(pairModel.getKey() + "=" + pairModel.getValue());
				model.setZdm(pairModel.getKey());
				model.setZdz(pairModel.getValue());
				
				dao.update(model);
				
				if(isCacheSupport()){
					if("XKXNM".equalsIgnoreCase(pairModel.getKey())||"XKXQM".equalsIgnoreCase(pairModel.getKey())){
						Cache cache = this.getCache();
						cache.evict(GlobalCacheKeyConstant.XKXNXQ_CACHEKEY);
					}
				}
			}
			
			//推送系统参数更新事件
			
			//第一步：对修改过的参数进行整合处理
			Map<String, String> tempMap = new HashMap<String, String>();
			for (PairModel pairModel : zdsjList){
				tempMap.put(pairModel.getKey(), pairModel.getValue());
			}
			getContext().publishEvent(new XtcsUpdateEvent( this , tempMap ));
			
		}
		model.setDeleteList(disc);
		
		//刷新缓存设
		refreshCache();
		
	}
	
	@Override
	public void setValue(String key, String value) {
		XtszModel model = new XtszModel();
		model.setZdm(key);
		model.setZdz(value);
		dao.update(model);
		//刷新缓存
		refreshCache();
	}
	
	public void refreshCache() {
		//缓存设置
		if(isCacheSupport()){
			Cache cache = this.getCache();
			cache.evict(GlobalCacheKeyConstant.XTSZ_CACHEKEY);
			cache.evict(GlobalCacheKeyConstant.CURRENT_XNXQ);
			cache.evict(GlobalCacheKeyConstant.CJLR_XNXQ);
			cache.evict(GlobalCacheKeyConstant.BKCJLR_XNXQ);
			//系统参数缓存
			cache.evict(getCacheKey());
			cache.put(getCacheKey(), dao.getModelList(XTSZ_MODEL));
		}
	}
	
	
	@Override
	public String getValue(String zdm) {
		if(isCacheSupport()){
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(getCacheKey());
			if(valueWrapper != null){
				List<XtszModel> list = (List<XtszModel>) valueWrapper.get();
				if(list == null || list.isEmpty()){
					return dao.getValue(zdm);
				}
				for (XtszModel csszModel : list) {
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
	
	@Override
	public Map<String, String> getValues(String ssmk) {
		if(isCacheSupport()){
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(getCacheKey());
			if(valueWrapper != null){
				List<XtszModel> list = (List<XtszModel>) valueWrapper.get();
				if(list == null || list.isEmpty()){
					list = dao.getModelList(XTSZ_MODEL);
					cache.put(getCacheKey(), list);
				}
				return toMap(list, ssmk);
			} else {
				List<XtszModel> list = dao.getModelList(XTSZ_MODEL);
				cache.put(getCacheKey(), list);
				return toMap(list, ssmk);
			}
		}else{
			return toMap(dao.getModelList(XTSZ_MODEL), ssmk);
		}
	}
	
	protected Map<String,String> toMap(List<XtszModel> list,String ssmk){
		Map<String,String> map = new HashMap<String, String>();
		for (XtszModel model : list){
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
