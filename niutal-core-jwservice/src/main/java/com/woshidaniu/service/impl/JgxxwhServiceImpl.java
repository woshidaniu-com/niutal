package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IJgxxwhDao;
import com.woshidaniu.entities.JgxxwhModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IJgxxwhService;

/**
 * 
 * @类名称:JgxxwhServiceImpl.java
 * @类描述：机构信息维护
 * @创建人：wjy
 * @创建时间：2015-2-14 下午02:56:54
 * @版本号:v1.0
 */
@After
@Logger(model = "N0105", business = "N010515")
@Service
public class JgxxwhServiceImpl extends CommonBaseServiceImpl<JgxxwhModel, IJgxxwhDao>
		implements IJgxxwhService {

	@Resource
	private IJgxxwhDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}

	@Comment(business = "N010515")
	public boolean update(JgxxwhModel model) {
		return getDao().update(model) > 0;
	}

	public void deleteJgxx(JgxxwhModel model) {
		String[] jg_idArr = model.getJg_id().split(",");
		for (int i = 0; i < jg_idArr.length; i++) {
			model.setJg_id(jg_idArr[i]);
			getDao().delete(model);
		}
	}
}
