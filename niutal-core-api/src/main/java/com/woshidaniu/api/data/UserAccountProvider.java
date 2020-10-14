package com.woshidaniu.api.data;

import com.woshidaniu.basemodel.BaseMap;

import java.util.Map;

public interface UserAccountProvider {

	/**
	 * @description	： 根据用户名获得用户状态
	 * @param username
	 * @return
	 */
	public Map<String, String> getAccountStatus(String username);
	
	/**
	 * @description	： 根据email获得用户状态
	 * @param email
	 * @return
	 */
	public Map<String, String> getAccountStatusByEmail(String email);
	
	/**
	 * @description	： 根据手机号码获得用户状态
	 * @param phone
	 * @return
	 */
	public Map<String, String> getAccountStatusByPhone(String phone);
	
	/**
	 * @description	： 根据用户的email地址获得用户信息
	 * @param map
	 * @return
	 */
	public BaseMap getUserAccountByEmail(Map<String, Object> map);
	
	/**
	 * @description	： 根据用户的手机号码获得用户信息
	 * @param map
	 * @return
	 */
	public BaseMap getUserAccountByPhone(Map<String, Object> map);
	/**
	 * 
	 *@描述		：通过页面绑定的参数查询用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月13日下午2:14:21
	 *@param data
	 *@return
	 */
	public BaseMap getUserAccount(Map<String, Object> map);
	
	public int updateAccount(Map<String, Object> map);
	
}
