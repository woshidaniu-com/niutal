/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

public class WjffmxModel implements Serializable{

	private static final long serialVersionUID = -2307161152572458586L;
	
	private String wjid;
	private String ffdx;
	private String dxtj;
	private String tjz;
	
	public String getWjid() {
		return wjid;
	}
	public void setWjid(String wjid) {
		this.wjid = wjid;
	}
	public String getFfdx() {
		return ffdx;
	}
	public void setFfdx(String ffdx) {
		this.ffdx = ffdx;
	}
	public String getDxtj() {
		return dxtj;
	}
	public void setDxtj(String dxtj) {
		this.dxtj = dxtj;
	}
	public String getTjz() {
		return tjz;
	}
	public void setTjz(String tjz) {
		this.tjz = tjz;
	}
	@Override
	public String toString() {
		return "WjffmxModel [wjid=" + wjid + ", ffdx=" + ffdx + ", dxtj=" + dxtj + ", tjz=" + tjz + "]";
	}
}
