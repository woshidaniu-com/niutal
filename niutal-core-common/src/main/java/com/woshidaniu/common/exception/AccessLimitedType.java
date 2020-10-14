/**
 * 
 */
package com.woshidaniu.common.exception;

/**
 * @author xiaokang
 * 
 * 
 * @描述 访问受限类别
 *
 */
public abstract class AccessLimitedType {

	/**
	 * 权限不足
	 */
	public static int INSUFFICIENT_PRIVILEGES = 1;
	
	/**
	 * 不在功能开放时间
	 */
	public static int NOT_IN_OPEN = 2;
	
	/**
	 * 由于安全问题拒绝访问
	 */
	public static int SECURITY_REASON = 3;
	
	/**
	 * 其他问题
	 */
	public static int OTHER = 99;
	
}
