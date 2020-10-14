package com.woshidaniu.designer.dao.entities;

/**
 * 
 *@类名称: BaseWidgetParameterModel.java
 *@类描述：功能js组件初始化参数信息表Model：指定系统中的js组件初始化需要哪些参数，以及默认值
 *@创建人：kangzhidong
 *@创建时间：2015-4-30 上午11:55:27
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class BaseWidgetParameterModel extends BaseWidgetDetailModel {

	/**
	 * 功能js组件初始化参数信息表ID
	 */
	protected String param_guid;
	/**
	 * 【功能js组件初始化参数】名称:如果该名称为 类似 treeReader.level_field结构则表示该参数是JSON对象中的一个值
	 */
	protected String param_name;
	/**
	 * 【功能js组件初始化参数】描述
	 */
	protected String param_desc;
	/**
	 * 【功能js组件初始化参数】默认值
	 */
	protected String param_default;
	/**
	 * 【功能js组件初始化参数】值
	 */
	protected String param_value;

	public String getParam_guid() {
		return param_guid;
	}

	public void setParam_guid(String paramGuid) {
		param_guid = paramGuid;
	}

	public String getParam_name() {
		return param_name;
	}

	public void setParam_name(String paramName) {
		param_name = paramName;
	}

	public String getParam_desc() {
		return param_desc;
	}

	public void setParam_desc(String paramDesc) {
		param_desc = paramDesc;
	}

	public String getParam_default() {
		return param_default;
	}

	public void setParam_default(String paramDefault) {
		param_default = paramDefault;
	}

	public String getParam_value() {
		return param_value;
	}

	public void setParam_value(String paramValue) {
		param_value = paramValue;
	}
	
	

}
