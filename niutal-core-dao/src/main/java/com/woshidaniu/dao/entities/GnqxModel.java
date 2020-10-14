package com.woshidaniu.dao.entities;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.QueryModel;

@Alias("gnqxModel")
public class GnqxModel {

	public QueryModel queryModel = new QueryModel();
	private String[] gnczid;
	

	public String[] getGnczid() {
		return gnczid;
	}
	public void setGnczid(String[] gnczid) {
		this.gnczid = gnczid;
	}
	public QueryModel getQueryModel() {
		return queryModel;
	}
	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}
	
	
}
