package com.woshidaniu.common.datarange;

import java.io.Serializable;

public class DataItem implements Serializable{

	private static final long serialVersionUID = 2905912425088621274L;
	
	private String key;
	private String value;
	private String pinyin;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	
}
