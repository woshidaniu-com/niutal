package com.woshidaniu.designer.dao.entities;

import java.util.Map;

/**
 * 
 *@类名称:DesignFuncDataModel.java
 *@类描述：功能数据model
 *@创建人：kangzhidong
 *@创建时间：2015-5-4 下午03:45:45
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */

@SuppressWarnings("serial")
public class DesignFuncDataModel extends DesignFuncModel {

	/**
	 * 功能页面自定义元素信息表ID（niutal_designer_func_elements.element_guid）
	 */
	protected String element_guid;
	/**
	 * 系统可自定义查询字段信息表ID（niutal_query_fields.field_guid或niutal_advanced_query_fields
	 * .field_guid）
	 */
	protected String field_guid;
	/**
	 * 功能页面组件初始化参数信息表ID
	 */
	protected String func_widget_guid;
	/**
	 * 数据查询SQL
	 */
	protected String data_sql;
	/**
	 * 参数集合
	 */
	protected Map<String, Object> paramMap;

	public String getElement_guid() {
		return element_guid;
	}

	public void setElement_guid(String elementGuid) {
		element_guid = elementGuid;
	}

	public String getField_guid() {
		return field_guid;
	}

	public void setField_guid(String fieldGuid) {
		field_guid = fieldGuid;
	}

	public String getFunc_widget_guid() {
		return func_widget_guid;
	}

	public void setFunc_widget_guid(String funcWidgetGuid) {
		func_widget_guid = funcWidgetGuid;
	}

	public String getData_sql() {
		return data_sql;
	}

	public void setData_sql(String dataSql) {
		data_sql = dataSql;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

}
