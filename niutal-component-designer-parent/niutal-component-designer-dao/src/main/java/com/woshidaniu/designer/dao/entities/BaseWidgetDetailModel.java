package com.woshidaniu.designer.dao.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称: BaseWidgetDetailModel.java
 *@类描述：功能组件描述信息表model:指定系统可用功能组件信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 上午10:30:13
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class BaseWidgetDetailModel extends ModelBase {

	/**
	 * 功能组件描述信息表ID
	 */
	protected String widget_guid;
	/**
	 * 【功能组件】名称（如：jqgrid列表）
	 */
	protected String widget_name;
	/**
	 * 【功能组件】详细描述
	 */
	protected String widget_desc;
	/**
	 * 【功能组件】是否引用资源
	 */
	protected String widget_hasResource;
	/**
	 * 【功能组件】是否有初始参数
	 */
	protected String widget_hasParameter;
	/**
	 * 【功能组件】是否被引用
	 */
	protected String widget_used;
	/**
	 * 【功能组件】参数集合
	 */
	protected List<BaseWidgetParameterModel> parameters;
	/**
	 * 【功能组件】引用资源集合
	 */
	protected List<BaseWidgetResourceModel> resources;
     
	public String getWidget_guid() {
		return widget_guid;
	}

	public void setWidget_guid(String widgetGuid) {
		widget_guid = widgetGuid;
	}

	public String getWidget_name() {
		return widget_name;
	}

	public void setWidget_name(String widgetName) {
		widget_name = widgetName;
	}

	public String getWidget_desc() {
		return widget_desc;
	}

	public void setWidget_desc(String widgetDesc) {
		widget_desc = widgetDesc;
	}

	public String getWidget_hasResource() {
		return widget_hasResource;
	}

	public void setWidget_hasResource(String widgetHasResource) {
		widget_hasResource = widgetHasResource;
	}

	public String getWidget_hasParameter() {
		return widget_hasParameter;
	}

	public void setWidget_hasParameter(String widgetHasParameter) {
		widget_hasParameter = widgetHasParameter;
	}

	public String getWidget_used() {
		return widget_used;
	}

	public void setWidget_used(String widgetUsed) {
		widget_used = widgetUsed;
	}

	public List<BaseWidgetParameterModel> getParameters() {
		return parameters;
	}

	public void setParameters(List<BaseWidgetParameterModel> parameters) {
		this.parameters = parameters;
	}

	public List<BaseWidgetResourceModel> getResources() {
		return resources;
	}

	public void setResources(List<BaseWidgetResourceModel> resources) {
		this.resources = resources;
	}

	
}
