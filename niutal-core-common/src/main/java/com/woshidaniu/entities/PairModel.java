package com.woshidaniu.entities;

import java.io.Serializable;

/**
 * 键值对基础元素.
 */
@SuppressWarnings("serial")
public class PairModel implements Serializable {
	
	private String key;
	private String value;

	public PairModel() {

	}

	public PairModel(String key, String value) {
		this.key = key;
		this.value = value;
	}

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

	public String toString() {
		return "key:" + key + " value:" + value;
	}
}
