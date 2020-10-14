package com.woshidaniu.designer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IBaseAutoCompleteFieldDao;
import com.woshidaniu.designer.dao.entities.BaseAutoCompleteFieldModel;
import com.woshidaniu.designer.service.svcinterface.IBaseAutoCompleteFieldService;



/**
 * 
 *@类名称: BaseAutoCompleteFieldServiceImpl.java
 *@类描述：系统可自定义自动完成字段信息操作Service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-5-7 下午03:05:14
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0305")
@Service
public class BaseAutoCompleteFieldServiceImpl extends BaseServiceImpl<BaseAutoCompleteFieldModel, IBaseAutoCompleteFieldDao> implements IBaseAutoCompleteFieldService {
	

	@Resource
	protected IBaseAutoCompleteFieldDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public int getUseCount(BaseAutoCompleteFieldModel model){
		return dao.getUseCount(model);
	}
	
	@Override
	@Comment
	public boolean insert(BaseAutoCompleteFieldModel model) {
		dao.insert(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean update(BaseAutoCompleteFieldModel model) {
		 dao.update(model);
		 return true;
	}
	
	@Override
	@Comment
	public boolean delete(BaseAutoCompleteFieldModel model) {
		dao.delete(model);
		return true;
	}
	
}
