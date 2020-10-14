package com.woshidaniu.service.svcinterface;

import com.woshidaniu.common.log.User;
import com.woshidaniu.shiro.service.AccountService;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：shiro权限管理数据查询接口
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年7月29日下午3:27:05
 */
public interface IShiroService extends AccountService{
	
	/**
	 * 
	 * <p>方法说明：查询用户信息，[参数：用户名和密码]<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年7月29日下午3:28:47<p>
	 */
	User queryUserInfo(String userName, String password);
	
}
