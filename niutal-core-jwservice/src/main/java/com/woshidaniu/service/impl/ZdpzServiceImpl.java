package com.woshidaniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.daointerface.IZdpzDao;
import com.woshidaniu.entities.ZdpzModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IZdpzService;

/**
 * 
 * @类名称:ZdpzServiceImpl.java
 * @类描述：字段配置service接口实现
 * @创建人：kangzhidong
 * @创建时间：2014-7-1 下午07:04:26
 * @版本号:v1.0
 */
@Service
public class ZdpzServiceImpl extends CommonBaseServiceImpl<ZdpzModel, IZdpzDao> implements IZdpzService {
	
	@Resource
	private IZdpzDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}

	@Override
	public boolean batchUpdate(List<ZdpzModel> list) {
		for (ZdpzModel model : list) {
			// 如果没有字段配置ID表示为新增操作
			if (BlankUtils.isBlank(model.getZdpz_id())) {
				getDao().insert(model);
			} else {
				// 否则为更新操作
				getDao().update(model);
			}
		}
		return true;
	}

}
