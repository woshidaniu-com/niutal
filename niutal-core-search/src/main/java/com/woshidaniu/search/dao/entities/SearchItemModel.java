/**
 * 
 */
package com.woshidaniu.search.dao.entities;

import java.util.List;



/**
 * @author xiaokang
 *
 */
public class SearchItemModel {

	//查询字段
	private String searchName;
	//数据总数
	private Integer totalCount;
	//是否还有很多
	private boolean hasMore;
	//当前查询的总数
	private Integer currentCount;
	//配置的查询最大数
	private Integer initialCount;
	//数据
	private List<SourceDataItem> sourceDataItemList;
	//查询方式
	private String queryMethod;
	
	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public Integer getInitialCount() {
		return initialCount;
	}

	public void setInitialCount(Integer initialCount) {
		this.initialCount = initialCount;
	}

	public List<SourceDataItem> getSourceDataItemList() {
		return sourceDataItemList;
	}

	public void setSourceDataItemList(List<SourceDataItem> sourceDataItemList) {
		this.sourceDataItemList = sourceDataItemList;
	}

	public String getQueryMethod() {
		return queryMethod;
	}

	public void setQueryMethod(String queryMethod) {
		this.queryMethod = queryMethod;
	}

}
