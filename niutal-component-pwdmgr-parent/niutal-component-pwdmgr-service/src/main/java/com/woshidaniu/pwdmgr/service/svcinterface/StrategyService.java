package com.woshidaniu.pwdmgr.service.svcinterface;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.pwdmgr.dao.entities.StrategyModel;

@Service
public interface StrategyService extends BaseService<StrategyModel> {
	
	boolean delete(StrategyModel model);
	
}
