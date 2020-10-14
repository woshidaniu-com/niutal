package com.woshidaniu.pwdmgr.dao.daointerface;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.pwdmgr.dao.entities.CaptchaModel;

@Repository
public interface CaptchaDao extends BaseDao<CaptchaModel> {

	String getUUID();
	
	String getDatetime();
}
