package com.woshidaniu.daointerface;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.LoginModel;

/**
 * 
 *@类名称		： ILoginDao.java
 *@类描述		：用户登录Dao接口
 *@创建人		：kangzhidong
 *@创建时间	：Jun 23, 2016 4:27:58 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Component("loginDao")
public interface ILoginDao extends BaseDao<LoginModel>{


	/***
	 * 
	 *@描述			: 根据用户ID和密码查询用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-23下午04:18:32
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public LoginModel cxYhxx(@Param(value="yhm")String yhm,@Param(value="mm")String mm);
	
	/***
	 * 
	 *@描述			: 无密码根据用户ID用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-23下午04:18:50
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public LoginModel cxYhxxWithoutPwd(@Param(value="yhm")String yhm);
	
	/**
	 * 
	 *@描述			: 根据用户ID和密码查询用户可否登录，角色数量等信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-23下午05:09:02
	 *@param yhm
	 *@param mm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public Map<String, String> cxYhxxStatus(@Param(value="yhm")String yhm,@Param(value="mm")String mm);
	

	/**
	 * 通过用户名，角色代码查询该角色对于的权限
	 * @param yhm
	 * @param jsdm
	 * @return
	 */
	public Set<String> getPermissionsInfo(@Param(value="yhm")String yhm,@Param(value="jsdm")String jsdm);

}
