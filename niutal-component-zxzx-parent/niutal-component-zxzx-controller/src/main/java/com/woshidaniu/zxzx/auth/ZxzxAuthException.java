/**
 * 
 */
package com.woshidaniu.zxzx.auth;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：在线咨询用户认证异常
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.auth.ZxzxAuthException.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月21日上午8:47:57
 */
public class ZxzxAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ZxzxAuthException() {
		super();
	}

	
	
	public ZxzxAuthException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}



	public ZxzxAuthException(String message, Throwable cause) {
		super(message, cause);
	}

}
