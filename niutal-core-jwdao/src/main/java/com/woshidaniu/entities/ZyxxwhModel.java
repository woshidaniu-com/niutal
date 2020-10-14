package com.woshidaniu.entities;

/**
 * 
 * @类名称:ZyxxwhModel.java
 * @类描述：专业信息维护
 * @创建人：wjy
 * @创建时间：2014-10-22 下午04:21:32
 * @版本号:v1.0
 */
public class ZyxxwhModel extends CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pcdm;// 批次代码
	private String zyjc; // 专业简称
	private String zyywmc; // 专业英文名称
	private String cydm_id_xkml; // 学科门类(关联常用代码表id)
	private String xz; // 学制
	private String ccdm; // 层次（关联常用代码表id）
	private String sfty; // 是否停用（1是 0否）
	private String xkmlm; // 学科门类码
	private String bzkzym; // 国家本专科专业码
	private String jlny; // 建立年月
	private String yjszym; // 研究生专业码
	private String yxj; // 排课优先级1:特别 2:优先 3:普通 4:最后
	private String xkmlmc; // 学科门类名称
	private String ccmc; // 层次名称
	private String bzkzymc; // 本专科专业名称
	private String yjszymc; // 研究生专业名称
	private String zyjj; // 专业简介
	private String zyjj_cx; // 专业简介（查询显示）
	private String dlbs; // 大类标识

	public String getPcdm() {
		return pcdm;
	}

	public void setPcdm(String pcdm) {
		this.pcdm = pcdm;
	}

	public String getZyjc() {

		return zyjc;
	}

	public void setZyjc(String zyjc) {
		this.zyjc = zyjc;
	}

	public String getZyywmc() {
		return zyywmc;
	}

	public void setZyywmc(String zyywmc) {
		this.zyywmc = zyywmc;
	}

	public String getCydm_id_xkml() {
		return cydm_id_xkml;
	}

	public void setCydm_id_xkml(String cydmIdXkml) {
		cydm_id_xkml = cydmIdXkml;
	}

	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	public String getCcdm() {
		return ccdm;
	}

	public void setCcdm(String ccdm) {
		this.ccdm = ccdm;
	}

	public String getSfty() {
		return sfty;
	}

	public void setSfty(String sfty) {
		this.sfty = sfty;
	}

	public String getXkmlm() {
		return xkmlm;
	}

	public void setXkmlm(String xkmlm) {
		this.xkmlm = xkmlm;
	}

	public String getBzkzym() {
		return bzkzym;
	}

	public void setBzkzym(String bzkzym) {
		this.bzkzym = bzkzym;
	}

	public String getJlny() {
		return jlny;
	}

	public void setJlny(String jlny) {
		this.jlny = jlny;
	}

	public String getYjszym() {
		return yjszym;
	}

	public void setYjszym(String yjszym) {
		this.yjszym = yjszym;
	}

	public String getYxj() {
		return yxj;
	}

	public void setYxj(String yxj) {
		this.yxj = yxj;
	}

	public String getXkmlmc() {
		return xkmlmc;
	}

	public void setXkmlmc(String xkmlmc) {
		this.xkmlmc = xkmlmc;
	}

	public String getCcmc() {
		return ccmc;
	}

	public void setCcmc(String ccmc) {
		this.ccmc = ccmc;
	}

	public String getBzkzymc() {
		return bzkzymc;
	}

	public void setBzkzymc(String bzkzymc) {
		this.bzkzymc = bzkzymc;
	}

	public String getYjszymc() {
		return yjszymc;
	}

	public void setYjszymc(String yjszymc) {
		this.yjszymc = yjszymc;
	}

	public String getZyjj() {
		return zyjj;
	}

	public void setZyjj(String zyjj) {
		this.zyjj = zyjj;
	}

	public String getZyjj_cx() {
		return zyjj_cx;
	}

	public void setZyjj_cx(String zyjjCx) {
		zyjj_cx = zyjjCx;
	}

	public String getDlbs() {
		return dlbs;
	}

	public void setDlbs(String dlbs) {
		this.dlbs = dlbs;
	}
}
