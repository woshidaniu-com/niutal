/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.wjdc.common.Constants;
import com.woshidaniu.wjdc.dao.daointerface.IStdlxxDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjglDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjhdDao;
import com.woshidaniu.wjdc.dao.entites.DjrffModel;
import com.woshidaniu.wjdc.dao.entites.FfdxModel;
import com.woshidaniu.wjdc.dao.entites.FfdxtjModel;
import com.woshidaniu.wjdc.dao.entites.FfmxModel;
import com.woshidaniu.wjdc.dao.entites.StdlxxModel;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjffmxModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.WjhdModel;
import com.woshidaniu.wjdc.dao.entites.WjtjtjModel;
import com.woshidaniu.wjdc.dao.entites.XxglModel;
import com.woshidaniu.wjdc.enums.Djzt;
import com.woshidaniu.wjdc.enums.QuestionType;
import com.woshidaniu.wjdc.event.WjdjCompleteEvent;
import com.woshidaniu.wjdc.event.WjffConditionUpdateEvent;
import com.woshidaniu.wjdc.event.WjstUpdateEvent;
import com.woshidaniu.wjdc.service.svcinterface.IDjrffService;
import com.woshidaniu.wjdc.service.svcinterface.IStglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化，扩展
 * wjffList惰性初始化开关
 * 
 * */
@Service("wjglService")
public class WjglServiceImpl extends BaseServiceImpl<WjglModel, IWjglDao> implements IWjglService,ApplicationContextAware,ApplicationListener<WjffConditionUpdateEvent>{
	
	private static final Logger log = LoggerFactory.getLogger(WjglServiceImpl.class);
	//使用uuid作为问卷id的配置key
	private static final String USE_NUMBER_SEQUENCE_WJID_KEY = "niutal.wjdc.useNumberSequenceWjid";
	//分发问卷时批量插入
	private static final String USE_BATCH_INSERT_ON_DISPATCH = "niutal.wjdc.useBatchInsertOnDispatch";
	//分发问卷时批量插入
	private static final String BATCH_INSERT_SIZE_ON_DISPATCH = "niutal.wjdc.batchInsertSizeOnDispatch";
	//使用uuid作为问卷id
	protected boolean useNumberSequenceWjid = false;
	@Resource
	private IWjglDao wjglDao;
	@Resource
	private IStglService stglService;
	@Resource
	private IDjrffService djrffService;
	@Resource
	private IWjhdDao wjhdDao;
	@Resource
	private IStdlxxDao stdlxxDao;
	@Autowired
	private List<FfdxModel> wjffList;
	@Autowired
	private EhCacheCacheManager ehCacheManager;
	//分发问卷时批量插入
	private boolean useBatchInsertOnDispatch = true;
	
	private boolean keepHistoryOnFfwj = false;
	
	private boolean useCache = false;
	
	private int batchInsertSizeOnDispatch = 100;
	
	private ApplicationContext applicationContext;
	
	private AtomicLong refreshFfdxCount = new AtomicLong(0L);
	
	private AtomicLong refreshFfdxFailCount = new AtomicLong(0L);
	
	/**
	private static enum SaveYhdjStrategy{
		
		ALL_IN_ONE("allInOne"),ONE_BY_ONE("oneByOne"),PAGE("page");
		
		private String configValue;
		
		SaveYhdjStrategy(String configValue){
			this.configValue = configValue;
		}

		public String getConfigValue() {
			return configValue;
		}
	}
	
	private SaveYhdjStrategy saveYhdjStrategy = SaveYhdjStrategy.ALL_IN_ONE;
	
	private int saveYhdjStrategyPageSize = 5;
	**/
	
	@PostConstruct
	public void init() {
		{
			//是否使用数字序列号问卷id
			String val = MessageUtil.getText(USE_NUMBER_SEQUENCE_WJID_KEY);
			this.useNumberSequenceWjid = StringUtils.isNotEmpty(val) ? Boolean.valueOf(val) : this.useNumberSequenceWjid;
			log.info("是否使用递增数字序列号作为问卷id,配置key:[{}],配置文件value:[{}],最终采用值:[{}]",USE_NUMBER_SEQUENCE_WJID_KEY,val,this.useNumberSequenceWjid);
		}
		{
			//分发问卷时批量插入
			String val = MessageUtil.getText(USE_BATCH_INSERT_ON_DISPATCH);
			this.useBatchInsertOnDispatch = StringUtils.isNotEmpty(val) ? Boolean.valueOf(val) : this.useBatchInsertOnDispatch;
		}
		{
			String val = MessageUtil.getText(BATCH_INSERT_SIZE_ON_DISPATCH);
			if(StringUtils.isNotEmpty(val)) {
				try {
					int size = Integer.parseInt(val);					
					this.batchInsertSizeOnDispatch = size > 0 ? size : this.batchInsertSizeOnDispatch;
				}catch (Exception e) {
					log.error("格式错误key:["+BATCH_INSERT_SIZE_ON_DISPATCH+"]"+",value=["+val+"]",e);
				}
			}
		}
		{
			String val =  MessageUtil.getText("niutal.wjdc.wjglServiceImpl.useCache");
			this.useCache = val != null ? Boolean.parseBoolean(val) : this.useCache;
			log.info("问卷调查,查询问卷信息是否使用缓存:{}",this.useCache);
		}
		{
			String val =  MessageUtil.getText("niutal.wjdc.wjglServiceImpl.keepHistoryOnFfwj");
			this.keepHistoryOnFfwj = val != null ? Boolean.parseBoolean(val) : this.keepHistoryOnFfwj;
			log.info("问卷调查,分发问卷时是否保留历史信息:{}",this.keepHistoryOnFfwj);
		}
		/**
		{
			String val =  MessageUtil.getText("niutal.wjdc.wjglServiceImpl.saveYhdjStrategy");
			if(StringUtils.isNotEmpty(val)){
				if(SaveYhdjStrategy.ALL_IN_ONE.getConfigValue().equals(val)){
					
					this.saveYhdjStrategy = SaveYhdjStrategy.ALL_IN_ONE;
					log.info("问卷调查,保存问卷回答策略:{}",val);
					
				}else if(SaveYhdjStrategy.ONE_BY_ONE.getConfigValue().equals(val)){
					
					this.saveYhdjStrategy = SaveYhdjStrategy.ONE_BY_ONE;
					log.info("问卷调查,保存问卷回答策略:{}",val);
					
				}else if(SaveYhdjStrategy.PAGE.getConfigValue().equals(val)){
					
					this.saveYhdjStrategy = SaveYhdjStrategy.PAGE;
					
					String pageSizeVal =  MessageUtil.getText("niutal.wjdc.wjglServiceImpl.saveYhdjStrategyPageSize");
					this.saveYhdjStrategyPageSize = StringUtils.isEmpty(pageSizeVal) ? this.saveYhdjStrategyPageSize : Integer.parseInt(pageSizeVal);
					
					log.info("问卷调查,保存问卷回答策略:{},pageSize:{}",val,this.saveYhdjStrategyPageSize);
				}else{
					log.warn("不支持的策略类型:{}",val);
				}
			}
		}
		**/
	}
	
	public void afterPropertiesSet() throws Exception {
		super.setDao(this.wjglDao);   
	}
	
	/**
	 * 刷新问卷分发列表数据
	 */
	protected synchronized void refreshFfdx() {
		
		log.debug("刷新问卷调查分发列表数据");
		
		try {
			this.doRefreshFfdx();
			this.refreshFfdxCount.incrementAndGet();
		}catch (Throwable t) {
			
			this.refreshFfdxFailCount.incrementAndGet();
			
			log.error("刷新问卷调查列表数据异常,cause:"+t.getMessage(),t);
		}
		
	}
	
	/**
	 * 刷新问卷分发列表数据
	 */
	protected synchronized void doRefreshFfdx() {
		
		for (FfdxModel ffdx : wjffList) {
			Set<FfdxtjModel> ffdxs = ffdx.getFftjs();
			
			for (FfdxtjModel fftj : ffdxs) {
				String sjly = fftj.getSjly();
				
				String[] info = sjly.split("#");
				String clazzName = info[0];
				String methodName = info[1];
				
				Object targetService = this.applicationContext.getBean(clazzName);
				if (info.length == 3) {
					
					String methodArgument = info[2];
					List<?> itemList = (List<?>) ReflectionUtils.invokeMethod(methodName, targetService,new Class[] { String.class }, new String[] { methodArgument});
					fftj.setItemList(itemList);
					
				} else {
					
					List<?> itemList = (List<?>) ReflectionUtils.invokeMethod(methodName, targetService, new Class[] {},new String[] {});
					fftj.setItemList(itemList);
				}
			}
			
			Set<WjtjtjModel> tjs = ffdx.getTjtjs();
			for (WjtjtjModel fftj : tjs) {
				String sjly = fftj.getSjly();
				//获得类名，方法名,???
				String[] info = sjly.split("#");
				String clazzName = info[0];
				String methodName = info[1];
				
				//获得服务类
				Object targetService = this.applicationContext.getBean(clazzName);
				if (info.length == 3) {
					//反射调用，获得类目列表
					String methodArgument = info[2];
					List<?> itemList = (List<?>) ReflectionUtils.invokeMethod(methodName, targetService,new Class[] { String.class }, new String[] { methodArgument});
					fftj.setItemList(itemList);
				} else {
					List<?> itemList = (List<?>) ReflectionUtils.invokeMethod(methodName, targetService, new Class[] {},new String[] {});
					fftj.setItemList(itemList);
				}
			}
			
			log.debug("刷新结果:{}",ffdx);
		}
	}
	
	@Override
	public synchronized List<FfdxModel> getInitedFfdxModels(){
		
		if(this.refreshFfdxCount.get() <= 0) {
			this.refreshFfdx();
		}
		return wjffList;
	}
	
	@Override
	public synchronized String getNextWjid() {
		if(this.useNumberSequenceWjid) {
			List<WjglModel> list = this.wjglDao.getModelList(new WjglModel());
			String nextWjid = "1";
			if(!list.isEmpty()) {
				List<Integer> ids = new ArrayList<Integer>();
				for(WjglModel wjglModel :list) {
					String wjid = wjglModel.getWjid();
					try {
						Integer id = Integer.valueOf(wjid);
						ids.add(id);
					}catch (Exception e) {
						log.warn("问卷id不是整数,忽略",e);
					}
				}
				if(ids.isEmpty()) {
					nextWjid = "1";
				}else {
					Collections.sort(ids,new Comparator<Integer>() {

						@Override
						public int compare(Integer i1, Integer i2) {
							return i1.compareTo(i2);
						}
					});
					Integer max = ids.get(ids.size() - 1);
					int next = max.intValue() + 1;
					next = next + new Random(100).nextInt();
					nextWjid = next +"";
				}
			}
			this.scWjxx(nextWjid);//预先删除这个wjid的数据,防止脏数据产生
			return nextWjid;
		}else {
			return UUID.randomUUID().toString();
		}
	}
	
	private synchronized WjglModel getModelWithCache(String id) {
		
		Cache cache = this.ehCacheManager.getCache(Constants.CACHE_NAME);
		
		String key = "com.woshidaniu.wjdc.service.impl.WjglServiceImpl.getModelWithCache_" + id;
		ValueWrapper vw = cache.get(key);
		if(vw == null){
			WjglModel model = this.wjglDao.getModel(id);
			log.debug("save cache key:"+key);
			cache.putIfAbsent(key, model);
			vw = cache.get(key);
		}
		return (WjglModel)vw.get();
	}
	
	private synchronized void deleteModelWithCache(String id) {
		
		Cache cache = this.ehCacheManager.getCache(Constants.CACHE_NAME);
		
		String key = "com.woshidaniu.wjdc.service.impl.WjglServiceImpl.getModelWithCache_" + id;
		ValueWrapper vw = cache.get(key);
		if(vw != null){
			log.debug("delete cache key:"+key);
			cache.evict(key);
		}
	}
	
	@Override
	public WjglModel getModel(String id){
		if(this.useCache){
			return this.getModelWithCache(id);
		}else{
			return this.wjglDao.getModel(id);			
		}
	}
	
	@Override
	public boolean scWjxx(String wjid) {
		
		//删除问卷信息
		int count = this.wjglDao.delete(wjid);
		//删除试题分类信息
		this.stglService.deleteStfl(wjid);
		//删除试题信息
		this.stglService.deleteWjSt(wjid);
		//删除问卷试题信息
		this.stglService.deleteWjStXx(wjid);
		//删除问卷回答信息
		this.stglService.deleteWjhd(wjid);
		//删除问卷分发信息
		this.wjglDao.deleteWjff(wjid);
		//删除问卷绑定信息
		this.wjglDao.deleteYwbd(wjid);
		//删除答卷人分发明细表
		this.djrffService.deleteAll(wjid);
		
		this.applicationContext.publishEvent(new WjstUpdateEvent(this, wjid));
		
		if(this.useCache){
			this.deleteModelWithCache(wjid);
		}
		
		return count > 0;
	}
	
	@Override
	public boolean saveWjst(String wjid, List<Map<String, String>> stflList, List<Map<String,String>> wjstList, List<Map<String, String>> stxxList) {
		
		this.cleanWj(wjid);
		
		//保存当前提交的数据
		int count = 0;
		if (stflList != null && !stflList.isEmpty()){
			count += this.stdlxxDao.insertStdlxx(stflList);
		}
		if(wjstList != null && !wjstList.isEmpty()) {
			count += this.stglService.insertStxx(wjstList);			
		}
		
		if (stxxList != null && !stxxList.isEmpty()){
			count += this.stglService.insertXxxx(stxxList);
		}
		
		this.applicationContext.publishEvent(new WjstUpdateEvent(this, wjid));
		
		if(this.useCache){
			this.deleteModelWithCache(wjid);
		}
		
		return count > 0;
	}
	
	private void cleanWj(String wjid) {
		//删除用户答卷
		this.wjglDao.deleteYhdj(wjid);
		//删除答卷人分发明细表
		this.djrffService.deleteAll(wjid);
		
		//删除原先保存的问卷试题信息
		this.stglService.deleteStfl(wjid);
		this.stglService.deleteWjSt(wjid);
		this.stglService.deleteWjStXx(wjid);
		
		this.applicationContext.publishEvent(new WjstUpdateEvent(this, wjid));
		
		if(this.useCache){
			this.deleteModelWithCache(wjid);
		}
	}

	@Override
	public boolean insertCopyWj(WjglModel wjglModel) {
		
		String oldWjid = wjglModel.getWjid();
		
		String newWjid = getNextWjid();
		
		this.cleanWj(newWjid);
		
		int insertWjCount = 0;
		int insertStdlxxCount = 0;
		int insertStxxCount = 0;
		int insertXxxxCount = 0;
		
		wjglModel.setWjid(newWjid);
		insertWjCount = this.wjglDao.insert(wjglModel);
		
		//问卷分类
		List<StdlxxModel> stdlxxList = this.stdlxxDao.getStldxx(oldWjid);
		if(!stdlxxList.isEmpty()) {
			List<Map<String,String>> stdlxxMapList = new ArrayList<Map<String,String>>();
			for(StdlxxModel model : stdlxxList) {
				Map<String,String> map = new HashMap<String,String>(5);
				map.put("wjid", newWjid);
				map.put("stdlid", model.getFlid());
				map.put("stdlmc", model.getFlmc());
				map.put("xssx", model.getXssx());
				map.put("dqfs", model.getDqfs());
				
				stdlxxMapList.add(map);
			}
			
			insertStdlxxCount = this.stdlxxDao.insertStdlxx(stdlxxMapList);
		}
		
		//问卷试题
		StglModel stglModel = new StglModel();
		stglModel.setWjid(oldWjid);
		List<StglModel> wjstList = this.stglService.getModelList(stglModel);
		
		if(!wjstList.isEmpty()) {
			
			List<Map<String,String>> stMapList = new ArrayList<Map<String,String>>();
			for(StglModel model : wjstList) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("wjid", newWjid);
				map.put("stid", model.getStid());
				map.put("stmc", model.getStmc());
				map.put("stlx", model.getStlx());
				map.put("dhxxgs", model.getDhxxgs());
				map.put("sfbd", model.getSfbd());
				map.put("stzf", model.getStzf());
				map.put("xssx", model.getXssx());
				map.put("stdlid", model.getStdlid());
				map.put("xxkzdxzs", model.getXxkzdxzs()+"");
				map.put("sfyxpx", model.getSfyxpx());
				map.put("ts", model.getTs());
				map.put("zdzs",model.getZdzs());
				map.put("wbgd", model.getWbgd());
				map.put("mhxxgs", model.getMhxxgs());
				map.put("wblx", model.getWblx());
				
				stMapList.add(map);
			}
			
			insertStxxCount = this.stglService.insertStxx(stMapList);
		}
		
		//问卷选项
		WjglModel queryWjglModel = new WjglModel();
		queryWjglModel.setWjid(oldWjid);
		List<XxglModel> xxList = this.stglService.getStXxxxList(queryWjglModel);
		if(!xxList.isEmpty()) {
			List<Map<String,String>> xxMapList = new ArrayList<Map<String,String>>();
			for(XxglModel model : xxList) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("wjid", newWjid);
				map.put("stid", model.getStid());
				map.put("xxid", model.getXxid());
				map.put("xxmc", model.getXxmc());
				map.put("xxfz", model.getXxfz());
				map.put("xssx", model.getXssx());
				map.put("tzstid", model.getTzstid());
				
				xxMapList.add(map);
			}
			insertXxxxCount = this.stglService.insertXxxx(xxMapList);
		}
		
		log.debug("copy wj source wjid[{}] to wjid[{}],insertWjCount[{}],insertStdlxxCount[{}],insertStxxCount[{}],insertXxxxCount[{}]",
				oldWjid,newWjid,insertWjCount,insertStdlxxCount,insertStxxCount,insertXxxxCount);
		
		this.applicationContext.publishEvent(new WjstUpdateEvent(this, newWjid));
		
		return insertWjCount > 0;
	}

	@Override
	public boolean saveWjff(String wjid, String ffdx, String gnid) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		JSONArray ffdxArr = JSONArray.fromObject(ffdx);
		
		WjglModel wjglModel = this.wjglDao.getModel(wjid);
		String clsx = wjglModel.getWjyxj();
		
		if(!this.keepHistoryOnFfwj){
			//删除用户答卷
			this.wjglDao.deleteYhdj(wjid);
			//删除答卷人分发明细表
			this.djrffService.deleteAll(wjid);
		}
		//删除问卷分发
		this.wjglDao.deleteWjff(wjid);
		//删除业务绑定
		this.wjglDao.deleteYwbd(wjid);
		//保存问卷分发
		this.wjglDao.saveWjff(wjid, ffdxArr);
		
		//解析出所有需要答卷的人,填充进niutal_wjdc_djrffb表,以此进行展示用户答卷状态列表
		//初始化答卷人分发表
		int djrffCount = this.initDjrff(wjid);
		//保存业务功能绑定
		String[] ywgnArr = gnid.split(",");
		int c = wjglDao.saveYwbd(wjid,clsx,ywgnArr);
		
		stopWatch.stop();
		log.info("分发问卷wjid:[{}],分发人数:[{}],处理时间耗时:[{}]ms",wjid,djrffCount,stopWatch.getTime());
		
		return c > 0;
	}
	
	@Override
	public boolean update(WjglModel t) {
		
		this.deleteModelWithCache(t.getWjid());
		
		String wjyxj = t.getWjyxj();
		this.wjglDao.updateYwbdWjClsx(t.getWjid(), wjyxj);
		return super.update(t);
	}
	
	@Override
	public List<String> getFinishedWjidList(String userid){
		
		List<String> wjids = this.djrffService.queryFinisedhWjidList(userid);
		return wjids;
	}
	
	@Override
	public List<String> getUnfinshWjidList(String userid) {
		
		List<String> wjids = this.djrffService.queryUnfinishWjidList(userid);
		return wjids;
	}

	@Override
	public List<WjglModel> getUnfinshedWjList(String userid) {
		
		List<String> wjids = this.getUnfinshWjidList(userid);
		if(wjids.isEmpty()) {
			return Collections.emptyList();
		}else {
			List<WjglModel> list = this.wjglDao.getModelsByWjids(wjids);
			return list;
		}
	}
	
	/**
	 * @description	： 根据ffdx聚合，构成map
	 * @param ffmxList
	 * @return
	 */
	private Map<String, List<FfmxModel>> groupByFfdxWithFfmxList(List<FfmxModel> ffmxList) {
		Map<String,List<FfmxModel>> map = new HashMap<String,List<FfmxModel>>();
		for(FfmxModel ffmxModel:ffmxList) {
			
			String ffdx = ffmxModel.getFfdx();
			List<FfmxModel> list = map.get(ffdx);
			if(list == null) {
				list = new ArrayList<FfmxModel>();
				map.put(ffdx, list);
			}
			list.add(ffmxModel);
		}
		return map;
	}

	/**
	 * @description	： 根据dxtj聚合，构成map
	 * @param ffmxList
	 * @return
	 */
	private Map<String, List<FfmxModel>> groupByDxtjWithFfmxList(List<FfmxModel> ffmxList) {
		
		Map<String,List<FfmxModel>> map = new HashMap<String,List<FfmxModel>>();
		
		for(FfmxModel ffmxModel:ffmxList) {
			String dxtj = ffmxModel.getDxtj();
			List<FfmxModel> list = map.get(dxtj);
			if(list == null) {
				list = new ArrayList<FfmxModel>();
				map.put(dxtj, list);
			}
			list.add(ffmxModel);
		}
		return map;
	}
	
	@Override
	public boolean updateSubmitFinish(User user, String wjid) {
		
		String currentUserId = user.getYhm();
		String yhlx = user.getYhlx();
		if(StringUtils.isEmpty(yhlx)){
			yhlx = "student";
		}
		this.djrffService.updateFinishZt(currentUserId, wjid, yhlx);
		//发布完成事件
		this.applicationContext.publishEvent(new WjdjCompleteEvent(this, wjid, currentUserId, yhlx));
		return true;
	}
	
	@Override
	public boolean saveSubmitYhdj(User user, WjglModel wjModel,HttpServletRequest request,boolean ajax) {
		String currentUserId = user.getYhm();
		String currentWjId = wjModel.getWjid();
		
		List<WjhdModel> wjhds = this.wjhdDao.queryWjhd(currentWjId, currentUserId);
		
		//解析用户提交的试题和选项数据
		ParseResultPair pair = this.parseAndFilterYhStxx(wjModel, request, user,wjhds);
		List<Map<String, String>> paramsList = pair.getParamsList();
		if(ajax && paramsList.isEmpty()) {
			return true;
		}
		
		int saveYhdjCount= this.saveYhdj(paramsList);

		//更新用户多选题的排序顺序
		List<Map<String, String>> sortParamsList = pair.getSortParamsList();
		if(!sortParamsList.isEmpty()) {
			for(int i=0;i<sortParamsList.size();i++) {
				Map<String, String> sortParam = sortParamsList.get(i);
				this.wjglDao.updateDxstSort(sortParam);			
			}
		}
		
		//如果不是ajax方式,则是页面提交方式，需设置完成状态
		if(!ajax) {
			boolean success = this.updateSubmitFinish(user, currentWjId);
			return success;
		}else {
			return saveYhdjCount > 0 ;
		}
	}
	
	private int saveYhdj(List<Map<String, String>> paramsList){
		
		int saveYhdjCount = this.wjglDao.saveYhdj(paramsList);
		/**
		int saveYhdjCount= 0;
		
		if(this.saveYhdjStrategy == SaveYhdjStrategy.ALL_IN_ONE){
			
			saveYhdjCount = this.wjglDao.saveYhdj(paramsList);
			
		}else if(this.saveYhdjStrategy == SaveYhdjStrategy.ONE_BY_ONE){
			
			for(Map<String,String> param : paramsList){
				int c = this.wjglDao.saveYhdjOneRecord(param);
				if(c > 0){
					saveYhdjCount ++;
				}
			}
		}else if(this.saveYhdjStrategy == SaveYhdjStrategy.PAGE){
			
			List<List<?>> pagedLists = CollectionUtils.splitList(paramsList, this.saveYhdjStrategyPageSize);
			
			for(List<?> l : pagedLists){
				List<Map<String, String>> pl = (List<Map<String, String>>)l;
				saveYhdjCount = this.wjglDao.saveYhdj(pl);
			}
		}else{
			//can't happen
		}
		**/
		return saveYhdjCount;
	}

	@Override
	public boolean saveAjaxSubmitSort(User user, String wjid, String stid, String xxids) {
		String currentUserId = user.getYhm();
		String[] xxidArray = xxids.split(",");
		List<String> xxidList = new ArrayList<String>();
		for(int i=0;i<xxidArray.length;i++) {
			String xxid = xxidArray[i];
			if(StringUtils.isNotEmpty(xxid)) {
				xxidList.add(xxid);
			}
		}
		List<Map<String, String>> sortParamsList = new ArrayList<Map<String, String>>();
		for(int i=0;i<xxidList.size();i++) {
			int hdsx = i + 1;
			String xxid = xxidList.get(i);
			Map<String,String> sortParamMap = new HashMap<String,String>();
			sortParamMap.put("wjid", wjid);
			sortParamMap.put("stid", stid);
			sortParamMap.put("xxid", xxid);
			sortParamMap.put("djrid", currentUserId);
			sortParamMap.put("hdsx", hdsx+"");
			sortParamsList.add(sortParamMap);
		}
		//更新用户多选题的排序顺序
		if(!sortParamsList.isEmpty()) {
			for(int i=0;i<sortParamsList.size();i++) {
				Map<String, String> sortParam = sortParamsList.get(i);
				sortParam.put("djrid", currentUserId);
				this.wjglDao.updateDxstSort(sortParam);	
			}
			return true;
		}
		return false;
	}
	
	@Override
	public List<WjhdModel> queryDxSort(User user, String wjid, String stid) {
		List<WjhdModel> wjhds = this.wjhdDao.queryWjSthd(wjid, stid,user.getYhm());
		return wjhds;
	}
	
	@Override
	public List<WjglModel> getWdwjList(String userid) {
		
		List<WjglModel> wjxxList = this.wjglDao.getFfwjList(userid);
		List<WjglModel> result = new ArrayList<WjglModel>(wjxxList.size());

		// 存放问卷id集合，避免重复添加到结果对象result中
		Set<String> resultWjidSet = new HashSet<String>();

		for (WjglModel model : wjxxList) {
			
			List<FfmxModel> ffmxList = model.getFfmxList();
			// 根据ffdx聚合
			Map<String, List<FfmxModel>> ffdxGroups = this.groupByFfdxWithFfmxList(ffmxList);

			for (FfdxModel ffdxModel : this.getInitedFfdxModels()) {

				String dxly = ffdxModel.getDxly();
				String dxid = ffdxModel.getDxid();
				List<FfmxModel> list = ffdxGroups.get(dxid);
				if(list != null && !list.isEmpty()) {
					// 根据dxtj聚合，即查询的where条件的字段名称
					Map<String, List<FfmxModel>> dxtjMaps = this.groupByDxtjWithFfmxList(list);
					Iterator<Entry<String, List<FfmxModel>>> dxtjIt = dxtjMaps.entrySet().iterator();
					SqlGroupEqualConditionAppender sqlAppender = new SqlGroupEqualConditionAppender(dxly);
					while (dxtjIt.hasNext()) {
						
						Entry<String, List<FfmxModel>> ee = dxtjIt.next();
						String dxtj = ee.getKey();
						List<FfmxModel> ll = ee.getValue();
						
						if (ll != null && !ll.isEmpty()) {
							
							GroupEqual groupEqual = sqlAppender.buildGroupEqualCondition();
							
							for (FfmxModel m : ll) {
								String tjz = m.getTjz();
								
								if (StringUtils.isNotBlank(dxtj)) {
									groupEqual.addEqual(dxtj, tjz);
								}
							}
						}
					}
					
					String sql = sqlAppender.getSqlResult();
					SingleColumnResultSqlConditionBuilder singleColumnResultSqlConditionBuilder = new SingleColumnResultSqlConditionBuilder(sql,userid);
					String finalConditionSql = singleColumnResultSqlConditionBuilder.build();
					List<String> ffrList = this.wjglDao.getFfrs(finalConditionSql);
					if (ffrList.contains(userid)) {
						
						//没有加入到result结果集中
						if (!resultWjidSet.contains(model.getWjid())) {
							resultWjidSet.add(model.getWjid());
							result.add(model);
						}
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public List<Map<String, String>> getWjffList(String wjid) {
		return this.wjglDao.getWjffList(wjid);
	}

	@Override
	public List<String> getWjgnList(String wjid) {
		return this.wjglDao.getWjgnList(wjid);
	}

	@Override
	public int getFfrs(String jsonArr) {
		JSONArray ffdxArr = JSONArray.fromObject(jsonArr);
		int count = 0;
		
		//先根据ffdx分组聚合
		Map<String,List<JSONObject>> ffdxGroups = this.groupByFfdx(ffdxArr);
		for (FfdxModel model : this.getInitedFfdxModels()){
			String dxly = model.getDxly();
			String dxid = model.getDxid();
			
			List<JSONObject> list = ffdxGroups.get(dxid);
			if(list != null && !list.isEmpty()) {
				//根据dxtj聚合，即查询的where条件的字段名称
				Map<String,List<JSONObject>> dxtjMaps = this.groupByDxtj(list);
				
				Iterator<Entry<String, List<JSONObject>>> dxtjIt = dxtjMaps.entrySet().iterator();
				SqlGroupEqualConditionAppender sqlAppender = new SqlGroupEqualConditionAppender(dxly);
				
				while (dxtjIt.hasNext()) {
					Entry<String, List<JSONObject>> ee = dxtjIt.next();
					String dxtj = ee.getKey();
					List<JSONObject> ll = ee.getValue();
					
					if (ll != null && !ll.isEmpty()) {
						
						GroupEqual groupEqual = sqlAppender.buildGroupEqualCondition();
						for (JSONObject json : ll) {
							String tjz = json.getString("tjz");
							if (StringUtils.isNotBlank(dxtj)) {
								groupEqual.addEqual(dxtj, tjz);
							}
						}
					}
				}
				String sql = sqlAppender.getSqlResult();
				SelectCountWrapper selectCountWrapper = new SelectCountWrapper(sql);
				String selectCountSql = selectCountWrapper.getWrapper();
				int c = this.wjglDao.getFfrsCount(selectCountSql);
				count += c;
			}
		}
		return count;
	}
	/**
	 * @description	： 根据dxtj聚合，构成map
	 * @param list
	 * @return
	 */
	private Map<String, List<JSONObject>> groupByDxtj(List<JSONObject> list) {
		
		Map<String,List<JSONObject>> map = new HashMap<String, List<JSONObject>>();
		for(int i=0;i<list.size();i++) {
			
			JSONObject jsonObject = list.get(i);
			String ffdx = jsonObject.getString("dxtj");
			
			List<JSONObject> l = map.get(ffdx);
			if(l == null) {
				l  = new ArrayList<JSONObject>();
				map.put(ffdx, l);
			}
			l.add(jsonObject);
		}
		return map;
	}
	/**
	 * @description	： 根据ffdx聚合json数组，构成map
	 * @param jsonArray
	 * @return
	 */
	private Map<String, List<JSONObject>> groupByFfdx(JSONArray jsonArray) {
		Map<String,List<JSONObject>> map = new HashMap<String, List<JSONObject>>();
		
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String ffdx = jsonObject.getString("ffdx");
			
			List<JSONObject> l = map.get(ffdx);
			if(l == null) {
				l  = new ArrayList<JSONObject>();
				map.put(ffdx, l);
			}
			l.add(jsonObject);
		}
		return map;
	}
	
	@Override
	public List<Map<String, Object>> getWjtjList(String wjid) {
		return this.wjglDao.getWjtjList(wjid);
	}
	
	@Override
	public void deleteBatchYhdjStxx(String djrid, String wjid, Set<String> stids) {
		this.wjglDao.deleteBatchYhdjStxx(djrid,wjid,stids);
	}
	
	/**
	 * @description	：  初始化答卷分发
	 * @param wjid
	 */
	private int initDjrff(final String wjid){
		List<WjffmxModel> list = this.wjglDao.getWjffmx(wjid);
		if(list.isEmpty()) {
			return 0;
		}
		
		//先通过类型group by
		Map<String/*dxid*/,List<WjffmxModel>> wjffmxModelMap = new HashMap<String,List<WjffmxModel>>();
		for(WjffmxModel wjffmxModel : list) {
			String dxid = wjffmxModel.getFfdx();
			List<WjffmxModel> l = wjffmxModelMap.get(dxid);
			if(l == null) {
				l = new ArrayList<WjffmxModel>(); 
				wjffmxModelMap.put(dxid, l);
			}
			l.add(wjffmxModel);
		}
		
		int count = 0;
		
		//遍历配置文件配置的分发对象列表
		for(FfdxModel ffdxModel : this.getInitedFfdxModels()) {
			
			String dxid = ffdxModel.getDxid();
			String dxly = ffdxModel.getDxly();
			
			List<WjffmxModel> l = wjffmxModelMap.get(dxid);
			
			if(l != null) {
				
				Map<String,List<String>> columnToValueMap = new HashMap<String,List<String>>();
				
				for(WjffmxModel wjffmxModel : l) {
					
					//数据库字段名
					String columnName =  wjffmxModel.getDxtj();
					//数据库字段值
					String columnValue = wjffmxModel.getTjz();
					
					if(StringUtils.isNotEmpty(columnName) && StringUtils.isNotEmpty(columnValue)) {
						List<String> values = columnToValueMap.get(columnName);
						if(values == null) {
							values = new ArrayList<String>();
							columnToValueMap.put(columnName, values);
						}
						values.add(columnValue);
					}
				}
				StringBuilder executeSql = new StringBuilder(dxly);
				executeSql.append(" where 1 = 1 ");
				
				Iterator<Entry<String, List<String>>> it = columnToValueMap.entrySet().iterator();
				while(it.hasNext()) {
					Entry<String, List<String>> e = it.next();
					String column = e.getKey();
					List<String> values = e.getValue();
					if(values.size() == 1) {
						
						String sql = " and " + column + " in ( '" + values.get(0) + "' ) ";
						executeSql.append(sql);
						
					}else if(values.size() > 1){
						List<String> wrappedValues = new ArrayList<String>();
						for(String v : values) {
							wrappedValues.add("'"+v+"'");
						}
						String joinStr = StringUtils.join(wrappedValues,",");
						String sql = " and " + column + " in ( " + joinStr + " ) ";
						executeSql.append(sql);
					}
				}
				
				List<String> ids = this.wjglDao.getStringList(executeSql.toString());
				//去重,防止业务数据库表具有重复数据而产生问题
				Set<String> idSet = new HashSet<String>(ids);

				log.info("根据问卷分发对象类型类型:[{}][{}],sql:[{}]获得需答卷人数:{},实际需答卷人数:{}",dxid,dxly,executeSql.toString(),ids.size(),idSet.size());

				//若保存历史数据，则移除已经存在的id
				if(this.keepHistoryOnFfwj){
					
					log.info("keepHistoryOnFfwj:{}",this.keepHistoryOnFfwj);
					
					DjrffModel queryZjzModel = new DjrffModel();
					
					queryZjzModel.setWjid(wjid);
					queryZjzModel.setLxid(dxid);
					
					List<String> historyZjzList = this.djrffService.queryZjzList(queryZjzModel);
					log.info("[{}][{}]查询到历史分发人员:{}个人",dxid,dxly,historyZjzList.size());
					
					idSet.removeAll(historyZjzList);
					log.info("[{}][{}]移除历史人员后还剩{}个人需要分发",dxid,dxly,idSet.size());
				}
				if(idSet.isEmpty()){
					continue;
				}
				if(this.useBatchInsertOnDispatch) {
					List<String> totalIdList = new ArrayList<String>(idSet);
					
					//当总数不足一页时
					if(totalIdList.size() <= this.batchInsertSizeOnDispatch) {
						List<DjrffModel> firstPageDjrffModels = new ArrayList<DjrffModel>(totalIdList.size());
						for(String id : totalIdList) {
							DjrffModel model = new DjrffModel();
							model.setWjid(wjid);
							model.setLxid(dxid);
							model.setDjzt(Djzt.unstart.getText());
							model.setZjz(id);
							
							firstPageDjrffModels.add(model);
						}
						count = count + this.djrffService.insertBatch(firstPageDjrffModels);	
					}else {//当总数满足远远大于一页时

						//预初始化一页的对象
						List<DjrffModel> djrffModels = new ArrayList<DjrffModel>(this.batchInsertSizeOnDispatch);
						for(DjrffModel model : djrffModels) {
							model.setWjid(wjid);
							model.setLxid(dxid);
							model.setDjzt(Djzt.unstart.getText());
							djrffModels.add(model);
						}

						List<List<?>> pagedIdList = CollectionUtils.splitList(totalIdList, this.batchInsertSizeOnDispatch);
						
						//一页一页处理
						for(List<?> pageIds : pagedIdList) {
							List<String> insertBatchIds = (List<String>)pageIds;
							
							//前几页
							if(insertBatchIds.size() == djrffModels.size()) {
								
								//逐条设置id
								for(int i=0;i<insertBatchIds.size();i++) {
									
									String id = insertBatchIds.get(i);
									DjrffModel model = djrffModels.get(i);
									model.setWjid(wjid);
									model.setLxid(dxid);
									model.setDjzt(Djzt.unstart.getText());
									model.setZjz(id);
								}
								count = count + this.djrffService.insertBatch(djrffModels);							
							}else {
								//最后一页或不足batchInsertSizeOnDispatch个数的页
								List<DjrffModel> lastPageDjrffModels = new ArrayList<DjrffModel>(insertBatchIds.size());
								
								//逐条设置id
								for(String id : insertBatchIds) {
									
									DjrffModel model = new DjrffModel();
									model.setWjid(wjid);
									model.setLxid(dxid);
									model.setDjzt(Djzt.unstart.getText());
									model.setZjz(id);
									
									lastPageDjrffModels.add(model);
								}
								count = count + this.djrffService.insertBatch(lastPageDjrffModels);	
							}
						}
					}
				}else {
					
					//逐条插入
					for(String id : idSet) {
						DjrffModel model = new DjrffModel();
						model.setWjid(wjid);
						model.setLxid(dxid);
						model.setDjzt(Djzt.unstart.getText());
						model.setZjz(id);
						
						count = count + this.djrffService.insert(model);
					}
				}
			}else {
				//nothing to do
				//说明没有选择这种类型的分发对象
			}
		}
		return count;
	}
	
	/**
	 * @description	：解析并过滤用户填写的试题及选项
	 * @param wjModel
	 * @param request
	 * @param user
	 * @return 试题和相应的选项数据
	 */
	private ParseResultPair parseAndFilterYhStxx(WjglModel wjModel, HttpServletRequest request, User user,List<WjhdModel> wjhds) {
		
		ParseResultPair pair = new ParseResultPair();
		
		List<Map<String, String>> paramsList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> sortParamsList = new ArrayList<Map<String, String>>();
		
		pair.setParamsList(paramsList);
		pair.setSortParamsList(sortParamsList);
		
		List<StglModel> stxxList = this.stglService.getStxxAndStdlXxList(wjModel);
		
		//多选题的选项排序
		Map<String/*stid*/,List<String>/*选项列表*/> sortMap = new HashMap<String,List<String>>();
		//首先构建已经回答的多选题的顺序
		if(!wjhds.isEmpty()) {
			for(int i=0;i<wjhds.size();i++) {
			 	WjhdModel wjhdModel =  wjhds.get(i);
			 	String stid = wjhdModel.getStid();
			 	String xxid = wjhdModel.getXxid();
			 	for (StglModel stxx : stxxList) {// 试卷的试题列表
			 		String stid_stxx = stxx.getStid();
					QuestionType type = QuestionType.getTypeByKey(Integer.valueOf(stxx.getStlx()));
					if (type == QuestionType.MULTI) {//多选题
						if(stid.equals(stid_stxx) && "1".equals(stxx.getSfyxpx())) {
							List<String> xxList = sortMap.get(stid);
							if(xxList == null) {
								xxList = new ArrayList<String>();
								sortMap.put(stid, xxList);
							}
							xxList.add(xxid);
						}
					}
			 	}
			}
		}
		
		
		String djrid = user.getYhm();
		String wjid = wjModel.getWjid();
		Set<String> paramterSet = request.getParameterMap().keySet();
		//需要删除的试题id
		Set<String> needDeleteStIds = new HashSet<String>();
		
		for (StglModel stxx : stxxList) {// 试卷的试题列表
			QuestionType type = QuestionType.getTypeByKey(Integer.valueOf(stxx.getStlx()));
			if (type == QuestionType.DESCRIBE) {// 描述
				continue;
			}
			String stid = stxx.getStid();
			String stid_txnr = stid + "_txnr";
			
			//不包含具体的试题id或stid_txnr，就忽略
			if(!paramterSet.contains(stid) && !paramterSet.contains(stid_txnr)) {
				continue;
			}

			if (type == QuestionType.RADIO) {// 单选
				sortMap.remove(stid);
				String xxid = request.getParameter(stid);
				if (StringUtils.isNotEmpty(xxid)) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("stid", stid);
					param.put("wjid", wjid);
					param.put("djrid", djrid);
					param.put("xxid", xxid);
					paramsList.add(param);
					
					log.trace("单选题:"+JSON.toJSONString(param));
					
					needDeleteStIds.add(stid);
				}
			} else if (type == QuestionType.RADIO_GROUP) {// 单选组合
				sortMap.remove(stid);
				String xxid = request.getParameter(stid);
				String txnr = request.getParameter(stid + "_txnr");
				if (StringUtils.isNotEmpty(xxid) || StringUtils.isNotEmpty(txnr)) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("stid", stid);
					param.put("wjid", wjModel.getWjid());
					param.put("djrid", user.getYhm());
					param.put("xxid", xxid);
					param.put("txnr", txnr);
					
					log.trace("单选组合题:"+JSON.toJSONString(param));
					
					paramsList.add(param);
					
					needDeleteStIds.add(stid);
					
					
				}
			} else if (type == QuestionType.MULTI) {// 多选
				String[] xxidArr = request.getParameterValues(stid);
				if (xxidArr != null && xxidArr.length > 0) {
					for (int i = 0; i < xxidArr.length; i++) {
						String xxid = xxidArr[i];
						if (StringUtils.isNotEmpty(xxid)) {
							Map<String, String> param = new HashMap<String, String>();
							param.put("stid", stid);
							param.put("wjid", wjModel.getWjid());
							param.put("djrid", user.getYhm());
							param.put("xxid", xxid);
							paramsList.add(param);
							
							log.trace("多选题:"+JSON.toJSONString(paramsList));
							
							if("1".equals(stxx.getSfyxpx())) {
								List<String> xxList = sortMap.get(stid);
								if(xxList == null) {
									xxList = new ArrayList<String>();
									sortMap.put(stid, xxList);
								}
								if(!xxList.contains(xxid)) {
									xxList.add(xxid);									
								}
							}
						}
					}
					needDeleteStIds.add(stid);
					
				}
			} else if (type == QuestionType.MULTI_GROUP) {// 多选组合
				sortMap.remove(stid);
				String[] xxidArr = request.getParameterValues(stid);
				// 选择了选项
				if (xxidArr != null && xxidArr.length > 0) {
					for (int i = 0; i < xxidArr.length; i++) {
						String xxid = xxidArr[i];
						if (StringUtils.isNotEmpty(xxid)) {
							Map<String, String> param = new HashMap<String, String>();
							param.put("stid", stid);
							param.put("wjid", wjModel.getWjid());
							param.put("djrid", user.getYhm());
							param.put("xxid", xxid);
							paramsList.add(param);
							
							log.trace("多选题:"+JSON.toJSONString(paramsList));
							
							needDeleteStIds.add(stid);
							
						}
					}
				}
				// 填写了自定义回答
				String txnr = request.getParameter(stid + "_txnr");
				if (StringUtils.isNotEmpty(txnr)) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("stid", stid);
					param.put("wjid", wjModel.getWjid());
					param.put("djrid", user.getYhm());
					param.put("txnr", txnr);
					
					log.trace("自定义回答题:"+JSON.toJSONString(paramsList));
					
					paramsList.add(param);
					
					needDeleteStIds.add(stid);
				}
			} else if (type == QuestionType.TEXT) {// 文本
				sortMap.remove(stid);
				String txnr = request.getParameter(stid);
				if (StringUtils.isNotEmpty(txnr)) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("stid", stid);
					param.put("wjid", wjModel.getWjid());
					param.put("djrid", user.getYhm());
					param.put("txnr", txnr);
					
					log.trace("文本题:"+JSON.toJSONString(param));
					
					paramsList.add(param);
					
					needDeleteStIds.add(stid);
				}
			} else {// 未定义类型
				log.warn("用户提交了未定义的试题类型:type["+type+"]");
				continue;
			}
		}
		
		//若之前存在问卷回答，且需要删除，那么删除之前的
		if(!needDeleteStIds.isEmpty() && !wjhds.isEmpty()) {
			this.deleteBatchYhdjStxx(djrid,wjid, needDeleteStIds);
		}
		
		//构建多选题排序
		if(!sortMap.isEmpty()) {
			Iterator<Entry<String, List<String>>> it = sortMap.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, List<String>> e = it.next();
				String stid = e.getKey();
				List<String> xxidList = e.getValue();
				if(!xxidList.isEmpty()) {
					for(int i=0;i<xxidList.size();i++) {
						String xxid = xxidList.get(i);
						
						String hdsxInRequest = request.getParameter("hdsx_"+xxid);
						if(StringUtils.isNotEmpty(hdsxInRequest)) {
							try {
								Integer.parseInt(hdsxInRequest);		
							}catch (Exception e1) {
								log.error("解析回答顺序的整数["+ hdsxInRequest +"]异常",e1);
							}
							
							Map<String,String> sortParamMap = new HashMap<String,String>();
							sortParamMap.put("wjid", wjModel.getWjid());
							sortParamMap.put("djrid", user.getYhm());
							sortParamMap.put("stid", stid);
							sortParamMap.put("xxid", xxid);
							sortParamMap.put("hdsx", hdsxInRequest);
							
							log.trace("多选题排序:"+JSON.toJSONString(sortParamMap));
							
							sortParamsList.add(sortParamMap);
						}
					}
				}
			}
		}
		
		return pair;
	}
	/**
	 * @description	： sql的where条件追加器
	 * 				  追加成形如
	 * 					select * from t_user where (  name='wei1'  or  name='wei2' ) and ( age = 13 or age = 14)
	 * 				这样的语句。
	 */
	private static class SqlGroupEqualConditionAppender{
		
		private StringBuilder sql = new StringBuilder();
		private List<GroupEqual> groupEquals = new ArrayList<GroupEqual>();
		
		public SqlGroupEqualConditionAppender(String sql) {
			super();
			this.sql.append(sql);
		}
		
		/**
		 * @description	： 返回键值对表达式组
		 * @return
		 */
		public GroupEqual buildGroupEqualCondition() {
			GroupEqual groupEqual = new GroupEqual();
			this.groupEquals.add(groupEqual);
			return groupEqual;
		}
		/**
		 * @description	： 追加条件列表
		 * @param groups
		 */
		private void appendCondition(List<StringBuilder> groups,boolean firstGroup) {
			if(groups == null || groups.isEmpty()) {
				return;
			}
			if(firstGroup) {
				sql.append(" where ");
			}else {
				sql.append(" and ");				
			}
			sql.append(" ( ");

			int firstIndex = 0;
			for (int i = 0; i < groups.size(); i++) {
				StringBuilder sb = groups.get(i);
				if (i == firstIndex) {
					sql.append(sb);
				} else {
					sql.append(" or ").append(sb);
				}
			}
			sql.append(" ) ");
		}
		
		/**
		 * @description	： 获得结果数据
		 * @return
		 */
		public String getSqlResult() {
			boolean firstGroup = true;
			for(GroupEqual ge : this.groupEquals) {
				List<StringBuilder> groups = ge.getEquals();
				this.appendCondition(groups,firstGroup);
				firstGroup = false;
			}
			return this.sql.toString();
		}
	}
	
	/**
	 * @className	： GroupEqual
	 * @description	： 键值对表达式组
	 */
	private static class GroupEqual{
		
		private List<StringBuilder> stringBuilders = new ArrayList<StringBuilder>();
		/**
		 * @description	： 添加一条表达式
		 * @param columnName
		 * @param vlaue
		 */
		public void addEqual(String columnName,String vlaue) {
			String sql = " " + columnName + " = " + " '" + vlaue + "' ";
			this.stringBuilders.add(new StringBuilder(sql));
		}
		/**
		 * @description	： 返回所有表达式
		 * @return
		 */
		public List<StringBuilder> getEquals(){
			return this.stringBuilders;
		}
	}
	
	private static class ParseResultPair{
		
		private List<Map<String, String>> paramsList;
		private List<Map<String, String>> sortParamsList;
		
		public List<Map<String, String>> getParamsList() {
			return paramsList;
		}
		public void setParamsList(List<Map<String, String>> paramsList) {
			this.paramsList = paramsList;
		}
		public List<Map<String, String>> getSortParamsList() {
			return sortParamsList;
		}
		public void setSortParamsList(List<Map<String, String>> sortParamsList) {
			this.sortParamsList = sortParamsList;
		}
	}
	
	/**
	 * 
	 * @className	： SelectCountWrapper
	 * @description	： 查询总数的包裹器 ,形如:select count(*) from (select * from xx where x = 'y')
	 */
	private static class SelectCountWrapper{
		
		private String orignalSql;
		
		public SelectCountWrapper(String orignalSql) {
			this.orignalSql = orignalSql;
		}
		/**
		 * @description	： 获得包裹结果
		 * @return
		 */
		public String getWrapper() {
			String newSql = "select count(*) from ( " + this.orignalSql + " ) ";
			return newSql;
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void onApplicationEvent(WjffConditionUpdateEvent event) {
		log.debug("receive WjffConditionUpdateEvent:"+event);
		this.refreshFfdx();
	}

}
