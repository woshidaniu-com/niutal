/**
 * 
 */
package com.woshidaniu.zxzx.auth;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.auth.ZxzxAuthStateChecker.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月20日下午8:13:17
 */
public interface AuthStateResolver {
	/**
	 * 
	 * <p>
	 * 方法说明：用户是否登录
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年6月20日下午8:14:31
	 * <p>
	 * 
	 * @param data
	 * @return
	 */
	boolean isAuthenticated();

	/**
	 * <p>
	 * 方法说明：用户认证,如果认证不通过请抛出<link>com.woshidaniu.zxzx.auth.ZxzxAuthException</link>异常
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年6月20日下午8:37:19
	 * <p>
	 * 
	 * @param request
	 * @return
	 */
	boolean authenticate(String username, String password);
	
	/**
	 * 
	 * <p>方法说明：获取当前会话用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年6月21日下午1:19:51<p>
	 * @return
	 */
	String getPrinciple();
}
