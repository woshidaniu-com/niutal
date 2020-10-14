package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;


/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 报表列定义表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class BbldyModel implements Serializable{
	private static final long serialVersionUID = -4525876328815740360L;
	private String zdmc;// 字段名称
	private String xmdm;// 项目ID
	private String zdsm;// 字段说明
	private String qzlx;// 取值类型： 1.固定值，格式为：1:男,2:女 2:数据库取值,“表名:代码,名称”,
	// 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型；
	private String qzfw;// 取值范围： 根据取值类型配置相应的值"
	private String xssx;// 显示顺序： 两位，不足补0 如：01,02....

	//private String 
	
	public BbldyModel() {
		super();
	}

	public BbldyModel(String xmdm) {
		super();
		this.xmdm = xmdm;
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

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

}
