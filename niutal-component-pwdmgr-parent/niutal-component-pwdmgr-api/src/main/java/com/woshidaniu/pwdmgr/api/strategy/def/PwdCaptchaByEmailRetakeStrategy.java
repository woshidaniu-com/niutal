package com.woshidaniu.pwdmgr.api.strategy.def;

import com.woshidaniu.pwdmgr.api.model.BindField;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;
/**
 * 
 *@类名称		： PwdEmailRetakeStrategy.java
 *@类描述		：邮件找回密码策略实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 下午1:55:02
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class PwdCaptchaByEmailRetakeStrategy extends PwdCaptchaRetakeStrategyAdapter {

	public PwdCaptchaByEmailRetakeStrategy(){
		this.bindFields = new BindField[]{new BindField( PwdStrategy.PWD_RETAKE_BY_EMAIL, "邮箱", true)};
	}
	
	@Override
	public String name() {
		return PwdStrategy.PWD_RETAKE_BY_EMAIL;
	}
	
}