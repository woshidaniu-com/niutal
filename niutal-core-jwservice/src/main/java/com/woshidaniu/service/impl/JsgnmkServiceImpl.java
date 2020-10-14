package com.woshidaniu.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.Predicate;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.cache.core.utils.CacheKeyUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.daointerface.IJsgnmkDao;
import com.woshidaniu.entities.JsgnmkModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IJsgnmkService;
import com.woshidaniu.service.utils.JsgnmkUtils;
import com.woshidaniu.util.local.LocaleUtils;


/**
 * 
 *@类名称:JsgnmkServiceImpl.java
 *@类描述：角色功能模块管理业务实现类
 *@创建人：kangzhidong
 *@创建时间：2014-10-20 上午08:57:41
 *@版本号:v1.0
 */
@After
@Logger(model="N0102",business="N010201")
@Service
public class JsgnmkServiceImpl extends CommonBaseServiceImpl<JsgnmkModel, IJsgnmkDao> implements IJsgnmkService {
	
	@Resource
	private IJsgnmkDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.afterPropertiesSet();
	    super.setDao(dao);   
	}
	
	public boolean getParentSfejsq(JsgnmkModel model){
		return dao.getParentSfejsq(model) > 0;
	}
	
	public List<JsgnmkModel> cxYjGnmkdmList(JsgnmkModel model)  {
		//判断是否admin角色
		model.setCdjb("1");
		if("admin".equals(model.getSqrJsdm())){
			//查询一级功能模块代码列表
			return dao.getXtGnmkdmList(model);
		}else{
			//查询一级功能模块代码列表
			return dao.getGnmkdmList(model);
		}
	}
		
	public List<JsgnmkModel> cxGnmkdmList(JsgnmkModel model)  {
		//查询所有功能模块代码，操作模块代码
		List<JsgnmkModel> gnmkdmList =  null;
		List<JsgnmkModel> gnmkczList = null;
		//判断是否admin角色
		if("admin".equals(model.getSqrJsdm())){
			//查询一级功能模块代码列表
			gnmkdmList =  dao.getXtGnmkdmList(model);
			//用户所有功能操作代码列表
			gnmkczList = dao.getXtGnmkczList(model);
		}else{
			//查询一级功能模块代码列表
			gnmkdmList =  dao.getGnmkdmList(model);
			//用户所有功能操作代码列表
			gnmkczList = dao.getGnmkczList(model);
		}
		//筛选一级功能模块节点数据
		List<JsgnmkModel> firstGnmkList = CollectionUtils.findAll(gnmkdmList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				JsgnmkModel jsgnmkModel = (JsgnmkModel)object;
				if("1".equalsIgnoreCase(jsgnmkModel.getCdjb())){
					return true;
				}
				return false;
			}
		});
		//循环一级功能菜单
		for (JsgnmkModel jsgnmkModel : firstGnmkList) {
			//从一级功能菜单开始初始化各级别功能模块
			JsgnmkUtils.initJsgnmkList(jsgnmkModel,gnmkdmList,gnmkczList);
			/*
			jsgnmkModel.setJsdm(model.getJsdm());
			//从一级功能菜单开始初始化各级别功能模块
			initJsgnmkList(gnmkczList,gnmkdmJbList,model.getSqrJsdm(),jsgnmkModel,2,max);*/
			//去除无用属性
			jsgnmkModel.setQueryList(null);
			jsgnmkModel.setQueryModel(null);
			jsgnmkModel.setListnav(null);
			jsgnmkModel.setTotalResult(null);
		}
		return firstGnmkList;
	}
	
	public JsgnmkModel getGnmkdmModel(JsgnmkModel model){
		return dao.getGnmkdmModel(model);
	}

	public List<JsgnmkModel> cxGnmkczList(JsgnmkModel model)  {
		return dao.getGnmkczList(model);
	}
	
	public List<String> cxJsGnmkdmList(String jsdm){
		return dao.getJsGnmkdmList(jsdm);
	}
	
	/**
	 * 更新角色功能授权信息
	 */
	@Comment(recordSQL=true)
	public void updateJsgnsqxx(JsgnmkModel model) {
		dao.updateJsgnsqxx(model);
	}

	@Override
	public List<List<Map<String,String>>> getJsgnList(String jsdm,String type) {
		//查询学生或教师功能菜单
		List<HashMap<String, String>> list =  dao.getJsgnList(jsdm , type , LocaleUtils.getLocaleKey());
		//组织原始数据
		return JsgnmkUtils.getStudentOrTeacherGnmkdmList(list);
	}
	
	@Override
	public String getValue(String key) {
		String cacheKey = CacheKeyUtils.genKey(this.getClass(), "getValue", key);
		Cache cache = getCacheManager().getCache(CacheName.CACHE_MENU);
		ValueWrapper valueWrapper = cache.get(cacheKey);
		if( valueWrapper != null){
			return (String) valueWrapper.get();
		}else{
			//缓存过期重新查询
			String value = dao.getValue(key);
			//缓存一周
			cache.put(cacheKey, value);
			return value;
		}
	}
	
}
