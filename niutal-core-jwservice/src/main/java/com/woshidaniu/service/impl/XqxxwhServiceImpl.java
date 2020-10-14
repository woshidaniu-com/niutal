package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IXqxxwhDao;
import com.woshidaniu.entities.XqxxwhModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IXqxxwhService;

/**
 * 
 * @类名称:XqxxwhServiceImpl.java
 * @类描述：校区信息维护
 * @创建人：zzh
 * @创建时间：2016-3-31
 */
@After
@Logger(model = "N0105", business = "N010527")
@Service
public class XqxxwhServiceImpl extends CommonBaseServiceImpl<XqxxwhModel, IXqxxwhDao> implements IXqxxwhService {
	
	@Resource
	private IXqxxwhDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}

	@Comment(business = "N010527")
	public boolean update(XqxxwhModel model) {
		return getDao().update(model) > 0;
	}

	public void deleteXqxx(XqxxwhModel model) {
		String[] xqh_idArr = model.getXqh_id().split(",");
		for (int i = 0; i < xqh_idArr.length; i++) {
			model.setXqh_id(xqh_idArr[i]);
			getDao().delete(model);
		}
	}
}
