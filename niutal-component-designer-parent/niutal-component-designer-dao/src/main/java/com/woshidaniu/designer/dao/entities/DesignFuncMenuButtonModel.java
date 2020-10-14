package com.woshidaniu.designer.dao.entities;


/**
 * 
 *@类名称: DesignFuncMenuButtonModel.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-5-18 下午02:06:51
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncMenuButtonModel extends DesignFuncMenuModel {

	/**
	 * 操作按钮代码
	 */
	protected String btn_code;
	/**
	 * 操作按钮名称
	 */
	protected String btn_text;
	/**
	 * 操作按钮图标
	 */
	protected String btn_icon;
	/**
	 * 操作按钮是否显示
	 */
	protected String btn_displayed;
	/**
	 * 操作按钮是否显示:解决多个菜单在一个界面显示的问题
	 */
	protected String btn_designed;
	/**
	 * 操作按钮是否用户自定义操作按钮
	 */
	protected String btn_user_defined;
	/**
	 * 操作按钮顺序
	 */
	protected String btn_ordinal;
	
	public String getBtn_code() {
		return btn_code;
	}

	public void setBtn_code(String btnCode) {
		btn_code = btnCode;
	}

	public String getBtn_text() {
		return btn_text;
	}

	public void setBtn_text(String btnText) {
		btn_text = btnText;
	}

	public String getBtn_icon() {
		return btn_icon;
	}

	public void setBtn_icon(String btnIcon) {
		btn_icon = btnIcon;
	}

	public String getBtn_displayed() {
		return btn_displayed;
	}

	public void setBtn_displayed(String btnDisplayed) {
		btn_displayed = btnDisplayed;
	}

	public String getBtn_designed() {
		return btn_designed;
	}

	public void setBtn_designed(String btnDesigned) {
		btn_designed = btnDesigned;
	}

	public String getBtn_user_defined() {
		return btn_user_defined;
	}

	public void setBtn_user_defined(String btnUserDefined) {
		btn_user_defined = btnUserDefined;
	}

	public String getBtn_ordinal() {
		return btn_ordinal;
	}

	public void setBtn_ordinal(String btnOrdinal) {
		btn_ordinal = btnOrdinal;
	}

}
