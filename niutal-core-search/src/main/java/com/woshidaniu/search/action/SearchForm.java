/**
 * 
 */
package com.woshidaniu.search.action;

/**
 * 高级查询form
 * 
 * @author 小康
 *
 */
public class SearchForm {
	
	private String searchSign; //高级查询标识
	
	private String linkageName;//联动字段
	
	private String byLinkageName;//被联动字段
	
	private String relatingName;//关联字段（若数据源为table则有效）
	
	private String valueSource;//数据源
	
	private String linkageValue;//关联字段值
	
	private String searchName;
	
	private String pageNumber;
	
	private String initalLabel; //首字母

	private String defaultCondition; //默认条件
	
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

	public String getByLinkageName() {
		return byLinkageName;
	}

	public void setByLinkageName(String byLinkageName) {
		this.byLinkageName = byLinkageName;
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

	public String getInitalLabel() {
		return initalLabel;
	}

	public void setInitalLabel(String initalLabel) {
		this.initalLabel = initalLabel;
	}

	public String getDefaultCondition() {
		return defaultCondition;
	}

	public void setDefaultCondition(String defaultCondition) {
		this.defaultCondition = defaultCondition;
	}
	
}
