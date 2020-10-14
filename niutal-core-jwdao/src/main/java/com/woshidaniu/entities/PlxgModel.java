package com.woshidaniu.entities;


/**
 * 
 *@类名称:PlxgModel.java
 *@类描述：功能数据批量修改配置Model
 *@创建人：kangzhidong
 *@创建时间：2015-4-10 下午12:29:28
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class PlxgModel extends CommonModel {

	private String gnmkdm; // 系统功能代码
	private String gnmkmc; // 批量修改数据的功能名称（如：个人信息校验）
	private String zddm; // 字段代码：表结构的字段列名称
	private String zdmc; // 字段名称：该字段的文本名称；如：姓名
	private String zdcd; // 字段长度:该字段的最大长度
	private String zdbm; // 字段所属表的名称
	private String zd_owner; // 字段所属数据库用户
	private String xssx; // 字段显示的顺序
	private String sfqy; // 字段是否启用

	private String zdz; // 字段值
	private String xgfs; // 字段修改方式：1：替换，2：前缀，3：后缀

	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getGnmkmc() {
		return gnmkmc;
	}

	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}

	public String getZddm() {
		return zddm;
	}

	public void setZddm(String zddm) {
		this.zddm = zddm;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZdbm() {
		return zdbm;
	}

	public void setZdbm(String zdbm) {
		this.zdbm = zdbm;
	}

	public String getZd_owner() {
		return zd_owner;
	}

	public void setZd_owner(String zdOwner) {
		zd_owner = zdOwner;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}


	public String getZdz() {
		return zdz;
	}

	public void setZdz(String zdz) {
		this.zdz = zdz;
	}

	public String getXgfs() {
		return xgfs;
	}

	public void setXgfs(String xgfs) {
		this.xgfs = xgfs;
	}

	public String getZdcd() {
		return zdcd;
	}

	public void setZdcd(String zdcd) {
		this.zdcd = zdcd;
	}

	
}
