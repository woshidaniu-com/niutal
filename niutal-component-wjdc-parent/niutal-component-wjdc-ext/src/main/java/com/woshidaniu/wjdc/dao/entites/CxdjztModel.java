/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.search.core.SearchModel;

public class CxdjztModel extends ModelBase{

	private static final long serialVersionUID = 1L;
	
	private String sql;
	
	private SearchModel searchModel = new SearchModel();
	//问卷id
	private String wjid;
	//答卷状态 
	private String djzt;
	
	private int startRowNo;
	
	private int endRowNo;

	public SearchModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

	public String getDjzt() {
		return djzt;
	}

	public void setDjzt(String djzt) {
		this.djzt = djzt;
	}

	public String getWjid() {
		return wjid;
	}
	public void setWjid(String wjid) {
		this.wjid = wjid;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getStartRowNo() {
		return startRowNo;
	}

	public void setStartRowNo(int startRowNo) {
		this.startRowNo = startRowNo;
	}

	public int getEndRowNo() {
		return endRowNo;
	}

	public void setEndRowNo(int endRowNo) {
		this.endRowNo = endRowNo;
	}

	@Override
	public String toString() {
		return "CxdjztModel [sql=" + sql + ", searchModel=" + searchModel + ", wjid=" + wjid + ", djzt=" + djzt
				+ ", startRowNo=" + startRowNo + ", endRowNo=" + endRowNo + "]";
	}
	
}
