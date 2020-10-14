package com.woshidaniu.designer.dao.entities;

import java.util.List;

/**
 * 
 *@类名称: DesignFuncMenuModel.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-5-18 下午02:07:03
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncMenuModel extends DesignFuncModel {

	/**
	 * 父级功能代码
	 */
	protected String parent_func_code;
	/**
	 * 父级功能名称
	 */
	protected String parent_func_name;
	/**
	 * 功能所属对象 xs,js,gl,空（表示所有）
	 */
	protected String func_target;
	/**
	 * 功能菜单级别
	 */
	protected String func_level;
	/**
	 * 功能菜单顺序
	 */
	protected String func_ordinal;
	/**
	 * 最大功能菜单顺序;
	 */
	protected String max_ordinal;
	/**
	 * 功能菜单路径
	 */
	protected String func_url;
	/**
	 * 功能菜单是否显示:解决多个菜单在一个界面显示的问题
	 */
	protected String func_designed;
	/**
	 * 功能菜单是否用户自定义功能界面
	 */
	protected String func_user_defined;
	/**
	 * 功能菜单是否显示
	 */
	protected String func_displayed;
	/**
	 * 功能菜单是否是具体功能页面
	 */
	protected String func_leaf;
	/**
	 * 功能菜单是否只读：如果一个功能菜单有子菜单或者自己是最底层菜单，但是只有查询权限按钮，则视为只读菜单，主要用于页面逻辑判断，显示不同的树形节点
	 */
	protected String func_readonly;
	/**
	 * 子级功能集合
	 */
	private List<DesignFuncMenuModel> childFuncList;
	/**
	 * 功能操作按钮
	 */
	private List<DesignFuncMenuButtonModel> funcBtnList;

	public String getParent_func_code() {
		return parent_func_code;
	}

	public void setParent_func_code(String parentFuncCode) {
		parent_func_code = parentFuncCode;
	}

	public String getParent_func_name() {
		return parent_func_name;
	}

	public void setParent_func_name(String parentFuncName) {
		parent_func_name = parentFuncName;
	}

	public String getFunc_target() {
		return func_target;
	}

	public void setFunc_target(String funcTarget) {
		func_target = funcTarget;
	}

	public String getFunc_level() {
		return func_level;
	}

	public void setFunc_level(String funcLevel) {
		func_level = funcLevel;
	}

	public String getFunc_ordinal() {
		return func_ordinal;
	}

	public void setFunc_ordinal(String funcOrdinal) {
		func_ordinal = funcOrdinal;
	}

	public String getFunc_url() {
		return func_url;
	}

	public void setFunc_url(String funcUrl) {
		func_url = funcUrl;
	}

	public String getFunc_designed() {
		return func_designed;
	}

	public void setFunc_designed(String funcDesigned) {
		func_designed = funcDesigned;
	}

	public String getFunc_user_defined() {
		return func_user_defined;
	}

	public void setFunc_user_defined(String funcUserDefined) {
		func_user_defined = funcUserDefined;
	}

	public String getFunc_displayed() {
		return func_displayed;
	}

	public void setFunc_displayed(String funcDisplayed) {
		func_displayed = funcDisplayed;
	}

	public String getFunc_leaf() {
		return func_leaf;
	}

	public void setFunc_leaf(String funcLeaf) {
		func_leaf = funcLeaf;
	}

	public String getFunc_readonly() {
		return func_readonly;
	}

	public void setFunc_readonly(String funcReadonly) {
		func_readonly = funcReadonly;
	}

	public String getMax_ordinal() {
		return max_ordinal;
	}

	public void setMax_ordinal(String maxOrdinal) {
		max_ordinal = maxOrdinal;
	}

	public List<DesignFuncMenuModel> getChildFuncList() {
		return childFuncList;
	}

	public void setChildFuncList(List<DesignFuncMenuModel> childFuncList) {
		this.childFuncList = childFuncList;
	}

	public List<DesignFuncMenuButtonModel> getFuncBtnList() {
		return funcBtnList;
	}

	public void setFuncBtnList(List<DesignFuncMenuButtonModel> funcBtnList) {
		this.funcBtnList = funcBtnList;
	}

}
