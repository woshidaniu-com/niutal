package com.woshidaniu.monitor.service.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.woshidaniu.cache.ehcache.EhcacheCacheStatistics;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
/**
 * @author 大康
 * 基础代码
 * @author ：康康（1571）
 * 调度线程保护，原先的设计方式有缺陷，spring的调度框架无法及时销毁线程，销毁各种Bean的顺序无法控制，导致有可能获取的是已经卸载过dll的Sigar
 */
public class EhcacheInfoWatchTask  extends AbstractIntervalTask{

	protected final Logger log = LoggerFactory.getLogger(EhcacheInfoWatchTask.class);
	//缓存队列
	private final BlockingQueue<Map<String, Object>> ehcacheSnapshot = new ArrayBlockingQueue<Map<String, Object>>(64); 
	
	@PostConstruct
	@Override
	public void startup() {
		super.startup();
	}
	
	@PreDestroy
	@Override
	public void shutdown() {
		super.shutdown();
	}

	@Autowired
	protected EhCacheCacheManager ehCacheManager;
	
	/**
	 *	缓存平均命中率、丢失率,堆内、堆外、磁盘命中率
	 */
	public void percentage(){
		try {
			
			Map<String, Object> dataMap = new HashMap<String, Object>(16);
			
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			CacheManager cacheManager = getEhCacheManager().getCacheManager();
			String[] cacheNames = cacheManager.getCacheNames();
			double in_memory_hit_percentage_sum = 0, off_heap_hit_percentage_sum = 0, on_disk_hit_percentage_sum = 0,
					cache_hit_percentage_sum = 0, cache_misse_percentage_sum = 0;
			Integer maxBytesLocalDiskPercentage_sum = 0, maxBytesLocalHeapPercentage_sum = 0,
					maxBytesLocalOffHeapPercentage_sum = 0;
			
			for (String cacheName : cacheNames) {
				
				Map<String,Object> map = new HashMap<String, Object>();
				
				Ehcache cache = cacheManager.getEhcache(cacheName);

				//缓存配置信息
				CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
				
				map.put("name", cacheConfiguration.getName());
				
				//缓存统计信息
				EhcacheCacheStatistics statistics = new EhcacheCacheStatistics(cache);
				
				double in_memory_hit_percentage = statistics.getInMemoryHitPercentage();
				double off_heap_hit_percentage = statistics.getOffHeapHitPercentage();
				double on_disk_hit_percentage = statistics.getOnDiskHitPercentage();
				double cache_hit_percentage = statistics.getCacheHitPercentage();
				double cache_misse_percentage = statistics.getCacheMissPercentage();
				
				//堆内命中率 
				map.put("in-memory-hit-pct", in_memory_hit_percentage);
				//堆外命中率
				map.put("off-heap-hit-pct", off_heap_hit_percentage);
				//磁盘命中率
				map.put("on-disk-hit-pct", on_disk_hit_percentage);
				//总缓存命中率
				map.put("cache-hit-pct", cache_hit_percentage);
				//总缓存丢失率
				map.put("cache-misse-pct", cache_misse_percentage);
				
				in_memory_hit_percentage_sum += in_memory_hit_percentage;
				off_heap_hit_percentage_sum += off_heap_hit_percentage;
				on_disk_hit_percentage_sum += on_disk_hit_percentage;
				cache_hit_percentage_sum += cache_hit_percentage;
				cache_misse_percentage_sum += cache_misse_percentage;
				
				//已用磁盘空间大小（单位：字节）
				//map.put("maxBytesLocalDisk", cacheConfiguration.getMaxBytesLocalDiskAsString());
				//已用磁盘空间百分比
				Integer maxBytesLocalDiskPercentage = cacheConfiguration.getMaxBytesLocalDiskPercentage();
						maxBytesLocalDiskPercentage = maxBytesLocalDiskPercentage == null ? 0 : maxBytesLocalDiskPercentage ;
				map.put("disk-usage", maxBytesLocalDiskPercentage);
				
				//已用堆内内存大小（单位：字节）
				//map.put("maxBytesLocalHeap", cacheConfiguration.getMaxBytesLocalHeapAsString());
				//已用堆内内存百分比
				Integer maxBytesLocalHeapPercentage = cacheConfiguration.getMaxBytesLocalHeapPercentage();
						maxBytesLocalHeapPercentage = maxBytesLocalHeapPercentage == null ? 0 : maxBytesLocalHeapPercentage ;
				map.put("heap-usage", maxBytesLocalHeapPercentage);
				
				//已用堆外内存大小（单位：字节）
				//map.put("maxBytesLocalOffHeap", cacheConfiguration.getMaxBytesLocalOffHeapAsString());
				//已用堆外内存百分比
				Integer maxBytesLocalOffHeapPercentage = cacheConfiguration.getMaxBytesLocalOffHeapPercentage();
						maxBytesLocalOffHeapPercentage = maxBytesLocalOffHeapPercentage == null ? 0 : maxBytesLocalOffHeapPercentage ;
				map.put("off-heap-usage", maxBytesLocalOffHeapPercentage);
				
				maxBytesLocalDiskPercentage_sum += maxBytesLocalDiskPercentage;
				maxBytesLocalHeapPercentage_sum += maxBytesLocalHeapPercentage;
				maxBytesLocalOffHeapPercentage_sum += maxBytesLocalOffHeapPercentage;
				
				dataList.add(map);
			}
			
			dataMap.put("caches", dataList);
			int divisor = dataList.isEmpty() ? 1 : dataList.size();
			
			//计算平均命中率（百分比）
			dataMap.put("mean-in-memory-hit-pct", in_memory_hit_percentage_sum / divisor);
			dataMap.put("mean-off-heap-hit-pct",  off_heap_hit_percentage_sum / divisor);
			dataMap.put("mean-on-disk-hit-pct",  on_disk_hit_percentage_sum / divisor);
			dataMap.put("mean-cache-hit-pct",  cache_hit_percentage_sum / divisor);
			dataMap.put("mean-cache-misse-pct",  cache_misse_percentage_sum / divisor);
			//计算平均使用率（百分比）
			dataMap.put("mean-disk-usage",  maxBytesLocalDiskPercentage_sum / divisor);
			dataMap.put("mean-heap-usage",  maxBytesLocalHeapPercentage_sum / divisor);
			dataMap.put("mean-off-heap-usage",  maxBytesLocalOffHeapPercentage_sum / divisor);
			
			ehcacheSnapshot.offer(dataMap);
			
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}

	public EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}
	

	@Override
	protected void doRun() {
		this.percentage();
	}

	public BlockingQueue<Map<String, Object>> getEhcacheSnapshot() {
		return ehcacheSnapshot;
	}
}
