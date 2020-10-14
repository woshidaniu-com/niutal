package com.woshidaniu.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.monitor.service.task.EhcacheInfoWatchTask;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.statistics.StatisticsGateway;

@Controller
@RequestMapping("/monitor/cache")
public class EhCacheMonitorController extends BaseController {

	@Autowired
	protected EhCacheCacheManager ehCacheManager;
	protected Map<String, Object> EMPTY_MAP = new HashMap<String, Object>(1);
	
	@Autowired
	private EhcacheInfoWatchTask ehcacheInfoWatchTask;
	
	private List<Map<String,Object>> emptyDateList = new ArrayList<Map<String,Object>>(1);
	
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-cache:cx")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		
		CacheManager cacheManager = getEhCacheManager().getCacheManager();
		model.addAttribute("cacheNames", cacheManager.getCacheNames());
		model.addAttribute("cacheInfos", cacheInfos());
		
		return "/monitor/cache/index";
	}
	
	
	
	@ResponseBody
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-cache:cx")
	@RequestMapping(value = "/info", method = RequestMethod.POST )
	public Map<String, Object> info(HttpServletRequest request) throws Exception {
		
		//使用量
		Map<String, Object> item = this.ehcacheInfoWatchTask.getEhcacheSnapshot().peek();
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>(item.size());
		
		Map<String, Object> historyMap = new HashMap<String, Object>(2);
		//获取最新的状态信息
		historyMap.put("ehcache", item == null ? EMPTY_MAP : item);
		
		//迭代集合中的数据对象
		Iterator<Map<String, Object>> ite = this.ehcacheInfoWatchTask.getEhcacheSnapshot().iterator();
		while (ite.hasNext()) {
			mapList.add(ite.next());
		}
		historyMap.put("ehcacheHistory", mapList);
		
		return historyMap;
	}
	
	@ResponseBody
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-cache:cx")
	@RequestMapping(value = "/status", method = RequestMethod.POST )
	public Object status(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取最新的状态信息
		Map<String, Object> item = this.ehcacheInfoWatchTask.getEhcacheSnapshot().peek();
		return item == null ? EMPTY_MAP : item;
	}
	
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-cache:cx")
	@RequestMapping(value = "list", method = RequestMethod.GET )
	public String list(HttpServletRequest request, HttpServletResponse response,Model model){
		
		model.addAttribute("cacheInfos", cacheInfos());
		
		return "/monitor/cache/index";
	}
	
	protected List<Map<String,Object>> cacheInfos() {
		
		List<Map<String,Object>> dataList = emptyDateList;
		try {
			
			CacheManager cacheManager = getEhCacheManager().getCacheManager();
			String[] cacheNames = cacheManager.getCacheNames();
			
			dataList = new ArrayList<Map<String,Object>>(cacheNames.length);
			
			for (String cacheName : cacheNames) {
				
				Map<String,Object> map = new HashMap<String, Object>(32);
				
				Ehcache cache = cacheManager.getEhcache(cacheName);
				StatisticsGateway statistics = cache.getStatistics();
				
				/*map.put("hits", statistics.getCacheHits());
				//堆内命中数
				map.put("in-memory-hits", statistics.getInMemoryHits());
				map.put("off-heap-hits", statistics.getOffHeapHits());
				map.put("on-disk-hits", statistics.getOnDiskHits());
				
				map.put("misses", statistics.getCacheMisses());
				//堆内丢失数
				map.put("in-memory-misses", statistics.getInMemoryMisses());
				map.put("off-heap-misses", statistics.getOffHeapMisses());
				map.put("on-disk-misses", statistics.getOnDiskMisses());*/
				
				//总对象个数
				map.put("objects", statistics.getSize());
				//堆内存储对象个数
				map.put("in-memory-objects", statistics.getLocalHeapSize());
				//堆外存储对象个数
				map.put("off-heap-objects", statistics.getLocalOffHeapSize());
				map.put("on-disk-objects", statistics.getLocalDiskSize());
				
				map.put("mean-get-time", statistics.cacheGetOperation().latency().average().value());
				map.put("mean-search-time", statistics.cacheSearchOperation().latency().average().value());
				map.put("eviction-count", statistics.cacheEvictionOperation().count().value());
				map.put("searches-per-second", statistics.cacheSearchOperation().rate().value());
				map.put("writer-queue-size", statistics.getWriterQueueLength());
				
				
				CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
				
				/**  
					以下属性是必须的：
					name： Cache的名称，必须是唯一的(ehcache会把这个cache放到HashMap里)。
					maxElementsInMemory： 在内存中缓存的element的最大数目。
					maxElementsOnDisk： 在磁盘上缓存的element的最大数目，默认值为0，表示不限制。
					eternal： 设定缓存的elements是否永远不过期。如果为true，则缓存的数据始终有效，如果为false那么还要根据timeToIdleSeconds，timeToLiveSeconds判断。
					overflowToDisk： 如果内存中数据超过内存限制，是否要缓存到磁盘上。
				 */
				
				
				map.put("name", cacheConfiguration.getName());
				//已作废
				//map.put("maxElementsInMemory", cacheConfiguration.getMaxElementsInMemory());
				//map.put("maxElementsOnDisk", cacheConfiguration.getMaxElementsOnDisk());
			
				//在堆内内存中缓存的element的最大数目（单位：个）
				map.put("maxEntriesInCache", cacheConfiguration.getMaxEntriesInCache());
				//在堆外内存中缓存的element的最大数目（单位：个）
				map.put("maxEntriesLocalHeap", cacheConfiguration.getMaxEntriesLocalHeap());
				//在磁盘空间中缓存的element的最大数目（单位：个），非集群环境使用 {@link #getMaxEntriesLocalDisk()} , 集群环境使用 {@link #getMaxEntriesInCache()}.
				map.put("maxEntriesLocalDisk", cacheConfiguration.getMaxEntriesLocalDisk());
				
				map.put("eternal", cacheConfiguration.isEternal());
				map.put("overflowToDisk", cacheConfiguration.isOverflowToDisk());
				//map.put("overflowToDisk", Strategy.LOCALTEMPSWAP.compareTo(cacheConfiguration.getPersistenceConfiguration().getStrategy()) == 0);
				/**
					以下属性是可选的：
					
					diskPersistent： 是否在磁盘上持久化。指重启jvm后，数据是否有效。默认为false。
					diskAccessStripes:并发磁盘访问条数
					diskExpiryThreadIntervalSeconds： 对象检测线程运行时间间隔。标识对象状态的线程多长时间运行一次。
					diskSpoolBufferSizeMB： DiskStore使用的磁盘大小，默认值30MB。每个cache使用各自的DiskStore。
					memoryStoreEvictionPolicy： 如果内存中数据超过内存限制，向磁盘缓存时的策略。默认值LRU，可选FIFO、LFU。
					timeToIdleSeconds： 对象空闲时间，指对象在多长时间没有被访问就会失效。只对eternal为false的有效。默认值0，表示一直可以访问。
					timeToLiveSeconds： 对象存活时间，指对象从创建到失效所需要的时间。只对eternal为false的有效。默认值0，表示一直可以访问。
					
					缓存的3 种清空策略 ：
					FIFO ，First In First Out (先进先出).
					LFU ， Less Frequently Used (最少使用).意思是一直以来最少被使用的。缓存的元素有一个hit 属性，hit 值最小的将会被清出缓存。
					LRU ，Least Recently Used(最近最少使用). (ehcache 默认值).缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。 
				 */
				
				map.put("diskPersistent", cacheConfiguration.isDiskPersistent());
				map.put("diskSpoolBufferSizeMB", cacheConfiguration.getDiskSpoolBufferSizeMB());
				map.put("memoryStoreEvictionPolicy", cacheConfiguration.getMemoryStoreEvictionPolicy());
				map.put("timeToLiveSeconds", cacheConfiguration.getTimeToLiveSeconds());
				map.put("timeToLiveSeconds", cacheConfiguration.getTimeToLiveSeconds());
				
				
				//已用磁盘缓存空间大小（单位：字节）
				map.put("maxBytesLocalDisk", cacheConfiguration.getMaxBytesLocalDiskAsString());
				//已用磁盘缓存空间百分比
				map.put("maxBytesLocalDiskPercentage", cacheConfiguration.getMaxBytesLocalDiskPercentage());
				
				//已用堆内缓存空间大小（单位：字节）
				map.put("maxBytesLocalHeap", cacheConfiguration.getMaxBytesLocalHeapAsString());
				//已用堆内缓存空间百分比
				map.put("maxBytesLocalHeapPercentage", cacheConfiguration.getMaxBytesLocalHeapPercentage());
				
				//已用堆外缓存空间大小（单位：字节）
				map.put("maxBytesLocalOffHeap", cacheConfiguration.getMaxBytesLocalOffHeapAsString());
				//已用堆外缓存空间百分比
				map.put("maxBytesLocalOffHeapPercentage", cacheConfiguration.getMaxBytesLocalOffHeapPercentage());
				
				
				dataList.add(map);
			}
		} catch (Exception e) {
			logException(this, e);
		}
		return dataList;
	}
	
	public EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

}
