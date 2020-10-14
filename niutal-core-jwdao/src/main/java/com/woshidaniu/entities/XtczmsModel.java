package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称:XtczmsModel.java
 *@类描述：系统操作描述bean
 *@创建人：kangzhidong
 *@创建时间：2015-3-5 下午05:15:29
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class XtczmsModel extends ModelBase {

	// 系统操作表描述ID
	private String xtczms_id;
	// 功能模块代码
	private String gndm;
	// 功能模块对应页面路径
	private String dyym;
	// 功能模块名称
	private String gnmkmc;
	// 操作代码
	private String czdm;
	// 操作名称
	private String czmc;
	// 操作描述信息
	private String czms;
	// 备注信息
	private String bz;
	// 是否使用（1：使用；2：停用）
	private String sfsy;
	
	public XtczmsModel() {
	}

	public XtczmsModel(String gnmkdm, String czdm) {
		this.gndm = gnmkdm;
		this.czdm = czdm;
	}

	public String getXtczms_id() {
		return xtczms_id;
	}

	public void setXtczms_id(String xtczmsId) {
		xtczms_id = xtczmsId;
	}

	public String getGndm() {
		return gndm;
	}

	public void setGndm(String gndm) {
		this.gndm = gndm;
	}

	public String getCzdm() {
		return czdm;
	}

	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}

	public String getCzms() {
		return czms;
	}

	public void setCzms(String czms) {
		this.czms = czms;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getGnmkmc() {
		return gnmkmc;
	}

	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}

	public String getCzmc() {
		return czmc;
	}

	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}

	public String getDyym() {
		return dyym;
	}

	public void setDyym(String dyym) {
		this.dyym = dyym;
	}

	public String getSfsy() {
		return sfsy;
	}

	public void setSfsy(String sfsy) {
		this.sfsy = sfsy;
	}
	
}
