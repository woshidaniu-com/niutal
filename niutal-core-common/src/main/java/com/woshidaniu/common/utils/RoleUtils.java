package com.woshidaniu.common.utils;

import com.woshidaniu.common.constant.BaseConstant;
/**
 * 
 *@类名称		： RoleUtils.java
 *@类描述		：角色判断工具
 *@创建人		：kangzhidong
 *@创建时间	：Sep 7, 2016 2:51:14 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 *@see com.woshidaniu.util.RoleUtils
 */
@Deprecated 
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
		//浙大系统中教师角色代码为05，学生角色代码为06
		if("10335".equalsIgnoreCase(BaseConstant.XXDM)){
			return "06".equals(jsdm);
		}
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
		//浙大系统中教师角色代码为05，学生角色代码为06
		if("10335".equalsIgnoreCase(BaseConstant.XXDM)){
			return "05".equals(jsdm);
		}
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
