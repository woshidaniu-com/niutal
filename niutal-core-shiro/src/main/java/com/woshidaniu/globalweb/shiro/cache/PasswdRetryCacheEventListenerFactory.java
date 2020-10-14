package com.woshidaniu.globalweb.shiro.cache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * 
 * @author zhidong kang
 *
 */
public class PasswdRetryCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(Properties properties) {
		
		return new AuthenticationFailedCacheListener();
	}

}
