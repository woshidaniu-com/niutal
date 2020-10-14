package com.woshidaniu.datarange.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.datarange.dao.daointerface.ISjfwdxDao;
import com.woshidaniu.datarange.dao.entities.SjfwdxModel;
import com.woshidaniu.datarange.service.svcinterface.ISjfwdxService;

/**
 * 
 * 类名称： SjfwdxServiceImpl
 * 类描述：数据范围对象Service
 * 创建人：caozf
 * 创建时间：2012-7-12
 */
@Service("sjfwdxService")
public class SjfwdxServiceImpl extends BaseServiceImpl<SjfwdxModel, ISjfwdxDao> implements ISjfwdxService {

	@Resource
	private ISjfwdxDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.afterPropertiesSet();
	    super.setDao(dao);
	}
	
	@Override
	@Cacheable(value = CacheName.CACHE_BASIC)
	public List<SjfwdxModel> cxSjfwdx(SjfwdxModel model) {
		return dao.cxSjfwdx(model);
	}
	
	@Override
	@Cacheable(value = CacheName.CACHE_BASIC)
	public List<SjfwdxModel> cxSjfwnr(SjfwdxModel model) {
		return dao.cxSjfwnr(model);
	}
	
}
