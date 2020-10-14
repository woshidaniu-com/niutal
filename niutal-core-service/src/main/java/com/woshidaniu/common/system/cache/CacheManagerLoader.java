package com.woshidaniu.common.system.cache;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * 缓存管理
 * 
 * Create on 2013-7-5 上午10:57:47
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public class CacheManagerLoader {

	private static Logger LOG = LoggerFactory.getLogger(CacheManagerLoader.class);
	
	// 默认基础数据缓存
	public static final String			BASIC_DATE_DEFAULT_CACHE_NAME	= CacheNames.BASIC_CACHE;

	private CacheManager				cacheManager;
	private static CacheManagerLoader	instance						= new CacheManagerLoader();

	public static CacheManagerLoader getInstance() {
		return instance;
	}

	private CacheManagerLoader() {

	}

	/**
	 * 通过缓存名,得到对应的缓存
	 * 
	 * @param cacheManagerFactory
	 *            :缓存工厂类
	 * @param cacheName
	 *            :缓存名
	 * @return
	 */
	public Ehcache getCache(String cacheName) {
		return cacheManager.getEhcache(cacheName);
	}

	/**
	 * 默认得到基础数据
	 * 
	 * @return Cache
	 */
	public Ehcache getCache() {
		LOG.info("系统启用的缓存块:" + StringUtils.join(cacheManager.getCacheNames(), ","));
		return cacheManager.getEhcache(BASIC_DATE_DEFAULT_CACHE_NAME);
	}

	/**
	 * 添加缓存
	 * 
	 * @param cacheManagerFactory
	 *            :缓存工厂类
	 * @param cacheName
	 *            :缓存名
	 * @param key
	 *            :缓存KEY
	 * @param value
	 *            :缓存VALUE
	 */
	public void addElement(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 默认增加基础数据
	 * 
	 * @param key
	 *            ：缓存KEY
	 * @param value
	 *            :缓存VALUE void
	 */
	public void addElement(String key, Object value) {
		Element element = new Element(key, value);
		getCache().put(element);
	}

	/**
	 * 添加缓存后，关闭管理器
	 * 
	 * @param cacheManagerFactory
	 *            :缓存工厂类
	 * @param cacheName
	 *            :缓存名
	 * @param key
	 *            :缓存KEY
	 * @param value
	 *            :缓存VALUE
	 */
	public void addElementAndShutDown(String cacheName, String key, Object value) {
		try {
			addElement(cacheName, key, value);
		} catch (Exception e) {
			// do nothing
		} finally {
			cacheManager.shutdown();
		}
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheManagerFactory
	 *            :缓存工厂类
	 * @param cacheName
	 *            :缓存名
	 * @param key
	 *            :缓存KEY
	 * @return
	 */
	public Element getElement(String cacheName, String key) {
		return getCache(cacheName).get(key);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            :缓存KEY
	 * @return
	 */
	public Element getElement(String key) {
		return getCache().get(key);
	}

	/**
	 * 获取缓存后关闭管理器
	 * 
	 * @param cacheManagerFactory
	 *            :缓存工厂类
	 * @param cacheName
	 *            :缓存名
	 * @param key
	 *            :缓存KEY
	 * @return
	 */
	public Element getElementAndShutDown(String cacheName, String key) {
		try {
			return getCache(cacheName).get(key);
		} finally {
			cacheManager.shutdown();
		}
	}

	/**
	 * 清空某個屬性的緩存
	 * 
	 * @param key
	 *            void
	 */
	public void clearElement(String key) {
		this.getCache().remove(key, false);
	}

	/**
	 * 更新缓存，原子操作
	 * 
	 * @param key
	 *            ：缓存KEY
	 * @param value
	 *            : 緩存VALUE
	 */
	public synchronized void updateElement(String key, Object value) {
		clearElement(key);
		addElement(key, value);
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	// -------------getter ---- setter--------

}
