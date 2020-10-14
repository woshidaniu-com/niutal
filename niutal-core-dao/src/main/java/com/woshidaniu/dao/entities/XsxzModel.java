package com.woshidaniu.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 学生信息
 * @author Jiangdong.Yi
 *
 */
public class XsxzModel extends ModelBase {

	private static final long serialVersionUID = 1L;
	//学生信息表
	private String xh;	//学号
	private String xm;	//姓名
	private String xbdm;	//性别
	private String mzdm;	//名族
	private String zzmmdm;	//政治面貌
	private String sfzh;	//身份证号
	private String csrq;	//出生日期
	private String syszd;	//生源所在地
	private String hkszd;	//户口所在地
	private String jg;	//籍贯
	private String bjdm;	//班级代码
	private String xlcc;	//学历层次
	private String xjzt;	//学籍状态
	private String sfzxs;	//是否在校生
	private String xszt;	//学生状态，1为在校生，0为历史生
	private String sjhm;	//手机号码
	private String gddh;	//固定电话
	private String email;	//电子邮箱
	private String qqhm;	//QQ号码
	private String jtdz;	//家庭地址
	private String jtyb;	//家庭邮编
	private String jtdh;	//家庭电话
	
	private String njdm;	//年级代码
	private String njmc;	//年级名称
	private String xydm;	//学院代码
	private String xymc;	//学院名称
	private String zydm;	//专业代码
	private String zymc;	//专业名称
	private String bjmc;	//班级名称
	private String xbmc;	//性别名称
	
	//非业务字段
	private String xlccmc;
	
	private String sfdx="0";//是否多选 ;  默认单选
	private String doSearch;//查询页面跳转
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXbdm() {
		return xbdm;
	}

	public void setXbdm(String xbdm) {
		this.xbdm = xbdm;
	}

	public String getMzdm() {
		return mzdm;
	}

	public void setMzdm(String mzdm) {
		this.mzdm = mzdm;
	}

	public String getZzmmdm() {
		return zzmmdm;
	}

	public void setZzmmdm(String zzmmdm) {
		this.zzmmdm = zzmmdm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getCsrq() {
		return csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	public String getSyszd() {
		return syszd;
	}

	public void setSyszd(String syszd) {
		this.syszd = syszd;
	}

	public String getHkszd() {
		return hkszd;
	}

	public void setHkszd(String hkszd) {
		this.hkszd = hkszd;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getXlcc() {
		return xlcc;
	}

	public void setXlcc(String xlcc) {
		this.xlcc = xlcc;
	}

	public String getXjzt() {
		return xjzt;
	}

	public void setXjzt(String xjzt) {
		this.xjzt = xjzt;
	}

	public String getSfzxs() {
		return sfzxs;
	}

	public void setSfzxs(String sfzxs) {
		this.sfzxs = sfzxs;
	}

	public String getXszt() {
		return xszt;
	}

	public void setXszt(String xszt) {
		this.xszt = xszt;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getGddh() {
		return gddh;
	}

	public void setGddh(String gddh) {
		this.gddh = gddh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQqhm() {
		return qqhm;
	}

	public void setQqhm(String qqhm) {
		this.qqhm = qqhm;
	}

	public String getJtdz() {
		return jtdz;
	}

	public void setJtdz(String jtdz) {
		this.jtdz = jtdz;
	}

	public String getJtyb() {
		return jtyb;
	}

	public void setJtyb(String jtyb) {
		this.jtyb = jtyb;
	}

	public String getJtdh() {
		return jtdh;
	}

	public void setJtdh(String jtdh) {
		this.jtdh = jtdh;
	}
	public String getSfdx() {
		return sfdx;
	}

	public void setSfdx(String sfdx) {
		this.sfdx = sfdx;
	}
	public String getBjdm() {
		return bjdm;
	}
	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
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
	public String getXydm() {
		return xydm;
	}
	public void setXydm(String xydm) {
		this.xydm = xydm;
	}
	public String getXymc() {
		return xymc;
	}
	public void setXymc(String xymc) {
		this.xymc = xymc;
	}
	public String getZydm() {
		return zydm;
	}
	public void setZydm(String zydm) {
		this.zydm = zydm;
	}
	public String getZymc() {
		return zymc;
	}
	public void setZymc(String zymc) {
		this.zymc = zymc;
	}
	public String getBjmc() {
		return bjmc;
	}
	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}
	public String getXbmc() {
		return xbmc;
	}
	public void setXbmc(String xbmc) {
		this.xbmc = xbmc;
	}
	public String getDoSearch() {
		return doSearch;
	}
	public void setDoSearch(String doSearch) {
		this.doSearch = doSearch;
	}
	public String getXlccmc() {
		return xlccmc;
	}
	public void setXlccmc(String xlccmc) {
		this.xlccmc = xlccmc;
	}
	
}
