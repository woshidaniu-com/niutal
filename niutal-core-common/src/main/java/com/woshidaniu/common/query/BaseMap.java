package com.woshidaniu.common.query;


/**
 * 
 *@类名称	: BaseMap.java
 *@类描述	：HashMap子类
 *@创建人	：kangzhidong
 *@创建时间	：Mar 21, 2016 11:30:03 AM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class BaseMap extends com.woshidaniu.basemodel.BaseMap {

	public QueryModel queryModel = new QueryModel();

	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

}
