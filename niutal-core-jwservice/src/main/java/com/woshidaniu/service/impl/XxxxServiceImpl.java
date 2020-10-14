package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.cache.core.annotation.CacheExpire;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.constant.GlobalCacheKeyConstant;
import com.woshidaniu.daointerface.IXxxxDao;
import com.woshidaniu.entities.XxxxModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IXxxxService;

/**
 * 
 *@类名称	: XxxxServiceImpl.java
 *@类描述	：
 *@创建人	：kangzhidong
 *@创建时间	：2016年4月20日 下午5:45:07
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@After
@Logger(model="N01",business="N000001")
@Service
public class XxxxServiceImpl extends CommonBaseServiceImpl<XxxxModel, IXxxxDao> implements IXxxxService {
	
	@Resource
	private IXxxxDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		 super.afterPropertiesSet();
		 super.setDao(dao);
		 
	}

	@Comment(model="N0105",business="N010510",recordSQL=true)
	public boolean insert(XxxxModel model) {
		return dao.insert(model)>0?true:false;
	}
	
	@Comment(model="N0105",business="N010510",recordSQL=true)
	@CacheExpire(keys={GlobalCacheKeyConstant.XXXX_MAP})
	public boolean update(XxxxModel model) {
		return dao.update(model)>0?true:false;
	}
	
}
