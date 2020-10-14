package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.service.common.ICommonQueryService;
import com.woshidaniu.service.common.ICommonSqlService;

public class CommonBaseAction extends BaseAction  {
	
	/** 公共service */
	@Resource
	protected ICommonSqlService commonSqlService;
	/**
	 * 业务框架通用结构数据查询接口；用于指定表名，列名称等进行查询
	 */
	@Resource
	private ICommonQueryService commonQueryService;

	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}

	public ICommonQueryService getCommonQueryService() {
		return commonQueryService;
	}

	public void setCommonQueryService(ICommonQueryService commonQueryService) {
		this.commonQueryService = commonQueryService;
	}
	
	
}
