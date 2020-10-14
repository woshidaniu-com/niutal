package com.woshidaniu.pwdmgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.pwdmgr.dao.daointerface.StrategyDao;
import com.woshidaniu.pwdmgr.dao.entities.StrategyModel;
import com.woshidaniu.pwdmgr.service.svcinterface.StrategyService;

@Service
public class StrategyServiceImpl extends BaseServiceImpl<StrategyModel, StrategyDao>
		implements StrategyService {

	@Autowired
	protected StrategyDao strategyDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(strategyDao);
	}

	@Override
	public boolean delete(StrategyModel model) {
		strategyDao.delete(model);
		return true;
	}

	public StrategyDao getStrategyDao() {
		return strategyDao;
	}

	public void setStrategyDao(StrategyDao strategyDao) {
		this.strategyDao = strategyDao;
	}
	
}
