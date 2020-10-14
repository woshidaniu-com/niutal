package com.woshidaniu.pwdmgr.api.strategy.def;

import com.woshidaniu.pwdmgr.api.model.BindField;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;

/**
 * 
 *@类名称		： PwdCaptchaByPhoneRetakeStrategy.java
 *@类描述		：手机找回密码策略实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 上午11:55:40
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class PwdCaptchaByPhoneRetakeStrategy extends PwdCaptchaRetakeStrategyAdapter {

	public PwdCaptchaByPhoneRetakeStrategy(){
		this.bindFields = new BindField[]{new BindField( PwdStrategy.PWD_RETAKE_BY_PHONE, "手机号码", true)};
	}
	
	@Override
	public String name() {
		return PwdStrategy.PWD_RETAKE_BY_PHONE;
	}
	
}
