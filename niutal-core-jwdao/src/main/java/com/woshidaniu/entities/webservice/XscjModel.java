package com.woshidaniu.entities.webservice;

/**
 *@类名称:XscjModel.java
 *@类描述：学生成绩
 *@创建人："huangrz"
 *@创建时间：2014-8-28 下午02:10:17
 *@版本号:v1.0
 */
public class XscjModel {
	private String xnmc;        //学年
	private String xqmc;        //学期
	private String kcmc;        //课程
	private String cj;          //成绩
	private String cjxz;        //成绩性质
	private String kcxz;        //课程性质
	
	public String getXnmc() {
		return xnmc;
	}
	public void setXnmc(String xnmc) {
		this.xnmc = xnmc;
	}
	public String getXqmc() {
		return xqmc;
	}
	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}
	public String getKcmc() {
		return kcmc;
	}
	public void setKcmc(String kcmc) {
		this.kcmc = kcmc;
	}
	public String getCj() {
		return cj;
	}
	public void setCj(String cj) {
		this.cj = cj;
	}
	public String getCjxz() {
		return cjxz;
	}
	public void setCjxz(String cjxz) {
		this.cjxz = cjxz;
	}
	public String getKcxz() {
		return kcxz;
	}
	public void setKcxz(String kcxz) {
		this.kcxz = kcxz;
	}	
}
