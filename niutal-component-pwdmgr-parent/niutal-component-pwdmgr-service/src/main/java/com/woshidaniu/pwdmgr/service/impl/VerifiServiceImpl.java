package com.woshidaniu.pwdmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.pwdmgr.dao.daointerface.VerifiDao;
import com.woshidaniu.pwdmgr.dao.entities.VerifiModel;
import com.woshidaniu.pwdmgr.service.svcinterface.VerifiService;

@Service
public class VerifiServiceImpl extends BaseServiceImpl<VerifiModel, VerifiDao>
		implements VerifiService {

	@Autowired
	protected VerifiDao VerifiDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(VerifiDao);
	}

	@Override
	public boolean delete(VerifiModel model) {
		List<String> ids = new ArrayList<String>();
		for (String id : model.getId().split(",")) {
			if(id.startsWith("R-")){
				continue;				
			}
			ids.add(id);
		}
		if(ids.size() == 0){
			return true;				
		}
		for (String id : ids) {
			model.setId(id);
			getVerifiDao().delete(model);
		}
		return true;
	}

	@Override
	public boolean updateVerifi(VerifiModel model) {
		for (VerifiModel verifiModel : model.getList()) {
			if(verifiModel.getId().startsWith("R-")){
				getVerifiDao().insert(verifiModel);					
			}else {
				getVerifiDao().update(verifiModel);
			}
		}
		return true;
	}
	
	public VerifiDao getVerifiDao() {
		return VerifiDao;
	}

	public void setVerifiDao(VerifiDao VerifiDao) {
		this.VerifiDao = VerifiDao;
	}
	
}
