/**
 * 
 */
package com.woshidaniu.zxzx.auth.impl;

import javax.servlet.http.HttpSession;

import com.woshidaniu.web.WebContext;
import com.woshidaniu.zxzx.auth.AuthStateResolver;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：供测试使用
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.auth.impl.DefaultZxzxAuthStateCheckerImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月20日下午8:17:30
 */
public class DefaultZxzxAuthStateResolverImpl implements AuthStateResolver {

	protected static final String SESSION_STATE_KEY = "zxzx-session_state";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.zxzx.auth.ZxzxAuthStateChecker#isAuthenticated(java.util.Map)
	 */
	@Override
	public boolean isAuthenticated() {
		HttpSession session = WebContext.getSession();
		return session.getAttribute(SESSION_STATE_KEY) != null;
	}

	@Override
	public boolean authenticate(String username, String password) {
		HttpSession session = WebContext.getSession();
		if ("1036".equals(username) && "123123.".equals(password)) {
			session.setAttribute(SESSION_STATE_KEY, "0");
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String getPrinciple() {
		return "1036";
	}

}
