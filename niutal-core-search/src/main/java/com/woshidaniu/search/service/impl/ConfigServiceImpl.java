/**
 * 
 */
package com.woshidaniu.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.woshidaniu.search.dao.daointerface.IConfigDao;
import com.woshidaniu.search.dao.entities.DataSourceModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;
import com.woshidaniu.search.service.svcinterface.IConfigService;

/**
 * 高级查询配置服务接口实现
 * 
 * @author 小康
 *
 */
@Service(value="configService")
public class ConfigServiceImpl implements IConfigService {

	@Resource
	private IConfigDao configDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.IConfigService#getAllSearchConfigs()
	 */
	@Override
	public Map<String, List<SearchConfigModel>> getAllSearchConfigs() {
		Map<String, List<SearchConfigModel>> val = new HashMap<String, List<SearchConfigModel>>();
		List<String> configKeyDistinct = configDao.getConfigKeyDistinct();
		for (String string : configKeyDistinct) {
			List<SearchConfigModel> searchConfigList = configDao.getSearchConfigList(string);
			val.put(string, searchConfigList);
		}
		return val;
	}
	
	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.IConfigService#getSearchConfigList(java.lang.String)
	 */
	@Override
	public List<SearchConfigModel> getSearchConfigList(String searchSign) {
		Assert.notNull(searchSign , "Argument searchSign can not be NULL.");
		return configDao.getSearchConfigList(searchSign);
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.IConfigService#getSearchConfigListByType(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SearchConfigModel> getSearchConfigListByType(String searchSign,
			String searchType) {
		Assert.notNull(searchSign , "Argument searchSign can not be NULL.");
		Assert.notNull(searchType , "Argument searchType can not be NULL.");
		HashMap<String , String> params = new HashMap<String, String>();
		params.put("searchSign", searchSign);
		params.put("searchType", searchType);
		return configDao.getSearchConfigListByType(params);
	}

	@Override
	public List<SearchLinkageModel> getAllLinkageConfig() {
		
		return configDao.getAllLinkageConfig();
	}

	@Override
	public SearchLinkageModel getLinkageConfig(String searchSign,
			String searchName, String nextName) {
		Assert.notNull(searchSign , "Argument searchSign can not be NULL.");
		Assert.notNull(searchName , "Argument searchName can not be NULL.");
		Assert.notNull(nextName , "Argument nextName can not be NULL.");
		Map<String , String> params = new HashMap<String, String>();
		params.put(searchSign, searchSign);
		params.put(searchName, searchName);
		params.put(nextName, nextName);
		return configDao.getLinkageConfig(params);
	}
	
	/* (non-Javadoc)
	 * @see com.woshidaniu.search.service.svcinterface.IConfigService#getSearchConfigModelById(java.lang.String)
	 */
	@Override
	public SearchConfigModel getSearchConfigModelById(String id) {
		Assert.notNull(id , "Argument id can not be NULL.");
		return configDao.getSearchConfigModelById(id);
	}
	
	@Override
	public List<DataSourceModel> getAllDataSourceConfig() {
		return configDao.getAllDataSourceConfig();
	}
	
	@Override
	public DataSourceModel getDataSourceConfig(String name) {
		Assert.notNull(name , "Argument name can not be NULL.");
		return configDao.getDataSourceConfig(name);
	}
	
	public IConfigDao getConfigDao() {
		return configDao;
	}

	public void setConfigDao(IConfigDao configDao) {
		this.configDao = configDao;
	}

	

	
}
