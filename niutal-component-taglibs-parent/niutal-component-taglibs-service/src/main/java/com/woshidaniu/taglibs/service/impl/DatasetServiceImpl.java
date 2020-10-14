package com.woshidaniu.taglibs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.taglibs.dao.daointerface.IDatasetDao;
import com.woshidaniu.taglibs.dao.entities.DatasetModel;
import com.woshidaniu.taglibs.service.svcinterface.IDatasetService;

/**
 * 
 *@类名称		： DatasetServiceImpl.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Jun 16, 2016 3:30:28 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@After
@Logger(model="N03",business="N0310")
@Service("datasetService")
public class DatasetServiceImpl extends BaseServiceImpl<DatasetModel, IDatasetDao> implements IDatasetService {
	
	@Resource
	protected IDatasetDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public List<DatasetModel> getModelList(String datatype, String key) {
		
		return getDao().getDatasetList(key);
		
	}
	 
	
}
