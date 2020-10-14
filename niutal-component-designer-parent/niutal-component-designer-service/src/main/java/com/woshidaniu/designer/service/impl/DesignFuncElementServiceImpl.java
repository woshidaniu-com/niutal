package com.woshidaniu.designer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementDao;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementFieldDao;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementQueryDao;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementWidgetDao;
import com.woshidaniu.designer.dao.entities.DesignFuncElementFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementService;

/**
 * 
 *@类名称: DesignFuncElementServiceImpl.java
 *@类描述：功能页面自定义元素信息操作SERVICE接口实现:指定设计器生成的功能页面的元素信息，元素分基本html元素和js组件
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午02:56:07
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designFuncElementService")
public class DesignFuncElementServiceImpl extends BaseServiceImpl<DesignFuncElementModel, IDesignFuncElementDao> implements IDesignFuncElementService {
	
	@Resource
	protected IDesignFuncElementFieldDao fieldDao;
	@Resource
	protected IDesignFuncElementQueryDao queryDao;
	@Resource
	protected IDesignFuncElementWidgetDao widgetDao;
	@Resource
	protected IDesignFuncElementDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	@Comment
	public boolean insert(DesignFuncElementModel model) {
		dao.insert(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean delete(DesignFuncElementModel model) {
		dao.deleteElementEntity(model);
		dao.delete(model);
		return true;
	}

	@Override
	@Comment
	public boolean update(DesignFuncElementModel model) {
		dao.update(model);
		return true;
	}
	
	@Override
	public DesignFuncElementModel getModel(DesignFuncElementModel model) {
		DesignFuncElementModel modelTmp = dao.getModel(model);
		if("widget".equalsIgnoreCase(modelTmp.getElement_type())){
			getWidgetDao().insert(modelTmp.getElement_widget());
		}else if("field".equalsIgnoreCase(modelTmp.getElement_type())){
			//查询关联的基础字段
			DesignFuncElementFieldModel fieldModel = new DesignFuncElementFieldModel();
			fieldModel.setElement_guid(modelTmp.getElement_guid());
			modelTmp.setElement_field_list(getFieldDao().getModelList(fieldModel));
			
		}else if("query".equalsIgnoreCase(modelTmp.getElement_type())){
			getQueryDao().insert(model.getElement_query());
		}
		return modelTmp;
	}

	@Override
	public List<DesignFuncElementModel> getFuncElementList(String func_code, String opt_code) {
		return dao.getFuncElementList(func_code, opt_code);
	}
	
	@Override
	public List<DesignFuncElementModel> getRelatedElementList(DesignFuncElementModel model) {
		return dao.getRelatedElementList(model);
	}

	@Override
	public boolean insertElementEntity(DesignFuncElementModel model) {
		if(BlankUtils.isBlank(model.getElement_type())){
			model = dao.getModel(model);
		}
		//1:'查询条件',2:'脚本控件',3:'普通字段
		if("1".equalsIgnoreCase(model.getElement_type())){
			model.getElement_query().setElement_guid(model.getElement_guid());
			getQueryDao().insert(model.getElement_query());
		}else if("2".equalsIgnoreCase(model.getElement_type()) && !BlankUtils.isBlank(model.getElement_widget())){
			
			DesignFuncElementWidgetModel widgetModel = model.getElement_widget();
			
			widgetModel.setElement_guid(model.getElement_guid());
			widgetModel.setWidget_params("{}");
			widgetModel.setFunc_code(model.getFunc_code());
			widgetModel.setOpt_code(model.getOpt_code());
			widgetModel.setFunc_guid(model.getFunc_guid());
			
			getWidgetDao().insert(widgetModel);
		}else if("3".equalsIgnoreCase(model.getElement_type())){
			//字段集合不为空
			if(!BlankUtils.isBlank(model.getElement_field_list())){
				for (DesignFuncElementFieldModel fieldModel : model.getElement_field_list()) {
					//fieldModel.setElement_guid(model.getElement_guid());
					getFieldDao().insert(fieldModel);
				}
			}
		} 
		return true;
	}
	
	@Override
	public boolean deleteElementEntity(DesignFuncElementModel model) {
		dao.deleteElementEntity(model);
		return true;
	}

	public IDesignFuncElementFieldDao getFieldDao() {
		return fieldDao;
	}

	public void setFieldDao(IDesignFuncElementFieldDao fieldDao) {
		this.fieldDao = fieldDao;
	}

	public IDesignFuncElementQueryDao getQueryDao() {
		return queryDao;
	}

	public void setQueryDao(IDesignFuncElementQueryDao queryDao) {
		this.queryDao = queryDao;
	}

	public IDesignFuncElementWidgetDao getWidgetDao() {
		return widgetDao;
	}

	public void setWidgetDao(IDesignFuncElementWidgetDao widgetDao) {
		this.widgetDao = widgetDao;
	}

	
}
