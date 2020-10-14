package com.woshidaniu.designer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.db.core.utils.SQLInjectionUtils;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncDataDao;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementWidgetDao;
import com.woshidaniu.designer.dao.entities.DesignFuncDataModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncDataService;
import com.woshidaniu.freemarker.utils.FormatUtils;

/**
 * 
 *@类名称: DesignFuncServiceImpl.java
 *@类描述：功能页面:功能设计操作SERVICE接口实现：为指定功能代码+操作代码维护操作功能数据
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:41:06
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designFuncDataService")
public class DesignFuncDataServiceImpl extends BaseServiceImpl<DesignFuncDataModel, IDesignFuncDataDao> implements IDesignFuncDataService {
	
	@Resource
	private IDesignFuncElementWidgetDao widgetDao;
	@Resource
	protected IDesignFuncDataDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public List<BaseMap> getPagedFuncDataList(DesignFuncDataModel model) {
		//查询组件信息
		DesignFuncElementWidgetModel  widgetModel = new DesignFuncElementWidgetModel();
		widgetModel.setElement_guid(model.getElement_guid());
		widgetModel.setFunc_widget_guid(model.getFunc_widget_guid());
		DesignFuncElementWidgetModel  elementWidgetModel = getWidgetDao().getModel(widgetModel);
		if(BlankUtils.isBlank(elementWidgetModel) || BlankUtils.isBlank(elementWidgetModel.getWidget_sql())){
			log.error("查询SQL为空，请设置查询SQL!");
			return null;
		}
		Map<String,Object> rootMap = model.getParamMap() == null ? new HashMap<String, Object>() : model.getParamMap() ;
		//数据查询SQL
		String data_sql = null;
		try {
			data_sql = FormatUtils.toTextStatic(rootMap,elementWidgetModel.getWidget_guid(), elementWidgetModel.getWidget_sql());
		} catch (Exception e) {
			log.error("查询SQL格式化异常，请遵循正确格式编写判断条件",e);
			return null;
		}
		if(!BlankUtils.isBlank(data_sql)){
			//判断有无SQL注入
			if(SQLInjectionUtils.hasSQLInjection("exec|insert|delete|update|declare", data_sql.trim())){
				log.error("查询SQL中不可包含exec|insert|delete|update|declare语句!");
				return null;
			}
			model.setData_sql(data_sql);
			if("1".equalsIgnoreCase(elementWidgetModel.getWidget_pageable())){
				return dao.getPagedFuncDataByScope(model);
			}else{
				List<BaseMap> list = dao.getFuncDataByScope(model);
				if(!BlankUtils.isBlank(list)){
					QueryModel pageModel = model.getQueryModel();
					if(pageModel==null){
						pageModel = new QueryModel();
						model.setQueryModel(pageModel);
					}
					pageModel.setTotalResult(list.size());
				}
				return list;
			}
		}else{
			log.error("查询SQL为空，请设置查询SQL!");
			return null;
		}
	}
 
	public List<BaseMap> getFuncDataList(DesignFuncDataModel model) {
		//查询组件信息.getFunc_widget_guid()
		DesignFuncElementWidgetModel  widgetModel = new DesignFuncElementWidgetModel();
		widgetModel.setElement_guid(model.getElement_guid());
		widgetModel.setFunc_widget_guid(model.getFunc_widget_guid());
		
		DesignFuncElementWidgetModel  elementWidgetModel = getWidgetDao().getModel(widgetModel);
		if(BlankUtils.isBlank(elementWidgetModel) || BlankUtils.isBlank(elementWidgetModel.getWidget_sql())){
			log.error("查询SQL为空，请设置查询SQL!");
			return null;
		}
		
		Map<String,Object> rootMap = model.getParamMap() == null ? new HashMap<String, Object>() : model.getParamMap() ;
		//数据查询SQL
		String data_sql = null;
		try {
			data_sql = FormatUtils.toTextStatic(rootMap,elementWidgetModel.getWidget_guid(), elementWidgetModel.getWidget_sql());
		} catch (Exception e) {
			log.error("查询SQL格式化异常，请遵循正确格式编写判断条件",e);
			return null;
		}
		if(!BlankUtils.isBlank(data_sql)){
			//判断有无SQL注入
			if(SQLInjectionUtils.hasSQLInjection("exec|insert|delete|update|declare", data_sql.trim())){
				log.error("查询SQL中不可包含exec|insert|delete|update|declare语句!");
				return null;
			}
			model.setData_sql(data_sql);
			return dao.getFuncDataByScope(model);
		}else{
			log.error("查询SQL为空，请设置查询SQL!");
			return null;
		}
	}
	
	public List<BaseMap> getReportDataList(DesignFuncDataModel model,List<PairModel> jqGridColumnList){
		//查询组件信息.getFunc_widget_guid()
		DesignFuncElementWidgetModel  widgetModel = new DesignFuncElementWidgetModel();
		widgetModel.setFunc_widget_guid(model.getFunc_widget_guid());
		
		DesignFuncElementWidgetModel  elementWidgetModel = getWidgetDao().getModel(widgetModel);
		if(BlankUtils.isBlank(elementWidgetModel) || BlankUtils.isBlank(elementWidgetModel.getWidget_sql())){
			log.error("查询SQL为空，请设置查询SQL!");
			return null;
		}
		
		Map<String,Object> rootMap = model.getParamMap() == null ? new HashMap<String, Object>() : model.getParamMap() ;
		//数据查询SQL
		String data_sql = null;
		try {
			data_sql = FormatUtils.toTextStatic(rootMap,elementWidgetModel.getWidget_guid(), elementWidgetModel.getWidget_sql());
		} catch (Exception e) {
			log.error("查询SQL格式化异常，请遵循正确格式编写判断条件",e);
			return null;
		}
		if(!BlankUtils.isBlank(data_sql)){
			//判断有无SQL注入
			if(SQLInjectionUtils.hasSQLInjection("exec|insert|delete|update|declare", data_sql.trim())){
				log.error("查询SQL中不可包含exec|insert|delete|update|declare语句!");
				return null;
			}
			StringBuilder builder = new StringBuilder("select  ");
			for (PairModel pairModel : jqGridColumnList) {
				builder.append(pairModel.getKey()).append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" from ( ").append(data_sql).append(" )" );
			model.setData_sql(builder.toString());
			return dao.getFuncDataByScope(model);
		}else{
			log.error("查询SQL为空，请设置查询SQL!");
			return null;
		}
	}
	
	public List<BaseMap> getFuncDataListForTest(DesignFuncDataModel model){
		if(!BlankUtils.isBlank(model.getData_sql())){
			//判断有无SQL注入
			if(SQLInjectionUtils.hasSQLInjection("exec|insert|delete|update|declare", model.getData_sql().trim())){
				log.error("查询SQL中不可包含exec|insert|delete|update|declare语句!");
				return null;
			}
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("select 'x' from ( ").append(model.getData_sql()).append(" ) x where 1 = 2 " );
			
			model.setData_sql(builder.toString());
			return dao.getFuncDataByScope(model);
		}else{
			log.error("查询SQL为空，请设置查询SQL!");
			return null;
		}
	}

	public IDesignFuncElementWidgetDao getWidgetDao() {
		return widgetDao;
	}

	public void setWidgetDao(IDesignFuncElementWidgetDao widgetDao) {
		this.widgetDao = widgetDao;
	}

	
}
