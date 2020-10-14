package com.woshidaniu.common.factory;

import javax.servlet.http.HttpServletRequest;

import com.woshidaniu.common.log.User;
import com.woshidaniu.web.WebContext;


/**
 * 
 *@类名称	: WebSessionFactory.java
 *@类描述	：会话工厂：只能用于当前线程，EJB端不支持使用（以后可能支持）
 *@创建人	：kangzhidong
 *@创建时间	：Mar 17, 2016 5:20:47 PM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class WebSessionFactory{
	
	private WebSessionFactory() {

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
		HttpServletRequest request = (HttpServletRequest) WebContext.getRequest();
		if (request == null) {
			return "xsgzgl";
		}
		return request.getContextPath();
	}

}
