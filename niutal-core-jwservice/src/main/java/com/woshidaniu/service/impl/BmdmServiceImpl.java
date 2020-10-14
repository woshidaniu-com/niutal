package com.woshidaniu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.daointerface.IBmdmDao;
import com.woshidaniu.entities.BmdmModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IBmdmService;
@Service
public class BmdmServiceImpl extends CommonBaseServiceImpl<BmdmModel, IBmdmDao> implements IBmdmService {

	@Resource
	private IBmdmDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public List<BmdmModel> queryModel(Map<String, String> map){
		return getDao().queryModel(map);
	}
 
}
