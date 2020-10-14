/**
 * 
 */
package com.woshidaniu.zxzx.auth;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:com.woshidaniu.zxzx.ZxzxAuthTokenValidator.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月21日上午9:11:20
 */
public interface ZxzxAuthentication {

	void login(ZxzxAuthToken token);
	
	boolean isAuthenticated();
	
	String getPrinciple();
	
}
