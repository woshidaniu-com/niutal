package com.woshidaniu.common;

import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.woshidaniu.web.context.WebContext;
import com.woshidaniu.web.utils.WebParameterUtils;
/**
 * 
 *@类名称		： ZFtalParameters.java
 *@类描述		： niutal框架参数对象
 *@创建人		：kangzhidong
 *@创建时间	：Mar 1, 2017 12:30:48 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
public abstract class ZFtalParameters {
	
	private ZFtalParameters() {
		super();
	}

	public static void initialize(ServletConfig config) {
		if (config != null) {
			WebContext.bindServletConfig(config);
			final ServletContext context = config.getServletContext();
			initialize(context);
		}
	}
	
	public static void initialize(FilterConfig config) {
		if (config != null) {
			WebContext.bindFilterConfig(config);
			final ServletContext context = config.getServletContext();
			initialize(context);
		}
	}

	public static void initialize(ServletContext context) {
		WebContext.bindServletContext(context);
	}
	
	/**
	 * @return Contexte de servlet de la webapp, soit celle monitorée ou soit celle de collecte.
	 */
	public static ServletContext getServletContext() {
		return WebContext.getServletContext();
	}

	/**
	 * 单个Boolean值解析
	 */
	public static boolean getBoolean(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getBoolean(servletOrFilterName, parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个Boolean值解析
	 */
	public static boolean getBoolean(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getBoolean(servletOrFilterName, parameter.getName(), def );
	}
	
	/**
	 * 单个Int值解析
	 */
	public static int getInt(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getInt(servletOrFilterName, parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个Int值解析
	 */
	public static int getInt(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getInt(servletOrFilterName, parameter.getName(), def );
	}
	
	/**
	 * 单个Long值解析
	 */
	public static long getLong(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getLong(servletOrFilterName, parameter.getName(), def );
	}
	
	/**
	 * 单个Long值解析
	 */
	public static long getLong(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getLong(servletOrFilterName, parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个String值解析
	 */
	public static String getString(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getString(servletOrFilterName, parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个String值解析
	 */
	public static String getString(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getString(servletOrFilterName, parameter.getName(), def );
	}
	
	/**
	 * 多个String值解析 ;多个配置可以用",; \t\n"中任意字符分割
	 */
	public static String[] getStringArray(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter){
		assert parameter != null;
		return WebParameterUtils.getStringArray(servletOrFilterName, parameter.getName(), parameter.getDefault());
	}
	
	/**多个键值对解析*/
	public static Map<String, String[]> getStringMultiMap(String servletOrFilterName, com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getStringMultiMap(servletOrFilterName, parameter.getName(), parameter.getDefault() );
    }
	
	/**
	 * 单个Boolean值解析
	 */
	public static boolean getGlobalBoolean(com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getBoolean(parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个Boolean值解析
	 */
	public static boolean getGlobalBoolean(com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getBoolean(parameter.getName(), def );
	}
	
	/**
	 * 单个Int值解析
	 */
	public static int getGlobalInt(com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getInt(parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个Int值解析
	 */
	public static int getGlobalInt(com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getInt(parameter.getName(), def );
	}
	
	/**
	 * 单个Long值解析
	 */
	public static long getGlobalLong(com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getLong(parameter.getName(), def );
	}
	
	/**
	 * 单个Long值解析
	 */
	public static long getGlobalLong(com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getLong(parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个String值解析
	 */
	public static String getGlobalString(com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getString(parameter.getName(), parameter.getDefault() );
	}
	
	/**
	 * 单个String值解析
	 */
	public static String getGlobalString(com.woshidaniu.common.ZFtalParameter parameter, String def) {
		assert parameter != null;
		return WebParameterUtils.getString(parameter.getName(), def );
	}
	
	/**
	 * 多个String值解析 ;多个配置可以用",; \t\n"中任意字符分割
	 */
	public static String[] getGlobalStringArray(com.woshidaniu.common.ZFtalParameter parameter){
		assert parameter != null;
		return WebParameterUtils.getStringArray(parameter.getName(), parameter.getDefault());
	}
	
	/**多个键值对解析*/
	public static Map<String, String> getGlobalStringMap(com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getStringMap(parameter.getName(), parameter.getDefault() );
    }
	
	/**多个键值对解析*/
	public static Map<String, String[]> getGlobalStringMultiMap(com.woshidaniu.common.ZFtalParameter parameter) {
		assert parameter != null;
		return WebParameterUtils.getStringMultiMap(parameter.getName(), parameter.getDefault() );
    }
}
