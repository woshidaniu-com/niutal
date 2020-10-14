/**
 * 
 */
package com.woshidaniu.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.service.svcinterface.IShiroService;
import com.woshidaniu.shiro.authc.DefaultDelegateAuthenticationInfo;
import com.woshidaniu.shiro.authc.DelegateAuthenticationInfo;
import com.woshidaniu.shiro.token.DelegateAuthenticationToken;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：外部系统登录系统数据查询实现
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年7月29日下午3:52:15
 */
@Service("outAccountShiroService")
public class OutAccountShiroServiceImpl implements IShiroService{
	
	@Resource
	private ILoginService loginService;


	@Override
	public User queryUserInfo(String userName, String password) {
		return queryUserInfo(userName);
	}
	
	protected User queryUserInfo(String userName){
		if(StringUtils.isBlank(userName)){
			return null;
		}
		LoginModel model = new LoginModel();
		model.setYhm(userName);
		return loginService.cxYhxx(model);
	}


	@Override
	public DelegateAuthenticationInfo getAuthenticationInfo(DelegateAuthenticationToken token) {
		User queryUserInfo = queryUserInfo(token.getUsername());
		if(queryUserInfo != null){
			DelegateAuthenticationInfo authcInfo = new DefaultDelegateAuthenticationInfo(queryUserInfo, "0");
			return authcInfo;
		}
		return null;
	}

	@Override
	public Set<String> getPermissionsInfo(Object principal) {
		return null;
	}

	@Override
	public Set<String> getPermissionsInfo(Set<Object> principals) {
		return null;
	}

	@Override
	public Set<String> getRolesInfo(Object principal) {
		return null;
	}

	@Override
	public Set<String> getRolesInfo(Set<Object> principals) {
		return null;
	}
}
