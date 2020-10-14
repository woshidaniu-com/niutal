/**
 * 
 */
package com.woshidaniu.globalweb.shiro.cache;

import com.woshidaniu.common.factory.ServiceFactory;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;

/**
 * @author zhidong.kang
 * @class com.woshidaniu.globalweb.shiro.cache.AuthenticationFailedCacheListener
 */
public class AuthenticationFailedCacheListener extends CacheEventListenerAdapter {

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {

		CacheEventListenerAdapter service = ServiceFactory.getService("authenticationFailedRealmListener",
				CacheEventListenerAdapter.class);

		service.notifyElementExpired(cache, element);
	}

}
