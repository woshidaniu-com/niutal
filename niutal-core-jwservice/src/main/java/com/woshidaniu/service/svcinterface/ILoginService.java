package com.woshidaniu.service.svcinterface;


import java.util.Map;
import java.util.Set;

import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.LoginModel;

/**
 * 
 *@类名称		： ILoginService.java
 *@类描述		：用户登录Service接口
 *@创建人		：kangzhidong
 *@创建时间	：Jun 23, 2016 5:31:26 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface ILoginService extends BaseService<LoginModel>{

	/**
	 * 
	 *@描述			: 根据用户ID和密码查询用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-23下午05:25:12
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public User cxYhxx(LoginModel model) ;
	
	
	/**
	 *  
	 *@描述			: 无密码根据用户ID用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-23下午05:25:30
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public User cxYhxxWithoutPwd(LoginModel model) ;
	
	/**
	 * 
	 *@描述			: 无密码根据用户ID用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-23下午05:25:45
	 *@param yhm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public User cxYhxxWithoutPwd(String yhm);
	
	/**
	 * 
	 *@描述		：查询用户状态信息
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 20168:13:40 PM
	 *@param yhm
	 *@param mm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public Map<String, String> cxYhxxStatus(String yhm,String mm);
	
	/**
	 * 通过用户名，角色代码查询该角色对于的权限
	 * @param yhm
	 * @param jsdm
	 * @return
	 */
	public Set<String> getPermissionsInfo(String yhm,String jsdm);
	 
}
