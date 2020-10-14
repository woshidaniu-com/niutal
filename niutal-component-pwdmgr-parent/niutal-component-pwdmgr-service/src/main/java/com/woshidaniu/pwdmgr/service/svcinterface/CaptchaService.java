package com.woshidaniu.pwdmgr.service.svcinterface;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.pwdmgr.dao.entities.CaptchaModel;

@Service
public interface CaptchaService extends BaseService<CaptchaModel> {

	boolean delete(CaptchaModel model);

	String getDatetime();
	
}
