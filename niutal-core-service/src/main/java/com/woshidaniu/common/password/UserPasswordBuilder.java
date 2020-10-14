/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.password;

import com.woshidaniu.service.svcinterface.IYhglService;

/**
 *				    用户密码构建器，根据职工号和用户服务实现类来构建一个职工的密码，用来进行密码初始化
 * 				    此类实现类，可配置在system.properties或runtime.properties配置文件中
 * 				    配置建为userManage.userPasswordBuilder
 * 				    形如默认实现类配置形式 
 * 						userManage.userPasswordBuilder=com.woshidaniu.common.password.impl.DefaultUserPasswordBuilder
 * @author 		：康康（1571）
 * @date		： 2018年9月26日 上午11:05:40
 * @version 	V1.0
 */
public interface UserPasswordBuilder {
	
	/**
	 * @description	： 根据职工号明文密码
	 * @param zgh 职工号
	 * @return
	 */
	public String buildPassword(String zgh);
	
	/**
	 * @description	： 设置用户管理服务实现类
	 * @param yhglService
	 */
	public void setYhglService(IYhglService yhglService);
}
