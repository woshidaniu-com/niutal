package com.woshidaniu.i18n.plugin;

import java.sql.Statement;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.woshidaniu.basicutils.LocaleUtils;
import com.woshidaniu.orm.mybatis.interceptor.AbstractDataI18nInternalInterceptor;
import com.woshidaniu.web.context.WebContext;
import com.woshidaniu.web.utils.WebUtils;

@Intercepts( { 
	@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class })
})
public class DataI18nInternalInterceptor extends AbstractDataI18nInternalInterceptor {

	/**
	 * 
	 *@描述		： 兼容SpringMVC和Struts2的上下文获取Request对象方法
	 *@创建人		: kangzhidong
	 *@创建时间	: Feb 8, 201711:24:03 AM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		if(requestAttributes != null){
			return requestAttributes.getRequest();
		}
		return WebUtils.toHttp(WebContext.getRequest());
	}
	
	@Override
	public Locale getLocale() {
		// 获取当前上下文中的Locale对象
		return LocaleUtils.getLocale(getRequest());
	}
	
}
