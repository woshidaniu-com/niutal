/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dao.entities;

/**
 * @description	： 导出列角色控制Model
 * @author 		：康康（1571）
 */
public class ExportAuthModel {
	
	//导出编号
	private String dcclbh;
	//字段
	private String zd;
	//授权值,暂时是角色
	private String sqz;
	
	public String getDcclbh() {
		return dcclbh;
	}
	public void setDcclbh(String dcclbh) {
		this.dcclbh = dcclbh;
	}
	public String getZd() {
		return zd;
	}
	public void setZd(String zd) {
		this.zd = zd;
	}
	public String getSqz() {
		return sqz;
	}
	public void setSqz(String sqz) {
		this.sqz = sqz;
	}
	@Override
	public String toString() {
		return "ExportAuthModel [dcclbh=" + dcclbh + ", zd=" + zd + ", sqz=" + sqz + "]";
	}
}
