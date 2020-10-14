package com.woshidaniu.taglibs.views.components;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.taglibs.service.svcinterface.IDatasetService;

public abstract class AbstractDatasetStrutsUIBean extends AbstractCacheableStrutsUIBean {
	
	// 数据集查询Service接口
	protected IDatasetService datasetService;

	public AbstractDatasetStrutsUIBean(ValueStack stack,
			HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		//从spring容器获取service
		datasetService = (IDatasetService) (datasetService == null ? ServiceFactory.getService("datasetService") : datasetService);
	}
	
	@Override
	public boolean start(Writer writer) {
		//查询对于的数据集
		
		
		
		return true;
	}

}
