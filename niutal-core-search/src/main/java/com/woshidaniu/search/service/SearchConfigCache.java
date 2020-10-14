/**
 * 
 */
package com.woshidaniu.search.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.woshidaniu.search.dao.entities.DataSourceModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;
import com.woshidaniu.search.service.svcinterface.IConfigService;

/**
 * 高级查询配置缓存
 * 
 * @author 小康 
 *
 */
public class SearchConfigCache {
	//启动加载所有配置
	static final String SCOPE_ALL = "all";
	//启动加载自定义
	static final String SCOPE_USER = "user";
	//启动不加载配置
	static final String SCOPE_NONE = "none";
	
	private static Logger logger = LoggerFactory.getLogger(SearchConfigCache.class);
	
	private String scope = SCOPE_USER;
	
	private Object lock = new Object();
	//配置缓存
	private Map<String , List<SearchConfigModel>> configMap = new HashMap<String, List<SearchConfigModel>>();
	//数据库表数据源配置缓存
	private Map<String , DataSourceModel> dataSourceMap = new HashMap<String, DataSourceModel>();
	//联动配置缓存
	private List<SearchLinkageModel> linkageConfigList = new ArrayList<SearchLinkageModel>();
	//需要数据范围控制的表
	private List<String> dataScopeControl = new ArrayList<String>();
	
	private IConfigService configService; 

	private String[] initConfigKeys;
	
	
	
	public SearchConfigCache() {
		super();
	}

	/**
	 * 判断是否需要做数据范围控制
	 * @param dataSource
	 * @return
	 */
	public boolean checkNeedDataScopeControl(String dataSource){
		logger.debug("高级查询配置:需要数据范围控制的表:[" + this.dataScopeControl + "]");
		
		if(dataSource == null || StringUtils.isBlank(dataSource)){
			return false;
		}
		
		if(this.dataScopeControl == null || this.dataScopeControl.size() == 0){
			return false;
		}
		
		return this.dataScopeControl.contains(dataSource);
	}
	
	/**
	 * 需要初始化的配置
	 * @param initConfigKeys
	 */
	public SearchConfigCache(String scope , String[] initConfigKeys) {
		super();
		if(!ArrayUtils.contains(new String[]{SCOPE_ALL, SCOPE_USER, SCOPE_NONE}, scope)){
			this.scope = SCOPE_NONE;
		}else{
			this.scope = scope;
		}
		this.initConfigKeys = initConfigKeys;
		//init(initConfigKeys);
	}

	/*
	 * 获取配置信息
	 */
	public List<SearchConfigModel> getSearchConfig(String configKey){
		Assert.notNull(configKey, "SearchConfigCache configKey can not be null.");
		if(!configMap.containsKey(configKey)){
			List<SearchConfigModel> searchConfigList = configService.getSearchConfigList(configKey);
			if(null != searchConfigList){
				//代码要同步
				synchronized (lock) {
					configMap.put(configKey, searchConfigList);
				}
			}
			return searchConfigList;
		}else{
			return configMap.get(configKey);
		}
	}
	
	/*
	 * 根據條件獲取單個配置
	 */
	public SearchConfigModel getSearchConfigBySearchSignAndSearchName(String searchSign , String searchName){
		SearchConfigModel searchConfigModel = null;
		Assert.notNull(searchSign, "SearchConfigCache searchSign can not be null.");
		Assert.notNull(searchName, "SearchConfigCache searchName can not be null.");
		List<SearchConfigModel> searchConfigList = null;
		if(configMap.containsKey(searchSign)){
			searchConfigList = configMap.get(searchSign);
		}else{
			searchConfigList = configService.getSearchConfigList(searchSign);
			if(null != searchConfigList){
				//代码要同步
				synchronized (lock) {
					configMap.put(searchSign, searchConfigList);
				}
			}
		}
		
		for (SearchConfigModel searchConfigModel2 : searchConfigList) {
			if(StringUtils.equals(searchConfigModel2.getSearchName(), searchName)){
				searchConfigModel = searchConfigModel2;
				break;
			}
		}

		return searchConfigModel;
	}
	
	public SearchConfigModel getSearchConfigModel(List<SearchConfigModel> SearchConfigList , String searchName){
		SearchConfigModel searchConfigModel = null;
		
		if(SearchConfigList == null || SearchConfigList.size() == 0){
			return null;
		}
		if(StringUtils.isBlank(searchName)){
			return null;
		}
		
		for (SearchConfigModel configItem : SearchConfigList) {
			if(StringUtils.equals(configItem.getSearchName(), searchName)){
				searchConfigModel = configItem;
				break;
			}
		}
		
		return searchConfigModel;
	}
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 获取联动配置
	 */
	public List<SearchLinkageModel> getLinkageConfig(String searchSign , String searchName){
		List<SearchLinkageModel> list = new ArrayList<SearchLinkageModel>();
		
		for (SearchLinkageModel element : linkageConfigList) {
			if(StringUtils.equals(element.getSearchSign(), searchSign) && StringUtils.equals(element.getNextName(), searchName)){
				list.add(element);
			}
		}
		return list;
	}
	
	/*
	 * 获取数据源配置
	 * 
	 */
	public DataSourceModel getDataSource(String name){
		Assert.notNull(name , "SearchConfigCache dataSource name can not be null.please check.");
        DataSourceModel dataSourceModel = this.dataSourceMap.get(name);
        if(dataSourceModel == null){
        	dataSourceModel = configService.getDataSourceConfig(name);
        }
        return dataSourceModel;
	}
	
	/*
	 * 清空缓存
	 */
	public synchronized void refresh(){
		if(!configMap.isEmpty()){
			configMap.clear();
		}
		init();
	}
	
	public void init(){
		logger.info("初始化高级查询缓存配置,默认初始化配置key:{}." ,  Arrays.toString(initConfigKeys));
		if(StringUtils.equals(scope, SCOPE_NONE)){
			return;
		}
		if(StringUtils.equals(scope, SCOPE_ALL)){
			this.configMap.putAll(configService.getAllSearchConfigs());
		}
		
		for (String string : initConfigKeys) {
			List<SearchConfigModel> searchConfigList = configService.getSearchConfigList(string);
			if(searchConfigList != null && searchConfigList.size() > 0){
				this.configMap.put(string, searchConfigList);
			}
		}
		logger.info("初始化高级查询缓存配置成功！共计:{}", configMap.size());
		
		logger.info("开始初始化高级查询数据库数据源配置");
		
		List<DataSourceModel> allDataSourceConfig = configService.getAllDataSourceConfig();
		if(null != allDataSourceConfig && allDataSourceConfig.size() > 0)
			//CollectionUtils.addAll(dataSourceList, allDataSourceConfig.iterator());
			for (DataSourceModel dataSourceModel : allDataSourceConfig) {
				this.dataSourceMap.put(dataSourceModel.getDataSource(), dataSourceModel);
			}
		logger.info("初始化高级查询数据库数据源配置完成");
		
		List<SearchLinkageModel> allLinkageConfig = configService.getAllLinkageConfig();
		this.linkageConfigList.addAll(allLinkageConfig);
		
		logger.info("初始化高级查询联动缓存配置完成![" + this.linkageConfigList.size() + "].");
	}


	public IConfigService getConfigService() {
		return configService;
	}


	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}


	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}


	public List<String> getDataScopeControl() {
		return dataScopeControl;
	}


	public void setDataScopeControl(List<String> dataScopeControl) {
		this.dataScopeControl = dataScopeControl;
	}

}
