package com.woshidaniu.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.daointerface.ILoginDao;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.service.svcinterface.IShiroService;
import com.woshidaniu.shiro.InvalidAccountException;
import com.woshidaniu.shiro.InvalidStateException;
import com.woshidaniu.shiro.authc.DefaultDelegateAuthenticationInfo;
import com.woshidaniu.shiro.authc.DelegateAuthenticationInfo;
import com.woshidaniu.shiro.token.DelegateAuthenticationToken;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3>说明：框架默认实现
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年7月29日下午3:32:21
 */
@Service("defaultShiroService")
public class ShiroServiceImpl implements IShiroService {

	@Resource
	private ILoginService loginService;
	@Resource
	private IJsglDao jsglDao;
	@Resource
	private ILoginDao loginDao;
	

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IShiroService#queryUserInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public User queryUserInfo(String userName, String password) {
		if (StringUtils.isBlank(userName) || StringUtils.isNull(password)) {
			return null;
		}
		LoginModel model = new LoginModel();
		model.setYhm(userName);
		model.setMm(password);
		return loginService.cxYhxx(model);
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getAuthenticationInfo(com.woshidaniu.shiro.token.DelegateAuthenticationToken)
	 */
	@Override
	public DelegateAuthenticationInfo getAuthenticationInfo(DelegateAuthenticationToken token) {
		User queryUserInfo = queryUserInfo(token.getUsername(), new String(token.getPassword()));
		if(queryUserInfo == null){
			throw new InvalidAccountException("查询不到用户信息");
		}
		if(StringUtils.equals(queryUserInfo.getSfqy(), "0")){
			throw new InvalidStateException("该用户没有启用");
		}
		return queryUserInfo == null ? null : new DefaultDelegateAuthenticationInfo(queryUserInfo, token.getPassword());
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getPermissionsInfo(java.lang.Object)
	 */
	@Override
	public Set<String> getPermissionsInfo(Object principal) {
		User user = (User) principal;
		//用户当前角色
		Set<String> rolePermissionInfoSet = this.doGetPermissionsInfo(user.getYhm(), user.getYhlx(), user.getJsdm());
		
		return rolePermissionInfoSet;
	}
	
	protected Set<String> doGetPermissionsInfo(String yhm,String yhlx,String jsdm) {
		
		if (User.UserType.TEACHER.toString().toLowerCase().equals(yhlx)){
			LoginModel yhModel = new LoginModel();
			yhModel.setYhm(yhm);
			User loginUser = loginDao.getUserInfo(yhModel);
			
			if (loginUser != null && loginUser.getJsdms() != null && loginUser.getJsdms().contains(jsdm)){
				//用户权限代码
				return jsglDao.getJsqxList(jsdm);
			} else {
				//二级授权下的角色权限
				return jsglDao.getUserEjqxList(jsdm,yhm);
			}
		}else{
			return Collections.emptySet();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getPermissionsInfo(java.util.Set)
	 */
	@Override
	public Set<String> getPermissionsInfo(Set<Object> principals) {
		return Collections.emptySet();
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getRolesInfo(java.lang.Object)
	 */
	@Override
	public Set<String> getRolesInfo(Object principal) {
		User user = (User) principal;
		String jsdm = user.getJsdm();
		Set<String> roles = new HashSet<String>();
		roles.add(jsdm);
		return roles;
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.shiro.service.AccountService#getRolesInfo(java.util.Set)
	 */
	@Override
	public Set<String> getRolesInfo(Set<Object> principals) {
		return Collections.emptySet();
	}


	public ILoginService getLoginService() {
		return loginService;
	}


	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}


	public IJsglDao getJsglDao() {
		return jsglDao;
	}


	public void setJsglDao(IJsglDao jsglDao) {
		this.jsglDao = jsglDao;
	}


	public ILoginDao getLoginDao() {
		return loginDao;
	}


	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}

}
