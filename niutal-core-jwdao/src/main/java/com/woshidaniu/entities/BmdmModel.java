package com.woshidaniu.entities;


/**
 * 
 *@类名称:BmdmModel.java
 *@类描述：部门代码(组织机构)
 *@创建人：kangzhidong
 *@创建时间：2014-6-13 下午02:41:44
 *@版本号:v1.0
 */
public class BmdmModel extends CommonModel {

	private static final long serialVersionUID = -6130116296218495498L;
	private String jg_id; // 机构id
	private String jgdm; // 机构代码
	private String jgmc; // 机构名称
	private String jgywmc; // 机构英文名称
	private String jgjc; // 机构简称
	private String jgjp; // 机构简拼
	private String jgdz; // 机构地址
	private String lssjjgid; // 隶属上级机构id
	private String lsxqid; // 隶属校区id
	private String jgyxbs; // 机构有效标识
	private String sfjxbm; // 是否教学部门
	private String sfst; // 是否实体（1是 0否）
	private String jgyzbm; // 机构邮政编码
	private String jlny; // 建立年月
	private String fzrjgh; // 负责人教工号
	private String num;		//查询记录数量
	public String getJg_id() {
		return jg_id;
	}

	public void setJg_id(String jgId) {
		jg_id = jgId;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getJgywmc() {
		return jgywmc;
	}

	public void setJgywmc(String jgywmc) {
		this.jgywmc = jgywmc;
	}

	public String getJgjc() {
		return jgjc;
	}

	public void setJgjc(String jgjc) {
		this.jgjc = jgjc;
	}

	public String getJgjp() {
		return jgjp;
	}

	public void setJgjp(String jgjp) {
		this.jgjp = jgjp;
	}

	public String getJgdz() {
		return jgdz;
	}

	public void setJgdz(String jgdz) {
		this.jgdz = jgdz;
	}

	public String getLssjjgid() {
		return lssjjgid;
	}

	public void setLssjjgid(String lssjjgid) {
		this.lssjjgid = lssjjgid;
	}

	public String getLsxqid() {
		return lsxqid;
	}

	public void setLsxqid(String lsxqid) {
		this.lsxqid = lsxqid;
	}

	public String getJgyxbs() {
		return jgyxbs;
	}

	public void setJgyxbs(String jgyxbs) {
		this.jgyxbs = jgyxbs;
	}

	public String getSfjxbm() {
		return sfjxbm;
	}

	public void setSfjxbm(String sfjxbm) {
		this.sfjxbm = sfjxbm;
	}

	public String getSfst() {
		return sfst;
	}

	public void setSfst(String sfst) {
		this.sfst = sfst;
	}

	public String getJgyzbm() {
		return jgyzbm;
	}

	public void setJgyzbm(String jgyzbm) {
		this.jgyzbm = jgyzbm;
	}

	public String getJlny() {
		return jlny;
	}

	public void setJlny(String jlny) {
		this.jlny = jlny;
	}

	public String getFzrjgh() {
		return fzrjgh;
	}

	public void setFzrjgh(String fzrjgh) {
		this.fzrjgh = fzrjgh;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	
}
