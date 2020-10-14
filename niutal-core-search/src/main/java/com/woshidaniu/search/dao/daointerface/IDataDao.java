/**
 * 
 */
package com.woshidaniu.search.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woshidaniu.search.dao.entities.InitialLabelCount;
import com.woshidaniu.search.dao.entities.SourceDataItem;



/**
 * 高级查询数据操作层
 * 
 * @author 小康
 *
 */
@Repository(value="searchDataDao")
public interface IDataDao {
	
	/*
	 * 根据条件获取数据
	 */
	List<SourceDataItem> getData(Map<String , Object> params);
	/*
	 * 根据条件获取数据  !!!!带数据范围控制!!!!
	 */
	List<SourceDataItem> getDataByScope(Map<String , Object> params);
	
	/*
	 * 根据条件获取数据 首字母模式
	 */
	List<SourceDataItem> getDataWithInitalLabel(Map<String , Object> params);
	
	/*
	 * 获取默认条件信息
	 */
	List<SourceDataItem> getDefaultConditionData();
	/*
	 * 获取默认条件信息  !!!!带数据范围控制!!!!
	 */
	List<SourceDataItem> getDefaultConditionDataByScope(Map<String , Object> params);
	
	/*
	 * 根据条件获取数据 首字母模式 !!!!带数据范围控制!!!!
	 */
	List<SourceDataItem> getDataWithInitalLabelByScope(Map<String , Object> params);
	
	
	/*
	 * 获取表记录总数
	 */
	Integer getDataTotalCount(Map<String , Object> params);
	/*
	 * 获取表记录总数  !!!!带数据范围控制!!!!
	 */
	Integer getDataTotalCountByScope(Map<String , Object> params);
	
	
	/*
	 * 获取首字母总计
	 */
	List<InitialLabelCount> getInitialLabelCount(Map<String , Object> params);
	
	/*
	 * 获取首字母总计  !!!!带数据范围控制!!!!
	 */
	List<InitialLabelCount> getInitialLabelCountByScope(Map<String , Object> params);
	
	
}
