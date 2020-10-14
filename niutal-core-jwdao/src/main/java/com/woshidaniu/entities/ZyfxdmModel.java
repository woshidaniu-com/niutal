package com.woshidaniu.entities;


/**
 * 
 *@类名称:ZyfxdmModel.java
 *@类描述：专业方向代码
 *@创建人：kangzhidong
 *@创建时间：2014-6-26 下午03:23:43
 *@版本号:v1.0
 */
public class ZyfxdmModel extends CommonModel {

	private static final long serialVersionUID = 3021173623687196606L;

	private String zyfx_id; // 专业方向ID
	private String zyfxdm; // 专业方向代码
	private String zyfxmc; // 专业方向名称
	private String zyfxywm; // 专业方向英文名
	private String zyfxywsx; // 专业方向英文缩写
	private String sxxnxq; // 专业方向生效学年学期
	private String sxsj; // 专业方向生效时间
	
	private String zyh_id; // 专业号ID
	private String zyh; // 专业代码
	private String zymc; // 专业名称
	private String zyjc; // 专业简称
	private String zyywmc; // 专业英文名称
	
	private String bmmc; // 部门名称
	private String jg_id; // 院系所号ID(从YXSDWB取)

	private String njdm_id; // 专业号ID
	private String njdm; // 专业代码
	private String njmc; // 专业名称
    
	private String jcdm_id_xkml; // 关联基础代码表 DMLB=XKML
	private String xz; // 删除，培养方案实现
	private String jcdm_id_cc; // 基础代码表 DMLB=CC
	private String bzkzym; // 国家本专科专业码
	private String sfty; // 是否停用（1是 0否）
	private String bz; // 备注
	private String num;		//查询记录数量
	public String getZyfx_id() {
		return zyfx_id;
	}
	public void setZyfx_id(String zyfxId) {
		zyfx_id = zyfxId;
	}
	public String getZyfxdm() {
		return zyfxdm;
	}
	public void setZyfxdm(String zyfxdm) {
		this.zyfxdm = zyfxdm;
	}
	public String getZyfxmc() {
		return zyfxmc;
	}
	public void setZyfxmc(String zyfxmc) {
		this.zyfxmc = zyfxmc;
	}
	public String getZyfxywm() {
		return zyfxywm;
	}
	public void setZyfxywm(String zyfxywm) {
		this.zyfxywm = zyfxywm;
	}
	public String getZyfxywsx() {
		return zyfxywsx;
	}
	public void setZyfxywsx(String zyfxywsx) {
		this.zyfxywsx = zyfxywsx;
	}
	public String getSxxnxq() {
		return sxxnxq;
	}
	public void setSxxnxq(String sxxnxq) {
		this.sxxnxq = sxxnxq;
	}
	public String getSxsj() {
		return sxsj;
	}
	public void setSxsj(String sxsj) {
		this.sxsj = sxsj;
	}
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
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getJg_id() {
		return jg_id;
	}
	public void setJg_id(String jgId) {
		jg_id = jgId;
	}
	public String getNjdm_id() {
		return njdm_id;
	}
	public void setNjdm_id(String njdmId) {
		njdm_id = njdmId;
	}
	public String getNjdm() {
		return njdm;
	}
	public void setNjdm(String njdm) {
		this.njdm = njdm;
	}
	public String getNjmc() {
		return njmc;
	}
	public void setNjmc(String njmc) {
		this.njmc = njmc;
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
	public String getBzkzym() {
		return bzkzym;
	}
	public void setBzkzym(String bzkzym) {
		this.bzkzym = bzkzym;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}
