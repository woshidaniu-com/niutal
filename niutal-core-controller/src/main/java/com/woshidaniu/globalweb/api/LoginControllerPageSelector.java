/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.api;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 		：康康（1571）
 * 
 * LoginController的页面选择器,编写这个类的目的是为了
 * 
 * 1.平滑适配以前的验证码和新开发的zfdun图片滑动验证码,当产品部门手动配置ZFDunCaptchaLoginControllerPageSelector就启用了新验证码
 * 
 * 2.减少产品部门个性登录页面而直接覆盖登录页面所带来的混乱
 */
public interface LoginControllerPageSelector {
	
	/**
	 * @description	： 如果未登录，那么跳转的就跳转的登录页面
	 * @param request
	 * @return
	 */
	String selectLoginPage(HttpServletRequest request);
}
