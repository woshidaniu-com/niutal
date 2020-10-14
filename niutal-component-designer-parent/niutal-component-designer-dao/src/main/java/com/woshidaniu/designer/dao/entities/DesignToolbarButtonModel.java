package com.woshidaniu.designer.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称:DesignToolbarButtonModel.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-4-27 上午11:16:39
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignToolbarButtonModel extends ModelBase {
	/**
	 * 功能代码
	 */
	protected String func_code;
	/**
	 * 操作的代码
	 */
	protected String opt_code;
	/**
	 * 操作按钮ID，作为页面元素ID使用
	 */
	protected String button_id;
	/**
	 * 操作按钮名称:作为页面元素Name使用
	 */
	protected String button_name;
	/**
	 * 操作按钮标题:作为页面元素前的文字标题使用
	 */
	protected String button_text;
	/**
	 * 扩展样式名称
	 */
	protected String button_class;
	/**
	 * 图标样式名称
	 */
	protected String button_icon_class;
	/**
	 * 操作按钮元素的描述信息
	 */
	protected String button_desc;
	/**
	 * 操作按钮元素的默认值
	 */
	protected String button_href;
	/**
	 * 功能对应操作按钮元素的显示顺
	 */
	protected String button_ordinal;
	/**
	 * 功能对应操作按钮元素的是否可见
	 */
	protected String button_visible;
	/**
	 * 功能对应操作按钮绑定的自定义功能信息
	 */
	protected DesignFuncMenuButtonModel bindFunc;

	public String getFunc_code() {
		return func_code;
	}

	public void setFunc_code(String funcCode) {
		func_code = funcCode;
	}

	public String getOpt_code() {
		return opt_code;
	}

	public void setOpt_code(String optCode) {
		opt_code = optCode;
	}

	public String getButton_id() {
		return button_id;
	}

	public void setButton_id(String buttonId) {
		button_id = buttonId;
	}

	public String getButton_name() {
		return button_name;
	}

	public void setButton_name(String buttonName) {
		button_name = buttonName;
	}

	public String getButton_text() {
		return button_text;
	}

	public void setButton_text(String buttonText) {
		button_text = buttonText;
	}

	public String getButton_class() {
		return button_class;
	}

	public void setButton_class(String buttonClass) {
		button_class = buttonClass;
	}

	public String getButton_icon_class() {
		return button_icon_class;
	}

	public void setButton_icon_class(String buttonIconClass) {
		button_icon_class = buttonIconClass;
	}

	public String getButton_desc() {
		return button_desc;
	}

	public void setButton_desc(String buttonDesc) {
		button_desc = buttonDesc;
	}

	public String getButton_href() {
		return button_href;
	}

	public void setButton_href(String buttonHref) {
		button_href = buttonHref;
	}

	public String getButton_ordinal() {
		return button_ordinal;
	}

	public void setButton_ordinal(String buttonOrdinal) {
		button_ordinal = buttonOrdinal;
	}

	public String getButton_visible() {
		return button_visible;
	}

	public void setButton_visible(String buttonVisible) {
		button_visible = buttonVisible;
	}

	public DesignFuncMenuButtonModel getBindFunc() {
		return bindFunc;
	}

	public void setBindFunc(DesignFuncMenuButtonModel bindFunc) {
		this.bindFunc = bindFunc;
	}
	
}
