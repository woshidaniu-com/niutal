package com.woshidaniu.service.provider;

import javax.annotation.Resource;

import com.woshidaniu.api.conf.KeyValueProvider;
import com.woshidaniu.service.svcinterface.IJsgnmkService;

public class JwFuncNameProvider implements KeyValueProvider<String> {

	@Resource
	protected IJsgnmkService jsgnmkService;
	
	@Override
	public String get(String key) {
		return getJsgnmkService().getValue(key);
	}

	@Override
	public boolean set(String key, String value) {
		return false;
	}

	public IJsgnmkService getJsgnmkService() {
		return jsgnmkService;
	}

	public void setJsgnmkService(IJsgnmkService jsgnmkService) {
		this.jsgnmkService = jsgnmkService;
	}
	
}
