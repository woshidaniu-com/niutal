/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

/**
 * @author xiaokang 1036
 * 导入辅助model
 */
public class DrFzModel {

	private String fzid;
	
	private String drmkdm;
	
	private String fzmc;
	
	private String pz;
	
	private String drl;

	public String getFzid() {
		return fzid;
	}

	public void setFzid(String fzid) {
		this.fzid = fzid;
	}

	public String getDrmkdm() {
		return drmkdm;
	}

	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}

	public String getPz() {
		return pz;
	}

	public void setPz(String pz) {
		this.pz = pz;
	}

	public String getFzmc() {
		return fzmc;
	}

	public void setFzmc(String fzmc) {
		this.fzmc = fzmc;
	}

	public String getDrl() {
		return drl;
	}

	public void setDrl(String drl) {
		this.drl = drl;
	}

	@Override
	public String toString() {
		return "DrFzModel [fzid=" + fzid + ", drmkdm=" + drmkdm + ", fzmc=" + fzmc + ", pz=" + pz + ", drl=" + drl
				+ "]";
	}
}
