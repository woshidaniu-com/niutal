package com.woshidaniu.common.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @类名 JsFilter.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2015 2015年11月10日 上午10:19:30
 * @功能描述 设置静态文件客户端缓存时间
 * <p>
 * <p>
 * ***********************************************
 * 												  <br>
 * 考虑到系能问题：								  <br>
 * 不太会发生变化的资源，例如第三方的脚本，css，图片等，<br>
 * 超时时间需要设置长一点；						  <br>
 * 												  <br>
 * 经常需要发生变更的文件，例如系统业务模块的js脚本，	  <br>
 * 则需要设置短一点；								  <br>
 * 												  <br>
 * 在这里提出一个方案：当我们的资源发生变更时，		  <br>
 * 如何让用户强制更新local cache.					  <br>
 * 												  <br>
 * 给资源文件带上版本号！只要资源文件升级就修改版本号，   <br>
 * 这样可以强制客户端不使用local cache.              <br>
 * 												  <br>
 * ***********************************************
 *
 */
public class JsFilter implements Filter {

	/**
	 * 默认的过期时间：30天
	 */
	protected static final int DEFAULT_MAX_AGE = 3600*24*30;
	
	/**
	 * 短时间：1天
	 */
	protected static final int MAX_AGE_SHORT = 3600*24*1;
	
	/**
	 * JS文件最大有效时间
	 */
	protected static final int DEFAULT_MAX_AGE_JS = DEFAULT_MAX_AGE;
	
	/**
	 * CSS文件最大有效时间
	 */
	protected static final int DEFAULT_MAX_AGE_CSS = DEFAULT_MAX_AGE;
	
	/**
	 * 图片文件最大有效时间
	 */
	protected static final int DEFAULT_MAX_AGE_IMAGE = MAX_AGE_SHORT;
	
	
	/**
	 * 过期时间(HTTP/1.0)
	 */
	protected static final String HEADER_EXPIRES = "Expires";
	
	/**
	 * 缓存控制(HTTP/1.1)，需要和ETag同时使用
	 * no-cache:标识资源需要重新验证
	 * no-store:标识每次都取服务器资源
	 */
	protected static final String HEADER_CACHE_CONTROL = "Cache-Control";
	
	/**
	 * 兼容HTTP/1.0 
	 * Pragma:no-cache 和 HTTP/1.1 中的 Cache- Control:no-cache作用一样
	 */
	protected static final String HEADER_PRAGMA = "Pragma";
	
	
	protected static final Pattern JS_PATTERN = Pattern.compile(".*\\.js.*");
	
	protected static final Pattern CSS_PATTERN = Pattern.compile(".*\\.css.*");
	
	protected static final Pattern IMAGE_PATTERN = Pattern.compile(".*\\.(png|gif|jpe|jpeg).*");
	
	private static final Log LOG = LogFactory.getLog(JsFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String requestURI = httpRequest.getRequestURI();
		
		if(JS_PATTERN.matcher(requestURI).matches()){
			handleJS(httpRequest, response);
		}else if(CSS_PATTERN.matcher(requestURI).matches()){
			handleCSS(httpRequest, response);
		}else if(IMAGE_PATTERN.matcher(requestURI).matches()){
			handleIMAGE(httpRequest, response);
		}
		if(LOG.isDebugEnabled()){
			LOG.debug("***" + requestURI + " set respons header " + HEADER_CACHE_CONTROL + " = " + response+ "***");
		}
		chain.doFilter(request, response);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	protected void handleJS(ServletRequest request, ServletResponse response){
		HttpServletResponse httpresponse = (HttpServletResponse)response;
		httpresponse.setHeader(HEADER_CACHE_CONTROL, "private,max-age=" + DEFAULT_MAX_AGE_JS);
		
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	protected void handleCSS(ServletRequest request, ServletResponse response){
		handleJS(request, response);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	protected void handleIMAGE(ServletRequest request, ServletResponse response){
		HttpServletResponse httpresponse = (HttpServletResponse)response;
		httpresponse.setHeader(HEADER_CACHE_CONTROL, "private,max-age=" + DEFAULT_MAX_AGE_IMAGE);
	}
	
	@Override
	public void destroy() {}

}
