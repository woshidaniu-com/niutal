package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.shiro.authc.DefaultDelegateAuthenticationInfo;
import com.woshidaniu.shiro.authc.DelegateAuthenticationInfo;
import com.woshidaniu.shiro.token.DelegateAuthenticationToken;

/**
 * 
 * @author zhangxb
 *
 */
@Service("ssoAccountServiceImpl")
public class SsoShiroServiceImpl extends ShiroServiceImpl {

	@Resource
	private ILoginService loginService;

	@Override
	public User queryUserInfo(String userName, String password) {
		return queryUserInfo(userName);
	}

	protected User queryUserInfo(String userName) {
		if (StringUtils.isBlank(userName)) {
			return null;
		}
		LoginModel model = new LoginModel();
		model.setYhm(userName);
		return loginService.cxYhxx(model);
	}

	@Override
	public DelegateAuthenticationInfo getAuthenticationInfo(DelegateAuthenticationToken token) {
		User queryUserInfo = queryUserInfo(token.getUsername());
		if (queryUserInfo != null) {
			DelegateAuthenticationInfo authcInfo = new DefaultDelegateAuthenticationInfo(queryUserInfo, "0");
			return authcInfo;
		}
		return null;
	}

}
