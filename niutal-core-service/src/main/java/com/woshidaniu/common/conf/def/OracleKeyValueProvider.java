package com.woshidaniu.common.conf.def;

import javax.annotation.Resource;

import com.woshidaniu.api.conf.KeyValueProvider;
import com.woshidaniu.service.svcinterface.ICsszService;

/**
 * 
 *@类名称		： OracleKeyValueProvider.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：2017年5月31日 下午12:04:50
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class OracleKeyValueProvider implements KeyValueProvider<String> {

	@Resource
	protected ICsszService csszService;
	
	@Override
	public String get(String key) {
		return getCsszService().getValue(key);
	}

	@Override
	public boolean set(String key, String value) {
		getCsszService().setValue(key, value);
		return true;
	}
	
	public ICsszService getCsszService() {
		return csszService;
	}

	public void setCsszService(ICsszService csszService) {
		this.csszService = csszService;
	}
	
}
