package com.woshidaniu.designer.dao.entities;

/**
 * 
 *@类名称:DesignWidgetResourceModel.java
 *@类描述：功能组件脚本样式资源信息表model：指定功能页面引入的各个组件的资源信息js/css
 *@创建人：kangzhidong
 *@创建时间：2015-4-27 下午05:19:16
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignWidgetResourceModel extends BaseWidgetResourceModel {
	
	/**
	 * 系统可自定义字段功能表ID（firework_designer_func.func_guid）
	 */
	protected String func_guid;
	/**
	 * 功能组件关联脚本是否程序自动插入，1:true,0:false;默认为0
	 */
	protected String auto_insert;
	
	/**
	 * 功能自定义脚本/样式名称
	 */
	protected String resource_name;
	/**
	 * 功能自定义脚本/样式文本数据
	 */
	protected String resource_text;
	/**
	 * 自定义功能资源类型：1：js脚本,2:css样式
	 */
	protected String resource_type;

	public String getFunc_guid() {
		return func_guid;
	}

	public void setFunc_guid(String funcGuid) {
		func_guid = funcGuid;
	}

	public String getAuto_insert() {
		return auto_insert;
	}

	public void setAuto_insert(String autoInsert) {
		auto_insert = autoInsert;
	}

	public String getResource_name() {
		return resource_name;
	}

	public void setResource_name(String resourceName) {
		resource_name = resourceName;
	}

	public String getResource_text() {
		return resource_text;
	}

	public void setResource_text(String resourceText) {
		resource_text = resourceText;
	}

	public String getResource_type() {
		return resource_type;
	}

	public void setResource_type(String resourceType) {
		resource_type = resourceType;
	}
	
}
