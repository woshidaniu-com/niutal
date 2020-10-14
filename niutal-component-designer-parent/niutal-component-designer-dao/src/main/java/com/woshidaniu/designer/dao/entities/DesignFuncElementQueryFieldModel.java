package com.woshidaniu.designer.dao.entities;

/**
 * 
 *@类名称 : DesignQueryFieldModel.java
 *@类描述 ：功能功能对应页面自定义字段设计表:指定设计器生成的功能页面的页面字段信息
 *@创建人 ：kangzhidong
 *@创建时间 ：2015-4-20 下午02:30:19
 *@修改人 ：
 *@修改时间 ：
 *@版本号 : v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementQueryFieldModel extends BaseQueryFieldModel {
	
	/**
	 * 系统可自定义字段功能对应字段设计表ID[niutal_designer_query_fields表主键]
	 */
	protected String table_guid;
	/**
	 * 功能对应字段别名：在字段生成查询条件SQL时用作别名
	 */
	protected String field_alias;
	/**
	 * 字段展示形态;默认 1,可选值 ：1：select(下拉框),2：input(文本框),3：textarea(文本域)
	 */
	protected String field_shape;
	/**
	 * 字段元素类型：字段作为input元素时的类型;默认 1,可选值 ：1：text(文本输入框),2：radio(单选框),3：checkbox(复选框),4：hidden(隐藏输入框)
	 */
	protected String field_type;
	/**
	 * 功能对应字段级联父级索引，如果有多个以","分割;
	 */
	protected String field_parent;
	/**
	 * 系统可自定义字段功能表 ID
	 */
	protected String query_guid;
	/**
	 * 字段元素的自定义属性
	 */
	protected String field_attr;
	/**
	 * 功能对应默认值:可以是固定的值，也可以是OGNL表达式，方便从struts上下文获取，如：#{dqxn}
	 */
	protected String field_value;
	/**
	 * 功能对应字段名称的映射名称:在作为其他字段父级条件，【name属性值】与级联字段父级条件【参数名称】不统一时作为参数名称使用
	 */
	protected String field_mapper;
	/**
	 * 字段作为select元素是否使用chosen字段美化组件;默认 1,可选值 ：1：使用,0：不使用
	 */
	protected String field_chosen;
	/**
	 * 字段作为文本输入框时是否使用自动完成组件;默认 0,可选值 ：1：使用,0：不使用
	 */
	protected String field_autocomplete;
	/**
	 * 功能对应字段作为筛选条件时的筛选类型：默认：等值筛选，可选：1：等值筛选;2:全模糊筛选;3:前缀模糊筛选;4:后缀模糊筛选;
	 */
	protected String field_filtertype;
	/**
	 * 功能对应字段是否必填;默认 0,可选值 ：1：必填,0：非必填
	 */
	protected String field_required;
	/**
	 * 功能对应字段元素的显示顺
	 */
	protected String field_ordinal;
	/**
	 * 字段对应的自动完成组件属性
	 */
	protected DesignAutoCompleteFieldModel field_auto;

	public String getTable_guid() {
		return table_guid;
	}

	public void setTable_guid(String tableGuid) {
		table_guid = tableGuid;
	}

	public String getField_alias() {
		return field_alias;
	}

	public void setField_alias(String fieldAlias) {
		field_alias = fieldAlias;
	}

	public String getField_mapper() {
		return field_mapper;
	}

	public void setField_mapper(String fieldMapper) {
		field_mapper = fieldMapper;
	}

	public String getField_shape() {
		return field_shape;
	}

	public void setField_shape(String fieldShape) {
		field_shape = fieldShape;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String fieldType) {
		field_type = fieldType;
	}
	
	public String getField_parent() {
		return field_parent;
	}

	public void setField_parent(String fieldParent) {
		field_parent = fieldParent;
	}

	public String getQuery_guid() {
		return query_guid;
	}

	public void setQuery_guid(String queryGuid) {
		query_guid = queryGuid;
	}

	public String getField_attr() {
		return field_attr;
	}

	public String getField_value() {
		return field_value;
	}

	public void setField_value(String fieldValue) {
		field_value = fieldValue;
	}

	public void setField_attr(String fieldAttr) {
		field_attr = fieldAttr;
	}

	public String getField_chosen() {
		return field_chosen;
	}

	public void setField_chosen(String fieldChosen) {
		field_chosen = fieldChosen;
	}

	public String getField_autocomplete() {
		return field_autocomplete;
	}

	public void setField_autocomplete(String fieldAutocomplete) {
		field_autocomplete = fieldAutocomplete;
	}

	public String getField_ordinal() {
		return field_ordinal;
	}

	public void setField_ordinal(String fieldOrdinal) {
		field_ordinal = fieldOrdinal;
	}

	public DesignAutoCompleteFieldModel getField_auto() {
		return field_auto;
	}

	public void setField_auto(DesignAutoCompleteFieldModel fieldAuto) {
		field_auto = fieldAuto;
	}

	public String getField_filtertype() {
		return field_filtertype;
	}

	public void setField_filtertype(String fieldFiltertype) {
		field_filtertype = fieldFiltertype;
	}

	public String getField_required() {
		return field_required;
	}

	public void setField_required(String fieldRequired) {
		field_required = fieldRequired;
	}
	
}
