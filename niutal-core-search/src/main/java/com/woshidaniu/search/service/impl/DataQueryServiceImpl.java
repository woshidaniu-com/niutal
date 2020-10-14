/**
 * 
 */
package com.woshidaniu.search.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.search.dao.daointerface.IDataDao;
import com.woshidaniu.search.dao.entities.InitialLabelCount;
import com.woshidaniu.search.dao.entities.LinkageQueryModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchItemModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;
import com.woshidaniu.search.dao.entities.SourceDataItem;
import com.woshidaniu.search.data.factory.ValueSourceQuerierFactory;
import com.woshidaniu.search.data.query.AbstractValueSourceQuerier;
import com.woshidaniu.search.service.SearchConfigCache;
import com.woshidaniu.search.service.svcinterface.IDataQueryService;

/**
 * 高级查询数据查询服务接口实现
 * 
 * @author xiaokang
 *
 */
public class DataQueryServiceImpl implements IDataQueryService {
	static final Logger LOG = LoggerFactory.getLogger(DataQueryServiceImpl.class);
	private IDataDao searchDataDao;
	private SearchConfigCache configCache;
	
	@Override
	public List<SearchItemModel> queryInitialData(
			List<SearchConfigModel> configList) {
		if(configList == null || configList.size() == 0)
			return null;
		List<SearchItemModel> queryResult = new ArrayList<SearchItemModel>();
		for (SearchConfigModel searchConfigModel : configList) {
			String vs = searchConfigModel.getValueSource();
			if(StringUtils.isBlank(vs))
				continue;
			else{
				String method = StringUtils.substringBefore(vs, VALUE_SOURCE_SEPARATOR);
				String source = StringUtils.substringAfter(vs, VALUE_SOURCE_SEPARATOR);
				AbstractValueSourceQuerier instance = ValueSourceQuerierFactory.getInstance(method, new Object[]{searchConfigModel , method , source});
				if(instance == null){
					continue;
				}
				//调用查询初始化数据
				List<SourceDataItem> queryInitData = instance.queryInitData();
				Integer dataTotalCount = instance.getDataTotalCount();
				
				SearchItemModel itemModel = new SearchItemModel();
				itemModel.setSearchName(searchConfigModel.getSearchName());
				itemModel.setCurrentCount(queryInitData.size());
				itemModel.setHasMore(dataTotalCount > queryInitData.size());
				itemModel.setInitialCount(NumberUtils.createInteger(searchConfigModel.getMaxRows()));
				itemModel.setSourceDataItemList(queryInitData);
				itemModel.setTotalCount(dataTotalCount);
				queryResult.add(itemModel);
			}
		}
		return queryResult;
	}

	@Override
	public List<SearchItemModel> queryInitialData(
			List<SearchConfigModel> configList,
			Map<String, Collection<String>> linkageQueryData) {
		if(configList == null || configList.size() == 0)
			return null;
		List<SearchItemModel> queryResult = new ArrayList<SearchItemModel>();
		for (SearchConfigModel searchConfigModel : configList) {
			String vs = searchConfigModel.getValueSource();
			String searchSign = searchConfigModel.getSearchSign();
			String searchName = searchConfigModel.getSearchName();
			if(StringUtils.isBlank(vs))
				continue;
			else{
				//查询该影响该字段查询结果所有其他字段集合
				List<SearchLinkageModel> allLinkageList = configCache.getLinkageConfig(searchSign, searchName);
				List<LinkageQueryModel> queryParam = new ArrayList<LinkageQueryModel>();
				//提取出联动字段的值
				for (SearchLinkageModel linkage : allLinkageList) {
					Collection<String> collection = linkageQueryData.get(linkage.getLinkageName());
					if(collection != null && collection.size() > 0){
						LinkageQueryModel v = new LinkageQueryModel();
						v.setLinkageModel(linkage);
						v.setLinkageValue(collection);
						queryParam.add(v);
					}
				}
				
				String method = StringUtils.substringBefore(vs, VALUE_SOURCE_SEPARATOR);
				String source = StringUtils.substringAfter(vs, VALUE_SOURCE_SEPARATOR);
				AbstractValueSourceQuerier instance = ValueSourceQuerierFactory.getInstance(method, new Object[]{searchConfigModel , method , source});
				if(instance == null){
					continue;
				}
				//调用查询初始化数据
				List<SourceDataItem> queryInitData = instance.queryInitData(queryParam);
				Integer dataTotalCount = instance.getDataTotalCount(queryParam);
				
				SearchItemModel itemModel = new SearchItemModel();
				itemModel.setSearchName(searchConfigModel.getSearchName());
				itemModel.setCurrentCount(queryInitData == null ? 0 : queryInitData.size());
				itemModel.setHasMore(dataTotalCount > (queryInitData == null ? 0 : queryInitData.size()));
				itemModel.setInitialCount(NumberUtils.createInteger(searchConfigModel.getMaxRows()));
				itemModel.setSourceDataItemList(queryInitData);
				itemModel.setTotalCount(dataTotalCount);
				queryResult.add(itemModel);
			}
		}
		return queryResult;
	}	
	
	
	@Override
	public List<SearchItemModel> queryDefaultConditionlData(
			List<SearchConfigModel> configList,
			Map<String, Collection<String>> defaultCondition) {
		if(configList == null || configList.size() == 0){
			return null;
		}
		if(defaultCondition == null || defaultCondition.size() == 0){
			return null;
		}
		List<SearchItemModel> queryResult = new ArrayList<SearchItemModel>();
		Set<String> keySet = defaultCondition.keySet();
		for (String searchName : keySet) {
			Collection<String> collection = defaultCondition.get(searchName);
			SearchConfigModel searchConfigModel = configCache.getSearchConfigModel(configList, searchName);	
			String vs = searchConfigModel.getValueSource();
			String method = StringUtils.substringBefore(vs, VALUE_SOURCE_SEPARATOR);
			String source = StringUtils.substringAfter(vs, VALUE_SOURCE_SEPARATOR);
			AbstractValueSourceQuerier instance = ValueSourceQuerierFactory.getInstance(method, new Object[]{searchConfigModel , method , source});
			if(instance == null){
				continue;
			}
			List<SourceDataItem> defaultConditionData = instance.getDefaultConditionData((List<String>) collection);
			
			if(defaultConditionData == null || defaultConditionData.size() == 0){
				continue;
			}
			
			SearchItemModel itemModel = new SearchItemModel();
			itemModel.setSearchName(searchName);
			itemModel.setSourceDataItemList(defaultConditionData);
			queryResult.add(itemModel);
		}
		
		return queryResult;
	}
	
	
	@Override
	public List<SearchItemModel> getLinkageDataListMap(
			SearchConfigModel configModel,
			Map<String, Collection<String>> linkageQueryData) {
 		if(configModel == null)
			return null;
		
 		List<SearchItemModel> queryResult = new ArrayList<SearchItemModel>();
 		
		String searchSign = configModel.getSearchSign();
		//需要被联动查询的字段集合
		List<String> linkageList = configModel.getLinkageList();
		//循环查询被联动字段的值列表
		for (String string : linkageList) {
			//被联动字段配置对象
			SearchConfigModel nextNameConfig = configCache.getSearchConfigBySearchSignAndSearchName(searchSign, string);
			String vs = nextNameConfig.getValueSource();
			String method = StringUtils.substringBefore(vs, VALUE_SOURCE_SEPARATOR);
			String source = StringUtils.substringAfter(vs, VALUE_SOURCE_SEPARATOR);
			AbstractValueSourceQuerier nextInstance = ValueSourceQuerierFactory.getInstance(method, new Object[]{nextNameConfig , method , source});
			//查询该影响该字段查询结果所有其他字段集合
			List<SearchLinkageModel> allLinkageList = configCache.getLinkageConfig(searchSign, string);
			List<LinkageQueryModel> queryParam = new ArrayList<LinkageQueryModel>();
			//提取出联动字段的值
			for (SearchLinkageModel linkage : allLinkageList) {
				Collection<String> collection = linkageQueryData.get(linkage.getLinkageName());
				if(collection != null && collection.size() > 0){
					LinkageQueryModel v = new LinkageQueryModel();
					v.setLinkageModel(linkage);
					v.setLinkageValue(collection);
					queryParam.add(v);
				}
			}
			//初始化数据查询,待联动参数的查询
			List<SourceDataItem> queryInitDataWithLinkageValue = nextInstance.queryInitData(queryParam);
			Integer dataTotalCount = nextInstance.getDataTotalCount(queryParam);
			SearchItemModel itemModel = new SearchItemModel();
			itemModel.setSearchName(nextNameConfig.getSearchName());
			itemModel.setCurrentCount(queryInitDataWithLinkageValue.size());
			itemModel.setHasMore(dataTotalCount > queryInitDataWithLinkageValue.size());
			itemModel.setInitialCount(NumberUtils.createInteger(nextNameConfig.getMaxRows()));
			itemModel.setSourceDataItemList(queryInitDataWithLinkageValue);
			itemModel.setTotalCount(dataTotalCount);
			queryResult.add(itemModel);
		}
		
		return queryResult;
	}
	
	@Override
	public SearchItemModel queryMoreData(SearchConfigModel configModel, Map<String, Collection<String>> linkageQueryData) {
		if(configModel == null) 
			return null;
		String vs = configModel.getValueSource();
		String method = StringUtils.substringBefore(vs, VALUE_SOURCE_SEPARATOR);
		String source = StringUtils.substringAfter(vs, VALUE_SOURCE_SEPARATOR);
		String searchSign = configModel.getSearchSign();
		String searchName = configModel.getSearchName();
		//查询该影响该字段查询结果所有其他字段集合
		List<SearchLinkageModel> allLinkageList = configCache.getLinkageConfig(searchSign, searchName);
		List<LinkageQueryModel> queryParam = new ArrayList<LinkageQueryModel>();
		//提取出联动字段的值
		for (SearchLinkageModel linkage : allLinkageList) {
			Collection<String> collection = linkageQueryData.get(linkage.getLinkageName());
			if(collection != null && collection.size() > 0){
				LinkageQueryModel v = new LinkageQueryModel();
				v.setLinkageModel(linkage);
				v.setLinkageValue(collection);
				queryParam.add(v);
			}
		}
		
		
		LOG.debug(configModel.toString());
		AbstractValueSourceQuerier instance = ValueSourceQuerierFactory.getInstance(method, new Object[]{configModel , method , source});
		List<SourceDataItem> queryInitData = instance.queryMoreData(queryParam);
		
		SearchItemModel itemModel = new SearchItemModel();
		itemModel.setSearchName(configModel.getSearchName());
		itemModel.setSourceDataItemList(queryInitData);
		return itemModel;
	}
	
	@Override
	public SearchItemModel queryMoreDataWithInitalLabel(
			SearchConfigModel configModel , String initalLabelValue, Map<String, Collection<String>> linkageQueryData) {
		String vs = configModel.getValueSource();
		String method = StringUtils.substringBefore(vs, VALUE_SOURCE_SEPARATOR);
		String source = StringUtils.substringAfter(vs, VALUE_SOURCE_SEPARATOR);
		String searchSign = configModel.getSearchSign();
		String searchName = configModel.getSearchName();
		//查询该影响该字段查询结果所有其他字段集合
		List<SearchLinkageModel> allLinkageList = configCache.getLinkageConfig(searchSign, searchName);
		List<LinkageQueryModel> queryParam = new ArrayList<LinkageQueryModel>();
		//提取出联动字段的值
		for (SearchLinkageModel linkage : allLinkageList) {
			Collection<String> collection = linkageQueryData.get(linkage.getLinkageName());
			if(collection != null && collection.size() > 0){
				LinkageQueryModel v = new LinkageQueryModel();
				v.setLinkageModel(linkage);
				v.setLinkageValue(collection);
				queryParam.add(v);
			}
		}
		AbstractValueSourceQuerier instance = ValueSourceQuerierFactory.getInstance(method, new Object[]{configModel , method , source});
		instance.setInitalLabelValue(initalLabelValue);
		List<SourceDataItem> queryMoreDataWithInitalLabel = instance.queryMoreDataWithInitalLabel(queryParam);
		SearchItemModel itemModel = new SearchItemModel();
		itemModel.setSearchName(configModel.getSearchName());
		itemModel.setSourceDataItemList(queryMoreDataWithInitalLabel);
		return itemModel;
	}
	
	@Override
	public List<InitialLabelCount> queryIntialLabelCount(
			SearchConfigModel configModel , Map<String, Collection<String>> linkageQueryData) {
		String vs = configModel.getValueSource();
		String method = StringUtils.substringBefore(vs, VALUE_SOURCE_SEPARATOR);
		String source = StringUtils.substringAfter(vs, VALUE_SOURCE_SEPARATOR);
		String searchSign = configModel.getSearchSign();
		String searchName = configModel.getSearchName();
		//查询该影响该字段查询结果所有其他字段集合
		List<SearchLinkageModel> allLinkageList = configCache.getLinkageConfig(searchSign, searchName);
		List<LinkageQueryModel> queryParam = new ArrayList<LinkageQueryModel>();
		//提取出联动字段的值
		for (SearchLinkageModel linkage : allLinkageList) {
			Collection<String> collection = linkageQueryData.get(linkage.getLinkageName());
			if(collection != null && collection.size() > 0){
				LinkageQueryModel v = new LinkageQueryModel();
				v.setLinkageModel(linkage);
				v.setLinkageValue(collection);
				queryParam.add(v);
			}
		}
		AbstractValueSourceQuerier instance = ValueSourceQuerierFactory.getInstance(method, new Object[]{configModel , method , source});
		List<InitialLabelCount> initalLabelCount = instance.getInitalLabelCount(queryParam);
		return initalLabelCount;
	}
	
	//===============================================================//
	public IDataDao getSearchDataDao() {
		return searchDataDao;
	}

	public void setSearchDataDao(IDataDao searchDataDao) {
		this.searchDataDao = searchDataDao;
	}

	public SearchConfigCache getConfigCache() {
		return configCache;
	}

	public void setConfigCache(SearchConfigCache configCache) {
		this.configCache = configCache;
	}

	
}
