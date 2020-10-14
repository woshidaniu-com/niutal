package com.woshidaniu.pwdmgr.provider;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.provider.DataInputProvider;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;
import com.woshidaniu.pwdmgr.service.svcinterface.UserAccountService;

/**
 * 
 *@类名称		： OracleAccountDataInputProvider.java
 *@类描述		：基于Oracle数据库存储的账号信息提供实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月13日 下午3:21:49
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class OracleAccountDataInputProvider implements DataInputProvider {

	protected Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	protected UserAccountService accountService;
	
	@Override
	public String name() {
		return PwdStrategy.DEFAULT_STRATEGY;
	}

	@Override
	public BaseMap input(BindData data) {
		return getAccountService().getUserAccount(data);
	}

	public UserAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(UserAccountService accountService) {
		this.accountService = accountService;
	}
	
	
}
