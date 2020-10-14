package com.woshidaniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.INjdmDao;
import com.woshidaniu.dao.entities.NjdmModel;
import com.woshidaniu.service.svcinterface.INjdmService;

@Service("njdmService")
public class NjdmServiceImpl extends BaseServiceImpl<NjdmModel,INjdmDao> implements INjdmService {

	@Resource
	private INjdmDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	public INjdmDao getDao() {
		return dao;
	}

	public void setDao(INjdmDao dao) {
		this.dao = dao;
	}

	@Override
	public List<NjdmModel> cxNjdmList() {
		return dao.cxNjdmList();
	}

}
