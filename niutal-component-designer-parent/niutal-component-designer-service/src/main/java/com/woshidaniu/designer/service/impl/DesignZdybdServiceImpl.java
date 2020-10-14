package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IDesignZdybdDao;
import com.woshidaniu.designer.dao.entities.DesignZdybdFlszModel;
import com.woshidaniu.designer.dao.entities.DesignZdybdZddyModel;
import com.woshidaniu.designer.service.svcinterface.IDesignZdybdService;

/**
 * 
 *@类名称		:DesignZdybdServiceImpl.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Oct 27, 2015 9:51:12 AM
 *@修改人		：
 *@修改时间	：
 *@版本号		:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designZdybdService")
public class DesignZdybdServiceImpl extends BaseServiceImpl<DesignZdybdZddyModel, IDesignZdybdDao> implements IDesignZdybdService {

	@Resource
	private IDesignZdybdDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	 
	@Override
	public List<DesignZdybdFlszModel> getFlszListByGndm(String func_code,String func_type) {
		return getDao().getFlszListByGndm(func_code,func_type);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DesignZdybdZddyModel> getZdyzdListByGndm(String func_code,String func_type,String jsdm) {
		List<DesignZdybdZddyModel> list;
		//缓存服务器开启
		if(isCacheSupport()){
			Cache cache = this.getCache();
			//根据条件生成唯一key
			String autoKey = "ZDDYLIST" + jsdm + func_code;
			ValueWrapper valueWrapper = cache.get(autoKey);
			if(valueWrapper != null){
				list = (List<DesignZdybdZddyModel>) valueWrapper.get();
			}else{
				//缓存过期
				list = getDao().getZdyzdListByGndm(func_code,func_type,jsdm);
				cache.put(autoKey, list);
			}
		}else{
			list = getDao().getZdyzdListByGndm(func_code,func_type,jsdm);
		}
		return list;
	}
	 
	
}
