package com.woshidaniu.service.provider;

import java.util.Map;

import javax.annotation.Resource;

import com.woshidaniu.api.data.UserAccountProvider;
import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.daointerface.IUserAccountDao;

public class JwUserAccountProvider implements UserAccountProvider {

	@Resource
	protected IUserAccountDao accountDao;
	
	@Override
	public Map<String, String> getAccountStatus(String username) {
		return getAccountDao().getAccountStatus(username);
	}

	@Override
	public BaseMap getUserAccount(Map<String, Object> map) {
		return getAccountDao().getUserAccount(map);
	}

	@Override
	public int updateAccount(Map<String, Object> map) {
		return getAccountDao().updateAccount(map);
	}

	public IUserAccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IUserAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public Map<String, String> getAccountStatusByEmail(String email) {
		return null;
	}

	@Override
	public Map<String, String> getAccountStatusByPhone(String phone) {
		return null;
	}

	@Override
	public BaseMap getUserAccountByEmail(Map<String, Object> map) {
		return null;
	}

	@Override
	public BaseMap getUserAccountByPhone(Map<String, Object> map) {
		return null;
	}
	
}
