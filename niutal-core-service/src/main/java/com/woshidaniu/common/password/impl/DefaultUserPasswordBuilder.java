/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.password.impl;

import com.woshidaniu.common.password.UserPasswordBuilder;
import com.woshidaniu.service.svcinterface.IYhglService;

/**
 * @className	： DefaultUserPasswordPairBuilder
 * @description	： 默认用户密码构建器，使用相同的密码
 * @author 		：康康（1571）
 * @date		： 2018年9月26日 上午11:20:06
 * @version 	V1.0
 */
public class DefaultUserPasswordBuilder implements UserPasswordBuilder{
	
	protected IYhglService yhglService;
	
	protected String defaultPassword = "Abcd1234@."; 
	
	@Override
	public String buildPassword(String zgh) {
		return defaultPassword;
	}

	@Override
	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService; 
	}
}
