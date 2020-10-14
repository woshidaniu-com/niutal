package com.woshidaniu.designer.dao.entities;

import java.util.List;

/**
 * 
 *@类名称:DesignQuerysModel.java
 *@类描述：自定义查询条件设计表：指定可自定义字段的系统功能
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午02:26:21
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementQueryModel extends DesignFuncElementModel {
	/**
	 * 功能页面自定义元素关联字段信息表ID(niutal_designer_func_elements.element_guid)
	 */
	protected String element_guid;
	/**
	 * 系统可自定义字段功能表ID
	 */
	protected String query_guid;
	/**
	 * 【自定义查询条件】名称（如：个人信息校验）
	 */
	protected String query_name;
	
	/**
	 * 【自定义查询条件】显示列数
	 */
	protected String query_column;
	/**
	 * 【自定义查询条件】描述
	 */
	protected String query_desc;

	/**
	 * 【自定义查询条件】条件字段集合
	 */
	protected List<DesignFuncElementQueryFieldModel>  query_field_list;
	
	public String getElement_guid() {
		return element_guid;
	}

	public void setElement_guid(String elementGuid) {
		element_guid = elementGuid;
	}

	public String getQuery_guid() {
		return query_guid;
	}

	public void setQuery_guid(String queryGuid) {
		query_guid = queryGuid;
	}

	public String getQuery_name() {
		return query_name;
	}

	public void setQuery_name(String queryName) {
		query_name = queryName;
	}
	
	public String getQuery_column() {
		return query_column;
	}

	public void setQuery_column(String queryColumn) {
		query_column = queryColumn;
	}

	public String getQuery_desc() {
		return query_desc;
	}

	public void setQuery_desc(String queryDesc) {
		query_desc = queryDesc;
	}

	public List<DesignFuncElementQueryFieldModel> getQuery_field_list() {
		return query_field_list;
	}

	public void setQuery_field_list(
			List<DesignFuncElementQueryFieldModel> queryFieldList) {
		query_field_list = queryFieldList;
	}

	

	
}
