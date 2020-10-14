package com.woshidaniu.common.factory;

import com.woshidaniu.common.log.User;
import com.woshidaniu.web.WebContext;


public class SessionFactory extends com.woshidaniu.struts2.factory.SessionFactory {

	private SessionFactory() {

	}
	 
	/**
	 * 取当前会话用户信息
	 * @see com.woshidaniu.web.WebContext#getUser()
	 * @return User
	 */
	@Deprecated
	public static User getUser() {
		return WebContext.getUser();
	}

	/**
	 * 取请求上下文路径
	 * @return
	 */
	@Deprecated
	public static String getContextPath() {
		
		return WebSessionFactory.getContextPath();
	}

	
}
