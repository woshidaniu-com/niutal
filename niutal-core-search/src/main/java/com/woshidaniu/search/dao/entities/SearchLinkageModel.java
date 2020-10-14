package com.woshidaniu.search.dao.entities;

public class SearchLinkageModel {

	private String searchSign; //高级查询标识
	private String linkageName;//联动字段
	private String nextName;//被联动字段
	private String relatingName;//关联字段（若数据源为table则有效）
	private String valueSource;//数据源
	
	private String linkageValue;//关联字段值
	
	private String searchName;
	private String pageNumber;
	
	public String getSearchSign() {
		return searchSign;
	}
	public void setSearchSign(String searchSign) {
		this.searchSign = searchSign;
	}
	public String getLinkageName() {
		return linkageName;
	}
	public void setLinkageName(String linkageName) {
		this.linkageName = linkageName;
	}
	public String getNextName() {
		return nextName;
	}
	public void setNextName(String nextName) {
		this.nextName = nextName;
	}
	public String getRelatingName() {
		return relatingName;
	}
	public void setRelatingName(String relatingName) {
		this.relatingName = relatingName;
	}
	public String getValueSource() {
		return valueSource;
	}
	public void setValueSource(String valueSource) {
		this.valueSource = valueSource;
	}
	public String getLinkageValue() {
		return linkageValue;
	}
	public void setLinkageValue(String linkageValue) {
		this.linkageValue = linkageValue;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
	
}
