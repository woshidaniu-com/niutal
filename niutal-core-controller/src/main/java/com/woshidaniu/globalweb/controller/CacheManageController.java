/**
 * 
 */
package com.woshidaniu.globalweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * @author kzd
 *
 */
@Controller
@RequestMapping("/system/cache")
public class CacheManageController extends BaseController {

	
	private static final Log logger = LogFactory.getLog(CacheManageController.class);
	
	@Autowired
	private EhCacheCacheManager ehCacheManager;
	
	/**
	 * 
	 * @return
	 */
	@RequiresRoles({"admin"})
	@RequestMapping("/cacheManagement")
	public ModelAndView cacheManagement(HttpServletRequest request,ModelAndView view){
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
		view.addObject("cacheConfigList", cacheConfigList);
		view.setViewName("/globalweb/comp/xtgl/cache/cacheManagement");
		return view;
	}

	@RequiresRoles({"admin"})
	@ResponseBody
	@RequestMapping("/refreshCache")
	public Object refreshCache(HttpServletRequest request){
		
		String cacheKey = request.getParameter("CACHE_KEY");
		
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
			
			return MessageKey.CACHE_REFRESH_SUCCESS.getJson(new String[]{cacheKey});
		}else{
			return MessageKey.CACHE_REFRESH_NONE.getJson();
		}
		
	}
	
	@RequiresRoles({"admin"})
	public String removeCache(){
		return "removeCache";
	}

	public EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

}
