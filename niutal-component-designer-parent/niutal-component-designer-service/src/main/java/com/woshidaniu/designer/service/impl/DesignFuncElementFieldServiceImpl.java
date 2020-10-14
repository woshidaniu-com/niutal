package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementFieldDao;
import com.woshidaniu.designer.dao.entities.DesignFuncElementFieldModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementFieldService;

/**
 * 
 *@类名称: DesignFuncElementFieldServiceImpl.java
 *@类描述：功能页面自定义元素关联字段信息操作SERVICE接口实现:指定设计器生成的功能页面元素的字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午03:15:59
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designFuncElementFieldService")
public class DesignFuncElementFieldServiceImpl extends BaseServiceImpl<DesignFuncElementFieldModel, IDesignFuncElementFieldDao> implements IDesignFuncElementFieldService {
	
	@Resource
	protected IDesignFuncElementFieldDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	@Comment
	public boolean insert(DesignFuncElementFieldModel t) {
		return dao.insert(t) > 0;
	}
	
	@Override
	@Comment
	public boolean update(DesignFuncElementFieldModel t) {
		return dao.update(t) > 0;
	}

	@Override
	@Comment
	public boolean delete(DesignFuncElementFieldModel t) {
		return dao.delete(t) > 0;
	}

	@Override
	public List<DesignFuncElementFieldModel> getFuncElementFieldList(String func_code, String opt_code) {
		return dao.getFuncElementFieldList(func_code, opt_code);
	}

}
