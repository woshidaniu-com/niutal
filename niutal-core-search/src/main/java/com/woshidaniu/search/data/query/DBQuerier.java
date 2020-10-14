package com.woshidaniu.search.data.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.search.dao.daointerface.IDataDao;
import com.woshidaniu.search.dao.entities.DataSourceModel;
import com.woshidaniu.search.dao.entities.InitialLabelCount;
import com.woshidaniu.search.dao.entities.LinkageQueryModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;
import com.woshidaniu.search.dao.entities.SourceDataItem;
import com.woshidaniu.search.service.SearchConfigCache;

public class DBQuerier extends AbstractValueSourceQuerier {
	
	/*
	 * 配置缓存服务
	 */
	private  SearchConfigCache configCache = (SearchConfigCache) 
											ServiceFactory.getService("searchConfigCache");
	/*
	 * 数据查询dao
	 */
	private IDataDao dataDao = (IDataDao) ServiceFactory.getService("searchDataDao");

	@Override
	public List<SourceDataItem> queryDate() {
		return null;
	}

	@Override
	public List<SourceDataItem> queryInitData() {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		
		if(dataSource == null)
			return null;
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("maxRows", maxRows);
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		params.put("useInitial", getConfig().getUseInitial());
		List<SourceDataItem> queryInitDataByConfig = null;
			
		//TODO 这里做数据范围判断,如果需要数据范围,调用ByScope方式
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		if(checkNeedDataScopeControl){
			queryInitDataByConfig = dataDao.getDataByScope(params);
		}else{
			queryInitDataByConfig = dataDao.getData(params);
		}
		return queryInitDataByConfig;
	}
	
	@Override
	public List<SourceDataItem> queryInitData(List<LinkageQueryModel> linkageQueryData) {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		
		if(dataSource == null)
			return null;
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("maxRows", maxRows);
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		StringBuffer linkageSQL = createLinkageSql(linkageQueryData);
		if(StringUtils.isNotBlank(linkageSQL.toString())){
			params.put("linkageSQL", linkageSQL.toString());
		}
		/////////////////////////////////////////////////////////////////
		//TODO 这里做数据范围判断,如果需要数据范围,调用ByScope方式
		
		List<SourceDataItem> queryInitDataByConfig = null;
		
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		if(checkNeedDataScopeControl){
			queryInitDataByConfig = dataDao.getDataByScope(params);
		}else{
			queryInitDataByConfig = dataDao.getData(params);
		}

		return queryInitDataByConfig;
	}


	

	@Override
	public List<SourceDataItem> queryMoreData(List<LinkageQueryModel> linkageQueryData) {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		
		if(dataSource == null)
			return null;
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("maxRows", maxRows);
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		//标识该查询是更多查询
		params.put("isMore", "yes");
		//联动条件
		StringBuffer linkageSQL = createLinkageSql(linkageQueryData);
		if(StringUtils.isNotBlank(linkageSQL.toString())){
			params.put("linkageSQL", linkageSQL.toString());
		}
		List<SourceDataItem> queryInitDataByConfig = null;
		//TODO 这里做数据范围判断,如果需要数据范围,调用ByScope方式
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		if(checkNeedDataScopeControl){
			queryInitDataByConfig = dataDao.getDataByScope(params);
		}else{
			queryInitDataByConfig = dataDao.getData(params);
		}
		
		return queryInitDataByConfig;
	}
	
	@Override
	public List<SourceDataItem> queryMoreDataWithInitalLabel(List<LinkageQueryModel> linkageQueryData) {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		
		if(dataSource == null)
			return null;
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("maxRows", maxRows);
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		//首字母值
		params.put("initalLabelValue", getInitalLabelValue());
		//联动条件
		StringBuffer linkageSQL = createLinkageSql(linkageQueryData);
		if(StringUtils.isNotBlank(linkageSQL.toString())){
			params.put("linkageSQL", linkageSQL.toString());
		}
		//TODO 这里做数据范围判断,如果需要数据范围,调用ByScope方式
		List<SourceDataItem> queryInitDataByConfig = null;
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		if(checkNeedDataScopeControl){
			queryInitDataByConfig = dataDao.getDataWithInitalLabelByScope(params);
		}else{
			queryInitDataByConfig = dataDao.getDataWithInitalLabel(params);
		}
		
		return queryInitDataByConfig;
	}
	
	@Override
	public List<SourceDataItem> getDefaultConditionData(
			List<String> defaultConditionData) {
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		//字段配置
		SearchConfigModel config = getConfig();

		if(dataSource == null)
			return null;
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		params.put("defaultConditionData", defaultConditionData);
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		List<SourceDataItem> queryDefaultConditionData = null;
		if(checkNeedDataScopeControl){
			queryDefaultConditionData = dataDao.getDefaultConditionDataByScope(params);
		}else{
			queryDefaultConditionData = dataDao.getDefaultConditionData();
		}
		return queryDefaultConditionData;
	}

	
	
	@Override
	public Integer getDataTotalCount(){
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		if(dataSource == null)
			return 0;
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		Integer dataTotalCount = 0;
		//数据范围控制
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		if(checkNeedDataScopeControl){
			dataTotalCount = dataDao.getDataTotalCountByScope(params);
		}else{
			dataTotalCount = dataDao.getDataTotalCount(params);
		}
		
		return dataTotalCount;
	}
	
	@Override
	public Integer getDataTotalCount(List<LinkageQueryModel> linkageQueryData){
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		if(dataSource == null)
			return 0;
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		StringBuffer linkageSQL = createLinkageSql(linkageQueryData);
		if(StringUtils.isNotBlank(linkageSQL.toString())){
			params.put("linkageSQL", linkageSQL.toString());
		}
		Integer dataTotalCount = 0;
		
		//数据范围控制
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		if(checkNeedDataScopeControl){
			dataTotalCount = dataDao.getDataTotalCountByScope(params);
		}else{
			dataTotalCount = dataDao.getDataTotalCount(params);
		}
		
		return dataTotalCount;
	}
	
	
	@Override
	public List<InitialLabelCount> getInitalLabelCount(List<LinkageQueryModel> linkageQueryData ) {
		//查询数据表或试图对象
		DataSourceModel dataSource = configCache.getDataSource(getSource());
		if(dataSource == null)
			return null;
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("maxRows", maxRows);
		params.put("columnKey", dataSource.getColumnKey());
		params.put("columnLabel", dataSource.getColumnLabel());
		params.put("dataSource", dataSource.getDataSource());
		//联动条件
		StringBuffer linkageSQL = createLinkageSql(linkageQueryData);
		if(StringUtils.isNotBlank(linkageSQL.toString())){
			params.put("linkageSQL", linkageSQL.toString());
		}
		List<InitialLabelCount> initialLabelCount = null;
		//数据范围控制
		boolean checkNeedDataScopeControl = configCache.checkNeedDataScopeControl(dataSource.getDataSource());
		if(checkNeedDataScopeControl){
			initialLabelCount = dataDao.getInitialLabelCountByScope(params);
		}else{
			initialLabelCount = dataDao.getInitialLabelCount(params);
		}
		return initialLabelCount;
	}
	
	
	
	/**
	 * 生成联动查询sql语句
	 * @param linkageQueryData
	 * @return
	 */
	private StringBuffer createLinkageSql(
			List<LinkageQueryModel> linkageQueryData) {
		StringBuffer linkageSQL = new StringBuffer();
		/////////////////////////////////////////////////////////////////
		if(linkageQueryData != null || linkageQueryData.size() > 0){
			List<String> outterConditions = new ArrayList<String>();
			for (LinkageQueryModel linkageQueryModel : linkageQueryData) {
				List<String> innerConditions = new ArrayList<String>();
				Collection<String> linkageValue = linkageQueryModel.getLinkageValue();
				SearchLinkageModel linkageModel = linkageQueryModel.getLinkageModel();
				String relatingName = linkageModel.getRelatingName();
				for (String string : linkageValue) {
					String v1 = " (" + relatingName + " = '" + string + "') ";
					innerConditions.add(v1);
				}
				if(innerConditions.size() > 0){
					outterConditions.add(" (" + StringUtils.join(innerConditions, " or ") + ") ");
				}
			}
			
			if(outterConditions.size() > 0){
				linkageSQL.append(" (" + StringUtils.join(outterConditions, " and ") + " )");
			}
			
		}
		return linkageSQL;
	}
	//=================================================================//
	
	public DBQuerier() {
		super();
	}

	public DBQuerier(String method, String source) {
		super(method, source);
	}

	public DBQuerier(SearchConfigModel config, String method, String source) {
		super(config, method, source);
	}

	public SearchConfigCache getConfigCache() {
		return configCache;
	}

	public void setConfigCache(SearchConfigCache configCache) {
		this.configCache = configCache;
	}

	public IDataDao getDataDao() {
		return dataDao;
	}

	public void setDataDao(IDataDao dataDao) {
		this.dataDao = dataDao;
	}

}

