package com.woshidaniu.entities;


/**
 * 
 *@类名称:XqxxwhModel.java
 *@类描述：校区信息维护
 *@创建人：zzh
 *@创建时间：2016-3-31
 */
public class XqxxwhModel extends CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xqh; // 校区代码
	private String xqmc; // 校区英文名称
	private String xqdz; // 校区地址
	private String zjr; // 增加人
	private String zjsj; // 增加时间
	private String xgr; // 修改人
	private String xgsj; // 修改时间
	private String xqyzbm; // 校区邮政编码
	private String xqfzrzgh; // 校区负责人职工号
	private String kkhbxqh_id; // 可跨校区合班
	private String kkxkxq_id; // 可跨校区选课
	private String kkhbxqhmc; // 可跨校区合班
	private String kkxkxqmc; // 可跨校区选课
	
	public String getKkhbxqhmc() {
		return kkhbxqhmc;
	}

	public void setKkhbxqhmc(String kkhbxqhmc) {
		this.kkhbxqhmc = kkhbxqhmc;
	}

	public String getKkxkxqmc() {
		return kkxkxqmc;
	}

	public void setKkxkxqmc(String kkxkxqmc) {
		this.kkxkxqmc = kkxkxqmc;
	}

	public String getXqh() {
		return xqh;
	}

	public void setXqh(String xqh) {
		this.xqh = xqh;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXqdz() {
		return xqdz;
	}

	public void setXqdz(String xqdz) {
		this.xqdz = xqdz;
	}

	public String getZjr() {
		return zjr;
	}

	public void setZjr(String zjr) {
		this.zjr = zjr;
	}

	public String getZjsj() {
		return zjsj;
	}

	public void setZjsj(String zjsj) {
		this.zjsj = zjsj;
	}

	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getXqyzbm() {
		return xqyzbm;
	}

	public void setXqyzbm(String xqyzbm) {
		this.xqyzbm = xqyzbm;
	}

	public String getXqfzrzgh() {
		return xqfzrzgh;
	}

	public void setXqfzrzgh(String xqfzrzgh) {
		this.xqfzrzgh = xqfzrzgh;
	}

	public String getKkhbxqh_id() {
		return kkhbxqh_id;
	}

	public void setKkhbxqh_id(String kkhbxqhId) {
		kkhbxqh_id = kkhbxqhId;
	}

	public String getKkxkxq_id() {
		return kkxkxq_id;
	}

	public void setKkxkxq_id(String kkxkxqId) {
		kkxkxq_id = kkxkxqId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
