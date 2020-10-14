/**
 * 
 */
package com.woshidaniu.service.common;

/**
 * @author zhidong.kang 密码强度校验
 *
 */
public interface IPasswordStrengthCheckService {

	/**
	 * 校验密码轻度是否符合要求
	 * @param passwordChars
	 * @return
	 */
	boolean check(String passwordChars);
	
	/**
	 * 密码规则描述
	 * @return
	 */
	String[] info();

}
