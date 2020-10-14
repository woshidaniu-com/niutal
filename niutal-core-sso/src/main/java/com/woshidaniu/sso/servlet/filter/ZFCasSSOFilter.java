/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.sso.servlet.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.core.AntPathMatcher;
import com.woshidaniu.web.core.PathMatcher;
import com.woshidaniu.niuca.tp.cas.client.filter.ZFSSOFilter;

/**
 * 
 * @className	： ZFCasSSOFilter
 * @description	： 门户单点登录过滤器
 * @author 		：大康（743）
 * @author 		：xiaokang（1036）
 * @date		： 2017年8月28日 下午3:26:44
 * @version 	V1.0
 * @desc 扩展了zfca包中过滤器，使之支持ant风格的匹配模式
 */
public class ZFCasSSOFilter extends ZFSSOFilter {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected PathMatcher pathMatcher = new AntPathMatcher();
	
	protected SecurityManager securityManager;
	
	
	@Override
	protected boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		 String path = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		 for (String pattern : getNotCheckURLList()) {
			if(pathMatcher.match(pattern, path)){
				return true;
			}
		}
		 return false;
	}

	/**
	 * 重写参数初始化函数，以便可从配置文件读取相关参数
	 */
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		//设置 SecurityManager
		SecurityUtils.setSecurityManager(getSecurityManager());
		//获取不进行检查的URL参数
		String notCheckURLListStr =StringUtils.getSafeStr(MessageUtil.getText("niutal.sso.notcheckURL"),config.getInitParameter("notCheckURLList"));
	    if (StringUtils.isNotEmpty(notCheckURLListStr)) {
	    	List<String> notCheckURLList = new ArrayList<String>();
	    	for (String notCheckURL : StringUtils.tokenizeToStringArray(notCheckURLListStr)) {
	    		notCheckURLList.add(notCheckURL);
			}
	    	ReflectionUtils.setField("notCheckURLList", this, notCheckURLList);
	    }
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws ServletException, IOException {
		
		HttpServletRequest httpServletRequest =(HttpServletRequest)request;
		if(log.isDebugEnabled()) {
			log.debug("filter url:"+httpServletRequest.getRequestURI());
		}
		
		WebSubject subject = new WebSubject.Builder(getSecurityManager(), WebUtils.toHttp(request), WebUtils.toHttp(response)).buildWebSubject();
		ThreadContext.bind(subject);

		if(!subject.isAuthenticated()) {
			if(log.isDebugEnabled()) {
				log.debug("UnAuthenticated, doFilter");
			}
			super.doFilter(request, response, fc);			
			Subject subjectAfterDoFilter = ThreadContext.getSubject();
			if(log.isDebugEnabled()) {
				boolean isAuth = subjectAfterDoFilter.isAuthenticated();
				log.debug("subject is auth:{}", isAuth);
			}
		}else {
			if(log.isDebugEnabled()) {
				log.debug("authed , skip this doFilter,do next filter");
			}
			fc.doFilter(request, response);
		}
	}

	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	public SecurityManager getSecurityManager() {
		return securityManager;
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	
}
