package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.log.User;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.shiro.NoneRoleException;
import com.woshidaniu.shiro.authc.DefaultDelegateAuthenticationInfo;
import com.woshidaniu.shiro.authc.DelegateAuthenticationInfo;
import com.woshidaniu.shiro.token.DelegateAuthenticationToken;

/**
 * 
 *@类名称	: ShiroSsoServiceImpl.java
 *@类描述	：
 *@创建人	：kangzhidong
 *@创建时间	：2017年8月22日 下午1:47:23
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Service("shiroSsoService")
public class ShiroSsoServiceImpl extends ShiroServiceImpl {

	@Resource
	private ILoginService loginService;
	
	@Override
	public DelegateAuthenticationInfo getAuthenticationInfo(DelegateAuthenticationToken token) {
		
		//调用原来的逻辑获取User对象
		User user = getLoginService().cxYhxxWithoutPwd(token.getUsername());
		if(user == null){
			//用户不存在
			throw new UnknownAccountException();
		}
		
		if (user.getJsdms().size() == 0){
			//未分配角色
			throw new NoneRoleException();
		}
		
		if (QYZT_WQY.equals(user.getSfqy())){
			//账户未启用
			throw new DisabledAccountException();
		}
		
		DelegateAuthenticationInfo authcInfo = new DefaultDelegateAuthenticationInfo(user, "0");
		return authcInfo;
	}

}
