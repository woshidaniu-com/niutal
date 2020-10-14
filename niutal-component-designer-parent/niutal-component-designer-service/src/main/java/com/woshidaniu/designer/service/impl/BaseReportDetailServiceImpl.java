package com.woshidaniu.designer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IBaseReportDetailDao;
import com.woshidaniu.designer.dao.entities.BaseReportDetailModel;
import com.woshidaniu.designer.service.svcinterface.IBaseReportDetailService;

/**
 * 
 *@类名称: BaseReportDetailServiceImpl.java
 *@类描述：高级报表基本信息表service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-6-3 下午07:59:16
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0315")
@Service
public class BaseReportDetailServiceImpl extends BaseServiceImpl<BaseReportDetailModel, IBaseReportDetailDao> implements IBaseReportDetailService {
	
	@Resource
	protected IBaseReportDetailDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	
	@Override
	@Comment
	public boolean insert(BaseReportDetailModel model) {
		dao.insert(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean update(BaseReportDetailModel model) {
		dao.update(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean delete(BaseReportDetailModel model) {
		dao.delete(model);
		return true;
	}
	
	@Override
	public int getUseCount(BaseReportDetailModel model) {
		return dao.getUseCount(model);
	}
	
	@Override
	public BaseReportDetailModel getReportDetailModel(String report_guid){
		return dao.getReportDetailModel(report_guid);
	}
	
	
}
