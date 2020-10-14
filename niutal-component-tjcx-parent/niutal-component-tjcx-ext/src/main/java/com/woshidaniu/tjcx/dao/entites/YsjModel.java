package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 源数据表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class YsjModel  implements Serializable{

	private static final long serialVersionUID = 1163616365285379517L;
	private String ysjdm;// 源数据代码
	private String ysjmc;// 源数据名称
	private String sql;// sql： 获取源数据的具体sql语句。 可直接输表名或视图名，也可写具体查询语句， 如：select
	// xh,xb,mc from xsxxb;"
	private String sjfwlx;// 数据范围类型 1:新框架;2:学工系统 说明：新框架模式字段sql或视图需按要求增加{}字符串;学工系统需包含xydm、bjdm字段
	private String ms;// 描述

	private String sjfwpz;//数据范围配置
	
	public YsjModel() {
		super();
	}

	public String getYsjdm() {
		return ysjdm;
	}

	public void setYsjdm(String ysjdm) {
		this.ysjdm = ysjdm;
	}

	public String getYsjmc() {
		return ysjmc;
	}

	public void setYsjmc(String ysjmc) {
		this.ysjmc = ysjmc;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getSjfwlx() {
		return sjfwlx;
	}

	public void setSjfwlx(String sjfwlx) {
		this.sjfwlx = sjfwlx;
	}

	public String getSjfwpz() {
		return sjfwpz;
	}

	public void setSjfwpz(String sjfwpz) {
		this.sjfwpz = sjfwpz;
	}

}
