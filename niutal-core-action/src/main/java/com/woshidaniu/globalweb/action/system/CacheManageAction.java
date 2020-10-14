/**
 * 
 */
package com.woshidaniu.globalweb.action.system;

import com.woshidaniu.common.action.BaseAction;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxb
 *
 */
public class CacheManageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8366622700825839943L;
	
	private static final Log logger = LogFactory.getLog(CacheManageAction.class);
	
	private org.springframework.cache.ehcache.EhCacheCacheManager ehCacheManager;
	
	/**
	 * 
	 * @return
	 */
	@RequiresRoles({"admin"})
	public String cacheManagement(){
		CacheManager cacheManager = ehCacheManager.getCacheManager();
		String[] cacheNames = cacheManager.getCacheNames();
		List<CacheConfiguration> cacheConfigList = new ArrayList<CacheConfiguration>();
		for (String string : cacheNames) {
			Ehcache cache = cacheManager.getEhcache(string);
			CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
			cacheConfigList.add(cacheConfiguration);
		}
		getValueStack().set("cacheConfigList", cacheConfigList);
		return "cacheManagement";
	}

	@RequiresRoles({"admin"})
	public String refreshCache(){
		
		String cacheKey = getRequest().getParameter("CACHE_KEY");
		
		if(StringUtils.isNoneBlank(cacheKey)){
			
			long t1 = System.currentTimeMillis();
			
			if(logger.isDebugEnabled()){
				logger.debug("开始刷新缓存:[" + cacheKey + "]...");
			}
			
			Ehcache ehcache = ehCacheManager.getCacheManager().getEhcache(cacheKey);
			if(ehcache != null){
				ehcache.removeAll();
				getValueStack().set(DATA, getText("C99001", new String[]{cacheKey}));
			}
			
			long t2 = System.currentTimeMillis();
			
			if(logger.isDebugEnabled()){
				logger.debug("刷新缓存结束:[" + cacheKey + "],耗时（ms）：" + (t2-t1));
			}
			
			if(logger.isInfoEnabled()){
				logger.debug("缓存刷新成功！");
			}
			
		}else{
			getValueStack().set(DATA, getText("C99002"));
		}
		
		return DATA;
	}
	
	@RequiresRoles({"admin"})
	public String removeCache(){
		return "removeCache";
	}

	public org.springframework.cache.ehcache.EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(
			org.springframework.cache.ehcache.EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

}
