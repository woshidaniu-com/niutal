/**
 * 
 */
package com.woshidaniu.search.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.search.dao.entities.DataSourceModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;

/**
 * @author 小康
 *
 * 高级查询配置服务
 *
 */
public interface IConfigService {

	/*
	 * 获取所有配置
	 */
	Map<String , List<SearchConfigModel>> getAllSearchConfigs(); 
	
	/*
	 * 根据查询标志获取配置信息
	 */
	List<SearchConfigModel> getSearchConfigList(String cxbz);
	
	/*
	 * 根据查询类型获取配置信息
	 */
	List<SearchConfigModel> getSearchConfigListByType(String cxbz , String cxlx);
	
	/*
	 * 获取所有联动配置
	 */
	List<SearchLinkageModel> getAllLinkageConfig();
	
	/*
	 * 根据条件获取某个联动配置
	 */
	SearchLinkageModel getLinkageConfig(String searchSign , String searchName , String nextName);
	
	/*
	 * 根据ID查询配置信息
	 */
	SearchConfigModel getSearchConfigModelById(String id);
	
	/*
	 * 获取所有数据源配置
	 */
	List<DataSourceModel> getAllDataSourceConfig();
	
	/*
	 * 获取数据源配置
	 */
	DataSourceModel getDataSourceConfig(String name);
	
}
