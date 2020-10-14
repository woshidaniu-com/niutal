package com.woshidaniu.i18n.plugin;

import java.sql.Statement;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.LocaleUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.orm.mybatis.interceptor.AbstractDataI18nExternalInterceptor;
import com.woshidaniu.orm.mybatis.spring.support.MybatisDataSourceDao;
import com.woshidaniu.web.context.WebContext;
import com.woshidaniu.web.utils.WebUtils;

@Intercepts( { 
	@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class })
})
public class DataI18nExternalInterceptor extends AbstractDataI18nExternalInterceptor {

	protected MybatisDataSourceDao daoSupport ;
	
	@Override
	public SqlSession getSqlSession() {
		//获取daoSupport
		daoSupport = BlankUtils.isBlank(daoSupport) ? (MybatisDataSourceDao)ServiceFactory.getService("i18nDataSourceDao") : daoSupport;
		// 从国际化SqlSessionFactory中获取Session对象
		SqlSession i18nSession = daoSupport.getDaoSupport().getSqlSession();
		return i18nSession;
	}

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
	
	public MybatisDataSourceDao getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(MybatisDataSourceDao daoSupport) {
		this.daoSupport = daoSupport;
	}

	
	
}
