package com.woshidaniu.designer.dao.entities;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.format.fastjson.FastJSONObject;

/**
 * 
 *@类名称: DesignFuncWidgetModel.java
 *@类描述：功能页面组件初始化参数信息表Model:指定设计器生成的功能页面中该组件参数信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 上午11:59:44
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementWidgetModel extends DesignFuncElementModel {

	/**
	 * 功能页面组件初始化参数信息表ID
	 */
	protected String func_widget_guid;
	/**
	 * 功能js组件描述信息表ID（niutal_widget_details.widget_guid）
	 */
	protected String widget_guid;
	/**
	 * 【功能页面组件】名称（如：jqgrid列表）
	 */
	protected String widget_name;
	/**
	 * 【功能页面组件】在页面中展示的标题
	 */
	protected String widget_title;
	/**
	 * 【功能页面组件】在页面中展示的描述信息
	 */
	protected String widget_desc;
	/**
	 * 【功能页面组件】初始化参数；该参数未JSON数据格式;如：{"name":"test"}
	 */
	protected String widget_params;
	/**
	 * 【功能页面组件】初始化参数Map对象
	 */
	protected Map<String,Object> widget_paramMap;
	/**
	 * 【功能页面组件】【功能页面组件】数据查询是否分页，1:true,0:false;默认为 0
	 */
	protected String widget_pageable;
	/**
	 * 【功能页面组件】是否使用缓存机制处理数据结果，1:true,0:false;默认为 0
	 */
	protected String widget_cacheable;
	/**
	 * 【功能页面组件】是否组件初始化完成后立刻根据data_url或者data_sql加载数据
	 */
	protected String widget_loadAtOnce;
	/**
	 * 【功能页面组件】数据查询SQL
	 */
	protected String widget_sql;
	/**
	 * 【功能页面组件】数据查询URL；如果在录入界面未指定，则会自动生成默认路径：/design/funcData_cxFuncDataList.html
	 */
	protected String widget_data_url;
	/**
	 * 组件是JQGrid时，每列的信息
	 */
	protected List<DesignFuncWidgetJQGridColumnModel> columnList;

	public String getFunc_widget_guid() {
		return func_widget_guid;
	}

	public void setFunc_widget_guid(String funcWidgetGuid) {
		func_widget_guid = funcWidgetGuid;
	}

	public String getWidget_guid() {
		return widget_guid;
	}

	
	public String getWidget_name() {
		return widget_name;
	}

	public void setWidget_name(String widgetName) {
		widget_name = widgetName;
	}

	public void setWidget_guid(String widgetGuid) {
		widget_guid = widgetGuid;
	}

	public String getWidget_title() {
		return widget_title;
	}

	public void setWidget_title(String widgetTitle) {
		widget_title = widgetTitle;
	}

	public String getWidget_desc() {
		return widget_desc;
	}

	public void setWidget_desc(String widgetDesc) {
		widget_desc = widgetDesc;
	}

	public String getWidget_params() {
		return widget_params;
	}

	public void setWidget_params(String widgetParams) {
		widget_params = widgetParams;
	}
	
	public Map<String, Object> getWidget_paramMap() {
		return widget_paramMap;
	}

	public void setWidget_paramMap(Map<String, Object> widgetParamMap) {
		widget_paramMap = widgetParamMap;
	}

	public String getWidget_pageable() {
		return StringUtils.getSafeStr(widget_pageable != null ? widget_pageable.trim() : widget_pageable, "1");
	}

	public void setWidget_pageable(String widgetPageable) {
		widget_pageable = widgetPageable;
	}

	
	public String getWidget_cacheable() {
		return  widget_cacheable != null ? widget_cacheable.trim() : widget_cacheable;
	}

	public void setWidget_cacheable(String widgetCacheable) {
		widget_cacheable = widgetCacheable;
	}

	public String getWidget_sql() {
		return widget_sql;
	}

	public void setWidget_sql(String widgetSql) {
		widget_sql = widgetSql;
	}

	public String getWidget_data_url() {
		return widget_data_url;
	}

	public void setWidget_data_url(String widgetDataUrl) {
		widget_data_url = widgetDataUrl;
	}

	public BeanMap getWidgetParameters(){
		return !BlankUtils.isBlank(getWidget_params()) ?  new BeanMap(FastJSONObject.parse(getWidget_params())) : null;
	}

	public List<DesignFuncWidgetJQGridColumnModel> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<DesignFuncWidgetJQGridColumnModel> columnList) {
		this.columnList = columnList;
	}

	public String getWidget_loadAtOnce() {
		return widget_loadAtOnce;
	}

	public void setWidget_loadAtOnce(String widgetLoadAtOnce) {
		widget_loadAtOnce = widgetLoadAtOnce;
	}
	
	
}
