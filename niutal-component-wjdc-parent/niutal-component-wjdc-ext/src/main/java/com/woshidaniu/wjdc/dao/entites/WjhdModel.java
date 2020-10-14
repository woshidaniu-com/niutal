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
public class WjhdModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String daid;
	private String djrid;
	private String wjid;
	private String stid;
	private String xxid;
	private String txnr;
	private String hdsx;
	
	public String getDaid() {
		return daid;
	}
	public void setDaid(String daid) {
		this.daid = daid;
	}
	public String getDjrid() {
		return djrid;
	}
	public void setDjrid(String djrid) {
		this.djrid = djrid;
	}
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
	public String getTxnr() {
		return txnr;
	}
	public void setTxnr(String txnr) {
		this.txnr = txnr;
	}
	public String getHdsx() {
		return hdsx;
	}
	public void setHdsx(String hdsx) {
		this.hdsx = hdsx;
	}
	@Override
	public String toString() {
		return "WjhdModel [daid=" + daid + ", djrid=" + djrid + ", wjid=" + wjid + ", stid=" + stid + ", xxid=" + xxid
				+ ", txnr=" + txnr + ", hdsx=" + hdsx + "]";
	}
}
