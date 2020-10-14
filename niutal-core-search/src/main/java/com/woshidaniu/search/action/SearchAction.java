package com.woshidaniu.search.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.search.dao.entities.InitialLabelCount;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchItemModel;
import com.woshidaniu.search.service.SearchConfigCache;
import com.woshidaniu.search.service.svcinterface.IDataQueryService;
import com.woshidaniu.search.service.svcinterface.ISearchService;
import com.woshidaniu.util.base.MessageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchAction extends BaseAction implements ModelDriven<SearchForm>  {

	private static Logger logger = LoggerFactory.getLogger(SearchAction.class);
	
	private static final long serialVersionUID = 1L;
	
	private SearchForm model = new SearchForm();
	
	private ISearchService searchService;
	
	private SearchConfigCache searchConfigCache;
	
	private IDataQueryService searchDataQueryService;
	
	public SearchForm getModel() {
		return model;
	}
	
	public void setSearchService(ISearchService searchService) {
		this.searchService = searchService;
	}

	/**
	 * 获取高级查询配置信息
	 * @return
	 */
	public String getConfig(){
		logger.info("获取高级查询配置信息:[配置ID：{}];" , this.model.getSearchSign());
		//从缓存中获取配置信息
		List<SearchConfigModel> searchConfig = searchConfigCache.getSearchConfig(this.model.getSearchSign());
		
		getValueStack().set(DATA, searchConfig);
		
		return DATA;
	}

	/**
	 * 获取高级查询初始化数据
	 * @return
	 */
	public String getInitData(){
		logger.info("获取高级查询初始化数据:[配置ID：{}];" , this.model.getSearchSign());
		String searchSign = getModel().getSearchSign();
		//如果配置了默认初始化的值,需要根据条件过滤数据
		String linkageValue = this.model.getLinkageValue();
		String message = null;
		List<SearchItemModel> queryInitialData = null;
		if(StringUtils.isBlank(searchSign)){
			message = MessageUtil.getText("SEARCH_INIT_ERROR", new Object[]{"无法找到配置信息"});
			JSONObject errorResult = new JSONObject();
			errorResult.put("error", message);
			getValueStack().set(DATA, errorResult.toString());
		}else{
			//从缓存中获取配置信息
			List<SearchConfigModel> searchConfig = searchConfigCache.getSearchConfig(this.model.getSearchSign());
			
			if(StringUtils.isNotBlank(linkageValue)){
				Map<String, Collection<String>> linkageQueryData = new HashMap<String, Collection<String>>();
				if(StringUtils.isNotBlank(linkageValue)){
					JSONObject jo = JSONObject.fromObject(linkageValue);
					Iterator keys = jo.keys();
					while(keys.hasNext()){
						String key = String.valueOf(keys.next());  
				        Collection<String> collection = JSONArray.toCollection(jo.getJSONArray(key), String.class);
				        linkageQueryData.put(key, collection);  
					}
				}
				queryInitialData = searchDataQueryService.queryInitialData(searchConfig, linkageQueryData);
			}else{
				queryInitialData = searchDataQueryService.queryInitialData(searchConfig);
			}
			getValueStack().set(DATA, queryInitialData);
		}
		return DATA;
	}
	
	/**
	 * 获取默认条件的数据
	 * @return
	 */
	public String getDefaultConditionData(){
		String searchSign = getModel().getSearchSign();
		String defaultCondition = getModel().getDefaultCondition();
		String message = null;
		if(StringUtils.isBlank(searchSign)){
			message = MessageUtil.getText("SEARCH_INIT_ERROR", new Object[]{"无法找到配置信息"});
			JSONObject errorResult = new JSONObject();
			errorResult.put("error", message);
			getValueStack().set(DATA, errorResult.toString());
		}else{
			//从缓存中获取配置信息
			List<SearchConfigModel> searchConfig = searchConfigCache.getSearchConfig(this.model.getSearchSign());
			Map<String, Collection<String>> defaultConditionMap = new HashMap<String, Collection<String>>();
			if(StringUtils.isNotBlank(defaultCondition)){
				JSONArray jo = JSONArray.fromObject(defaultCondition);
				Iterator iterator = jo.iterator();
				while(iterator.hasNext()){
					JSONObject next = (JSONObject)iterator.next();
					String searchName = (String) next.get("cxzd");
					JSONArray values = next.getJSONArray("defaultValue");
					Collection<String> valueList = new ArrayList<String>();
					Iterator valueIt = values.iterator();
					while(valueIt.hasNext()){
						String val = (String)valueIt.next();
						valueList.add(val);
					}
					if(valueList.size() > 0){
						defaultConditionMap.put(searchName, valueList);
					}
				}
			}
			List<SearchItemModel> queryDefaultConditionlData = searchDataQueryService.queryDefaultConditionlData(searchConfig , defaultConditionMap);
			getValueStack().set(DATA, queryDefaultConditionlData);
		}
		return DATA;
	}
	
	/**
	 * 獲取高級查詢跟多數據
	 * @return
	 */
	public String getMoreData(){
		logger.info("获取高级查询更多数据:[配置ID：{}];" , this.model.getSearchSign());
		String searchSign = getModel().getSearchSign();
		String searchName = getModel().getSearchName();
		String linkageValue = this.model.getLinkageValue();
		String message = null;
		if(StringUtils.isBlank(searchSign) || StringUtils.isBlank(searchName)){
			message = MessageUtil.getText("SEARCH_INIT_ERROR", new Object[]{"无法找到配置信息"});
			JSONObject errorResult = new JSONObject();
			errorResult.put("error", message);
			getValueStack().set(DATA, errorResult.toString());
		}else{
			//从缓存中获取配置信息
			SearchConfigModel searchConfigModel = 
					searchConfigCache.getSearchConfigBySearchSignAndSearchName(searchSign, searchName);
			
			Map<String, Collection<String>> linkageQueryData = new HashMap<String, Collection<String>>();
			if(StringUtils.isNotBlank(linkageValue)){
				JSONObject jo = JSONObject.fromObject(linkageValue);
				Iterator keys = jo.keys();
				while(keys.hasNext()){
					String key = String.valueOf(keys.next());  
			        Collection<String> collection = JSONArray.toCollection(jo.getJSONArray(key), String.class);
			        linkageQueryData.put(key, collection);  
				}
			}
		
			SearchItemModel queryMoreData = searchDataQueryService.queryMoreData(searchConfigModel , linkageQueryData);
			
			getValueStack().set(DATA, queryMoreData);
		}
		return DATA;
	}
	
	/**
	 * 如果配置的根据字母搜索，则获取跟多调用该方法
	 * @return
	 */
	public String getMoreDataWithIntialLabel(){
		logger.info("获取高级查询更多数据:[配置ID：{}];" , this.model.getSearchSign());
		String searchSign = getModel().getSearchSign();
		String searchName = getModel().getSearchName();
		String initalLabel = getModel().getInitalLabel();
		String linkageValue = this.model.getLinkageValue();
		String message = null;
		if(StringUtils.isBlank(searchSign) || StringUtils.isBlank(searchName)){
			message = MessageUtil.getText("SEARCH_INIT_ERROR", new Object[]{"无法找到配置信息"});
			JSONObject errorResult = new JSONObject();
			errorResult.put("error", message);
			getValueStack().set(DATA, errorResult.toString());
		}else{
			//从缓存中获取配置信息
			SearchConfigModel searchConfigModel = 
					searchConfigCache.getSearchConfigBySearchSignAndSearchName(searchSign, searchName);
			Map<String, Collection<String>> linkageQueryData = new HashMap<String, Collection<String>>();
			if(StringUtils.isNotBlank(linkageValue)){
				JSONObject jo = JSONObject.fromObject(linkageValue);
				Iterator keys = jo.keys();
				while(keys.hasNext()){
					String key = String.valueOf(keys.next());  
			        Collection<String> collection = JSONArray.toCollection(jo.getJSONArray(key), String.class);
			        linkageQueryData.put(key, collection);  
				}
			}
			SearchItemModel queryMoreData = searchDataQueryService.queryMoreDataWithInitalLabel(searchConfigModel , initalLabel , linkageQueryData);
			getValueStack().set(DATA, queryMoreData);
		}
		return DATA;
	}
	
	/**
	 * 获取首字母记录数
	 * @return
	 */
	public String getInitalLabelCountData(){
		String searchSign = getModel().getSearchSign();
		String searchName = getModel().getSearchName();
		String linkageValue = this.model.getLinkageValue();
		String message = null;
		if(StringUtils.isBlank(searchSign) || StringUtils.isBlank(searchName)){
			message = MessageUtil.getText("SEARCH_INIT_ERROR", new Object[]{"无法找到配置信息"});
			JSONObject errorResult = new JSONObject();
			errorResult.put("error", message);
			getValueStack().set(DATA, errorResult.toString());
		}else{
			//从缓存中获取配置信息
			SearchConfigModel searchConfigModel = 
					searchConfigCache.getSearchConfigBySearchSignAndSearchName(searchSign, searchName);
			Map<String, Collection<String>> linkageQueryData = new HashMap<String, Collection<String>>();
			if(StringUtils.isNotBlank(linkageValue)){
				JSONObject jo = JSONObject.fromObject(linkageValue);
				Iterator keys = jo.keys();
				while(keys.hasNext()){
					String key = String.valueOf(keys.next());  
			        Collection<String> collection = JSONArray.toCollection(jo.getJSONArray(key), String.class);
			        linkageQueryData.put(key, collection);  
				}
			}
			List<InitialLabelCount> queryIntialLabelCount = searchDataQueryService.queryIntialLabelCount(searchConfigModel , linkageQueryData);
			SearchItemModel queryMoreDataWithInitalLabel = null;
			String columnInitialValue = null;
			if(queryIntialLabelCount != null && queryIntialLabelCount.size() > 0){
				InitialLabelCount initialLabelCount = queryIntialLabelCount.get(0);
				columnInitialValue = initialLabelCount.getColumnInitialValue();
				queryMoreDataWithInitalLabel = searchDataQueryService.queryMoreDataWithInitalLabel(searchConfigModel, columnInitialValue ,linkageQueryData);
			}
			Map<String , Object> value = new HashMap<String, Object>();
			value.put("initialLabelCount", queryIntialLabelCount);
			value.put("moreData", queryMoreDataWithInitalLabel);
			value.put("initialLabelValue", columnInitialValue);
			getValueStack().set(DATA, value);
		}
		return DATA;
	}
	
	/**
	 * 获取联动数据
	 * @return
	 */
	public String getLinkageData(){
		String searchSign = this.model.getSearchSign();
		String searchName = this.model.getLinkageName();
		String linkageValue = this.model.getLinkageValue();
		
		String message = null;
		if(StringUtils.isBlank(searchSign) || StringUtils.isBlank(searchName)){
			message = MessageUtil.getText("SEARCH_INIT_ERROR", new Object[]{"无法找到配置信息"});
			JSONObject errorResult = new JSONObject();
			errorResult.put("error", message);
			getValueStack().set(DATA, errorResult.toString());
		}else{
			//从缓存中获取配置信息
			SearchConfigModel searchConfigModel = 
					searchConfigCache.getSearchConfigBySearchSignAndSearchName(searchSign, searchName);
			Map<String, Collection<String>> linkageQueryData = new HashMap<String, Collection<String>>();
			
			JSONObject jo = JSONObject.fromObject(linkageValue);
			
			Iterator keys = jo.keys();
			
			while(keys.hasNext()){
				String key = String.valueOf(keys.next());  
		        Collection<String> collection = JSONArray.toCollection(jo.getJSONArray(key), String.class);
		        linkageQueryData.put(key, collection);  
			}
			
			List<SearchItemModel> linkageDataListMap = searchDataQueryService.getLinkageDataListMap(searchConfigModel, linkageQueryData);
		
			getValueStack().set(DATA, linkageDataListMap);
		}
		return DATA;
	}
	
	/*public String getLinkageContent(){
		
		
		 * 查找影响该字段的关联字段，如：班级被年级、学院、专业关联
		 * 这里查询的是关联关系中的年级、学院、专业
		 
		List<SearchLinkageModel> linkageList =	searchService.getLinkageList(model);
		HttpServletRequest request = getRequest();
		
		List<SearchLinkageModel> linkageInfoList = new ArrayList<SearchLinkageModel>(); 
		
		for (SearchLinkageModel linkageModel : linkageList){
			String linkageValue = request.getParameter(linkageModel.getLinkageName());
			linkageModel.setLinkageValue(linkageValue);
			linkageModel.setSearchSign(model.getSearchSign());
			linkageInfoList.add(linkageModel);
		}
		
		SearchConfigModel config = new SearchConfigModel();
		config.setSearchSign(model.getSearchSign());
		config.setSearchName(model.getNextName());
		
		try {
			int pageNumber = StringUtil.isBlank(model.getPageNumber()) ? 0 : Integer.valueOf(model.getPageNumber());
			
			String linkageContent = searchService.buildLinkageContent(config, linkageList,pageNumber);
			getValueStack().set(DATA, linkageContent);
			return DATA;
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	
	public String getMoreOptions() {
		try {
			String html = searchService.getMoreOptions(model);
			getValueStack().set(DATA, html);
			return DATA;
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}*/

	public SearchConfigCache getSearchConfigCache() {
		return searchConfigCache;
	}

	public void setSearchConfigCache(SearchConfigCache searchConfigCache) {
		this.searchConfigCache = searchConfigCache;
	}

	public IDataQueryService getSearchDataQueryService() {
		return searchDataQueryService;
	}

	public void setSearchDataQueryService(
			IDataQueryService searchDataQueryService) {
		this.searchDataQueryService = searchDataQueryService;
	}
	
	public static void main(String[] args) {
		String json = "{\"bmdm\":[\"01020000\",\"501000\",\"01030000\"],\"nj\":[\"2003\"]}";
		JSONObject jo = JSONObject.fromObject(json);
		
	}
}
