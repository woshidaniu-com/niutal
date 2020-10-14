package com.woshidaniu.wjdc.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.wjdc.common.Constants;
import com.woshidaniu.wjdc.dao.daointerface.IDjrffDao;
import com.woshidaniu.wjdc.dao.entites.DjrffModel;
import com.woshidaniu.wjdc.service.svcinterface.IDjrffService;

@Service("djrffService")
public class DjrffServiceImpl implements IDjrffService{
	
	private static final Logger log = LoggerFactory.getLogger(DjrffServiceImpl.class);
	
	@Autowired
	private IDjrffDao djrffDao;
	
	@Autowired
	private EhCacheCacheManager ehCacheManager;
	
	private boolean useCache = false;
	
	@PostConstruct
	public void init() {
		{
			String val =  MessageUtil.getText("niutal.wjdc.djrffServiceImpl.useCache");
			this.useCache = val != null ? Boolean.parseBoolean(val) : this.useCache;
			log.info("问卷调查,查询答卷人分发表是否使用缓存:{}",this.useCache);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DjrffModel> queryWithCache(String wjid,String zjz) {
		Cache cache = ehCacheManager.getCache(Constants.CACHE_NAME);
		
		String key = "drjff_"+ wjid+ "_"+ zjz;
		ValueWrapper vw = cache.get(key);
		if(vw == null){
			DjrffModel model = new DjrffModel();
			model.setWjid(wjid);
			model.setZjz(zjz);
			List<DjrffModel> list = this.djrffDao.query(model);
			log.debug("save cache key:"+key);
			cache.putIfAbsent(key, list);
			vw = cache.get(key);
		}
		return (List<DjrffModel>) vw.get();
	}
	
	private void deleteCache(String wjid,String zjz){
		Cache cache = ehCacheManager.getCache(Constants.CACHE_NAME);
		String key = "drjff_"+ wjid + "_"+ zjz;
		cache.evict(key);
	}

	@Override
	public List<DjrffModel> query(DjrffModel model) {
		return this.djrffDao.query(model);
	}

	@Override
	public int updateDoingZt(String zjz,String wjid,String lxid) {
		if(this.useCache){
			this.deleteCache(wjid, zjz);
		}
		return this.djrffDao.updateDoingZt(zjz,wjid,lxid);
	}

	@Override
	public void deleteAll(String wjid) {
		DjrffModel model = new DjrffModel();
		model.setWjid(wjid);
		List<DjrffModel> list = this.djrffDao.query(model);
		for(DjrffModel m : list){
			this.deleteCache(wjid, m.getZjz());
		}
		this.djrffDao.deleteAll(wjid);
	}

	@Override
	public int insertBatch(List<DjrffModel> models) {
		return this.djrffDao.insertBatch(models);
	}

	@Override
	public int insert(DjrffModel model) {
		return this.djrffDao.insert(model);
	}
	
	@Override
	public int insertSafe(DjrffModel model) {
		return this.djrffDao.insertSafe(model);
	}

	@Override
	public int update(DjrffModel model) {
		return this.djrffDao.update(model);
	}

	@Override
	public List<String> queryWjidList(String userid) {
		return this.djrffDao.queryWjidList(userid);
	}

	@Override
	public List<String> queryUnfinishWjidList(String userid) {
		return this.djrffDao.queryUnfinishWjidList(userid);
	}

	@Override
	public List<String> queryFinisedhWjidList(String userid) {
		return this.djrffDao.queryFinisedhWjidList(userid);
	}

	@Override
	public int updateFinishZt(String zjz, String wjid, String lxid) {
		if(this.useCache){
			this.deleteCache(wjid, zjz);
		}
		return this.djrffDao.updateFinishZt(zjz, wjid, lxid);
	}

	@Override
	public List<DjrffModel> queryByZjz(String zjz) {
		DjrffModel model = new DjrffModel();
		model.setZjz(zjz);
		return this.djrffDao.query(model);
	}

	@Override
	public List<DjrffModel> queryByWjidAndZjz(String wjid,String zjz) {
		if(this.useCache){
			return this.queryWithCache(wjid,zjz);
		}else{
			DjrffModel model = new DjrffModel();
			model.setWjid(wjid);
			model.setZjz(zjz);
			return this.djrffDao.query(model);
		}
	}

	@Override
	public List<String> queryZjzList(DjrffModel model) {
		return this.djrffDao.queryZjzList(model);
	}
}
