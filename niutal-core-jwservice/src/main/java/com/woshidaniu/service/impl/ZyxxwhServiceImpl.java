package com.woshidaniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IZyxxwhDao;
import com.woshidaniu.entities.ZyxxwhModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IZyxxwhService;

/**
 * 
 * @类名称:ZyxxwhServiceImpl.java
 * @类描述：专业信息维护
 * @创建人：wjy
 * @创建时间：2014-10-22 下午04:25:18
 * @版本号:v1.0
 */
@After
@Logger(model = "N0105", business = "N010525")
@Service
public class ZyxxwhServiceImpl extends CommonBaseServiceImpl<ZyxxwhModel, IZyxxwhDao> implements IZyxxwhService {
	
	@Resource
	private IZyxxwhDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}

	@Comment
	public boolean insert(ZyxxwhModel t) {
		return dao.insert(t) > 0;
	}

	@Comment
	public boolean update(ZyxxwhModel t) {
		return dao.update(t) > 0;
	}

	public List<ZyxxwhModel> getXkmlList() {
		return dao.getXkmlList();
	}

	public List<ZyxxwhModel> getCcList() {
		return dao.getCcList();
	}

	@Comment
	public void updateSfty(ZyxxwhModel model) {
		String[] pks = model.getZyh_id().split(",");
		model.setPks(pks);
		dao.updateSfty(model);
	}
}
