package com.woshidaniu.pwdmgr.api.strategy;

import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.ResultData;

/**
 * 
 *@类名称		： PwdRetakeStrategy.java
 *@类描述		：密码找回策接口
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 上午9:58:48
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface PwdRetakeStrategy {

	/**
	 * 
	 *@描述		：策略名称，该策略名称将对应系统提供的找回方式代码
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月7日上午9:58:59
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String name();
	
	/**
	 * 
	 *@描述		： 密码找回前的消息通知，通常用于验证码发送
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月7日上午11:23:50
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public ResultData advice(BindData data);
	
	/**
	 * 
	 *@描述		：密码找回操作，验证提供的验证码和请求有效性
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月17日上午8:37:13
	 *@param data
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public ResultData retake(BindData data);
	
}
