package com.woshidaniu.daointerface;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.api.entity.UserAccount;
import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.common.dao.BaseDao;

public interface IUserAccountDao extends BaseDao<UserAccount>{
	
	
	public Map<String, String> getAccountStatus(@Param(value="username")String username);
	
	/**
	 * 
	 *@描述		：通过页面绑定的参数查询用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月13日下午2:14:21
	 *@param data
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public BaseMap getUserAccount(Map<String, Object> map);
	
	public int updateAccount(Map<String, Object> map);
	
	
}
