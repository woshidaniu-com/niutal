/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

/**
 * @author Penghui.Qu(445)
 * 试题管理DAO
 * 
  * @author ：康康（1571）
 * 整理，优化
 * */
public class WjstxxModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String wjid;
	private String stid;
	private String xxid;
	private String xxmc;
	private String xssx;
	
	public String getWjid() {
		return wjid;
	}
	public void setWjid(String wjid) {
		this.wjid = wjid;
	}
	public String getStid() {
		return stid;
	}
	public void setStid(String stid) {
		this.stid = stid;
	}
	public String getXxid() {
		return xxid;
	}
	public void setXxid(String xxid) {
		this.xxid = xxid;
	}
	public String getXxmc() {
		return xxmc;
	}
	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	@Override
	public String toString() {
		return "WjstxxModel [wjid=" + wjid + ", stid=" + stid + ", xxid=" + xxid + ", xxmc=" + xxmc + ", xssx=" + xssx
				+ "]";
	}
}
