/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dao.entities;

public class ExportConfigPhModel {

	private String id;
	
	private String mc;
	
	private String dcclbh;
	
	private String zd;
	
	private String zdmc;
	
	private String zgh;

	private String xssx;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

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

	public String getZgh() {
		return zgh;
	}

	public void setZgh(String zgh) {
		this.zgh = zgh;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	@Override
	public String toString() {
		return "ExportConfigPhModel [id=" + id + ", mc=" + mc + ", dcclbh=" + dcclbh + ", zd=" + zd + ", zdmc=" + zdmc + ", zgh=" + zgh + ", xssx=" + xssx + "]";
	}
}