/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.api.impl;

import javax.servlet.http.HttpServletRequest;

import com.woshidaniu.globalweb.api.LoginControllerPageSelector;

/**
 * @author 		：康康（1571）
 * 
 * 默认登录页面选择器,保持以前的登录页面逻辑
 * 
 */
public class DefaultLoginControllerPageSelector implements LoginControllerPageSelector{
	
	private String loginPage = "/globalweb/login";
	
	@Override
	public String selectLoginPage(HttpServletRequest request) {
		return this.loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
}
