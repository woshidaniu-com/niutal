package com.woshidaniu.dao.entities;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：功能菜单对象
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月15日下午3:57:17
 */
@Alias(value="menu")
public class Menu implements Serializable {

	private static final long serialVersionUID = 2294544254508789708L;

	private String gnmkdm;
	private String gnmkmc;
	private String dyym;
	private String xssx;
	private List<Menu> childrens ;
	private List<Button> buttonList;
	private String gnjc;
	
	
	public String getGnjc() {
		return gnjc;
	}
	public void setGnjc(String gnjc) {
		this.gnjc = gnjc;
	}
	public List<Button> getButtonList() {
		return buttonList;
	}
	public void setButtonList(List<Button> buttonList) {
		this.buttonList = buttonList;
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
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public List<Menu> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<Menu> childrens) {
		this.childrens = childrens;
	}
}
