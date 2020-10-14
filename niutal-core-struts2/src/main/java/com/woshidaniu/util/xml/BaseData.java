package com.woshidaniu.util.xml;

/**
 * @Description 页面基础数据
 * @author Zhenfei.Cao
 * @date 2013-8-1
 * @version
 */
public class BaseData {

	public final static String ISNOT = "isNot";
	
	public final static String ISALLOW = "isAllow";
	
	public final static String ISSTART = "isStart";
	
	private String key;//baseData对应的key值
	
	private String value;//baseData对应的value值

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
