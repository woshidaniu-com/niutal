package com.woshidaniu.entities;


/**
 * 类描述：专业代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class ZydmModel extends CommonModel {

	private static final long serialVersionUID = 3021173623687196606L;

	private String zyh_id; // 专业号ID
	private String zyh; // 专业代码
	private String zymc; // 专业名称
	private String zyjc; // 专业简称
	private String zyywmc; // 专业英文名称
	private String bmmc; // 部门名称
	private String jg_id; // 院系所号ID(从YXSDWB取)

	private String jcdm_id_xkml; // 关联基础代码表 DMLB=XKML
	private String xz; // 删除，培养方案实现
	private String jcdm_id_cc; // 基础代码表 DMLB=CC
	private String bzkzym; // 国家本专科专业码
	private String xkmlm; // 
	private String sfty; // 是否停用（1是 0否）
	private String bz; // 备注
	private String num;		//查询记录数量
	private String yxj;		// 优先级
	
	
	public String getZyh_id() {
		return zyh_id;
	}

	public void setZyh_id(String zyhId) {
		zyh_id = zyhId;
	}

	public String getZyh() {
		return zyh;
	}

	public void setZyh(String zyh) {
		this.zyh = zyh;
	}
	public String getJg_id() {
		return jg_id;
	}

	public void setJg_id(String jgId) {
		jg_id = jgId;
	}

	public String getBzkzym() {
		return bzkzym;
	}

	public void setBzkzym(String bzkzym) {
		this.bzkzym = bzkzym;
	}

	public String getZymc() {
		return zymc;
	}

	public void setZymc(String zymc) {
		this.zymc = zymc;
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


	public String getJcdm_id_xkml() {
		return jcdm_id_xkml;
	}

	public void setJcdm_id_xkml(String jcdmIdXkml) {
		jcdm_id_xkml = jcdmIdXkml;
	}

	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	public String getJcdm_id_cc() {
		return jcdm_id_cc;
	}

	public void setJcdm_id_cc(String jcdmIdCc) {
		jcdm_id_cc = jcdmIdCc;
	}


	public String getSfty() {
		return sfty;
	}

	public void setSfty(String sfty) {
		this.sfty = sfty;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getYxj() {
		return yxj;
	}

	public void setYxj(String yxj) {
		this.yxj = yxj;
	}

	public String getXkmlm() {
		return xkmlm;
	}

	public void setXkmlm(String xkmlm) {
		this.xkmlm = xkmlm;
	}
	
	
}
