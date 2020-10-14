/**
 * 
 */
package com.woshidaniu.globalweb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;

@SuppressWarnings("serial")
public class CacheManageAction extends CommonBaseAction {

	private static final Log logger = LogFactory.getLog(CacheManageAction.class);
	
	@Autowired
	private EhCacheCacheManager ehCacheManager;
	
	public String cacheManagement(){
		CacheManager cacheManager = ehCacheManager.getCacheManager();
		String[] cacheNames = cacheManager.getCacheNames();
//		List<CacheConfiguration> cacheConfigList = new ArrayList<CacheConfiguration>();
		List<Map<String,Object>> cacheConfigList = new ArrayList<Map<String,Object>>();
		for (String string : cacheNames) {
			Ehcache cache = cacheManager.getEhcache(string);
			CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
//			cacheConfigList.add(cacheConfiguration);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name", cacheConfiguration.getName());
			map.put("maxEntriesLocalHeap", cacheConfiguration.getMaxEntriesLocalHeap());
			map.put("overflowToDisk", cacheConfiguration.isOverflowToDisk());
			//Strategy.LOCALTEMPSWAP.equals(other)
			//cacheConfiguration.getPersistenceConfiguration().getStrategy().
			
			map.put("eternal", cacheConfiguration.isEternal());
			map.put("maxElementsOnDisk", cacheConfiguration.getMaxEntriesLocalDisk());
			map.put("timeToIdleSeconds", cacheConfiguration.getTimeToIdleSeconds());
			map.put("timeToLiveSeconds", cacheConfiguration.getTimeToLiveSeconds());
			map.put("memoryStoreEvictionPolicy", cacheConfiguration.getMemoryStoreEvictionPolicy());
			cacheConfigList.add(map);
		}
		getValueStack().set("cacheConfigList", cacheConfigList);
		return SUCCESS;
	}
	
	public String refreshCache(HttpServletRequest request){
		
		String cacheKey = request.getParameter("CACHE_KEY");
		try {
			
			if(StringUtils.isNoneBlank(cacheKey)){
				
				long t1 = System.currentTimeMillis();
				
				if(logger.isDebugEnabled()){
					logger.debug("开始刷新缓存:[" + cacheKey + "]...");
				}
				
				Ehcache ehcache = ehCacheManager.getCacheManager().getEhcache(cacheKey);
				
				if(ehcache != null){
					ehcache.removeAll();
				}
				
				long t2 = System.currentTimeMillis();
				
				if(logger.isDebugEnabled()){
					logger.debug("刷新缓存结束:[" + cacheKey + "],耗时（ms）：" + (t2-t1));
				}
				
				if(logger.isDebugEnabled()){
					logger.debug("缓存刷新成功！");
				}
				
				getValueStack().set(DATA, getText("C99001", new String[]{ cacheKey }));
			}else{
				getValueStack().set(DATA, getText("C99002"));
			}
			
		} catch (Exception e) {
			getValueStack().set(DATA, getText("I99008","缓存刷新"));
			logException(e);
		}
		return DATA;
	}
	

	public EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

}
