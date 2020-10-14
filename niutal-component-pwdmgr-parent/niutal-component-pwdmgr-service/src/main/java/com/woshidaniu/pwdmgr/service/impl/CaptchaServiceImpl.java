package com.woshidaniu.pwdmgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.pwdmgr.dao.daointerface.CaptchaDao;
import com.woshidaniu.pwdmgr.dao.entities.CaptchaModel;
import com.woshidaniu.pwdmgr.service.svcinterface.CaptchaService;

@Service
public class CaptchaServiceImpl extends BaseServiceImpl<CaptchaModel, CaptchaDao>
		implements CaptchaService {

	@Autowired
	protected CaptchaDao captchaDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(captchaDao);
	}

	@Override
	public boolean delete(CaptchaModel model) {
		captchaDao.delete(model);
		return true;
	}

	@Override
	public String getDatetime() {
		return captchaDao.getDatetime();
	}

	public CaptchaDao getCaptchaDao() {
		return captchaDao;
	}

	public void setCaptchaDao(CaptchaDao captchaDao) {
		this.captchaDao = captchaDao;
	}
	
}
