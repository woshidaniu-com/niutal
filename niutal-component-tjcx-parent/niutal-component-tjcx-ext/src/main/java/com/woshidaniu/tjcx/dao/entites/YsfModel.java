package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;


/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 运算符表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class YsfModel  implements Serializable{

	private static final long serialVersionUID = 1970885871643256576L;
	private String ysfdm;// 运算符代码
	private String ysfmc;// 运算符名称

	public YsfModel() {
		super();
	}

	public String getYsfdm() {
		return ysfdm;
	}

	public void setYsfdm(String ysfdm) {
		this.ysfdm = ysfdm;
	}

	public String getYsfmc() {
		return ysfmc;
	}

	public void setYsfmc(String ysfmc) {
		this.ysfmc = ysfmc;
	}

}
