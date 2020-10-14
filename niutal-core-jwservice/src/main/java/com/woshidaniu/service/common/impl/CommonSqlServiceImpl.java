package com.woshidaniu.service.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.cache.core.utils.CacheKeyUtils;
import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.constant.GlobalCacheKeyConstant;
import com.woshidaniu.common.global.GlobalSysValues;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.daointerface.ICommonSqlDao;
import com.woshidaniu.daointerface.IJcsjDao;
import com.woshidaniu.daointerface.IXtszDao;
import com.woshidaniu.entities.BjdmModel;
import com.woshidaniu.entities.BmdmModel;
import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.entities.JcsjModel;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.NjdmModel;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.XqdmModel;
import com.woshidaniu.entities.ZydmModel;
import com.woshidaniu.entities.ZyfxdmModel;
import com.woshidaniu.orm.mybatis.spring.support.MybatisDataSourceDao;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.util.local.LocaleUtils;
import com.woshidaniu.web.WebContext;

/**
 * 
 *@类名称:CommonSqlServiceImpl.java
 *@类描述：公共基础查询service接口实现
 *@创建人：kangzhidong
 *@创建时间：2014-6-23 下午05:27:40
 *@版本号:v1.0
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Service("commonSqlService")
public class CommonSqlServiceImpl extends BaseServiceImpl<Object, ICommonSqlDao> implements ICommonSqlService,InitializingBean {

	@Resource
	protected ICommonSqlDao dao;
	//基础数据获取方法
	@Resource
	protected IJcsjDao jcsjDao;
	//系统设置dao
	@Resource
	protected IXtszDao xtszDao;
	//成绩数据源
	@Resource
	public MybatisDataSourceDao securityDataSourceDao;
		
	protected ConcurrentMap<String, String> COMPLIED_FORMAT = new ConcurrentHashMap<String, String>();

	public String getConstant(String key) {
		if (StringUtils.isNotEmpty(key)) {
			String ret = COMPLIED_FORMAT.get(key);
			if (ret != null) {
				return ret;
			}
			ret = dao.getInnerParam(key);
			if(ret != null){
				String existing = COMPLIED_FORMAT.putIfAbsent(key, ret);
				if (existing != null) {
					ret = existing;
				}
			}
			return ret;
		}
		return null;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		 
	}
	
	@Override
	public String cxXtszxz(String xtszx) {
		String zdz = null;
		List<Map<String,String>> xtList = null;
		//缓存服务器开启
		if(isCacheSupport()){
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(GlobalCacheKeyConstant.XTSZ_CACHEKEY);
			if(valueWrapper == null){
				xtList = xtszDao.cxXtszxz();
				cache.put(GlobalCacheKeyConstant.XTSZ_CACHEKEY, xtList);
			}else{
				xtList = ((List<Map<String,String>>) valueWrapper.get());
			}
			if(xtList!=null){
				for(Map<String,String> xtMap:xtList){
					if(xtMap.get("ZDM").toLowerCase().equals(xtszx.toString().toLowerCase())){
						zdz = xtMap.get("ZDZ");
						break;
					}
				}
			}
		}else{
			zdz = xtszDao.cxXtszxzWithZdm(xtszx.toString());
		}
		
		return zdz;
	}
	
	@Override
	public String cxCacheXtszxz(String xtszx) {
		String zdz = null;
		List<Map<String,String>> xtList = null;
		//缓存服务器开启
		Cache cache = this.getCache();
		ValueWrapper valueWrapper = cache.get(GlobalCacheKeyConstant.XTSZ_CACHEKEY);
		if(valueWrapper == null){
			xtList = xtszDao.cxXtszxz();
			cache.put(GlobalCacheKeyConstant.XTSZ_CACHEKEY, xtList);
		}else{
			xtList = ((List<Map<String,String>>) valueWrapper.get());
		}
		if(xtList!=null){
			for(Map<String,String> xtMap:xtList){
				if(xtMap.get("ZDM").toLowerCase().equals(xtszx.toString().toLowerCase())){
					zdz = xtMap.get("ZDZ");
					break;
				}
			}
		}
		return zdz;
	}
	
	@Override
	@Cacheable(value = CacheName.CACHE_BASIC)
	public String getInnerParam(GlobalSysValues zdm) {
		String zdz = null;
		/*//缓存服务器开启
		if(isCacheStart()){
			Object object = cacheClient.get(zdm.toString());
			if(!BlankUtils.isBlank(object)){
				zdz =  (String) object;
			}else{
				//缓存过期重新查询
				zdz = getDao().getInnerParam(zdm.toString());
				//缓存一天
				cacheClient.set(zdm.toString(),ICacheClient.CACHE_EXP_DAY ,zdz);
			}
		}else{
			zdz = getDao().getInnerParam(zdm.toString());
		}*/
		return getDao().getInnerParam(zdm.toString());
	}
	
	public List<Map<String, String>> getInnerParams(List<String> zdmList) {
		CommonModel model = new CommonModel();
		model.setZdmList(zdmList);
		return getDao().getInnerParams(model);
	}
	
	
	@Override
	public Map<String, String> getXxxx(){
		return getDao().getXxxx();
	}
	
	@Override
	public String getXxcjmy(){
		String xxcjmy = null;
		/*if(isCacheStart()){
			//根据条件生成唯一key
			Object object = cacheClient.get("CacheKey_Xxcjmy");
			if(!BlankUtils.isBlank(object)){
				xxcjmy =  (String) object;
			}else{
				//缓存过期重新查询
				xxcjmy = securityDataSourceDao.selectOne("com.woshidaniu.dao.daointerface.ICommonSqlDao.getXxcjmy");
				//缓存一个月
				cacheClient.set("CacheKey_Xxcjmy", ICacheClient.CACHE_EXP_FOREVER,xxcjmy);
			}
		}else{
			xxcjmy = securityDataSourceDao.selectOne("com.woshidaniu.dao.daointerface.ICommonSqlDao.getXxcjmy");
		}*/
		xxcjmy = securityDataSourceDao.selectOne("com.woshidaniu.dao.daointerface.ICommonSqlDao.getXxcjmy");
		return xxcjmy;
	}
	
	public CommonModel getCommonModel(Map<String,Object> map){
		CommonModel model = new CommonModel();
		if(map!=null){
			try {
				PropertyUtils.copyProperties(model, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}
	
	public List<JsglModel> getJsList() {
		return this.getJsList(new HashMap());
	}
	
	@Override
	public List<BaseMap> getJsMapList() {
		return dao.getJsMapList();
	}
	
	@Override
	public List<JsglModel> getJsList(Map<String,Object> map) {
		return dao.getJsListByScope(getCommonModel(map));
	}
	
	@Override
	public List<JsglModel> queryAllJs() {
		List<JsglModel> modelList = null;
		//缓存服务器开启
		if(isCacheSupport()){
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(GlobalCacheKeyConstant.JS_LIST2);
			if(valueWrapper == null){
				//缓存过期重新查询
				modelList = dao.queryAllJsByScope();
				cache.put(GlobalCacheKeyConstant.JS_LIST2, modelList);
			}else{
				modelList = ( (List) valueWrapper.get());
			}
		}else{
			modelList = dao.queryAllJsByScope();
		}
		return modelList;
	}
	
	@Override
	public List<JsglModel> getPagedJsxxList(CommonModel model) {
		/*List<JsglModel> modelList = null;
		//缓存服务器开启
		if(isCacheStart()){
			//根据条件生成唯一key
			String autoKey = CacheKeyGen.genKeyForModel(getClass(), "getPagedJsxxList", GlobalCacheKeyConstant.JS_LIST2,model);
			String queryKey = CacheKeyGen.genQueryKey(autoKey);
			Object object = cacheClient.get(autoKey);
			if(!BlankUtils.isBlank(object)){
				modelList =  (List) object;
				model.setQueryModel((QueryModel)cacheClient.get(queryKey));
			}else{
				//缓存过期重新查询
				modelList = dao.getPagedJsxxListByScope(model);
				//缓存一周
				cacheClient.set(autoKey, ICacheClient.CACHE_EXP_WEEK,modelList);
				//queryModel 缓存时间相对长点，防止集合还在，分页信息丢失
				cacheClient.set(queryKey, ICacheClient.CACHE_EXP_WEEK + ICacheClient.CACHE_EXP_MINUTE,model.getQueryModel());
				//缓存当前缓存对应的key
				cacheClient.storeAutoKey(getClass(), "getPagedJsxxList", new String[]{autoKey , queryKey});
			}
		}else{
			modelList = dao.getPagedJsxxListByScope(model);
		}*/
		return dao.getPagedJsxxListByScope(model);
	}
	
	@Override
	public List<XqdmModel> queryAllXq(){
		/*List<XqdmModel> modelList = null;
		//用户不同用户看到的校区应不相同，考虑到缓存，这里需要使用参数区别不同用户
		CommonModel model = getCommonModel(null);
		//缓存服务器开启
		if(isCacheStart()){
			//根据条件生成唯一key
			String autoKey = CacheKeyGen.genKeyForModel(getClass(), "queryAllXq", CacheKeyGen.genPrefix(GlobalCacheKeyConstant.XQ_LIST) ,model);
			Object object = cacheClient.get(autoKey);
			if(!BlankUtils.isBlank(object)){
				modelList =  (List) object;
			}else{
				//缓存过期重新查询
				modelList = dao.queryAllXqByScope(model);
				//缓存一周
				cacheClient.set(autoKey,ICacheClient.CACHE_EXP_WEEK, modelList);
				//缓存当前缓存对应的key
				cacheClient.storeAutoKey(getClass(), "queryAllXq", autoKey);
			}
		}else{
			modelList = dao.queryAllXqByScope(model);
		}*/
		return dao.queryAllXqByScope(getCommonModel(null));
	}
	
	@Override
	@Cacheable(value = CacheName.CACHE_BASIC , key = GlobalCacheKeyConstant.XQJ_CACHEKEY)
	public List<JcsjModel> queryXqj(){
		return dao.queryXqj();
	}
	
	@Override
	public List<XqdmModel> getPagedXqxxList(CommonModel model) {
		return dao.getPagedXqxxListByScope(model);
	}
	
	
	@Override
	@Cacheable(value = CacheName.CACHE_BASIC , key = GlobalCacheKeyConstant.NJ_LIST )
	public List<NjdmModel> queryAllNj() {
		return dao.queryAllNjByScope();
	}
	
	@Override
	public List<NjdmModel> queryNjList(Map<String,Object> map) {
		return dao.queryNjListByScope(getCommonModel(map));
	}
	
	@Override
	public NjdmModel getNjdmModel(String njdm_id) {
		return dao.getNjdmModel(njdm_id);
	}
	
	@Override
	public List<NjdmModel> getPagedNjList(CommonModel model) {
		return  dao.getPagedNjListByScope(model);
	}
	
	@Override
	public List<BmdmModel> queryAllJgxx() {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		return dao.queryAllJgxxByScope(jgpxzd);
	}

	@Override
	public List<BmdmModel> getAllJgxxList() {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		return dao.getAllJgxxList(jgpxzd);
	}
	
	@Override
	public BmdmModel getJgxxModel(String jg_id) {
		return dao.getJgxxModel(jg_id);
	}

	@Override
	public List<BmdmModel> getPagedJgxxList(CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedJgxxListByScope(model);
	}
	
	@Override
	public List<PairModel> getPagedJgxxPairList(CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedJgxxPairListByScope(model);
	}

	@Override
	public List<BmdmModel> queryAllKkbm(){
		CommonModel model = getCommonModel(null);
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		//rangeable为false 没有数据范围
		boolean rangeable = StringUtils.getSafeBoolean(WebContext.getRequest().getParameter("rangeable"), "true");
		model.setRangeable(rangeable);
		return dao.queryAllKkbmByScope(model);
	}
	
	
	
	@Override
	public List<BmdmModel> queryKkbmNoScope(){
		CommonModel model = getCommonModel(null);
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		//rangeable为false 没有数据范围
		model.setRangeable(false);
		
		return dao.queryAllKkbmByScope(model);
	}
	
	

	@Override
	public BmdmModel getKkbmModel(String kkbm_id) {
		return dao.getKkbmModel(kkbm_id);
	}

	@Override
	public List<BmdmModel> getPagedKkbmList(CommonModel model){
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedKkbmListByScope(model);
	}
	
	@Override
	public List<PairModel> getPagedKkbmPairList(CommonModel model){
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedKkbmPairListByScope(model);
	}
	
	@Override
	public List<BmdmModel> queryAllJsbm(){
		//用户不同用户看到的校区应不相同，考虑到缓存，这里需要使用参数区别不同用户
		CommonModel model = getCommonModel(null);
		model.setUser(WebContext.getUser());
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.queryAllJsbmByScope(model);
	}
	
	@Override
	public List<BmdmModel> getPagedJsbmList(CommonModel model){
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedJsbmListByScope(model);
	}
	
	@Override
	public List<PairModel> getPagedJsbmPairList(CommonModel model){
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedJsbmPairListByScope(model);
	}
	
	@Override
	public List<BmdmModel> queryAllXy() {
		//用户不同用户看到的校区应不相同，考虑到缓存，这里需要使用参数区别不同用户
		CommonModel model = getCommonModel(null);
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.queryXyxxListByScope(model);
	}
	
	@Override
	public List<BmdmModel> queryXyNoScope(){
		//用户不同用户看到的校区应不相同，考虑到缓存，这里需要使用参数区别不同用户
		CommonModel model = getCommonModel(null);
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		//rangeable为false 没有数据范围
		model.setRangeable(false);
		return dao.queryXyxxListByScope(model);
	}
	
	
	
	@Override
	public List<BmdmModel> queryXyxxList(CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.queryXyxxListByScope(model);
	}

	@Override
	public BmdmModel getXyxxModel(String xy_id) {
		return dao.getXyxxModel(xy_id);
	}

	@Override
	public List<BmdmModel> getPagedXyxxList(final CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedXyxxListByScope(model);
	}
	
	@Override
	public List<PairModel> getPagedXyxxPairList(CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedXyxxPairListByScope(model);
	}
	

	@Override
	public List<BmdmModel> queryXxxList(CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.queryXxxListByScope(model);
	}

	@Override
	public BmdmModel getXxxModel(String xxx_id) {
		return dao.getXxxModel(xxx_id);
	}

	@Override
	public List<BmdmModel> getPagedXxxList(CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedXxxListByScope(model);
	}
	
	@Override
	public List<PairModel> getPagedXxxPairList(CommonModel model) {
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.getPagedXxxPairListByScope(model);
	}
	
	@Override
	public List<ZydmModel> queryAllZy(){
		//用户不同用户看到的专业应不相同，考虑到缓存，这里需要使用参数区别不同用户
		CommonModel model = getCommonModel(null);
		model.setUser(WebContext.getUser());
		// 机构排序方式：1:按机构代码排序；2:按机构名称排序	
		String jgpxzd = getConstant("JGPXZD");
		model.setJgpxzd(jgpxzd);
		return dao.queryZyxxListByScope(model);
	}

	@Override
	public List<ZydmModel> queryZyxxList(CommonModel model){
		return dao.queryZyxxListByScope(model);
	}
	
	@Override
	public ZydmModel getZyxxModel(String zyh_id) {
		return dao.getZyxxModel(zyh_id);
	}
	
	@Override
	public List<ZydmModel> getPagedZyxxList(CommonModel model) {
		return dao.getPagedZyxxListByScope(model);
	}

	@Override
	public List<PairModel> getPagedZyxxPairList(CommonModel model) {
		return dao.getPagedZyxxPairListByScope(model);
	}
	
	@Override
	public List<ZyfxdmModel> queryZyfxList(CommonModel model){
		return dao.queryZyfxListByScope(model);
	}
	
	@Override
	public ZyfxdmModel getZyfxModel(String zyfx_id) {
		return dao.getZyfxModel(zyfx_id);
	}

	public List<ZyfxdmModel> getPagedZyfxList(CommonModel model){
		return dao.getPagedZyfxListByScope(model);
	}
	
	@Override
	public List<PairModel> getPagedZyfxPairList(CommonModel model) {
		return dao.getPagedZyfxPairListByScope(model);
	}

	@Override
	public List<BjdmModel> queryAllBj() {
		//用户不同用户看到的校区应不相同，考虑到缓存，这里需要使用参数区别不同用户
		CommonModel model = getCommonModel(null);
		model.setUser(WebContext.getUser());
		return dao.queryAllBjByScope(model);
	}
	
	@Override
	public List<BjdmModel> queryBjxxList(CommonModel model) {
		return dao.queryBjxxByScope(model);
	}
	
	@Override
	public BjdmModel getBjxxModel(String bh_id) {
		return dao.getBjxxModel(bh_id);
	}
	
	@Override
	public List<BjdmModel> getPagedBjxxList(CommonModel model) {
		return dao.getPagedBjxxListByScope(model);
	}
	
	@Override
	public List<PairModel> getPagedBjxxPairList(CommonModel model) {
		return dao.getPagedBjxxPairListByScope(model);
	}

	@Override
	public List<JcsjModel> queryJcsjList(String lxdm) {
		List<JcsjModel> modelList = null;
		//缓存服务器开启
		if(isCacheSupport()){
			
			String autoKey = GlobalCacheKeyConstant.JCSJ_LIST+"_"+lxdm;
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(autoKey);
			if(valueWrapper != null){
				modelList = (List<JcsjModel>) valueWrapper.get();
			} else{
				//缓存过期重新查询
				modelList = jcsjDao.getJcsjList(LocaleUtils.getLocaleKey(),lxdm);
				//缓存一周
				cache.put(autoKey, modelList);
			}
		}else{
			modelList = jcsjDao.getJcsjList(LocaleUtils.getLocaleKey(),lxdm);
		}
		return modelList;
	}
	
	
	@Override
	@Cacheable(value = CacheName.CACHE_BASIC , key = GlobalCacheKeyConstant.DXQ_LIST)
	public List<JcsjModel> getXqList() {
		return jcsjDao.getXqList(null);
	}
	
	@Override
	@Cacheable(value = CacheName.CACHE_BASIC , key = GlobalCacheKeyConstant.XXQ_LIST)
	public List<JcsjModel> getXxqList() {
		return jcsjDao.getXqList("0");
	}
	

	@Override
	public List<Map<String, String>> getPagedJxcdListByScope(CommonModel model) {
		return dao.getPagedJxcdListByScope(model);
	}

	
	public List<Map<String, String>> getPagedZynjListByScope(CommonModel model) {
		return dao.getPagedZynjListByScope(model);
	}
	
	@Override
	public List<Map<String, String>> getPagedYhList(CommonModel model){
		return dao.getPagedYhList(model);
	}

	@Override
	@Cacheable(value = CacheName.CACHE_BASIC , key = GlobalCacheKeyConstant.XN_LIST)
	public List<BaseMap> getXnMapList() {
		return dao.getXnMapList();
	}
	
	@Override
	public List<BaseMap> getJcsjList(String lxdm) {
		List<BaseMap> jcsjList  =  null;
		//缓存服务器开启
		if(cacheSupport){
			String autoKey = CacheKeyUtils.genKey(getClass(), "getJcsjList",GlobalCacheKeyConstant.JCSJ_LIST + lxdm.toString());
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(autoKey);
			if(valueWrapper != null){
				jcsjList =  (List<BaseMap>) valueWrapper.get();
			} else {
				//缓存过期重新查询
				jcsjList = dao.getJcsjList(LocaleUtils.getLocaleKey(),lxdm.toString());
				//缓存
				cache.put(autoKey, jcsjList);
			}
		}else{
			jcsjList = dao.getJcsjList(LocaleUtils.getLocaleKey(),lxdm.toString());
		}
		
		return jcsjList;
	}
	
	/**
	 * 
	 *@描述：获得【功能模块代码表】中所有进行不重复处理后的功能模块代码信息List<PairModel>集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-10下午03:56:40
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<PairModel> getGnmkdmPairList(){
		List<PairModel> gnmkdmPairList  =  null;
		//缓存服务器开启
		if(cacheSupport){
			String autoKey = GlobalCacheKeyConstant.GNMKDM_CACHEKEY + LocaleUtils.getLocaleKey();
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(autoKey);
			if(valueWrapper != null){
				gnmkdmPairList =  (List<PairModel>) valueWrapper.get();
			} else {
				//缓存过期重新查询
				gnmkdmPairList = dao.getGnmkdmPairList(LocaleUtils.getLocaleKey());
				//缓存
				cache.put(autoKey, gnmkdmPairList);
			}
		}else{
			gnmkdmPairList = dao.getGnmkdmPairList(LocaleUtils.getLocaleKey());
		}
		return gnmkdmPairList;
	}
	
	/**
	 * 
	 *@描述：获得【操作代码表】中所有进行不重复处理后的操作代码信息List<PairModel>集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-10下午03:56:21
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<PairModel> getCzdmPairList(){
		List<PairModel> czdmPairList  =  null;
		//缓存服务器开启
		if(cacheSupport){
			String autoKey = GlobalCacheKeyConstant.CZDM_CACHEKEY + LocaleUtils.getLocaleKey();
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(autoKey);
			if(valueWrapper != null){
				czdmPairList =  (List<PairModel>) valueWrapper.get();
			} else {
				//缓存过期重新查询
				czdmPairList = dao.getCzdmPairList(LocaleUtils.getLocaleKey());
				//缓存
				cache.put(autoKey, czdmPairList);
			}
		} else{
			czdmPairList = dao.getCzdmPairList(LocaleUtils.getLocaleKey());
		}
		return czdmPairList;
	}

	public ICommonSqlDao getDao() {
		return dao;
	}

	public void setDao(ICommonSqlDao dao) {
		this.dao = dao;
	}
	
	public IJcsjDao getJcsjDao() {
		return jcsjDao;
	}

	public void setJcsjDao(IJcsjDao jcsjDao) {
		this.jcsjDao = jcsjDao;
	}
	
	public MybatisDataSourceDao getSecurityDataSourceDao() {
		return securityDataSourceDao;
	}

	public void setSecurityDataSourceDao(MybatisDataSourceDao securityDataSourceDao) {
		this.securityDataSourceDao = securityDataSourceDao;
	}

	public IXtszDao getXtszDao() {
		return xtszDao;
	}

	public void setXtszDao(IXtszDao xtszDao) {
		this.xtszDao = xtszDao;
	}

	@Override
	public List<JcsjModel> queryXwxxList() {
		//查询授予学位信息
		List<JcsjModel> list = jcsjDao.getJcsjList(LocaleUtils.getLocaleKey(),"0027");
		return list;
	}

	@Override
	public List<JcsjModel> queryKsfsList() {
		//查询考试方式
		List<JcsjModel> list = jcsjDao.getJcsjList(LocaleUtils.getLocaleKey(),"0035");
		return list;
	}

	@Override
	public List<NjdmModel> queryAllNj2(String xhId) {
		List<NjdmModel> modelList = dao.queryAllNjByScope2(xhId);
		return modelList;
	}

	@Override
	public String getDqnd() {
		return dao.getDqnd();
	}

	@Override
	public String getInnerParam(String zdm) {
		return dao.getInnerParam(zdm);
	}
	
	
	@Override
	public String getCacheInnerParam(String zdm) {
		Cache cache = this.getCache();
		String zdz = null;
		//缓存服务器开启
		ValueWrapper valueWrapper = cache.get(zdm);
		if(!BlankUtils.isBlank(valueWrapper)){
			zdz =  (String) valueWrapper.get();
		}else{
			//缓存过期重新查询
			zdz = getDao().getInnerParam(zdm);
			//缓存一天
			cache.put(zdm,zdz);
		}
		return zdz;
	}
	
	
	
	@Override
	public String getCacheName() {
		return CacheName.CACHE_BASIC;
	}
	
	
	@Override
	public List<CommonModel> queryKsmcList(CommonModel model){
		return dao.queryKsmcList(model);
	}
}
