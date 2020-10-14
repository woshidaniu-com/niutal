/**
 * 
 */
package com.woshidaniu.common.cache;

/**
 *  @author kzd
 *
 *	系统各个模块缓存名称定义
 *
 */
public abstract class CacheName {

	/*基础缓存*/
	public static final String CACHE_BASIC = "niutal_BASIC";
	
	/*标签缓存*/
	public static final String CACHE_TAGS = "niutal_TAGS";
	
	/*高级查询缓存*/
	public static final String CACHE_SUPERSEARCH = "niutal_SUPERSEARCH";
	
	/*统计查询缓存*/
	public static final String CACHE_TJCX = "niutal_TJCX";
	
	/*自定义表单缓存*/
	public static final String CACHE_ZDYBD = "niutal_ZDYBD";
	
	/*其他*/
	public static final String CACHE_OTHER = "niutal_OTHER";
	
	
	public static final String CACHE_MENU = "niutal_USER_MENU";
	
	
	public static final String USED_MENU = "niutal_USED_MENU";
	
	/**
	 * shiro控制的缓存
	 */
	public static final String niutal_SHIRO_CACHE = "niutal_SHIRO";
	
	/**
	 * SHIRO niutal_SHIRO_AUTHENTICATION_CACHE
	 */
	public static final String niutal_SHIRO_AUTHENTICATION_CACHE = "niutal_SHIRO_AUTHENTICATION_CACHE";
	
	/**
	 * SHIRO niutal_SHIRO_AUTHORIZATION_CACHE
	 */
	public static final String niutal_SHIRO_AUTHORIZATION_CACHE = "niutal_SHIRO_AUTHORIZATION_CACHE";
	
	/**
	 * SHIRO niutal_SHIRO_PASSWORD_RETRRY_CACHE
	 */
	public static final String niutal_SHIRO_PASSWORD_RETRRY_CACHE = "niutal_SHIRO_PASSWORD_RETRRY_CACHE";
	
	/**
	 * SHIRO niutal_SHIRO_KICKOUT_SESSION_CONTROL_CACHE
	 */
	public static final String niutal_SHIRO_KICKOUT_SESSION_CONTROL_CACHE = "niutal_SHIRO_KICKOUT_SESSION_CONTROL_CACHE";
	
}
