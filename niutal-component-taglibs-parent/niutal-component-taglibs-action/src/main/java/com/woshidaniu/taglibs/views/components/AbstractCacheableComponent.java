package com.woshidaniu.taglibs.views.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.annotations.StrutsTagAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.common.cache.CacheName;

public class AbstractCacheableComponent extends Component {

	protected Logger LOG = LoggerFactory.getLogger(AbstractCacheableStrutsUIBean.class);
	
	protected ApplicationContext applicationContext = null;
	/**缓存实例*/
	protected CacheManager cacheManager;
	protected String cacheName;
	protected boolean cacheSupport = true;//是否支持缓存
	
	/**
	 * 组件是否使用缓存
	 */
	protected String cacheable;
	
	public AbstractCacheableComponent(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack);
		//判断是否使用缓存
		if(isCacheable()){
			this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
			this.cacheManager = this.applicationContext.getBean(CacheManager.class);
			this.cacheSupport = cacheManager!=null && cacheManager.getCache(getCacheName()) != null;
		}
	}
	
	public boolean isCacheable(){
		return BooleanUtils.parse(getCacheable());
	}
	
	public String getCacheable() {
		return cacheable;
	}
	
	@StrutsTagAttribute(description="set cacheable", type="String")
	public void setCacheable(String cacheable) {
		this.cacheable = cacheable;
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
