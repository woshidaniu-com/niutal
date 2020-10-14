package com.woshidaniu.ms.service.svcinterface;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.ms.dao.entities.EmailHostModel;

@Service
public interface EmailHostService extends BaseService<EmailHostModel> {
	
	boolean delete(EmailHostModel model);

}
