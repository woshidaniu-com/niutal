package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IDesignWidgetResourceDao;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignWidgetResourceModel;
import com.woshidaniu.designer.service.svcinterface.IDesignWidgetResourceService;

/**
 * 
 *@类名称: DesignWidgetResourceServiceImpl.java
 *@类描述：功能组件脚本样式资源信息service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午08:42:19
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designWidgetResourceService")
public class DesignWidgetResourceServiceImpl extends BaseServiceImpl<DesignWidgetResourceModel, IDesignWidgetResourceDao> implements IDesignWidgetResourceService {
	
	@Resource
	private IDesignWidgetResourceDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public List<DesignWidgetResourceModel> getFuncResourceList(DesignFuncModel model){
		return dao.getFuncResourceList(model);
	}
	
	@Override
	public List<DesignWidgetResourceModel> getFuncWidgetResourceList(DesignFuncModel model){
		return dao.getFuncWidgetResourceList(model);
	}
	
	@Override
	public List<DesignWidgetResourceModel> getFuncFileResourceList(DesignFuncModel model){
		return dao.getFuncFileResourceList(model);
	}
	
}
