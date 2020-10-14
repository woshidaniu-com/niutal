package com.woshidaniu.common.system.cache;

import java.io.Serializable;

/**
 * 缓存键值对
 * 
 * Create on 2013-7-5 下午01:35:18
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public class CacheOption implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2458511318233633025L;

	private String				key;

	private String				value;

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

}
