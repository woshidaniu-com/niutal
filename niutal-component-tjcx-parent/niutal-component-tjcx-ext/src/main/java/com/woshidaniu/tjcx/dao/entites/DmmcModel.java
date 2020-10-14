package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 代码,名称
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-24 上午11:49:13
 * @版本： V1.0
 * @修改记录:
 */
public class DmmcModel  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -520882041393863458L;
	private String dm = null;// 代码
	private String mc = null;// 名称

	public DmmcModel() {
		super();
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

}
