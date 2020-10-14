package com.woshidaniu.service.common.factory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;

@Scope("singleton")
public class BaseConstantFactory implements InitializingBean {

	protected static ConcurrentMap<String, String> COMPLIED_FORMAT = new ConcurrentHashMap<String, String>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public static String getConstant(String key) {
		if (StringUtils.isNotEmpty(key)) {
			String ret = COMPLIED_FORMAT.get(key);
			if (ret != null) {
				return ret;
			}
			ret = null;
			String existing = COMPLIED_FORMAT.putIfAbsent(key, ret);
			if (existing != null) {
				ret = existing;
			}
			return ret;
		}
		return null;
	}
	 
}
