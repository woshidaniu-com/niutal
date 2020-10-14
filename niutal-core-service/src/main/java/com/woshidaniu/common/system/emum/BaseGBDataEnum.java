package com.woshidaniu.common.system.emum;

/**
 * 收费系统国标枚举类
 * 
 * Create on 2013-7-16 下午05:45:14
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public enum BaseGBDataEnum {
	// 民族
	MING_ZHU("MING_ZHU"),

	// 性别
	XING_BIE("XING_BIE"),

	// 学历
	XUE_LI("XUE_LI"),

	// 学位
	XUE_WEI("XUE_WEI"),

	// 政治面貌
	ZHENG_ZHI_MIAN_MIAO("ZHENG_ZHI_MIAN_MIAO"),
	
	// 行政区
	XING_ZHENG_QU("XING_ZHENG_QU"),

	// 高级查询多个值，返回为html字符串
	GJCX_HTML("GJCX_HTML");

	private String	value;

	private BaseGBDataEnum(String _value) {
		this.value = _value;
	}

	public String getValue() {
		return this.value;
	}
}
