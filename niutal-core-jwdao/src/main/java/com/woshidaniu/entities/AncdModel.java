package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * 
 * 类名称：AncdModel 类描述：按钮菜单Model 创建人：yijd 创建时间：2012-4-25 上午10:22:13 修改人：yijd
 * 修改时间：2012-4-25 上午10:22:13 修改备注： 修改时间：2014-11-17 下午16:25:13 修改人：kangzhidong
 * 修改备注：继承ModelBase对象
 * 
 * @version
 * 
 */
@SuppressWarnings("serial")
public class AncdModel extends ModelBase {
	private String gnmkdm;// 功能模块代码
	private String gnmkmc;// 功能模块名称
	private String dyym;// 对应页面
	private String czdm;// 操作代码
	private String czmc;// 操作名称
	private String xssx;// 显示顺序
	private String xslx = "0";// 显示类型：0：菜单样式，1：页签样式
	private String yhm;// 用户名
	private String jsdm;// 角色代码
	private String anys;
	private String button;// 按钮
	private String tblj;// 菜单图标路径，在最近使用或者我的应用中会用到
	// 用户类型：student（学生）、teacher（老师）
	private String yhlx;
	// 菜单级别
	private String cdjb;
	private String icon;// 菜单图标
	private String path;// 路径
	private String sfxs;// 按钮是否显示
	

	public String getSfxs() {
		return sfxs;
	}

	public void setSfxs(String sfxs) {
		this.sfxs = sfxs;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCdjb() {
		return cdjb;
	}

	public void setCdjb(String cdjb) {
		this.cdjb = cdjb;
	}

	public String getYhlx() {
		return yhlx;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

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

	public String getDyym() {
		return dyym;
	}

	public void setDyym(String dyym) {
		this.dyym = dyym;
	}

	public String getCzdm() {
		return czdm;
	}

	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}

	public String getCzmc() {
		return czmc;
	}

	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getXslx() {
		return xslx;
	}

	public void setXslx(String xslx) {
		this.xslx = xslx;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getAnys() {
		return anys;
	}

	public void setAnys(String anys) {
		this.anys = anys;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	/**
	 * @return the tblj
	 */
	public String getTblj() {
		return tblj;
	}

	/**
	 * @param tblj
	 *            the tblj to set
	 */
	public void setTblj(String tblj) {
		this.tblj = tblj;
	}

}
