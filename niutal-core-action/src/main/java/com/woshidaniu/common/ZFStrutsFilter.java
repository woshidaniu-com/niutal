package com.woshidaniu.common;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import com.woshidaniu.basicutils.LocaleUtils;
import com.woshidaniu.web.WebContext;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：TODO
 *	 <br>class：com.woshidaniu.common.ZFStrutsFilter.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 * 
 * @see com.woshidaniu.struts2.filter.ZFStrutsPrepareAndExecuteFilter
 * 
 */
@Deprecated
public class ZFStrutsFilter extends StrutsPrepareAndExecuteFilter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		//绑定上下文请求
    	HttpSession session = ((HttpServletRequest)req).getSession();
		WebContext.bindServletContext(session.getServletContext());
		WebContext.bindRequest(req);
		WebContext.bindResponse(res);
		
		//国际化语言切换
		Locale locale = LocaleUtils.interceptLocale((HttpServletRequest)req);
		WebContext.setLocale(locale);
		
		super.doFilter(req, res, chain);
	}
	
}
