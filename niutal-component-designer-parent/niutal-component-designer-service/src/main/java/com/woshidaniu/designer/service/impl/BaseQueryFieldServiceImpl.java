package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IBaseQueryFieldDao;
import com.woshidaniu.designer.dao.entities.BaseQueryFieldModel;
import com.woshidaniu.designer.service.svcinterface.IBaseQueryFieldService;



/**
 * 
 *@类名称:BaseQueryFieldServiceImpl.java
 *@类描述：系统可自定义查询字段信息service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午03:02:12
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0310")
@Service
public class BaseQueryFieldServiceImpl extends BaseServiceImpl<BaseQueryFieldModel, IBaseQueryFieldDao> implements IBaseQueryFieldService {
	
	@Resource
	protected IBaseQueryFieldDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	@Comment
	public boolean insert(BaseQueryFieldModel model) {
		//'APP':'程序内置','SQL':'数据库','XML':'XML数据','Spring':'Spring集合对象','Fixed':'固定值'
		if("APP".equalsIgnoreCase(model.getField_source_type())){
			model.setField_source("APP:");
		}else {
			model.setField_source(model.getField_source_type() + ":"+model.getField_source());
		}
		dao.insert(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean update(BaseQueryFieldModel model) {
		//'APP':'程序内置','SQL':'数据库','XML':'XML数据','Spring':'Spring集合对象','Fixed':'固定值'
		if("APP".equalsIgnoreCase(model.getField_source_type())){
			model.setField_source("APP:");
		}else {
			model.setField_source(model.getField_source_type() + ":"+model.getField_source());
		}
		dao.update(model);
		return true;
	}
	
	public int getUseCount(BaseQueryFieldModel model){
		return dao.getUseCount(model);
	}
	
	@Override
	@Comment
	public boolean delete(BaseQueryFieldModel model) {
		dao.delete(model);
		return true;
	}
	
	public List<BaseQueryFieldModel> getFuncModelList(String func_code){
		return dao.getFuncModelList(func_code);
	}
}
