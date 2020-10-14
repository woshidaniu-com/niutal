package com.woshidaniu.util;

/**
 * 
 *@类名称		： RoleUtils.java
 *@类描述		：角色判断工具
 *@创建人		：kangzhidong
 *@创建时间	：Sep 7, 2016 2:51:14 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
public class RoleUtils {

	/**
	 * 
	 *@描述		：是否是管理员
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 7, 20162:51:26 PM
	 *@param jsdm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean isAdmin(String jsdm){
		return "admin".equals(jsdm);
	}
	
	/**
	 * 
	 *@描述		： 是否是学生角色
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 7, 20162:51:38 PM
	 *@param jsdm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean isStudent(String jsdm){
		return "xs".equals(jsdm);
	}
	
	/**
	 * 
	 *@描述		： 是否是教师角色
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 7, 20162:51:42 PM
	 *@param jsdm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean isTeacher(String jsdm){
		return "js".equals(jsdm);
	}
	
	/**
	 * 
	 *@描述		： 是否是监控角色：在系统进行性能监控时仅允许部分人员可进行监控查看
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 7, 20162:52:02 PM
	 *@param jsdm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean isMonitor(String jsdm) {
		return "admin".equals(jsdm);
	}
	
}
