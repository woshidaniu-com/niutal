package com.woshidaniu.common.system.cache;

import com.woshidaniu.beanutils.reflection.ReflectionUtils;

import net.sf.ehcache.CacheManager;

public class CacheHelper {

	public static void setFiledValue(CacheManager cacheManager) {
		try {
			ReflectionUtils.setValueByFieldName(CacheManagerLoader.getInstance(), "cacheManager", cacheManager);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	
}
