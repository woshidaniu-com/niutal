package com.woshidaniu.entities;


public class IndexModel extends CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5859533510068196083L;
	private String gnmkdm;
	private String gnmkmc;
	private String fjgndm;//父级功能代码
	private String dyym;
	private String userName; // 用户名
	private String time;	//时间戳
	private String verify;	//证书号
	/**
	 * 是否功能页面：方便控制二级菜单进入页面后是卡片模式
	 */
	private String sfgnym;
	private String xssx;
	private String yhm;
	private String fsZgh;//附属职工号
	private String xm;
	private String jsdm = "";// 角色代码
	
	//用于点击更多时，用于首页面的参数传递
	private String gnmkdm_ej;//功能模块代码二级
	private String gnmkdm_sj;//果农模块代码三级

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

	public String getFjgndm() {
		return fjgndm;
	}

	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}

	public String getDyym() {
		return dyym;
	}

	public void setDyym(String dyym) {
		this.dyym = dyym;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getFsZgh() {
		return fsZgh;
	}

	public void setFsZgh(String fsZgh) {
		this.fsZgh = fsZgh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getGnmkdm_ej() {
		return gnmkdm_ej;
	}

	public void setGnmkdm_ej(String gnmkdmEj) {
		gnmkdm_ej = gnmkdmEj;
	}

	public String getGnmkdm_sj() {
		return gnmkdm_sj;
	}

	public void setGnmkdm_sj(String gnmkdmSj) {
		gnmkdm_sj = gnmkdmSj;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getSfgnym() {
		return sfgnym;
	}

	public void setSfgnym(String sfgnym) {
		this.sfgnym = sfgnym;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}
	
}
