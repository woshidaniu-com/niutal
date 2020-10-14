package com.woshidaniu.common.filter;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.woshidaniu.api.conf.KeyValueProvider;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.web.sitemesh3.config.ParamConfigurableSiteMeshFilter;

/**
 * 
 *@类名称		： ZFParamConfigurableSiteMeshFilter.java
 *@类描述		：扩展实现注入基于request参数decorator值进行动态定位装饰器的选择器
 *@创建人		：kangzhidong
 *@创建时间	：Feb 23, 2017 10:42:51 AM
 *@修改人		：
 *@修改时间	：
 *@版本号		: v1.0
 */
public class ZFParamConfigurableSiteMeshFilter extends ParamConfigurableSiteMeshFilter {

	@Resource(name = "funcNameProvider")
	protected KeyValueProvider<String> funcNameProvider;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		//将前端参数全部传递到下一步请求
		Map<String, String[]>  params = WebUtils.toHttp(servletRequest).getParameterMap();
		for (String key : params.keySet()) {
			servletRequest.setAttribute(key, params.get(key));
		}

		String gnmkdm = servletRequest.getParameter("gnmkdmKey");
	    if(StringUtils.isEmpty(gnmkdm)){
		   gnmkdm = servletRequest.getParameter("gnmkdm"); 
	    }
	    servletRequest.setAttribute("gnmkdm", gnmkdm);
		//扩展功能名称获取逻辑：基于缓存提高效率
		String layout = servletRequest.getParameter("layout");
		if( getFuncNameProvider() != null && StringUtils.isNotEmpty(gnmkdm) && StringUtils.isNotEmpty(layout)){
			servletRequest.setAttribute("gnmkmc", getFuncNameProvider().get(gnmkdm));
		}
		
		super.doFilter(servletRequest, servletResponse, filterChain);
	}

	public KeyValueProvider<String> getFuncNameProvider() {
		return funcNameProvider;
	}

	public void setFuncNameProvider(KeyValueProvider<String> funcNameProvider) {
		this.funcNameProvider = funcNameProvider;
	}
    
}
