package com.woshidaniu.dao.entities;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.search.core.SearchModel;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：二级权限
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月27日下午3:23:31
 */
@Alias(value="EjqxModel")
public class EjqxModel implements Serializable {

	private static final long serialVersionUID = 4833642562885229949L;
	
	private String jsdm;
	private String gxz;
	private String sxz;
	private String[] gnczids;
	
	private SearchModel searchModel;
	public QueryModel queryModel = new QueryModel();
	
	
	public SearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}
	public QueryModel getQueryModel() {
		return queryModel;
	}
	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}
	public String getJsdm() {
		return jsdm;
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getGxz() {
		return gxz;
	}
	public void setGxz(String gxz) {
		this.gxz = gxz;
	}
	public String getSxz() {
		return sxz;
	}
	public void setSxz(String sxz) {
		this.sxz = sxz;
	}
	public String[] getGnczids() {
		return gnczids;
	}
	public void setGnczids(String[] gnczids) {
		this.gnczids = gnczids;
	}

	
}
