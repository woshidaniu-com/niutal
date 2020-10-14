package com.woshidaniu.ms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.ms.dao.daointerface.EmailHostDao;
import com.woshidaniu.ms.dao.entities.EmailHostModel;
import com.woshidaniu.ms.service.svcinterface.EmailHostService;

@Service
public class EmailHostServiceImpl extends BaseServiceImpl<EmailHostModel, EmailHostDao>
		implements EmailHostService {

	@Autowired
	protected EmailHostDao emailHostDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(emailHostDao);
	}

	@Override
	public boolean delete(EmailHostModel model) {
		emailHostDao.delete(model);
		return true;
	}

	public EmailHostDao getEmailHostDao() {
		return emailHostDao;
	}

	public void setEmailHostDao(EmailHostDao emailHostDao) {
		this.emailHostDao = emailHostDao;
	}
	
}
