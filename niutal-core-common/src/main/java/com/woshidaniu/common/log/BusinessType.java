package com.woshidaniu.common.log;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：日志操作类型-枚举
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年10月13日下午2:30:38
 */
public enum BusinessType {

	LOGIN("login"),
	LOGOUT("logout"),
	SELECT("select"),
	UPDATE("update"),
	DELETE("delete"),
	INSERT("insert"),
	;
	
	private String key;
	
	BusinessType(String key){
		this.key=key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
