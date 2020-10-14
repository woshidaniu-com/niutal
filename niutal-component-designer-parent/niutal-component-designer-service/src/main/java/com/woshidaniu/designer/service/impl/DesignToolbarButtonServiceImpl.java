package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IDesignToolbarButtonDao;
import com.woshidaniu.designer.dao.entities.DesignToolbarButtonModel;
import com.woshidaniu.designer.service.svcinterface.IDesignToolbarButtonService;
import com.woshidaniu.web.WebContext;

/**
 * 
 *@类名称:DesignQueryFieldServiceImpl.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午03:02:17
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designToolbarButtonService")
public class DesignToolbarButtonServiceImpl extends BaseServiceImpl<DesignToolbarButtonModel, IDesignToolbarButtonDao> implements IDesignToolbarButtonService {
	
	@Resource
	private IDesignToolbarButtonDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public List<DesignToolbarButtonModel> getToolbarButtonList(String func_code) {
		DesignToolbarButtonModel model = new DesignToolbarButtonModel();
		model.setFunc_code(func_code);
		model.setUser(WebContext.getUser());
		return dao.getToolbarButtonList(model);
	}
	
	@Override
	public List<DesignToolbarButtonModel> getReportButtonList(String func_code) {
		DesignToolbarButtonModel model = new DesignToolbarButtonModel();
		model.setFunc_code(func_code);
		model.setUser(WebContext.getUser());
		return dao.getReportButtonList(model);
	}
}
