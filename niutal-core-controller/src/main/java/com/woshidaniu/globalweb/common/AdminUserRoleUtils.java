/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.util.base.MessageUtil;

public class AdminUserRoleUtils {

	
	/**
	 * @description	： 是否存在管理员用户
	 * @author 		： 康康（1571）
	 * @date 		：2018年6月21日 下午2:05:37
	 * @param idSet
	 * @return
	 */
	public static boolean existAdminUser(String[] idArray) {
		Set<String> idSet = new HashSet<String>(Arrays.asList(idArray));
		//管理员用户id列表
		String adminUserIds = MessageUtil.getText("userManage.adminUsers");
		if(StringUtils.isNotEmpty(adminUserIds)) {
			String[] arr = adminUserIds.split(",");
			if(arr != null && arr.length > 0) {
				for(String id : arr) {
					//包含管理员账号
					if(idSet.contains(id)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @description	： 是否存在管理员角色
	 * @author 		： 康康（1571）
	 * @date 		：2018年6月21日 下午2:05:50
	 * @param idArraySet
	 * @return
	 */
	public static boolean existAdminRole(String[] idArray) {
		Set<String> idSet = new HashSet<String>(Arrays.asList(idArray));
		//管理员角色
		String adminUserIds = MessageUtil.getText("userManage.adminRoles");
		if(StringUtils.isNotEmpty(adminUserIds)) {
			String[] arr = adminUserIds.split(",");
			if(arr != null && arr.length > 0) {
				for(String id : arr) {
					//包含管理员角色
					if(idSet.contains(id)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
