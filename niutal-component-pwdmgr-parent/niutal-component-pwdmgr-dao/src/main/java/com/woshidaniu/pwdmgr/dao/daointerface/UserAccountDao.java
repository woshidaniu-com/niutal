package com.woshidaniu.pwdmgr.dao.daointerface;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.api.entity.UserAccount;
import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.common.dao.BaseDao;

@Repository
public interface UserAccountDao extends BaseDao<UserAccount>{
	/**
	 * @description	： 根据用户名获得用户状态
	 * @param username 用户名
	 * @return
	 */
	public Map<String, String> getAccountStatus(@Param(value="username")String username);
	
	/**
	 * @description	： 根据email获得用户状态
	 * @param email 邮箱地址
	 * @return
	 */
	public Map<String, String> getAccountStatusByEmail(@Param(value="email")String email);
	
	/**
	 * @description	： 根据手机号码获得用户状态
	 * @param phone 手机号码
	 * @return
	 */
	public Map<String, String> getAccountStatusByPhone(@Param(value="phone")String phone);

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
	 *@描述		：根据用户名通过页面绑定的参数查询用户信息
	 *@创建人		: kangzhidong
	 */
	public BaseMap getUserAccount(Map<String, Object> map);
	
	public int updateAccount(Map<String, Object> map);
	
	
}
