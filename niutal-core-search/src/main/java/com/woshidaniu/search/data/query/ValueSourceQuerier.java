/**
 * 
 */
package com.woshidaniu.search.data.query;

import java.util.List;

import com.woshidaniu.search.dao.entities.InitialLabelCount;
import com.woshidaniu.search.dao.entities.LinkageQueryModel;
import com.woshidaniu.search.dao.entities.SourceDataItem;


/**
 * 数据源处理器
 * 
 * @author 小康
 *
 */
public interface ValueSourceQuerier {

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	List<SourceDataItem> queryDate();
	
	/**
	 * 获取页面初始化的数据
	 * @return
	 */
	List<SourceDataItem> queryInitData(List<LinkageQueryModel> linkageQueryData);
	
	/**
	 * 获取页面初始化的数据
	 * @return
	 */
	List<SourceDataItem> queryInitData();
	
	/**
	 * 获取记录总数
	 * @return
	 */
	Integer getDataTotalCount();
	
	/**
	 * 获取记录总数
	 * @return
	 */
	Integer getDataTotalCount(List<LinkageQueryModel> linkageQueryData);
	/**
	 * 获取更多数据
	 * @return
	 */
	List<SourceDataItem> queryMoreData(List<LinkageQueryModel> linkageQueryData);
	
	/**
	 * 获取更多数据 首字母方式
	 * @return
	 */
	List<SourceDataItem> queryMoreDataWithInitalLabel(List<LinkageQueryModel> linkageQueryData);
	
	/**
	 * 获取首字母记录总数
	 * @return
	 */
	List<InitialLabelCount> getInitalLabelCount(List<LinkageQueryModel> queryParam );

	/**
	 * 
	 * @return
	 */
	List<SourceDataItem> getDefaultConditionData(List<String> defaultConditionData);
	
}
