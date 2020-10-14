package com.woshidaniu.taglibs.dao.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.entities.PairModel;

/**
 * 
 *@类名称		： DatasetModel.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Jun 16, 2016 3:31:23 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class DatasetModel extends ModelBase {

	/**
	 * 系统可自定义查询字段信息表ID（niutal_query_fields.field_guid或niutal_advanced_query_fields
	 * .field_guid）
	 */
	protected String field_guid;
	/**
	 * 字段ID，作为页面元素ID使用
	 */
	protected String field_id;
	/**
	 * 字段名称:作为页面元素Name使用
	 */
	protected String field_name;
	/**
	 * 字段元素外层单元div占比
	 */
	protected String field_div_width;
	/**
	 * 字段名称元素占比：总数12，可选[1-12]的整数
	 */
	protected String field_name_width;
	/**
	 * 字段元素本身占比：field_element_width = 12 - field_name_width
	 */
	protected String field_element_width;
	/**
	 * 字段标题:作为页面元素前的文字标题使用
	 */
	protected String field_text;
	/**
	 * 字段数据取值索引：struts的<s:select list=""></s:select>标签list对应的值
	 */
	protected String field_list;
	/**
	 * 字段key取值索引：struts的<s:select listKey=""></s:select>标签listKey对应的值
	 */
	protected String field_listKey;
	/**
	 * 字段value取值索引：struts的<s:select listValue=""></s:select>标签listValue对应的值
	 */
	protected String field_listValue;
	/**
	 * 下拉框默认值：字段作为select元素默认option元素key值：struts的<s:select
	 * headerKey=""></s:select>标签headerKey对应的值
	 */
	protected String field_headerKey;
	/**
	 * 下拉框默认文字：字段作为select元素默认option元素value值：struts的<s:select
	 * headerValue=""></s:select>标签headerValue对应的值
	 */
	protected String field_headerValue;
	
	/**
	 * 字段使用范围：在系统中使用范围;全局 或 固定功能 ;默认 1,1：全局
	 */
	protected String field_scope;
	/**
	 * 字段值来源类型：'APP':'程序内置','SQL':'数据库','XML':'XML数据','Spring':'Spring集合对象','Fixed':'固定值
	 * '
	 */
	protected String field_source_type; 
	/**
	 * 字段值来源：
	 *  程序内置	：此时如果field_list值不为空且field_shape=2，需要开发者在进入页面前指定field_index对应的对象结果
	 *  数据库	  	：格式如 SQL:查询SQL 例如 SQL:select id as key,name as value from table_name
	 *  XML数据 	：格式如 XML:(baseData.xml)文件中的id 例如 XML:isValid Spring集合对象 ：格式如 Spring:文件中的id 例如 Spring:field_list 
	 * 	固定值		：格式如 Fixed:固定值1,固定值2,...(多个用,隔开) 例如 Fixed:aaa,bbb,ccc 或 Fixed:1:aaa,2:bbb,3：ccc
	 */
	protected String field_source;
	/**
	 * 字段数据后台请求路径，即Ajax请求更新数据的路径： 返回的数据元素必须包含 field_listKey 和 field_listValue
	 * 指定属性
	 */
	protected String field_update_url;

	/**
	 * 字段数据值对象
	 */
	protected List<PairModel> field_data_list;
	/**
	 * 扩展样式名称
	 */
	protected String field_class;
	/**
	 * 字段元素的描述信息
	 */
	protected String field_desc;
	/**
	 * 字段元素内的占位描述信息：如input输入前的提示
	 */
	protected String field_placeholder;

	public String getField_guid() {
		return field_guid != null ? field_guid.trim() : field_guid;
	}

	public void setField_guid(String fieldGuid) {
		field_guid = fieldGuid;
	}

	public String getField_id() {
		return field_id;
	}

	public void setField_id(String fieldId) {
		field_id = fieldId;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String fieldName) {
		field_name = fieldName;
	}

	public String getField_div_width() {
		return field_div_width;
	}

	public void setField_div_width(String fieldDivWidth) {
		field_div_width = fieldDivWidth;
	}

	public String getField_name_width() {
		return field_name_width;
	}

	public void setField_name_width(String fieldNameWidth) {
		field_name_width = fieldNameWidth;
	}

	public String getField_element_width() {
		return field_element_width;
	}

	public void setField_element_width(String fieldElementWidth) {
		field_element_width = fieldElementWidth;
	}

	public String getField_text() {
		return field_text;
	}

	public void setField_text(String fieldText) {
		field_text = fieldText;
	}

	public String getField_list() {
		return field_list;
	}

	public void setField_list(String fieldList) {
		field_list = fieldList;
	}

	public String getField_listKey() {
		return field_listKey;
	}

	public void setField_listKey(String fieldListKey) {
		field_listKey = fieldListKey;
	}

	public String getField_listValue() {
		return field_listValue;
	}

	public void setField_listValue(String fieldListValue) {
		field_listValue = fieldListValue;
	}

	public String getField_headerKey() {
		return field_headerKey;
	}

	public void setField_headerKey(String fieldHeaderKey) {
		field_headerKey = fieldHeaderKey;
	}

	public String getField_headerValue() {
		return field_headerValue;
	}

	public void setField_headerValue(String fieldHeaderValue) {
		field_headerValue = fieldHeaderValue;
	}


	public String getField_scope() {
		return field_scope;
	}

	public void setField_scope(String fieldScope) {
		field_scope = fieldScope;
	}

	public String getField_source_type() {
		return field_source_type;
	}

	public void setField_source_type(String fieldSourceType) {
		field_source_type = fieldSourceType;
	}

	public String getField_source() {
		return field_source;
	}

	public void setField_source(String fieldSource) {
		field_source = fieldSource;
	}

	public String getField_update_url() {
		return field_update_url;
	}

	public void setField_update_url(String fieldUpdateUrl) {
		field_update_url = fieldUpdateUrl;
	}

	public List<PairModel> getField_data_list() {
		return field_data_list;
	}

	public void setField_data_list(List<PairModel> fieldDataList) {
		field_data_list = fieldDataList;
	}

	public String getField_class() {
		return field_class;
	}

	public void setField_class(String fieldClass) {
		field_class = fieldClass;
	}

	public String getField_desc() {
		return field_desc;
	}

	public void setField_desc(String fieldDesc) {
		field_desc = fieldDesc;
	}

	public String getField_placeholder() {
		return field_placeholder;
	}

	public void setField_placeholder(String fieldPlaceholder) {
		field_placeholder = fieldPlaceholder;
	}

}
