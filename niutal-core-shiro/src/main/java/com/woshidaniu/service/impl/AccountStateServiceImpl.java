package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.dao.daointerface.ILoginDao;
import com.woshidaniu.service.svcinterface.IAccountStateService;

@Service("accountStateService")
public class AccountStateServiceImpl implements IAccountStateService{

	@Resource
	private ILoginDao loginDao;
	
	public ILoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Override
	public void disableAccount(String userName) {
		if(!StringUtils.isEmpty(userName)){
			getLoginDao().disableAccount(userName);
		}
	}
	
}
