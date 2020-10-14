package com.woshidaniu.common.system.emum;

/**
 * 基础数据类型
 * 
 * Create on 2013-7-5 下午01:54:49 
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013  Version 1.0 
 *
 * @author : HJL [718]
 */
public enum BasicDataEmum {
    // 性别
    SEX("SEX"),
	// 学期
	SCHOOL_YEAR("SCHOOL_YEAR");

	private String	value;

	private BasicDataEmum(String _value) {
		this.value = _value;
	}

	public String getValue() {
		return this.value;
	}
}
