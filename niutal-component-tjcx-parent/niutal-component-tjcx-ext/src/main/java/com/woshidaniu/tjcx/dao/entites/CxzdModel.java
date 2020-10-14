package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 查询字段表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class CxzdModel  implements Serializable{
	private static final long serialVersionUID = -7635107877582162023L;
	private String zdmc;// 字段名称
	private String xmdm;// 项目ID
	private String ysfzdm;// 运算符组代码
	private String zdsm;// 字段说明
	private String qzlx;// 取值类型： 1.固定值，格式为：1:男,2:女。显示格式：复选框
	// 2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	// 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
	// 11,普通文本框; 12,数字文本框; 13,日期选择框;"
	private String qzfw;// 取值范围： 根据取值类型配置相应的值"
	private String ms;// 描述
	private String xssx;// 显示顺序： 两位，不足补0 如：01,02....
	private String sjfwlx;// 数据范围类型
	private String sjfw;// 数据范围

	// 为已配置好的父类字段名称.
	// 如：当前字段为专业，另已配置好学院xydm,则此处填写xydm
	// 若当前字段为方法取值，父类字段代码将以逗号分割形式，作为参数传入方法中。
	private String flzdmc;
	private String flwjzdmc;// 若当前字段为数据库取值，外键关联父类查询字段与实际父类字段不一致时填写。其余情况为空
	// 如：当前字段为zydm，当前表中外键字段为bmdm,。学院表中实际字段为xydm

	private String flzdz;// 父类字段值
	private String cxtjdm;// 查询条件代码，以逗号分割，如：2011,2012
	private String zdxsms;// 显示模式1:仅在功能首页显示 2:仅在列表页面显示

	private List<YsfModel> ysfList = null;

	public CxzdModel() {
		super();
	}

	public CxzdModel(String xmdm) {
		super();
		this.xmdm = xmdm;
	}

	public CxzdModel(String xmdm, String zdxsms) {
		super();
		this.xmdm = xmdm;
		this.zdxsms = zdxsms;
	}

	public String getZdxsms() {
		return zdxsms;
	}

	public void setZdxsms(String zdxsms) {
		this.zdxsms = zdxsms;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getYsfzdm() {
		return ysfzdm;
	}

	public void setYsfzdm(String ysfzdm) {
		this.ysfzdm = ysfzdm;
	}

	public String getZdsm() {
		return zdsm;
	}

	public void setZdsm(String zdsm) {
		this.zdsm = zdsm;
	}

	public String getQzlx() {
		return qzlx;
	}

	public void setQzlx(String qzlx) {
		this.qzlx = qzlx;
	}

	public String getQzfw() {
		return qzfw;
	}

	public void setQzfw(String qzfw) {
		this.qzfw = qzfw;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public List<YsfModel> getYsfList() {
		return ysfList;
	}

	public void setYsfList(List<YsfModel> ysfList) {
		this.ysfList = ysfList;
	}

	public String getSjfwlx() {
		return sjfwlx;
	}

	public void setSjfwlx(String sjfwlx) {
		this.sjfwlx = sjfwlx;
	}

	public String getSjfw() {
		return sjfw;
	}

	public void setSjfw(String sjfw) {
		this.sjfw = sjfw;
	}

	public String getFlzdmc() {
		return flzdmc;
	}

	public void setFlzdmc(String flzdmc) {
		this.flzdmc = flzdmc;
	}

	public String getFlzdz() {
		return flzdz;
	}

	public void setFlzdz(String flzdz) {
		this.flzdz = flzdz;
	}

	public String getFlwjzdmc() {
		return flwjzdmc;
	}

	public void setFlwjzdmc(String flwjzdmc) {
		this.flwjzdmc = flwjzdmc;
	}

	public String getCxtjdm() {
		return cxtjdm;
	}

	public void setCxtjdm(String cxtjdm) {
		this.cxtjdm = cxtjdm;
	}

}
