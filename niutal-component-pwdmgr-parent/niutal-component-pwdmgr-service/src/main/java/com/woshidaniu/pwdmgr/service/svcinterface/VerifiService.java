package com.woshidaniu.pwdmgr.service.svcinterface;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.pwdmgr.dao.entities.VerifiModel;

@Service
public interface VerifiService extends BaseService<VerifiModel> {

	boolean delete(VerifiModel model);

	boolean updateVerifi(VerifiModel model);
	
}
