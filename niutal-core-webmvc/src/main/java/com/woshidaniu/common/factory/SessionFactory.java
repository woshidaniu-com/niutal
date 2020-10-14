package com.woshidaniu.common.factory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.woshidaniu.common.log.User;
import com.woshidaniu.shiro.SubjectUtils;
import com.woshidaniu.web.WebContext;


/**
 * 会话工厂：只能用于当前线程，EJB端不支持使用（以后可能支持）
 * 
 * @author Administrator
 * 
 */
public class SessionFactory{
	
	/**
	 * 获取用户会话
	 * @return
	 */
	@Deprecated
	public static Session getUserSession(){
		return SubjectUtils.getSession();
	}
	/**
	 * 常用于主进程取会话
	 * 
	 * 
	 * @return
	 */
	@Deprecated
	public static HttpSession getSession() {
		return ((HttpServletRequest)WebContext.getRequest()).getSession();
	}

	@Deprecated
	public static ServletContext getApplicationContext(){
		return WebContext.getServletContext();
	}
	
	/**
	 * 常用于主进程取请求信息
	 * 
	 * @param svrCode
	 * @return
	 */
	@Deprecated
	public static HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) WebContext.getRequest();
	}

	/**
	 * 取主机信息【ip,port,name】
	 * 
	 * @return
	 */
	@Deprecated
	public static String[] getHostInfo() {
		HttpServletRequest request = null;
		try {
			request = getHttpRequest();
		} catch (NullPointerException npEx) {
			request=null;
		}
		if (request == null) {
			return new String[] { "", "", "" };
		}
		return new String[] { request.getRemoteAddr(),
				request.getRemotePort() + "", request.getRemoteHost()};
	}

	/**
	 * 取用户权限信息[用户编号,岗位级别,保留]
	 * 
	 * @return
	 */
	@Deprecated
	public static String[] getUserInfo() {
		HttpServletRequest request = getHttpRequest();
		if (request == null) {
			return new String[] { "", "", "" };
		}
		return new String[] { "1", "1", "1" };
	}
	
	/**
	 * 取当前会话用户信息
	 * @return User
	 */
	@Deprecated
	public static User getUser() {	
		if(SecurityUtils.getSubject().getPrincipals() == null)
			return null;
		return (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
	}

	/**
	 * 取请求上下文路径
	 * 
	 * @return
	 */
	@Deprecated
	public static String getContextPath() {
		HttpServletRequest request = getHttpRequest();
		return request.getContextPath();
	}
}
