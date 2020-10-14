package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IXxzywhDao;
import com.woshidaniu.entities.XxzywhModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IXxzywhService;


/**
 * 
 *@类名称:XxzywhServiceImpl.java
 *@类描述：学信专业维护
 *@创建人：gc
 *@创建时间：2015-11-6 下午01:57:46
 *@版本号:v1.0
 */
@After
@Logger(model="N0105",business="N010526")
@Service
public class XxzywhServiceImpl extends CommonBaseServiceImpl<XxzywhModel, IXxzywhDao> implements IXxzywhService {
	
	@Resource
	private  IXxzywhDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.afterPropertiesSet();
	    super.setDao(dao);
	}
	
}
