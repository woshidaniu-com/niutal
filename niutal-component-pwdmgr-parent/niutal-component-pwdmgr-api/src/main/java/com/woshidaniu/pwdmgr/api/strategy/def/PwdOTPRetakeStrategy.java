package com.woshidaniu.pwdmgr.api.strategy.def;

import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.ResultData;
import com.woshidaniu.pwdmgr.api.strategy.PwdRetakeStrategy;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;

public class PwdOTPRetakeStrategy implements PwdRetakeStrategy {

	@Override
	public String name() {
		return PwdStrategy.PWD_RETAKE_BY_OTP;
	}
	
	@Override
	public ResultData advice(BindData data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResultData retake(BindData data) {
		// TODO Auto-generated method stub
		return null;
	}


}