package com.woshidaniu.entities;

import com.woshidaniu.common.query.QueryModel;

@SuppressWarnings("serial")
public class RzglModel extends CommonModel {

	//日志组号ID
	private String rzzh_id;
	//操作编号
	private String czbh;
	//用户名
	private String yhm;
	// 操作人
	private String czr;
	//操作日期
	private String czrq;
	//操作模块
	private String czmk;
	//业务名称
	private String ywmc;
	//操作类型
	private String czlx;
	//操作描述
	private String czms;
	//IP地址
	private String ipdz;
	//主机名
	private String zjm;
	//所属部门名称
	private String bmmc;

	private String czkssj;
	private String czjssj;
	private String czmkmc;

	private QueryModel page;

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getCzkssj() {
		return czkssj;
	}

	public void setCzkssj(String czkssj) {
		this.czkssj = czkssj;
	}

	public String getCzjssj() {
		return czjssj;
	}

	public void setCzjssj(String czjssj) {
		this.czjssj = czjssj;
	}

	public QueryModel getPage() {
		return page;
	}

	public void setPage(QueryModel page) {
		this.page = page;
	}

	public String getCzbh() {
		return czbh;
	}

	public void setCzbh(String czbh) {
		this.czbh = czbh;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getCzrq() {
		return czrq;
	}

	public void setCzrq(String czrq) {
		this.czrq = czrq;
	}

	public String getCzmk() {
		return czmk;
	}

	public void setCzmk(String czmk) {
		this.czmk = czmk;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getCzms() {
		return czms;
	}

	public void setCzms(String czms) {
		this.czms = czms;
	}

	public String getIpdz() {
		return ipdz;
	}

	public void setIpdz(String ipdz) {
		this.ipdz = ipdz;
	}

	public String getZjm() {
		return zjm;
	}

	public void setZjm(String zjm) {
		this.zjm = zjm;
	}

	public String getCzmkmc() {
		return czmkmc;
	}

	public void setCzmkmc(String czmkmc) {
		this.czmkmc = czmkmc;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public String getRzzh_id() {
		return rzzh_id;
	}

	public void setRzzh_id(String rzzhId) {
		rzzh_id = rzzhId;
	}

	
}