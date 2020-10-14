package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;


/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 运算符组表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class YsfzModel  implements Serializable{
	private static final long serialVersionUID = -5102312244483929147L;
	private String ysfzdm;// 运算符组代码
	private String mc;// 名称
	private String ms;// 描述

	public YsfzModel() {
		super();
	}

	public String getYsfzdm() {
		return ysfzdm;
	}

	public void setYsfzdm(String ysfzdm) {
		this.ysfzdm = ysfzdm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

}
