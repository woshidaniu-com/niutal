package com.woshidaniu.designer.dao.entities;

import java.util.List;

/**
 * 
 *@类名称: DesignFuncElementModel.java
 *@类描述：功能页面自定义元素信息表Model:指定设计器生成的功能页面的元素信息，元素分基本html元素和js组件
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 上午11:56:19
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementModel extends DesignFuncModel {

	/**
	 * 功能页面自定义元素信息表ID（niutal_designer_func_elements.element_guid）
	 */
	protected String element_guid;
	/**
	 * 【功能页面元素】索引，作为页面元素ID使用;这里如果元素关联的是普通字段则优先取field_id
	 */
	protected String element_id;
	/**
	 * 【功能页面元素】关联元素索引，指定受影响元素，如查询区域元素关联jqgrid结果区域元素
	 */
	protected String element_related_guid;
	/**
	 * 【功能页面元素】关联元素对象
	 */
	protected DesignFuncElementModel element_related;
	/**
	 * 【功能页面元素】描述:作为页面元素扩展描述信息
	 */
	protected String element_desc;
	/**
	 * 【功能页面元素】栅格占比：总数12，可选[1-12]的整数
	 */
	protected String element_width;
	/**
	 * 【功能页面元素】的显示顺
	 */
	protected String element_ordinal;
	/**
	 * 【功能页面元素】类型：1:'查询条件',2:'脚本控件',3:'普通字段
	 */
	protected String element_type;
	/**
	 * 【功能页面元素】最大顺序;
	 */
	protected String max_ordinal;
	/**
	 * 元素关联的查询条件信息
	 */
	protected DesignFuncElementQueryModel element_query;
	/**
	 * 元素关联的字段信息
	 */
	protected List<DesignFuncElementFieldModel> element_field_list;
	/**
	 * 元素关联js组件信息
	 */
	protected DesignFuncElementWidgetModel element_widget;

	public String getElement_guid() {
		return element_guid;
	}

	public void setElement_guid(String elementGuid) {
		element_guid = elementGuid;
	}

	public String getElement_desc() {
		return element_desc;
	}
	
	public String getElement_id() {
		return element_id;
	}

	public void setElement_id(String elementId) {
		element_id = elementId;
	}

	public String getElement_related_guid() {
		return element_related_guid;
	}

	public void setElement_related_guid(String elementRelatedGuid) {
		element_related_guid = elementRelatedGuid;
	}
	
	public DesignFuncElementModel getElement_related() {
		return element_related;
	}

	public void setElement_related(DesignFuncElementModel elementRelated) {
		element_related = elementRelated;
	}

	public void setElement_desc(String elementDesc) {
		element_desc = elementDesc;
	}

	public String getElement_width() {
		return element_width;
	}

	public void setElement_width(String elementWidth) {
		element_width = elementWidth;
	}

	public String getElement_ordinal() {
		return element_ordinal;
	}

	public void setElement_ordinal(String elementOrdinal) {
		element_ordinal = elementOrdinal;
	}

	public DesignFuncElementQueryModel getElement_query() {
		return element_query;
	}

	public void setElement_query(DesignFuncElementQueryModel elementQuery) {
		element_query = elementQuery;
	}

	public List<DesignFuncElementFieldModel> getElement_field_list() {
		return element_field_list;
	}

	public void setElement_field_list(List<DesignFuncElementFieldModel> elementFieldList) {
		element_field_list = elementFieldList;
	}

	public DesignFuncElementWidgetModel getElement_widget() {
		return element_widget;
	}

	public void setElement_widget(DesignFuncElementWidgetModel elementWidget) {
		element_widget = elementWidget;
	}

	public String getMax_ordinal() {
		return max_ordinal;
	}

	public void setMax_ordinal(String maxOrdinal) {
		max_ordinal = maxOrdinal;
	}

	public String getElement_type() {
		return element_type;
	}

	public void setElement_type(String elementType) {
		element_type = elementType;
	}
	
}
