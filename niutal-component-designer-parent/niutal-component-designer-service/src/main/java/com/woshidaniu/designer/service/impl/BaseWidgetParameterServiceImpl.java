package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IBaseWidgetParameterDao;
import com.woshidaniu.designer.dao.entities.BaseWidgetParameterModel;
import com.woshidaniu.designer.service.svcinterface.IBaseWidgetParameterService;



/**
 * 
 *@类名称: BaseWidgetParameterServiceImpl.java
 *@类描述：功能js组件初始化参数信息Service接口实现：指定系统中的js组件初始化需要哪些参数，以及默认值
 *@创建人：kangzhidong
 *@创建时间：2015-5-13 下午04:12:08
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N0105",business="N010525")
@Service
public class BaseWidgetParameterServiceImpl extends BaseServiceImpl<BaseWidgetParameterModel, IBaseWidgetParameterDao> implements IBaseWidgetParameterService {

	@Resource
	protected IBaseWidgetParameterDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public List<BaseWidgetParameterModel> getWidgetParameterList( String widget_guid) {
		return dao.getWidgetParameterList(widget_guid);
	}
	
	
}
