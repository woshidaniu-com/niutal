package com.woshidaniu.entities;

import java.util.List;

/**
 * 
 *@类名称:JsgnmkModel.java
 *@类描述：角色功能模块
 *@创建人：kangzhidong
 *@创建时间：2014-10-18 下午04:58:24
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class JsgnmkModel extends CommonModel {

	/**
	 * 父级功能代码
	 */
	private String fjgndm;
	/**
	 * 父级角色代码
	 */
	private String fjsdm;
	/**
	 * 父级角色名称
	 */
	private String fjsmc;
	/**
	 * 父级角色级别
	 */
	private String fjsjb;
	/**
	 * 功能模块代码
	 */
	private String gwjbdm;
	/**
	 * 功能模块名称
	 */
	private String gwjbmc;
	/**
	 * 功能代码
	 */
	private String gnmkdm;
	/**
	 * 功能名称
	 */
	private String gnmkmc;
	/**
	 * 显示顺序
	 */
	private String xssx;
	/**
	 * 对应页面
	 */
	private String dyym;
	/**
	 * 操作代码
	 */
	private String czdm;
	/**
	 * 操作名称
	 */
	private String czmc;
	/**
	 * 功能按钮
	 */
	private String[] btns;
	/**
	 * 菜单级别
	 */
	private String cdjb;
	/**
	 * 授权人角色代码
	 */
	private String sqrJsdm;
	/**
	 * 功能使用对象(学生:xs;教师:js;管理:gl;空:面向所有用户)
	 */
	private String gnsydx;
	/**
	 * 是否选中
	 */
	private String ischecked;
	/**
	 * 角色功能子模块集合
	 */
	private List<JsgnmkModel> childList;
	/**
	 * 页面提交的功能模块代码集合:最底层功能模块+操作代码
	 */
	private List<String> gnmkdm_list;
	/**
	 * 按钮
	 */
	private List<JsgnmkModel> btnList;

	public JsgnmkModel() {

	}

	public JsgnmkModel(String cdjb) {
		super();
		this.cdjb = cdjb;
	}

	public String getFjgndm() {
		return fjgndm;
	}

	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}

	public String getFjsdm() {
		return fjsdm;
	}

	public void setFjsdm(String fjsdm) {
		this.fjsdm = fjsdm;
	}

	public String getFjsmc() {
		return fjsmc;
	}

	public void setFjsmc(String fjsmc) {
		this.fjsmc = fjsmc;
	}

	public String getFjsjb() {
		return fjsjb;
	}

	public void setFjsjb(String fjsjb) {
		this.fjsjb = fjsjb;
	}

	public String getGwjbdm() {
		return gwjbdm;
	}

	public void setGwjbdm(String gwjbdm) {
		this.gwjbdm = gwjbdm;
	}

	public String getGwjbmc() {
		return gwjbmc;
	}

	public void setGwjbmc(String gwjbmc) {
		this.gwjbmc = gwjbmc;
	}

	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getGnmkmc() {
		return gnmkmc;
	}

	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getDyym() {
		return dyym;
	}

	public void setDyym(String dyym) {
		this.dyym = dyym;
	}

	public String getCzdm() {
		return czdm;
	}

	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}

	public String getCzmc() {
		return czmc;
	}

	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}

	public String[] getBtns() {
		return btns;
	}

	public void setBtns(String[] btns) {
		this.btns = btns;
	}

	public String getCdjb() {
		return cdjb;
	}

	public void setCdjb(String cdjb) {
		this.cdjb = cdjb;
	}

	public String getSqrJsdm() {
		return sqrJsdm;
	}

	public void setSqrJsdm(String sqrJsdm) {
		this.sqrJsdm = sqrJsdm;
	}

	public String getGnsydx() {
		return gnsydx;
	}

	public void setGnsydx(String gnsydx) {
		this.gnsydx = gnsydx;
	}

	public String getIschecked() {
		return ischecked;
	}

	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}

	public List<JsgnmkModel> getChildList() {
		return childList;
	}

	public void setChildList(List<JsgnmkModel> childList) {
		this.childList = childList;
	}

	public List<String> getGnmkdm_list() {
		return gnmkdm_list;
	}

	public void setGnmkdm_list(List<String> gnmkdmList) {
		gnmkdm_list = gnmkdmList;
	}

	public List<JsgnmkModel> getBtnList() {
		return btnList;
	}

	public void setBtnList(List<JsgnmkModel> btnList) {
		this.btnList = btnList;
	}

}
