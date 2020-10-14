package com.woshidaniu.datarange.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.datarange.dao.daointerface.ISjfwzDao;
import com.woshidaniu.datarange.dao.entities.SjfwzModel;
import com.woshidaniu.datarange.service.svcinterface.ISjfwzService;

/**
 * 
 * 类名称： SjfwzServiceImpl
 * 类描述：数据范围组Service
 * 创建人：caozf
 * 创建时间：2012-7-12
 */
@Service("sjfwzService")
public class SjfwzServiceImpl extends BaseServiceImpl<SjfwzModel, ISjfwzDao>
		implements ISjfwzService {
	@Resource
	private ISjfwzDao dao;
	
	@Override
	public List<SjfwzModel> cxSjfwzYhjs(Map<String, Object> maps) {
	/*	List<SjfwzModel> modelList = null;
		//缓存服务器开启
		if(!BlankUtil.isBlank(cacheClient)){
			//根据条件生成唯一key
			String autoKey = CacheKeyGen.genKeyForMap(getClass(), "cxSjfwzYhjs",CacheKeyConstant.SJFWZ_CACHEKEY,maps);
			Object object = cacheClient.get(autoKey);
			if(!BlankUtil.isBlank(object)){
				modelList =  (List) object;
			}else{
				//缓存过期重新查询
				modelList = dao.cxSjfwzYhjs(maps);
				//缓存一天
				cacheClient.set(autoKey,ICacheClient.CACHE_EXP_DAY, modelList);
			}
		}else{
			modelList = dao.cxSjfwzYhjs(maps);
		}*/
		return dao.cxSjfwzYhjs(maps);
	}

}
