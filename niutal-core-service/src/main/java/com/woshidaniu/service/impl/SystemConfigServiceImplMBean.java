package com.woshidaniu.service.impl;

public interface SystemConfigServiceImplMBean {

	/**
	 * 写覆盖的值
	 * @param key
	 * @param value
	 * @return
	 */
	String writeOverrideConfigValue(String key,String value);
	
	/**
	 * 读覆盖的值
	 * @param key
	 * @return
	 */
	String readOverrideConfigValue(String key);
	
	/**
	 * 移除覆盖的值
	 * @param key
	 * @return
	 */
	String removeOverrideConfigValue(String key);
	
	/**
	 * 移除所有的覆盖值
	 */
	void removeAllOverrideConfigValue();
	
	/**
	 * 获得配置值
	 * @param key
	 * @return
	 */
	String getConfigValue(String key);
	
	/**
	 * dump
	 * @return
	 */
	String dump();
}
