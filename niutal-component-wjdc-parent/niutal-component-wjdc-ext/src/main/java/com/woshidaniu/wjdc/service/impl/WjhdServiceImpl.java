package com.woshidaniu.wjdc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.woshidaniu.wjdc.dao.daointerface.IWjhdDao;
import com.woshidaniu.wjdc.dao.entites.WjhdModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjhdService;
import com.woshidaniu.wjdc.vo.WjhdWorkspace;

/**
 * 问卷回答服务
 * @author 1571
 * 
 * 这里我们encache缓存用户的所有操作，包括更新操作，删除操作，插入操作
 * 
 * 当调用updateFinishZt方法的时候，就是释放这些缓存的操作的时机
 * 
 * 这里要把所有的问卷回答放入内存，那么我们考虑一下容量，假设一份问卷调查的上限的200题，且一个学校有10000人答题，那么就要200万个对象条目！！！
 * 也就是200万个WjhdModel,所以说，需要nginx负载均衡一下,设置成1个nginx和4个tomcat

 * 文件回答服务,专门为压测提供
 * 
 * 因为ehcache的过期通知事件是访问时被动触发，所以这里我们周期性扫描
 * 如果有某个WjhdWorkspace超过30分钟没有访问，那么我们断定这个是过期数据，持久化到数据库当中，此时如果碰到恰好用户点击了提交按钮，那么就要判断一下是否已经是“完成”状态
 * 如果是“完成”状态，那么我们就没有必要持久化了
 * 
 * 单一的提交队列是一种尝试性的特性，通过开关来开启关闭，保证基本可用
 * 单一的提交队列是减少数据库锁竞争的一种方法，因为问卷调查是读写评率相当的操作，而不是其他的读远远大于写的操作
 * 
 * @author 1571
 *
 */
//TODO 待完善
public class WjhdServiceImpl implements IWjhdService{
	
	private static final Logger log = LoggerFactory.getLogger(WjhdServiceImpl.class);
	
	@Resource
	private IWjhdDao wjhdDao;
	
	/**
	 * 检测是否过期
	 */
	private final ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(1,new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r,"WjhdServiceImpl-scheduledExecutorService-checkTimeout");
		}
	}, new ThreadPoolExecutor.CallerRunsPolicy());
	
	/**
	 * 提交队列,数据库插入操作只使用单一线程进行操作
	 */
	private final ThreadPoolExecutor submitThreadPoolExecutor = new ThreadPoolExecutor(1, 1, 5*60,TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10000), new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r,"WjhdServiceImpl-submitThreadPoolExecutor");
		}
	}, new ThreadPoolExecutor.CallerRunsPolicy());
	
	private static final String CACHE_NAME = "niutal_WJDC_WJHD";
	
	@Autowired
	private EhCacheCacheManager ehCacheManager;
	
	@PostConstruct
	public void init() {
		
		this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				
			}
		}, 60,60, TimeUnit.SECONDS);
	}
	
	@PreDestroy
	public void stop(){
		this.scheduledExecutorService.shutdown();
		for(int i =0;i<100;i++){
			try {
				boolean termined = this.scheduledExecutorService.awaitTermination(10, TimeUnit.MILLISECONDS);
				if(termined){
					break;
				}
			} catch (InterruptedException e) {
				//ignore
			}
		}
	}
	
	/**
	 * 这里检测是否长时间没有访问，如果是这样的，那么就持久化到数据库 
	 */
	protected synchronized void checkTimeout(){
		
		Cache cache = this.ehCacheManager.getCache(CACHE_NAME);
		
		//如果发现了这样的，投递到提交队列
		
	}
	
	private String getWorkspaceKey(String wjid,String djrid){
		return String.format("wjhd_%s_%s", wjid,djrid);
	}
	
	private WjhdWorkspace buildWjhdWorkspace(String wjid,String djrid){
		
		log.debug("build workspace : wjid:{},djrid:{}",wjid,djrid);
		
		List<WjhdModel> list = this.wjhdDao.queryWjhd(wjid, djrid);
		
		WjhdWorkspace workspace = new WjhdWorkspace(wjid,djrid);
		//TODO 这里需要查询数据库
		workspace.setFinish(false);
		workspace.setLastAccessTime(System.currentTimeMillis());
		workspace.setWjhdModels(new LinkedList<WjhdModel>(list));
		
		return workspace;
	}

	@Override
	public int saveYhdj(String wjid,String djrid,List<Map<String, String>> paramsList) {
		Cache cache = this.ehCacheManager.getCache(CACHE_NAME);
		String cacheKey = this.getWorkspaceKey(wjid, djrid);
		ValueWrapper vw = cache.get(cacheKey);
		if(vw == null){
			cache.put(cacheKey, buildWjhdWorkspace(wjid,djrid));
			vw = cache.get(cacheKey);
		}
		return 0;
	}

	@Override
	public boolean updateDxstSort(Map<String, String> sortParam) {
		return false;
	}
	
	@Override
	public int updateFinishZt(String wjid, String djrid, String lxid) {
		Cache cache = this.ehCacheManager.getCache(CACHE_NAME);
		return 0;
	}

	@Override
	public List<WjhdModel> queryWjhd(String wjid, String djrid) {
		Cache cache = this.ehCacheManager.getCache(CACHE_NAME);
		String cacheKey = this.getWorkspaceKey(wjid, djrid);
		ValueWrapper vw = cache.get(cacheKey);
		if(vw == null){
			cache.put(cacheKey, buildWjhdWorkspace(wjid,djrid));
			vw = cache.get(cacheKey);
		}
		WjhdWorkspace workspace = (WjhdWorkspace) vw.get();
		List<WjhdModel> list = workspace.getWjhdModels();
		return list; 
	}

	@Override
	public List<WjhdModel> queryWjSthd(String wjid, String djrid,String stid) {
		
		List<WjhdModel> destList = new ArrayList<WjhdModel>(4);
		
		Cache cache = this.ehCacheManager.getCache(CACHE_NAME);
		String cacheKey = this.getWorkspaceKey(wjid, djrid);
		ValueWrapper vw = cache.get(cacheKey);
		
		if(vw == null){
			cache.put(cacheKey, buildWjhdWorkspace(wjid,djrid));
			vw = cache.get(cacheKey);
		}
		
		WjhdWorkspace workspace = (WjhdWorkspace) vw.get();
		List<WjhdModel> list = workspace.getWjhdModels();
		
		doFilterPopulateByStid(list, stid, destList);
		
		return destList; 
	}
	
	private void doFilterPopulateByStid(List<WjhdModel> src,String stid,List<WjhdModel> dest){
		for(WjhdModel model : src){
			if(model.getStid().equals(stid)){
				dest.add(model);
			}
		}
	}
	
	@Override
	public int deleteBatchYhdjStxx(String wjid, String djrid, Set<String> stids) {
		Cache cache = this.ehCacheManager.getCache(CACHE_NAME);
		String cacheKey = this.getWorkspaceKey(wjid, djrid);
		ValueWrapper vw = cache.get(cacheKey);
		if(vw == null){
			List<WjhdModel> list = this.wjhdDao.queryWjhd(wjid, djrid);
			
			int delCount = 0;
			
			LinkedList<WjhdModel> list2 = new LinkedList<WjhdModel>();
			for(WjhdModel model : list){
				if(!stids.contains(model.getStid())){
					list2.add(model);
				}else{
					delCount ++;
				}
			}
			
			WjhdWorkspace workspace = new WjhdWorkspace(djrid, wjid);
			//TODO 这里需要查询数据库
			workspace.setFinish(false);
			workspace.setLastAccessTime(System.currentTimeMillis());
			workspace.setWjhdModels(list2);
			
			cache.put(cacheKey, workspace);
			
			return delCount;
			
		}else{
			WjhdWorkspace workspace = (WjhdWorkspace) vw.get();
			LinkedList<WjhdModel> list = workspace.getWjhdModels();
			
			int delCount = 0;
			
			Iterator<WjhdModel> it = list.iterator();
			while(it.hasNext()){
				WjhdModel model = it.next();
				if(stids.contains(model.getStid())){
					it.remove();
					delCount ++;
				}
			}
			return delCount; 
		}
	}
}
