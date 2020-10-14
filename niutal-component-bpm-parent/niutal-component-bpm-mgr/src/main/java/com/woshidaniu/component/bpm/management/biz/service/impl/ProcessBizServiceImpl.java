/**
 * 
 */
package com.woshidaniu.component.bpm.management.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.component.bpm.common.BaseDao;
import com.woshidaniu.component.bpm.common.BaseServiceImpl;
import com.woshidaniu.component.bpm.management.biz.dao.daointerface.IProcessBizDao;
import com.woshidaniu.component.bpm.management.biz.dao.entities.ProcessBizModel;
import com.woshidaniu.component.bpm.management.biz.service.svcinterface.IProcessBizService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：ProcessBizServiceImpl
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.biz.controller.service.impl.ProcessBizServiceImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月10日上午11:41:14
 */
@Service("processBizService")
public class ProcessBizServiceImpl extends BaseServiceImpl<ProcessBizModel, BaseDao<ProcessBizModel>>
		implements IProcessBizService {

	@Autowired
	protected IProcessBizDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.dao = this.dao;
	}

	
	
}
