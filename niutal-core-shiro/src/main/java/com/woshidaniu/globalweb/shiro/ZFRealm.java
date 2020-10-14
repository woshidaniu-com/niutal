/**
 * 
 */
package com.woshidaniu.globalweb.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import com.woshidaniu.common.GlobalString;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.service.svcinterface.IShiroService;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：默认shiro realm实现
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月1日下午12:38:48
 */
public class ZFRealm extends AuthorizingRealm{
	
	protected IShiroService shiroService;
	
	@Resource
	private IJsglDao jsglDao;
	
	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(principals == null){
			throw new AuthenticationException();
		}
		User user = (User) principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//用户当前角色
		String jsdm = user.getJsdm();
		info.addRole(jsdm);
		
		Set<String> perms = new HashSet<String>();
		
		if (User.UserType.TEACHER.toString().toLowerCase().equals(user.getYhlx())){
			//用户权限代码
			perms.addAll(jsglDao.getJsqxList(jsdm));
		}
		
		//使用包含(学生角色)方式，主要考虑学生兼教师功能的情况
		if (User.UserType.STUDENT.toString().toLowerCase().equals(jsdm)){
//			perms.addAll(shiroService.queryStudentPermissions());
		}
		
		info.setStringPermissions(perms);
		return info;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if(!(token instanceof ZFUsernamePasswordToken)){
			throw new UnsupportedTokenException();
		}
		ZFUsernamePasswordToken zfToken = (ZFUsernamePasswordToken) token;
		String yhm =zfToken.getUsername();
		String mm = new String((char[])zfToken.getPassword());
		User loginUser = shiroService.queryUserInfo(yhm, mm);
		/*查不到用户*/
		if(loginUser == null){
			throw new AccountException("查询不到用户信息");
		/*用户没有启用*/
		}else if(StringUtils.equals(loginUser.getSfqy(), "0")){
			throw new LockedAccountException("该用户没有启用");
		}
		List<String> jsdms = loginUser.getJsdms();
		if (jsdms != null && !jsdms.isEmpty()){
			loginUser.setJsdm(jsdms.get(0));
		}
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(loginUser, mm, getName());
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute(GlobalString.WEB_SESSION_USER_KEY, loginUser);
        session.setAttribute(loginUser.getYhm(), jsdms);
        return simpleAuthenticationInfo;
		
	}
	
	public void clearAuthorizationCache(){
		clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

	public IShiroService getShiroService() {
		return shiroService;
	}

	public void setShiroService(IShiroService shiroService) {
		this.shiroService = shiroService;
	}

}
