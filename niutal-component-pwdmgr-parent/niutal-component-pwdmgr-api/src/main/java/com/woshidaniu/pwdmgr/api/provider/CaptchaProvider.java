package com.woshidaniu.pwdmgr.api.provider;

import com.woshidaniu.pwdmgr.api.model.BindCaptcha;
import com.woshidaniu.pwdmgr.api.model.BindData;

/**
 * 
 *@类名称		： PwdValidateCodeStrategy.java
 *@类描述		：验证码生成,存储策略接口
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 下午1:24:06
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface CaptchaProvider {
	
	/**
	 * 
	 *@描述		：服务提供者名称
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月13日下午12:19:06
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String name();
	
	/**
	 * 
	 *@描述		：验证码提取，用于获取上次存储的验证码
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月7日下午2:19:39
	 *@param data
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public BindCaptcha input(BindData data);
	
	/**
	 * 
	 *@描述		：验证上次的验证码是否有效
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月17日下午5:22:30
	 *@param data
	 *@param captcha
	 *@return -1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String valid(BindData data,BindCaptcha captcha);
	
	/**
	 * 
	 *@描述		：重新生成新的验证码
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月7日下午2:20:39
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public BindCaptcha gen(BindData data);

	/**
	 * 
	 *@描述		：存储生成后的验证码
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月7日下午2:20:54
	 *@param captcha
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public boolean store(BindData data,BindCaptcha captcha);
	
	/**
	 * 
	 *@描述		：手动清除无用的验证码
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月18日上午11:34:24
	 *@param data
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public boolean evict(BindData data);
	
	
}
