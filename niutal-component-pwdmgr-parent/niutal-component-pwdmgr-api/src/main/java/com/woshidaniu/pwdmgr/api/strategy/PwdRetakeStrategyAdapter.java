package com.woshidaniu.pwdmgr.api.strategy;

import com.woshidaniu.pwdmgr.api.model.BindField;
import com.woshidaniu.pwdmgr.api.provider.DataInputProvider;
/**
 * 
 *@类名称		： PwdRetakeStrategyAdapter.java
 *@类描述		：密码找回策略Adapter
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 下午2:24:31
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public abstract class PwdRetakeStrategyAdapter implements PwdRetakeStrategy {

	protected DataInputProvider accountProvider;
	protected BindField[] bindFields;

	public PwdRetakeStrategyAdapter(){
	}
	
	public void setBindFields(BindField[] bindFields) {
		this.bindFields = bindFields;
	}

	public DataInputProvider getAccountProvider() {
		return accountProvider;
	}

	public void setAccountProvider(DataInputProvider accountProvider) {
		this.accountProvider = accountProvider;
	}
	
}
