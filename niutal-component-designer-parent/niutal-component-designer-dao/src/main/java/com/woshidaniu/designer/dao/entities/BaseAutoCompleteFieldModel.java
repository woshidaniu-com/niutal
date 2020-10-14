package com.woshidaniu.designer.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称: BaseAutoCompleteFieldModel.java
 *@类描述： 系统可自定义自动完成自动信息表 模型:存储系统中所有可自动完成的字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-24 下午03:42:44
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class BaseAutoCompleteFieldModel extends ModelBase {
	
	/**
	 * 系统可自定义自动完成字段基础信息表ID(niutal_autocomplete_fields.auto_guid)
	 */
	protected String auto_guid;
	/**
	 * 字段名称:作为页面元素Name使用
	 */
	protected String field_name;
	/**
	 * 字段标题:作为页面元素前的文字标题使用
	 */
	protected String field_text;
	/**
	 * 当前文本输入框中字符串达到该属性值时才进行匹配处理，默认：1
	 */
	protected String field_minLength = "1";
	/**
	 * 自动完成提示的最大结果集数量，默认：10
	 */
	protected String field_items = "10";
	/**
	 * 指定延时毫秒数后，才正真向后台请求数据，以防止输入过快导致频繁向后台请求，默认：300
	 */
	protected String field_delay = "300";
	/**
	 * 此值在点击了某项后会将当前元素的值为此处指定的 key或value 对应的后台数据;默认：2;1：表示key,2：表示value 
	 */
	protected String field_setvalue;
	/**
	 * 此值在点击了某项后会将当前元素添加 real-value属性 ，值为此处指定的 key或value 对应的后台数据;默认：1;1：表示key,2：表示value
	 */
	protected String field_realvalue;
	/**
	 * 此值在你需进行特殊的显示时，改变这个值可以改变组件每个元素显示的组合效果，默认是value（key）；此处处理用到正则替换value字符和key字符，所以可写 key[value],value【key】 等等的包含value和key字符的任意模板
	 */
	protected String field_format;
	/**
	 *  自动完成数据查询路径
	 */
	protected String field_action;
	/**
	 * 字段元素生成的缩略图：需要手动上传
	 */
	protected byte[] field_thumbnail;
	/**
	 *  自动完成已经被使用
	 */
	protected String field_used;
	
	public String getAuto_guid() {
		return auto_guid;
	}

	public void setAuto_guid(String autoGuid) {
		auto_guid = autoGuid;
	}
	
	public String getField_name() {
		return field_name;
	}
	
	public void setField_name(String fieldName) {
		field_name = fieldName;
	}
	
	public String getField_text() {
		return field_text;
	}
	
	public void setField_text(String fieldText) {
		field_text = fieldText;
	}
	
	public String getField_minLength() {
		return field_minLength;
	}
	
	public void setField_minLength(String fieldMinLength) {
		field_minLength = fieldMinLength;
	}
	
	public String getField_items() {
		return field_items;
	}
	
	public void setField_items(String fieldItems) {
		field_items = fieldItems;
	}
	
	public String getField_delay() {
		return field_delay;
	}
	
	public void setField_delay(String fieldDelay) {
		field_delay = fieldDelay;
	}
	
	public String getField_setvalue() {
		return field_setvalue;
	}
	
	public void setField_setvalue(String fieldSetvalue) {
		field_setvalue = fieldSetvalue;
	}
	
	public String getField_realvalue() {
		return field_realvalue;
	}
	
	public void setField_realvalue(String fieldRealvalue) {
		field_realvalue = fieldRealvalue;
	}
	
	public String getField_format() {
		return field_format;
	}
	
	public void setField_format(String fieldFormat) {
		field_format = fieldFormat;
	}
	
	public String getField_action() {
		return field_action;
	}
	
	public void setField_action(String fieldAction) {
		field_action = fieldAction;
	}
	
	public byte[] getField_thumbnail() {
		return field_thumbnail;
	}
	
	public void setField_thumbnail(byte[] fieldThumbnail) {
		field_thumbnail = fieldThumbnail;
	}

	public String getField_used() {
		return field_used;
	}

	public void setField_used(String fieldUsed) {
		field_used = fieldUsed;
	}
	
	
}
