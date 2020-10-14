package com.woshidaniu.common;

import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;

public class GlobalString {
	
	/****************WEB STATICS*********************/
	//会话用户KEY
	public static final String WEB_SESSION_USER_KEY = Parameters.getGlobalString(Parameter.SESSION_USER_KEY);
	public static final String WEB_SESSION_USER_TYPE_KEY = "userType";
	public static final String WEB_SESSION_USER_IP = "http_req_ip";
	
	//========用户类型===========//
	public static final String USER_TYPE_TEACHER = "teacher";
	public static final String USER_TYPE_STUDENT = "student";
	public static final String USER_TYPE_COMPANY = "company";
	//========用户类型===========//
	
	//========三级菜单按钮菜单标识=========//
	public static final String WEB_SESSION_ANCD = "ancdModelList";
	//========三级菜单按钮菜单标识=========//
	
	//用户数据范围session标识
	public static final String SESSION_YHJSSJFWZ_LIST = "SESSION_YHJSSJFWZ_LIST";
	
	//系统初始化密码
	public static final String SYSTEM_INITIAL_PASSWORD_KEY = "system.initial.password";
	public static final String SYSTEM_INITIAL_PASSWORD = "888888";
	
	/****************WEB STATICS*********************/
	
	public static final String JCSJ_XUEQDM = "lx01";//学期代码
	public static final String JCSJ_XIAOQDM = "lx02";//校区代码
	public static final String JCSJ_XBDM = "lx03";//性别代码
	public static final String JCSJ_MZDM = "lx04";//名族代码
	public static final String JCSJ_ZZMMDM = "lx05";//政治面貌代码
	public static final String JCSJ_XZQHDM = "lx06";//学期代码
	public static final String JCSJ_GJDM = "lx07";//国籍代码
	public static final String JCSJ_ZJDM = "lx08";//宗教代码
	public static final String JCSJ_HKXZDM = "lx09";//户口性质代码
	public static final String JCSJ_RXFSDM = "lx10";//入学方式代码
	public static final String JCSJ_ZSLBDM = "lx11";//招生类别代码
	public static final String JCSJ_PYFXDM = "lx12";//培养方向代码
	public static final String JCSJ_KSLBDM = "lx13";//考生类别代码
	public static final String JCSJ_PYCCDM = "lx14";//培养层次代码
	public static final String JCSJ_XSLBDM = "lx15";//学生类别代码
	public static final String JCSJ_XLCCDM = "lx16";//学历层次代码
	public static final String JCSJ_XJZTDM = "lx17";//学籍状态代码
	public static final String JCSJ_XJYDDM = "lx18";//学籍异动代码
	
}
