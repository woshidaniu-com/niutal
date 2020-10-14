/**
 * 
 */
package com.woshidaniu.search.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woshidaniu.search.dao.entities.DataSourceModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;

/**
 * @author 小康
 * 高级查询配置Dao
 */
@Repository(value="configDao")
public interface IConfigDao {

	/*
	 * 根据查询标志获取配置信息
	 */
	List<SearchConfigModel> getSearchConfigList(String cxbz);

	/*
	 * 获取所有配置代码
	 */
	List<String> getConfigKeyDistinct();
	
	/*
	 * 根据查询类型获取配置信息
	 */
	List<SearchConfigModel> getSearchConfigListByType(Map<String , String> params);
	
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
	
	/*
	 * 获取所有联动配置
	 */
	List<SearchLinkageModel> getAllLinkageConfig();
	
	/*
	 * 根据条件获取某个联动配置
	 */
	SearchLinkageModel getLinkageConfig(Map<String , String> params);
	
	
}
