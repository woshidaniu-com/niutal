package com.woshidaniu.common.conf.def;

import java.util.Map;

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
public class OracleKeyValuesProvider implements KeyValueProvider<Map<String, String>> {

	@Resource
	protected ICsszService csszService;
	
	@Override
	public Map<String, String> get(String key) {
		return getCsszService().getValues(key);
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
