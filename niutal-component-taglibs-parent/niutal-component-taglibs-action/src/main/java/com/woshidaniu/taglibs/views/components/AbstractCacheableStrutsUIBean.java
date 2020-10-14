package com.woshidaniu.taglibs.views.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.cache.CacheName;

public abstract class AbstractCacheableStrutsUIBean extends AbstractComplexStrutsUIBean  {

	protected Logger LOG = LoggerFactory.getLogger(AbstractCacheableStrutsUIBean.class);
	/**缓存实例*/
	protected CacheManager cacheManager;
	protected String cacheName;
	protected boolean cacheSupport = true;//是否支持缓存
	
	public AbstractCacheableStrutsUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		//判断是否使用缓存
		if(isCacheable()){
			this.cacheManager = this.applicationContext.getBean(CacheManager.class);
			this.cacheSupport = cacheManager!=null && cacheManager.getCache(getCacheName()) != null;
		}
	}
	
	public CacheManager getCacheManager() {
		return cacheManager;
	}
	
	public String getCacheName() {
		return cacheName == null ? CacheName.CACHE_TAGS : cacheName;
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	public Cache getCache() {
		return getCacheManager().getCache(getCacheName());
	}
	
}
