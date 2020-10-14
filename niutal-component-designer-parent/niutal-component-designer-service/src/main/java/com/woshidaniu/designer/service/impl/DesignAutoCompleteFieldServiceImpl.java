package com.woshidaniu.designer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IDesignAutoCompleteFieldDao;
import com.woshidaniu.designer.dao.entities.DesignAutoCompleteFieldModel;
import com.woshidaniu.designer.service.svcinterface.IDesignAutoCompleteFieldService;

/**
 * 
 *@类名称: DesignAutoCompleteFieldServiceImpl.java
 *@类描述：功能页面:自动完成字段编辑service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-4-24 下午04:37:48
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N0105",business="N010525")
@Service("designAutoCompleteFieldService")
public class DesignAutoCompleteFieldServiceImpl extends BaseServiceImpl<DesignAutoCompleteFieldModel, IDesignAutoCompleteFieldDao> implements IDesignAutoCompleteFieldService {
	
	@Resource
	protected IDesignAutoCompleteFieldDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	@Comment
	public boolean insert(DesignAutoCompleteFieldModel model) {
		return dao.insert(model) > 0;
	}
	
	@Override
	@Comment
	public boolean update(DesignAutoCompleteFieldModel model) {
		return dao.update(model) > 0;
	}

	
}
