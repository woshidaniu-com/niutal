package com.woshidaniu.workflow.model;

import com.woshidaniu.annotation.MyBatisBean;
import com.woshidaniu.annotation.SQLField;
import com.woshidaniu.annotation.Table;

@Table("niutal_xtgl_xxdlb")
public class XxdlModel extends MyBatisBean {
	@SQLField(key=true)
	private String id;			//编号 
	
	@SQLField("xxbt")
	private String xxbt;
	
	@SQLField("xxnr")
	private String xxnr;
	
	@SQLField("jsdm")
	private String jsdm;
	
	@SQLField("yhm")
	private String yhm;
	
	@SQLField("sfyx")
	private String sfyx;
	
	@SQLField("ljdz")
	private String ljdz;
	
	@SQLField("clzt")
	private String clzt;
	
	@SQLField("xxzh")
	private String xxzh;
	
	@SQLField("cjsj")
	private String cjsj;
	
	@SQLField("clsj")
	private String clsj;
	
	@SQLField("sjfwztj")
	private String sjfwztj;
	
	@SQLField("sjfwztj_qz")
	private String sjfwztj_qz;
	
	@SQLField("pId")
	private String pId;
	
	@SQLField("wId")
	private String wId;
	
	@SQLField("nodeId")
	private String nodeId;
	
	@SQLField("sjhm")
	private String sjhm;
	
	@SQLField("dzyx")
	private String dzyx;
	
	@SQLField("lx")
	private String lx;
	
	@SQLField("send_sms")
	private String send_sms;
	
	@SQLField("xxnr_sms")
	private String xxnr_sms;
	
	@SQLField("send_mail")
	private String send_mail;
	
	@SQLField("xxnr_mail")
	private String xxnr_mail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXxbt() {
		return xxbt;
	}

	public void setXxbt(String xxbt) {
		this.xxbt = xxbt;
	}

	public String getXxnr() {
		return xxnr;
	}

	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getLjdz() {
		return ljdz;
	}

	public void setLjdz(String ljdz) {
		this.ljdz = ljdz;
	}

	public String getClzt() {
		return clzt;
	}

	public void setClzt(String clzt) {
		this.clzt = clzt;
	}

	public String getXxzh() {
		return xxzh;
	}

	public void setXxzh(String xxzh) {
		this.xxzh = xxzh;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getClsj() {
		return clsj;
	}

	public void setClsj(String clsj) {
		this.clsj = clsj;
	}

	public String getSjfwztj() {
		return sjfwztj;
	}

	public void setSjfwztj(String sjfwztj) {
		this.sjfwztj = sjfwztj;
	}

	public String getSjfwztj_qz() {
		return sjfwztj_qz;
	}

	public void setSjfwztj_qz(String sjfwztj_qz) {
		this.sjfwztj_qz = sjfwztj_qz;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getwId() {
		return wId;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getDzyx() {
		return dzyx;
	}

	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getSend_sms() {
		return send_sms;
	}

	public void setSend_sms(String send_sms) {
		this.send_sms = send_sms;
	}

	public String getXxnr_sms() {
		return xxnr_sms;
	}

	public void setXxnr_sms(String xxnr_sms) {
		this.xxnr_sms = xxnr_sms;
	}

	public String getSend_mail() {
		return send_mail;
	}

	public void setSend_mail(String send_mail) {
		this.send_mail = send_mail;
	}

	public String getXxnr_mail() {
		return xxnr_mail;
	}

	public void setXxnr_mail(String xxnr_mail) {
		this.xxnr_mail = xxnr_mail;
	}
	
	
	
}
