package com.woshidaniu.dao.entities;

import java.io.Serializable;

/**
 * 国标基础数据
 * 
 * Create on 2013-7-16 下午05:10:38
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public class BaseGBModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// 代码
	private String				dm;

	// 名称
	private String				mc;

	// 说明
	private String				sm;

	// 状态
	private String				zt;

	// 最后更新时间
	private String				zhgxsj;

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

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getZhgxsj() {
		return zhgxsj;
	}

	public void setZhgxsj(String zhgxsj) {
		this.zhgxsj = zhgxsj;
	}

}
