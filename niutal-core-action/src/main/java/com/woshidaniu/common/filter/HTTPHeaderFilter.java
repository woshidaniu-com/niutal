/**
 * 
 */
package com.woshidaniu.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：设置http header
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年7月13日下午3:52:30
 */
public class HTTPHeaderFilter  implements Filter{
	
	private FilterConfig filerConfig;
	
	private String HTTP_HEAD_X_FRAME_OPTIONS = "SAMEORIGIN";
	
	private String HTTP_HEAD_X_XSS_PROTECTION = "1; mode=block";
	
	private String HTTP_HEAD_X_CONTENT_TYPE_OPTIONS = "nosniff";
	
	private String[] allowedHTTPMethods;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		filerConfig = filterConfig;
		String initParameter_HTTP_HEAD_X_FRAME_OPTIONS = filerConfig.getInitParameter("X_FRAME_OPTIONS");
		if(initParameter_HTTP_HEAD_X_FRAME_OPTIONS != null && initParameter_HTTP_HEAD_X_FRAME_OPTIONS.trim().length() > 0){
			HTTP_HEAD_X_FRAME_OPTIONS = initParameter_HTTP_HEAD_X_FRAME_OPTIONS.trim();
		}
		
		String initParameter_HTTP_HEAD_X_XSS_PROTECTION = filerConfig.getInitParameter("X_XSS_PROTECTION");
		if(initParameter_HTTP_HEAD_X_XSS_PROTECTION != null && initParameter_HTTP_HEAD_X_XSS_PROTECTION.trim().length() > 0){
			HTTP_HEAD_X_XSS_PROTECTION = initParameter_HTTP_HEAD_X_XSS_PROTECTION.trim();
		}
		
		String initParameter_HTTP_HEAD_X_CONTENT_TYPE_OPTIONS = filerConfig.getInitParameter("X_CONTENT_TYPE_OPTIONS");
		if(initParameter_HTTP_HEAD_X_CONTENT_TYPE_OPTIONS != null && initParameter_HTTP_HEAD_X_CONTENT_TYPE_OPTIONS.trim().length() > 0){
			HTTP_HEAD_X_CONTENT_TYPE_OPTIONS = initParameter_HTTP_HEAD_X_CONTENT_TYPE_OPTIONS.trim();
		}
		
		String initParameter_HTTP_HEAD_ALLOWED_METHODS = filerConfig.getInitParameter("ALLOWED_HTTP_METHODS");
		if(initParameter_HTTP_HEAD_ALLOWED_METHODS != null && initParameter_HTTP_HEAD_ALLOWED_METHODS.trim().length() > 0){
			allowedHTTPMethods = initParameter_HTTP_HEAD_ALLOWED_METHODS.trim().split(",");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		//iframe策略
		httpResponse.setHeader("X-Frame-Options", HTTP_HEAD_X_FRAME_OPTIONS);
		//X-XSS-Protection：主要是用来防止浏览器中的反射性xss，IE，chrome和safari（webkit）支持这个header,有以下两种方式：
		//1; mode=block – 开启xss防护并通知浏览器阻止而不是过滤用户注入的脚本；
		//1; report=http://site.com/report – 这个只有chrome和webkit内核的浏览器支持，这种模式告诉浏览器当
		//发现疑似xss攻击的时候就将这部分数据post到指定地址。
		httpResponse.setHeader("X-XSS-Protection", HTTP_HEAD_X_XSS_PROTECTION);
		//防止在IE9、chrome和safari中的MIME类型混淆攻击
		httpResponse.setHeader("X-Content-Type-Options", HTTP_HEAD_X_CONTENT_TYPE_OPTIONS);
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		//请求使用的方法
		String method = httpRequest.getMethod();
		boolean isAllowed = true;
		
		//需要过滤HTTP METHOD
		if(allowedHTTPMethods != null && allowedHTTPMethods.length > 0){
			isAllowed = false;
			for (String httpMethod : allowedHTTPMethods) {
				if(httpMethod != null && httpMethod.equalsIgnoreCase(method)){
					isAllowed = true;
				}
			}
		}
		
		if(!isAllowed){
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Request Denied!");
			return;
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		
		
	}

}
