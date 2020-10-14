package com.woshidaniu.i18n.plugin;

import java.sql.Connection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.woshidaniu.basicutils.LocaleUtils;
import com.woshidaniu.orm.mybatis.interceptor.AbstractDataI18nColumnInterceptor;
import com.woshidaniu.web.context.WebContext;
import com.woshidaniu.web.utils.WebUtils;

@Intercepts( { 
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }), 
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) 
})
public class DataI18nColumnInterceptor extends AbstractDataI18nColumnInterceptor {

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
