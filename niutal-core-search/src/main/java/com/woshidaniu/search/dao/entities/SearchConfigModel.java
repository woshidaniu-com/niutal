package com.woshidaniu.search.dao.entities;

import java.util.List;

/**
 * 高级查询配置模型
 * @author Penghui.Qu
 *
 */
public class SearchConfigModel {

	private String id;//ID
	private String searchSign;//查询标志（代表业务功能的唯一标识）
	private String searchName;//查询字段
	private String searchLabel;//字段中文名称
	//
	private String searchType;//条件类型（模糊查询、条件查询）
	//
	private String searchByname;//字段别名
	private String valueSource;//数据来源
	private String useInitial; //是否使用英文字母标识
	private String maxRows;//条件最多所占行数
	private String isNewLine;//是否换行
	private String isScroll;//滚动条
	private String isOnly;//是否单选
	private String linkageId;//联动编号
	private String onOrOff;//是否启用
	private String showOrder;//显示顺序
	
	private List<String> linkageList;//被关联的字段名列表
	
	private List<String> byLinkageList; //影响该字段值得字段
	
	public List<String> getLinkageList() {
		return linkageList;
	}
	public void setLinkageList(List<String> linkageList) {
		this.linkageList = linkageList;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSearchSign() {
		return searchSign;
	}
	public void setSearchSign(String searchSign) {
		this.searchSign = searchSign;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchLabel() {
		return searchLabel;
	}
	public void setSearchLabel(String searchLabel) {
		this.searchLabel = searchLabel;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchByname() {
		return searchByname;
	}
	public void setSearchByname(String searchByname) {
		this.searchByname = searchByname;
	}
	public String getValueSource() {
		return valueSource;
	}
	public void setValueSource(String valueSource) {
		this.valueSource = valueSource;
	}
	public String getUseInitial() {
		return useInitial;
	}
	public void setUseInitial(String useInitial) {
		this.useInitial = useInitial;
	}
	public String getMaxRows() {
		return maxRows;
	}
	public void setMaxRows(String maxRows) {
		this.maxRows = maxRows;
	}
	public String getIsNewLine() {
		return isNewLine;
	}
	public void setIsNewLine(String isNewLine) {
		this.isNewLine = isNewLine;
	}
	public String getIsScroll() {
		return isScroll;
	}
	public void setIsScroll(String isScroll) {
		this.isScroll = isScroll;
	}
	public String getIsOnly() {
		return isOnly;
	}
	public void setIsOnly(String isOnly) {
		this.isOnly = isOnly;
	}
	public String getLinkageId() {
		return linkageId;
	}
	public void setLinkageId(String linkageId) {
		this.linkageId = linkageId;
	}
	public String getOnOrOff() {
		return onOrOff;
	}
	public void setOnOrOff(String onOrOff) {
		this.onOrOff = onOrOff;
	}
	public String getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}
	public List<String> getByLinkageList() {
		return byLinkageList;
	}
	public void setByLinkageList(List<String> byLinkageList) {
		this.byLinkageList = byLinkageList;
	}
	
}
