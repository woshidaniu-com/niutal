package com.woshidaniu.entities;


/**
 * 类描述：班级代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class BjdmModel extends CommonModel {

	private static final long serialVersionUID = -3418198086311187767L;

	private String bh_id; // 班号ID
	private String bh; // 班号
	private String bj; // 班级
	private String zyh_id; // 所属专业（关联专业代码表ID)
	private String zyh; // 专业名称
	private String dwh_id; // 所在学院（关联部门代码表ID）
	private String jgmc; // 部门名称
	private String njdm_id; // 所属年级（关联年级代码表ID）
	private String njmc; // 年级名称
	private String xqh_id; // 所属校区（关联校区代码表ID）
	private String xqmc; // 校区名称
	private String jbny; // 建班年月
	private String bzrjgh_id; // 班主任教工号ID
	private String bzxh_id; // 班长学号
	private String fdyh_id; // 辅导员号
	private String zxrs; // 在校人数
	private String bz; // 备注
	private String num;		//查询记录数量

	public String getBh_id() {
		return bh_id;
	}

	public void setBh_id(String bhId) {
		bh_id = bhId;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}


	public String getDwh_id() {
		return dwh_id;
	}

	public void setDwh_id(String dwhId) {
		dwh_id = dwhId;
	}

	public String getNjdm_id() {
		return njdm_id;
	}

	public void setNjdm_id(String njdmId) {
		njdm_id = njdmId;
	}

	public String getNjmc() {
		return njmc;
	}

	public void setNjmc(String njmc) {
		this.njmc = njmc;
	}

	public String getXqh_id() {
		return xqh_id;
	}

	public void setXqh_id(String xqhId) {
		xqh_id = xqhId;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getJbny() {
		return jbny;
	}

	public void setJbny(String jbny) {
		this.jbny = jbny;
	}

	public String getBzrjgh_id() {
		return bzrjgh_id;
	}

	public void setBzrjgh_id(String bzrjghId) {
		bzrjgh_id = bzrjghId;
	}

	public String getBzxh_id() {
		return bzxh_id;
	}

	public void setBzxh_id(String bzxhId) {
		bzxh_id = bzxhId;
	}

	public String getFdyh_id() {
		return fdyh_id;
	}

	public void setFdyh_id(String fdyhId) {
		fdyh_id = fdyhId;
	}

	public String getZxrs() {
		return zxrs;
	}

	public void setZxrs(String zxrs) {
		this.zxrs = zxrs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	
}
