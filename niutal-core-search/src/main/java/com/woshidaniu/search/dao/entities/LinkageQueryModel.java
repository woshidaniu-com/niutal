package com.woshidaniu.search.dao.entities;

import java.util.Collection;
import java.util.List;

import com.woshidaniu.search.dao.entities.SearchLinkageModel;

/**
 * 高级查询联动数据模型
 * 
 * @author xiaokang
 *
 */
public class LinkageQueryModel {

	private String linkageName;
	
	private String relatingName;
	
	private SearchLinkageModel linkageModel;
	
	private Collection<String> linkageValue;

	
	
	public LinkageQueryModel(String linkageName, String relatingName,
			Collection<String> linkageValue) {
		super();
		this.linkageName = linkageName;
		this.relatingName = relatingName;
		this.linkageValue = linkageValue;
	}

	public LinkageQueryModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLinkageName() {
		return linkageName;
	}

	public void setLinkageName(String linkageName) {
		this.linkageName = linkageName;
	}

	
	public Collection<String> getLinkageValue() {
		return linkageValue;
	}

	public void setLinkageValue(Collection<String> linkageValue) {
		this.linkageValue = linkageValue;
	}

	public String getRelatingName() {
		return relatingName;
	}

	public void setRelatingName(String relatingName) {
		this.relatingName = relatingName;
	}

	public void setLinkageValue(List<String> linkageValue) {
		this.linkageValue = linkageValue;
	}

	public SearchLinkageModel getLinkageModel() {
		return linkageModel;
	}

	public void setLinkageModel(SearchLinkageModel linkageModel) {
		this.linkageModel = linkageModel;
	}

	
	
	
}
