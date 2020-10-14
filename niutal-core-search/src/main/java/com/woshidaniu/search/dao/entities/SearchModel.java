package com.woshidaniu.search.dao.entities;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SearchModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private String selectOptions;
	private String blurType;
	private String blurValue;
	private String szqjcxValue;//数字区间查询
	private String sjqjcxValue;//时间区间查询(数据库字段为date类型)
	private String zfsjqjcxValue;//字符时间区间查询(数据库字段为字符类型)
	
	//最终生成的查询sql语句
	private String querySQL;
	
	private Map<String,String> params;
	//-----------------优化--------------------//
	//高级查询配置id
	private String searchSign;
	//高级查询配置对象
	private List<SearchConfigModel> configList;
	//高级查询的数据,json格式
	private String superSearchData;
	/*
	 * 传输交换的数据
	 */
	private String transferData;
	/*
	 * 传输类型
	 */
	private String tranferType;
	
	
	//-----------------优化--------------------//
	
	public String getSelectOptions() {
		return selectOptions;
	}

	public void setSelectOptions(String selectOptions) {
		this.selectOptions = selectOptions;
	}

	public String getBlurType() {
		return blurType;
	}

	public void setBlurType(String blurType) {
		this.blurType = blurType;
	}

	public String getBlurValue() {
		return blurValue;
	}

	public void setBlurValue(String blurValue) {
		this.blurValue = blurValue;
	}

	public String getQuerySQL() {
		return querySQL;
	}

	public void setQuerySQL(String querySQL) {
		this.querySQL = querySQL;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getSzqjcxValue() {
		return szqjcxValue;
	}

	public void setSzqjcxValue(String szqjcxValue) {
		this.szqjcxValue = szqjcxValue;
	}

	public String getSjqjcxValue() {
		return sjqjcxValue;
	}

	public void setSjqjcxValue(String sjqjcxValue) {
		this.sjqjcxValue = sjqjcxValue;
	}

	public String getZfsjqjcxValue() {
		return zfsjqjcxValue;
	}

	public void setZfsjqjcxValue(String zfsjqjcxValue) {
		this.zfsjqjcxValue = zfsjqjcxValue;
	}

	public String getTransferData() {
		return transferData;
	}

	public void setTransferData(String transferData) {
		this.transferData = transferData;
	}

	public String getTranferType() {
		return tranferType;
	}

	public void setTranferType(String tranferType) {
		this.tranferType = tranferType;
	}

	public String getSuperSearchData() {
		return superSearchData;
	}

	public void setSuperSearchData(String superSearchData) {
		this.superSearchData = superSearchData;
	}
	public String getSearchSign() {
		return searchSign;
	}

	public void setSearchSign(String searchSign) {
		this.searchSign = searchSign;
	}

	public List<SearchConfigModel> getConfigList() {
		return configList;
	}

	public void setConfigList(List<SearchConfigModel> configList) {
		this.configList = configList;
	}
}
