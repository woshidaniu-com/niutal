package com.woshidaniu.search.service;

public enum SearchTypeEnum {

	TJCX, //条件查询
	MHCX, //模糊查询
	SZQJCX,  //数字区间查询，固定为全闭合区间
	SJQJCX,  //时间区间查询，固定为全闭合区间，数据库字段为日期类型
	ZFSJQJCX  //数字时间区间查询，固定为全闭合区间，数据库字段为字符类型
}
