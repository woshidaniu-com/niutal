package com.woshidaniu.tjcx.service.impl;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计查询公共变量
 * @类功能描述: 
 * @作者： ligl 
 * @时间： 2013-9-18 下午04:42:50 
 * @版本： V1.0
 * @修改记录:
 */
public interface Constants {
	
	public static String TJCX_GNMK_BS = "TJCX_GNMK";//统计查询功能模块标识
	public static String TJCX_CURRENT_MENU = "TJCX_CURRENT_MENU";//统计查询当前菜单位置
	
	// private static String FLAG_QZLX_GDQZ = "1";//
	// 取值类型：1.固定值，格式为：1:男,2:女。显示格式：复选框
	public static String FLAG_QZLX_SJKQZ = "2";// 取值类型：2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	public static String FLAG_QZLX_FFQZ = "3";// 取值类型：3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
	public static String FLAG_QZLX_QJK = "4";// 取值类型：4：区间块

	//public static String FLAG_SJFWLX_XKJ = "1";// 数据范围类型，新框架方式
	//public static String FLAG_SJFWLX_XG = "2";// 数据范围类型，接口方式,学工系统

	public static String CHAR_DH = ",";
	public static String CHAR_XHX = "_";
	public static String CHAR_MH = ":";
	public static String CHAR_DYH = "'";
	public static String CHAR_MH_REG = "[:]";	
	public static String CHAR_SX = "|";
	public static String CHAR_SX_REG = "[|]";
	public static String CHAR_FH = ";";
	public static String CHAR_JH = "#";	
	public static String CHAR_MY = "$";
	
	public static String GD_TSX = "hxlhj,zxlhj,xj,zs,bfb";// 固定特殊项
	public static String GD_TSX_JS = "max,min,avg,sum,ptg";// 固定计算特殊项
	public static String GD_TSX_JS_PTG = "ptg";//特殊项百分比
	
	
	public static String WZ_EN = "WZ";// 未知
	public static String WZ_CN = "未知";// 未知
	
	public static String WZ_VALUE = "WZ_VALUE"; //未知的值

	// ///// 区间块字段
	public static String TJCXQJK = "tjcxqjk_";// 区间块字段前缀
	public static String TJCXQJK_TO = "to";// 区间块字段,取值范围代码标识，至

	public static String CHAR_FGF_BS = CHAR_MY;
	
	public static String GLTJ_DEFAULT_BS = "${gltj}";//过滤条件默认标识
	
	public static String TJCX_SQL_QX_XG = "TJCX_SQL_QX_XG";//统计查询sql权限_学工

}
