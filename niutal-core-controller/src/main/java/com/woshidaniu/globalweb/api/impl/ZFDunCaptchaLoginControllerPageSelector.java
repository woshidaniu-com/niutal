/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.api.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.globalweb.api.LoginControllerPageSelector;

/**
 * @author 		：康康（1571）
 * 
 * 我是大牛盾验证码登录页面选择器,指向新登录页面
 * 
 */
public class ZFDunCaptchaLoginControllerPageSelector implements LoginControllerPageSelector{
	
	private static final Logger log = LoggerFactory.getLogger(ZFDunCaptchaLoginControllerPageSelector.class);
	
	private String loginPage = "/globalweb/login_zfdun_captcha";

	@Override
	public String selectLoginPage(HttpServletRequest request) {
		Object render = request.getServletContext().getAttribute("com.woshidaniu.zfdun.zfcaptcha.page_render_type_login");
		if(render == null) {
			log.warn("getAttribute('com.woshidaniu.zfdun.zfcaptcha.page_render_type_login') return null");
		}
		request.setAttribute("zfcaptchaPageRender", render);
		return this.loginPage;
	}
	
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
}
