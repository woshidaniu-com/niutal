package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;


/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 字段运算符关系表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class ZdysfgxModel  implements Serializable{
	private static final long serialVersionUID = 563089613360976823L;
	private String ysfzdm;// 运算符组代码
	private String ysfdm;// 运算符代码

	public ZdysfgxModel() {
		super();
	}

	public String getYsfzdm() {
		return ysfzdm;
	}

	public void setYsfzdm(String ysfzdm) {
		this.ysfzdm = ysfzdm;
	}

	public String getYsfdm() {
		return ysfdm;
	}

	public void setYsfdm(String ysfdm) {
		this.ysfdm = ysfdm;
	}

}
