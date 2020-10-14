/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dao.entities;

import java.util.Collections;
import java.util.List;

public class ExportConfigVoModel extends ExportConfigModel {
	
	//虚拟id
	private String id;
	//授权值列表
	private List<String> sqzList = Collections.emptyList();
	//授权值join字符串列表
	private String sqzStrList;
	//授权值名称字符串列表
	private String sqzMcStrList;

	public String getSqzStrList() {
		return sqzStrList;
	}

	public void setSqzStrList(String sqzStrList) {
		this.sqzStrList = sqzStrList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getSqzList() {
		return sqzList;
	}

	public void setSqzList(List<String> sqzList) {
		this.sqzList = sqzList;
	}

	public String getSqzMcStrList() {
		return sqzMcStrList;
	}

	public void setSqzMcStrList(String sqzMcStrList) {
		this.sqzMcStrList = sqzMcStrList;
	}

	@Override
	public String toString() {
		return "ExportConfigVoModel [id=" + id + ", sqzList=" + sqzList + ", sqzStrList=" + sqzStrList
				+ ", sqzMcStrList=" + sqzMcStrList + ", dcclbh=" + dcclbh + ", dcclmc=" + dcclmc + ", zd=" + zd
				+ ", zdmc=" + zdmc + ", xssx=" + xssx + ", zgh=" + zgh + ", sfmrzd=" + sfmrzd + ", zt=" + zt
				+ ", queryModel=" + queryModel + "]";
	}
	
}
