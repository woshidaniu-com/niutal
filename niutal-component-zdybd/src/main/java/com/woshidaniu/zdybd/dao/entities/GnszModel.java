package com.woshidaniu.zdybd.dao.entities;

/**
 * 
 * @系统名称: 新框架
 * @模块名称: 自定义表单
 * @类功能描述: 功能设置表
 * @作者： ligl
 * @时间： 2013-11-26 下午03:50:38
 * @版本： V1.0
 * @修改记录:
 */
public class GnszModel implements java.io.Serializable {
	private static final long serialVersionUID = 4474386158325500625L;
	private String gndm;// 功能代码
	private String gnmc;// 功能名称
	private String gnlx;// 功能类型 1:增加 2:修改 3:查看";
	private String yzsz;// 验证设置 js方法
	private String bhmk;// 包含模块 1:包含模块;0:不包含模块
	private String flszid;//
	private String pzlx;//

	public GnszModel() {
		super();
	}

	public String getGndm() {
		return gndm;
	}

	public void setGndm(String gndm) {
		this.gndm = gndm;
	}

	public String getGnmc() {
		return gnmc;
	}

	public void setGnmc(String gnmc) {
		this.gnmc = gnmc;
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

	public String getFlszid() {
		return flszid;
	}

	public void setFlszid(String flszid) {
		this.flszid = flszid;
	}

	public String getPzlx() {
		return pzlx;
	}

	public void setPzlx(String pzlx) {
		this.pzlx = pzlx;
	}

	
}
