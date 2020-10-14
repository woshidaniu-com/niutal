package com.woshidaniu.zdybd.dao.entities;

/**
 * 
 * @系统名称: 学生工作管理系统
 * @模块名称: 自定义表单
 * @类功能描述: 分类设置表
 * @作者： ligl
 * @时间： 2013-11-26 下午03:50:38
 * @版本： V1.0
 * @修改记录:
 */
public class FlszModel implements java.io.Serializable {
	private static final long serialVersionUID = -2924542695182937526L;
	private String flszid;// 分类设置id
	private String gndm;// 功能代码
	private String flflszid;// 父类id
	private String flmc;// 分类名称
	private String flsm;// 分类说明
	private String bdls;// 表单列数 为表单整体字段列数设置，不包含照片列(由程序自动计算)
	private String bdms;// 表单模式 1:单条记录模式:2:多条记录模式;3:功能自定义代码
	private String sfqy;// 是否启用 1:是,0:否
	private String sfzk;// 是否展开 1:是,0:否
	private String sfmk;// 是否模块 1:是,0:否
	private String bdszz;// 表单设置值
	private String xsxx;// 显示顺序
	private String gnlx;// 功能类型
	private String yzsz;// 验证设置

	private String bhmk;// 包含模块 1:包含模块;0:不包含模块

	public FlszModel() {
		super();
	}

	public String getFlszid() {
		return flszid;
	}

	public void setFlszid(String flszid) {
		this.flszid = flszid;
	}

	public String getGndm() {
		return gndm;
	}

	public void setGndm(String gndm) {
		this.gndm = gndm;
	}

	public String getFlflszid() {
		return flflszid;
	}

	public void setFlflszid(String flflszid) {
		this.flflszid = flflszid;
	}

	public String getFlmc() {
		return flmc;
	}

	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}

	public String getFlsm() {
		return flsm;
	}

	public void setFlsm(String flsm) {
		this.flsm = flsm;
	}

	public String getBdls() {
		return bdls;
	}

	public void setBdls(String bdls) {
		this.bdls = bdls;
	}

	public String getBdms() {
		return bdms;
	}

	public void setBdms(String bdms) {
		this.bdms = bdms;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public String getSfzk() {
		return sfzk;
	}

	public void setSfzk(String sfzk) {
		this.sfzk = sfzk;
	}

	public String getSfmk() {
		return sfmk;
	}

	public void setSfmk(String sfmk) {
		this.sfmk = sfmk;
	}

	public String getBdszz() {
		return bdszz;
	}

	public void setBdszz(String bdszz) {
		this.bdszz = bdszz;
	}

	public String getXsxx() {
		return xsxx;
	}

	public void setXsxx(String xsxx) {
		this.xsxx = xsxx;
	}

	public String getGnlx() {
		return gnlx;
	}

	public void setGnlx(String gnlx) {
		this.gnlx = gnlx;
	}

	public String getYzsz() {
		return yzsz;
	}

	public void setYzsz(String yzsz) {
		this.yzsz = yzsz;
	}

	public String getBhmk() {
		return bhmk;
	}

	public void setBhmk(String bhmk) {
		this.bhmk = bhmk;
	}

}
