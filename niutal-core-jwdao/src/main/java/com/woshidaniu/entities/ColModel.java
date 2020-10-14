package com.woshidaniu.entities;

import java.io.Serializable;
import java.util.List;

import com.woshidaniu.util.xml.BaseData;

public class ColModel  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String label;
	/**
	 * 字段名
	 */
	private String name;
	
	private String index;
	/***
	 * 字段是否主键
	 */
	private boolean key;
	/**
	 * 字段是否隐藏
	 */
	private boolean hidden;
	/**
	 * 字段显示位置
	 */
	private String align;
	/***
	 * 字段类型
	 * 目前支持字段类型说明：text 输入框，hidden 隐藏输入框，radio 单选项，textarea 文本输入框
 	 */
	private String type;
	
	private String value;
	/**
	 * 字段长度
	 */
	private String maxLength;
	/***
	 * 日期格式 如：“yyyy-MM-dd HH:mm:ss”
	 */
	private String dateFmt;
	/**
	 * 是否为空 false不为空 true为空，默认为空
	 */
	private boolean Nullable;
	/***
	 * 表格宽度
	 */
	private String width;
	/***
	 * 是否唯一 true唯一
	 */
	private boolean unique = false;
	
	private List<BaseData> boxList;
	/***
	 * 下拉框值
	 */
	private List<?> selectList;
	/**
	 * 下拉框key
	 */
	private String listKey;
	/**
	 * 下拉框value
	 */
	private String listValue;
	//字段验证类型
	private String validateType;
	//页面验证规则
	private String validateMap;
	
	private String readonly;
	//字段描述
	private String desc;
	
	public String getValidateMap() {
		return validateMap;
	}

	public void setValidateMap(String validateMap) {
		this.validateMap = validateMap;
	}

	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}

	public String getListKey() {
		return listKey;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

	public List<?> getSelectList() {
		return selectList;
	}

	public void setSelectList(List<?> selectList) {
		this.selectList = selectList;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isNullable() {
		return Nullable;
	}

	public void setNullable(boolean nullable) {
		Nullable = nullable;
	}

	public String getDateFmt() {
		return dateFmt;
	}

	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}

	

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<BaseData> getBoxList() {
		return boxList;
	}

	public void setBoxList(List<BaseData> boxList) {
		this.boxList = boxList;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
