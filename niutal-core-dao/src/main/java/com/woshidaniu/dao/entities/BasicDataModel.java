package com.woshidaniu.dao.entities;

import java.io.Serializable;

/**
 * 基础数据MODEL
 * 
 * Create on 2013-7-5 下午01:57:10 
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013  Version 1.0 
 *
 * @author : HJL [718] 
 */
public class BasicDataModel implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8314923268214555410L;
	
	// 类型
	private String lx;
	
	// 代码
	private String dm;
	
	// 名称
	private String mc;

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
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
