package com.woshidaniu.web;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;

import com.woshidaniu.common.log.User;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：Web对象
 *	 <br>class：com.woshidaniu.web.WebContext.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class WebContext extends com.woshidaniu.web.context.WebContext {
	
	private static String USER_KEY = Parameters.getGlobalString(Parameter.SESSION_USER_KEY);
	
	/**
	 * 
	 * <p>方法说明：获取用户信息<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年9月14日上午9:20:01<p>
	 */
	public static User getUser(){
		
		try {
			
			//获取Session中的User对象，如果没有获取到，则认为使用了Shiro权限控制组件
			HttpSession session = WebContext.getSession();
			User user = (User) session.getAttribute(USER_KEY);
			if(null != user){
				return user;
			}
			
			//获取Shiro权限控制组件中的User对象
			if(SecurityUtils.getSubject().getPrincipals() == null){
				return null;
			}
			user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			session.setAttribute(USER_KEY, user);
			
			return user;
		} catch (Exception e) {
			return null;
		}
		
	}
	 
}
