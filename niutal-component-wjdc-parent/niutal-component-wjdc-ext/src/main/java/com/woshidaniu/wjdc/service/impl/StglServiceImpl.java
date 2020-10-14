/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.wjdc.common.Constants;
import com.woshidaniu.wjdc.dao.daointerface.IStglDao;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.XxglModel;
import com.woshidaniu.wjdc.event.WjstUpdateEvent;
import com.woshidaniu.wjdc.service.svcinterface.IStglService;

/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化，扩展
 * 
 * */
@Service("stglService")
public class StglServiceImpl extends BaseServiceImpl<StglModel, IStglDao> implements IStglService,ApplicationListener<WjstUpdateEvent>{
	
	@Autowired
	private IStglDao dao;
	
	@Autowired
	private EhCacheCacheManager ehCacheManager;
	
	private boolean useCache = false;
	
	@PostConstruct
	public void init() {
		{
			String val =  MessageUtil.getText("niutal.wjdc.stglServiceImpl.useCache");
			this.useCache = val != null ? Boolean.parseBoolean(val) : this.useCache;
			log.info("问卷调查,查询问卷试题是否使用缓存:{}",this.useCache);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	@SuppressWarnings("unchecked")
	private synchronized List<StglModel> getStxxAndStdlXxListWithCache(WjglModel model) {
		
		Cache cache = this.ehCacheManager.getCache(Constants.CACHE_NAME);
		
		String key = "StglServiceImpl.getStxxAndStdlXxList_" + model.getWjid();
		ValueWrapper vw = cache.get(key);
		if(vw == null){
			List<StglModel> list = this.dao.getStxxAndStdlXxList(model);
			log.debug("save cache key:"+key);
			cache.putIfAbsent(key, list);
			vw = cache.get(key);
		}
		return (List<StglModel>)vw.get();
	}
	
	/**
	 * 获取试题和试题大类排序后的列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<StglModel> getStxxAndStdlXxList(WjglModel model){
		if(this.useCache){
			return this.getStxxAndStdlXxListWithCache(model);
		}else{
			return dao.getStxxAndStdlXxList(model);			
		}
	}
	
	@Override
	public List<StglModel> getYhdjStxxAndStdlXxList(WjglModel model) {
		return dao.getYhdjStxxAndStdlXxList(model);
	}
	
	@SuppressWarnings("unchecked")
	private synchronized List<XxglModel> getStXxxxListWithCache(WjglModel model) {
		
		Cache cache = this.ehCacheManager.getCache(Constants.CACHE_NAME);
		
		String key = "StglServiceImpl.getStXxxxList_" + model.getWjid();
		ValueWrapper vw = cache.get(key);
		if(vw == null){
			List<XxglModel> list = this.dao.getStXxxxList(model);
			log.debug("save cache key:"+key);
			cache.putIfAbsent(key, list);
			vw = cache.get(key);
		}
		return (List<XxglModel>)vw.get();
	}
	
	/**
	 * 获取试题选项信息列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<XxglModel> getStXxxxList(WjglModel model){
		if(this.useCache){
			return this.getStXxxxListWithCache(model);
		}else{
			return dao.getStXxxxList(model);			
		}
	}

	@Override
	public List<XxglModel> getYhdjStXxxxList(WjglModel wjModel) {
		return dao.getYhdjStXxxxList(wjModel);
	}

	@Override
	public Map<String,Map<String,String>> getStLastOptionValues(String wjid,String djrid) {
		return dao.getStLastOptionValues(wjid,djrid);
	}
	
	@Override
	public List<StglModel> getModelList(StglModel stglModel){
		return this.dao.getModelList(stglModel);
	}
	
	@Override
	public int deleteStfl(String wjid){
		return this.dao.deleteStfl(wjid);
	}
	
	@Override
	public int deleteWjSt(String wjid) {
		return this.dao.deleteWjSt(wjid);
	}
	
	@Override
	public int deleteWjStXx(String wjid) {
		return this.dao.deleteWjStXx(wjid);
	}
	
	@Override
	public int deleteWjhd(String wjid) {
		return this.dao.deleteWjhd(wjid);
	}
	
	@Override
	public void onApplicationEvent(WjstUpdateEvent event) {
		Cache cache = this.ehCacheManager.getCache(Constants.CACHE_NAME);
		if(cache != null){
			{
				String key = "StglServiceImpl.getStxxAndStdlXxList_" + event.getWjid();
				log.debug("evict cache key:"+key);
				cache.evict(key);
			}
			
			{
				String key = "StglServiceImpl.getYhdjStxxAndStdlXxList_" + event.getWjid();
				log.debug("evict cache key:"+key);
				cache.evict(key);
			}
			
			{
				String key = "StglServiceImpl.getStXxxxList_" + event.getWjid();
				log.debug("evict cache key:"+key);
				cache.evict(key);
			}
		}
	}

	@Override
	public int insertStxx(List<?> list) {
		return this.dao.insertStxx(list);
	}

	@Override
	public int insertXxxx(List<?> list) {
		return this.dao.insertXxxx(list);
	}

	@Override
	public List<StglModel> getStxxList(String wjid) {
		return this.dao.getStxxList(wjid);
	}
}
