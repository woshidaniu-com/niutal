package com.woshidaniu.dao.entities;

public class BigComboSelectModel extends BaseGBModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8812789477137084018L;
	private String dm;//代码
	private String mc;//名称
	private String initialname;//英文首字母
	
	private String selectId;
	private String selectName;
	private String selectTable;
	
	private String parentName;
	private String parentId;
	private String secondparentName;
	private String secondParentId;
	
	
	public String getSelectId() {
		return selectId;
	}
	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public String getSelectTable() {
		return selectTable;
	}
	public void setSelectTable(String selectTable) {
		this.selectTable = selectTable;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getInitialname() {
		return initialname;
	}
	public void setInitialname(String initialname) {
		this.initialname = initialname;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSecondparentName() {
		return secondparentName;
	}
	public void setSecondparentName(String secondparentName) {
		this.secondparentName = secondparentName;
	}
	public String getSecondParentId() {
		return secondParentId;
	}
	public void setSecondParentId(String secondParentId) {
		this.secondParentId = secondParentId;
	}
}
