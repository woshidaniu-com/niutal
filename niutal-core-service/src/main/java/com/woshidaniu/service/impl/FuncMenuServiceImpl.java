package com.woshidaniu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.cache.core.utils.CacheKeyUtils;
import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IFuncMenuDao;
import com.woshidaniu.dao.entities.AncdModel;
import com.woshidaniu.service.svcinterface.IFuncMenuService;
import com.woshidaniu.service.utils.NavMenuUtils;

/**
 * 
 *@类名称	: FuncMenuServiceImpl.java
 *@类描述	：功能菜单Service接口实现
 *@创建人	：kangzhidong
 *@创建时间	：2017年7月17日 下午4:01:12
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Service("funcMenuService")
public class FuncMenuServiceImpl  extends BaseServiceImpl<AncdModel, IFuncMenuDao> implements IFuncMenuService {

	@Autowired
	protected IFuncMenuDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.afterPropertiesSet();
	    super.setDao(dao);   
	}
	
	/**
	 * 根据功能代码查询功能名称
	 */
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

	@Override
	public List<BaseMap> getTopNavMenuList(String yhm, String jsdm, String localeKey) {
		return dao.getTopNavMenuList(yhm, jsdm, localeKey);
	}

	@Override
	public List<BaseMap> getChildNavMenuList(String yhm, String jsdm, String parentGnmkdm, String localeKey) {
		return dao.getChildNavMenuList(yhm, jsdm, parentGnmkdm, localeKey);
	}
	
	@Override
	public List<BaseMap> getChildNavMenuTreeList(String yhm, String jsdm, String parentGnmkdm, String localeKey) {
		List<BaseMap> navMenuTreeList = dao.getNavMenuTreeList(yhm, jsdm, parentGnmkdm, localeKey);
		return NavMenuUtils.getNavTreeMenuList(navMenuTreeList, StringUtils.hasText(parentGnmkdm) ? parentGnmkdm : "N");
	}

	@Override
	public List<BaseMap> getNavMenuTreeList(String yhm, String jsdm, String localeKey) {
		List<BaseMap> navMenuTreeList = dao.getNavMenuTreeList(yhm, jsdm, null, localeKey);
		return NavMenuUtils.getNavTreeMenuList(navMenuTreeList);
	}
	
	
}
