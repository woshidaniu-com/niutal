package com.woshidaniu.jcsj.dao.entities;

import com.woshidaniu.common.query.ModelBase;

public class BjdmModel extends ModelBase {

	private static final long serialVersionUID = 1L;
	private String bjdm_id ;//班级代码ID（自增长） 
	private String bjdm ;//班级代码 
	private String bjmc ;//班级名称 
	private String zydm_id ;//所属专业（关联专业代码表ID) 
	private String bmdm_id ;//所在学院（关联部门代码表ID） 
	private String njdm_id ;//所属年级（关联年级代码表ID）
	private String zymc;
	private String bmmc;
	
	public String getBjdm_id() {
		return bjdm_id;
	}
	public void setBjdm_id(String bjdm_id) {
		this.bjdm_id = bjdm_id;
	}
	public String getBjdm() {
		return bjdm;
	}
	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
	}
	public String getBjmc() {
		return bjmc;
	}
	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}
	public String getZydm_id() {
		return zydm_id;
	}
	public void setZydm_id(String zydm_id) {
		this.zydm_id = zydm_id;
	}
	public String getBmdm_id() {
		return bmdm_id;
	}
	public void setBmdm_id(String bmdm_id) {
		this.bmdm_id = bmdm_id;
	}
	public String getNjdm_id() {
		return njdm_id;
	}
	public void setNjdm_id(String njdm_id) {
		this.njdm_id = njdm_id;
	}
	public String getZymc() {
		return zymc;
	}
	public void setZymc(String zymc) {
		this.zymc = zymc;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	 
	
}
