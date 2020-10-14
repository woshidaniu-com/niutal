package com.woshidaniu.entities.webservice;

/**
 *@类名称:DbsyModel.java
 *@类描述：待办事宜
 *@创建人："huangrz"
 *@创建时间：2014-8-28 下午02:10:17
 *@版本号:v1.0
 */
public class DbsyModel {
	private String url;                 //链接地址
	private String dblx;                //类型   ywdm
	private String dbmc;                //代办名称
	private String dbs;                 //待办条数	
	
	public String getDblx() {
		return dblx;
	}
	public void setDblx(String dblx) {
		this.dblx = dblx;
	}
	public void setDbmc(String dbmc) {
		this.dbmc = dbmc;
	}
	public String getDbmc() {
		return dbmc;
	}
	public void setDbs(String dbs) {
		this.dbs = dbs;
	}
	public String getDbs() {
		return dbs;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}	
}
