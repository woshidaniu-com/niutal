package com.woshidaniu.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;


/**
 * @author majun <br />
 *         描述：实现防XSS攻击 <br />
 */
public class XssFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig filterConfig;
	protected AntiSamy antiSamy = null;
	protected Policy xssPolicy = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		String path = this.getClass().getClassLoader().getResource("woshidaniu-antixss-policy.xml").getFile();
  	  	if (path.startsWith("file")) {
  	      path = path.substring(6);
  	    }
  	  	try {
			xssPolicy = Policy.getInstance(path); 
			antiSamy = new AntiSamy(xssPolicy);
		} catch (PolicyException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
//		String czdm  = WebRequestUtils.getOptCode(path,"html");
//		if(czdm == null || "cx".equals(czdm)){
//			chain.doFilter(request,response);
//		}else{
			chain.doFilter(new XSSRequestWrapper(antiSamy,(HttpServletRequest) request),response);
//		}
	}

	public void destroy() {
		this.filterConfig = null;
		this.antiSamy = null;
		this.xssPolicy = null;
	}
	

}
