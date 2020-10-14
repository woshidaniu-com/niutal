package com.woshidaniu.common.conf.def;

import javax.annotation.Resource;

import com.woshidaniu.api.conf.KeyValueProvider;
import com.woshidaniu.service.svcinterface.IFuncMenuService;

public class OracleFuncNameProvider implements KeyValueProvider<String> {

	@Resource(name  = "funcMenuService")
	protected IFuncMenuService funcMenuService;
	
	@Override
	public String get(String key) {
		return getFuncMenuService().getValue(key);
	}

	@Override
	public boolean set(String key, String value) {
		return false;
	}

	public IFuncMenuService getFuncMenuService() {
		return funcMenuService;
	}

	public void setFuncMenuService(IFuncMenuService funcMenuService) {
		this.funcMenuService = funcMenuService;
	}

}
