package com.woshidaniu.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StrengthUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.entities.LoginModel;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.shiro.NoneRoleException;
import com.woshidaniu.shiro.authc.DefaultDelegateAuthenticationInfo;
import com.woshidaniu.shiro.authc.DelegateAuthenticationInfo;
import com.woshidaniu.shiro.service.AccountService;
import com.woshidaniu.shiro.token.DelegateAuthenticationToken;
import com.woshidaniu.shiro.token.ZfSsoToken;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：shiro认证与授权
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]</a>
 * @version 2017年6月7日上午9:18:20
 */
@Service("shiroService")
public class ShiroServiceImpl implements AccountService{

	protected static final String QYZT_WQY = "0";
	
	@Autowired
	protected ILoginService loginService;
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getAuthenticationInfo(com.woshidaniu.shiro.token.DelegateAuthenticationToken)
	 */
	public DelegateAuthenticationInfo getAuthenticationInfo(DelegateAuthenticationToken token) {
		
		//调用原来的逻辑获取User对象
		User user = getLoginService().cxYhxxWithoutPwd(token.getUsername());
		if(user == null){
			//用户不存在
			throw new UnknownAccountException();
		}
		
		if (!(token instanceof ZfSsoToken)){
			//非来自sso需校验密码
			LoginModel model = new LoginModel();
			model.setYhm(token.getUsername());
			model.setMm(new String(token.getPassword()));
			user = getLoginService().cxYhxx(model);
			
			if(user == null){
				//用户名或密码错误
				throw new AuthenticationException();
			}
			user.setYhmmdj(String.valueOf(StrengthUtils.getStrength(model.getMm())));
			
		}
		
		if (user.getJsdms().size() == 0){
			//未分配角色
			throw new NoneRoleException();
		}
		
		if (QYZT_WQY.equals(user.getSfqy())){
			//账户未启用
			throw new DisabledAccountException();
		}
		
		return new DefaultDelegateAuthenticationInfo(user, token.getPassword());
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getPermissionsInfo(java.lang.Object)
	 */
	public Set<String> getPermissionsInfo(Object principal) {
		User user = (User) principal;
		return getLoginService().getPermissionsInfo(user.getYhm() ,user.getJsdm());
	}

	public Set<String> getPermissionsInfo(Set<Object> principals) {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getRolesInfo(java.lang.Object)
	 */
	public Set<String> getRolesInfo(Object principal) {
		
		User user = (User) principal;
		List<String> jsdmList = user.getJsdms();
		Set<String> jsdms = new HashSet<String>();
		jsdms.addAll(jsdmList);
		return jsdms;
	}

	public Set<String> getRolesInfo(Set<Object> principals) {
		
		return null;
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}
	
	
}
