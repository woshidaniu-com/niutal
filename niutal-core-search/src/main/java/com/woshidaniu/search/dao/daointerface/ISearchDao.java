package com.woshidaniu.search.dao.daointerface;

import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import com.woshidaniu.search.dao.entities.DataSourceModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchLinkageModel;

@Repository(value="superSearchDao")
public interface ISearchDao {

	
	/**
	 * 根据查询标志查询该业务所有的查询条件
	 * @param searchSign
	 * @return
	 */
	public List<SearchConfigModel> getSearchConfigList(SearchConfigModel config);
	
	
	
	/**
	 * 查询单个字段的配置
	 * @param config
	 * @return
	 */
	public SearchConfigModel getSearchConfig(SearchConfigModel config);
	
	
	
	
	/**
	 * 根据查询标志查询该业务所有条件的联动情况
	 * @param linkageModel
	 * @return
	 */
	public List<SearchLinkageModel> getLinkageList(SearchLinkageModel linkageModel);
	
	
	
	/**
	 * 查询高级查询中数据源信息
	 * @param sourceTable
	 * @return
	 */
	public DataSourceModel getDataSourceInfo(String sourceTable);
	
	
	
	
	/**
	 * 查询数据源
	 * @param model
	 * @return
	 */
	public List<DataSourceModel> getInitialList(DataSourceModel model);
	
	
	
	
	/**
	 * 查询数据源
	 * @param model
	 * @return
	 */
	public List<DataSourceModel> getInitialListByScope(DataSourceModel model);
	
	
	
	/**
	 * 查询联动数据
	 * @param map
	 * @return
	 */
	public List<DataSourceModel> getLinkageData(Map<String,Object> map);
	
	
	/**
	 * 查询联动数据(带数据范围)
	 * @param map
	 * @return
	 */
	public List<DataSourceModel> getLinkageDataByScope(Map<String,Object> map);
}
