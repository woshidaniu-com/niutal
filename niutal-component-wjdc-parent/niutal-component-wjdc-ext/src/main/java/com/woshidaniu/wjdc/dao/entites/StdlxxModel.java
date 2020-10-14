/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

/**
 * @description	： 试题大类
 * @author 		：康康（1571）
 */
public class StdlxxModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 分类ID
	 */
	private String flid;
	/**
	 * 问卷ID
	 */
	private String wjid;
	/**
	 * 分类名称
	 */
	private String flmc;
	/**
	 * 显示顺序
	 */
	private String xssx;
	/**
	 * 对齐方式 left,right,center
	 */
	private String dqfs;
	/**
	 * 问卷元素序号
	 */
	private String wjysxh;

	public String getFlid() {
		return flid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public String getWjid() {
		return wjid;
	}

	public void setWjid(String wjid) {
		this.wjid = wjid;
	}

	public String getFlmc() {
		return flmc;
	}

	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getDqfs() {
		return dqfs;
	}

	public void setDqfs(String dqfs) {
		this.dqfs = dqfs;
	}

	public String getWjysxh() {
		return wjysxh;
	}

	public void setWjysxh(String wjysxh) {
		this.wjysxh = wjysxh;
	}

	@Override
	public String toString() {
		return "StdlxxModel [flid=" + flid + ", wjid=" + wjid + ", flmc=" + flmc + ", xssx=" + xssx + ", dqfs=" + dqfs
				+ ", wjysxh=" + wjysxh + "]";
	}
	
}
