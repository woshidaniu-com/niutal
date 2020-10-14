package com.woshidaniu.designer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import com.fr.base.FRContext;
import com.fr.base.Parameter;
import com.fr.dav.LocalEnv;
import com.fr.io.TemplateWorkBookIO;
import com.fr.main.TemplateWorkBook;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.db.core.utils.JSQLParserUtils;
import com.woshidaniu.designer.dao.daointerface.IBaseReportDetailDao;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementQueryDao;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementWidgetDao;
import com.woshidaniu.designer.dao.entities.BaseReportDetailModel;
import com.woshidaniu.designer.dao.entities.DesignAutoCompleteFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementQueryService;
import com.woshidaniu.designer.utils.FuncCacheKeyUtils;
import com.woshidaniu.designer.utils.FuncDataUtils;
import com.woshidaniu.designer.utils.FuncSQLUtils;
import com.woshidaniu.designer.utils.ReportParserUtils;
import com.woshidaniu.freemarker.utils.FormatUtils;
import com.woshidaniu.util.file.DirectoryUtils;
import com.woshidaniu.web.context.WebContext;

/**
 * 
 *@类名称:DesignQueryFieldServiceImpl.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午03:02:17
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designFuncElementQueryService")
public class DesignFuncElementQueryServiceImpl extends BaseServiceImpl<DesignFuncElementQueryModel, IDesignFuncElementQueryDao> implements IDesignFuncElementQueryService {
	
	@Resource
	private IDesignFuncElementWidgetDao widgetDao;
	@Resource
	private IBaseReportDetailDao reportDetailDao;
	@Resource
	protected IDesignFuncElementQueryDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	@Comment
	public boolean insert(DesignFuncElementQueryModel model) {
		dao.insert(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean delete(DesignFuncElementQueryModel model) {
		dao.delete(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean update(DesignFuncElementQueryModel model) {
		
		
		List<DesignFuncElementQueryFieldModel> dbFieldList = new ArrayList<DesignFuncElementQueryFieldModel>();
		List<DesignFuncElementQueryFieldModel> removedFieldList = new ArrayList<DesignFuncElementQueryFieldModel>();
		//去除前段传递来的字段为空的对象;防止下面报错
		model.setQuery_field_list(CollectionUtils.killNull(model.getQuery_field_list()));
		//根据查询区域对象查询该区域关联查询条件集合
		List<DesignFuncElementQueryFieldModel> queryFieldList = dao.getFuncElementQueryFieldList2(model);
		//判断查询条件不为空
		if( !BlankUtils.isBlank(model.getQuery_field_list()) && !BlankUtils.isBlank(queryFieldList)){
			//删除移除的查询条件
			for (DesignFuncElementQueryFieldModel oldElement : queryFieldList) {
				boolean isHas = false;
				for (DesignFuncElementQueryFieldModel newElement : model.getQuery_field_list()) {
					newElement.setQuery_guid(model.getQuery_guid());
					if(!BlankUtils.isBlank(newElement.getTable_guid())  && !newElement.getTable_guid().startsWith("field") &&  oldElement.getTable_guid().equals(newElement.getTable_guid())){
						isHas = true;
						break;
					}
				}
				//历史查询条件被删除
				if(!isHas){
					dao.deleteQueryField(oldElement);
					removedFieldList.add(oldElement);
				}
			}
			//添加和更新新的查询条件
			for (DesignFuncElementQueryFieldModel newElement : model.getQuery_field_list()) {
				newElement.setQuery_guid(model.getQuery_guid());
				boolean isHas = false;
				if(!BlankUtils.isBlank(newElement.getTable_guid()) && !newElement.getTable_guid().startsWith("field") ){
					for (DesignFuncElementQueryFieldModel oldElement : queryFieldList) {
						if( newElement.getTable_guid().equals(oldElement.getTable_guid())){
							isHas = true;
							break;
						}
					}
				}
				//历史查询条件更新
				if(isHas){
					dao.updateQueryField(newElement);
					dbFieldList.add(newElement);
				}else{
					dao.insertQueryField(newElement);
					dbFieldList.add(newElement);
				}
			}
		}else if( !BlankUtils.isBlank(model.getQuery_field_list()) && BlankUtils.isBlank(queryFieldList)){
			//添加和更新新的查询条件
			for (DesignFuncElementQueryFieldModel newElement : model.getQuery_field_list()) {
				newElement.setQuery_guid(model.getQuery_guid());
				dao.insertQueryField(newElement);
				dbFieldList.add(newElement);
			}
		}else if(!BlankUtils.isBlank(queryFieldList)){
			//没有查询条件设置,且有历史资源，则删除历史查询条件
			for (DesignFuncElementQueryFieldModel oldElement : queryFieldList) {
				dao.deleteQueryField(oldElement);
				removedFieldList.add(oldElement);
			}
		}
		//查询关联的数据展示组件：如JQGrid组件
		if (!BlankUtils.isBlank(model.getElement_related_guid())) {
			DesignFuncElementWidgetModel widgetModel = new DesignFuncElementWidgetModel();
			// 获取查询区域关联组件对象
			widgetModel.setElement_guid(model.getElement_related_guid());
			DesignFuncElementWidgetModel eWidgetModel = widgetDao.getModel(widgetModel);
			// 如果组件SQL不为空，则使用Freemarker进行去除条件处理
			if (!BlankUtils.isBlank(eWidgetModel) && !BlankUtils.isBlank(eWidgetModel.getWidget_sql())) {
				//先移除已经去除的条件
				for (DesignFuncElementQueryFieldModel removedField : removedFieldList) {
					eWidgetModel.setWidget_sql(FuncSQLUtils.removeTerm(eWidgetModel.getWidget_sql(), removedField.getField_name()));
				}
				StringBuffer buffer = new StringBuffer(eWidgetModel.getWidget_sql());
				//追加新的条件
				for (DesignFuncElementQueryFieldModel newField : dbFieldList) {
					//判断该字段是否已有条件
					if(!FuncSQLUtils.isHasTerm(eWidgetModel.getWidget_sql(), newField.getField_name())){
						buffer.append(FuncSQLUtils.buildTerm(newField.getField_name(),newField.getField_filtertype() ,newField.getField_alias()));
					}
				}
				eWidgetModel.setWidget_sql(buffer.toString().trim());
				widgetDao.updateWidgetSQL(eWidgetModel);
			}
		}
		dao.update(model);
		
		if( isCacheSupport() ){
			Cache cache = this.getCache();
			String autoKey1 = FuncCacheKeyUtils.getCacheKey(model.getFunc_code(),model.getOpt_code(),"func-element-querys");
			cache.evict(autoKey1);
		}
		return true;
	}
	
	@Override
	public DesignFuncElementQueryModel getModel(DesignFuncElementQueryModel model) {
		DesignFuncElementQueryModel queryModel = dao.getModel(model);
		/*if(BlankUtils.isBlank(queryModel)){
			queryModel.setQuery_field_list(dao.getFuncElementQueryFieldList(queryModel));
		}*/
		return queryModel;
	}
	
	@Override
	public List<DesignFuncElementQueryFieldModel> getFuncElementQueryFieldList(DesignFuncElementQueryModel model){
		return dao.getFuncElementQueryFieldList2(model);
	}

	@Override
	public List<DesignFuncElementQueryModel> getFuncElementQueryList(String func_code,String opt_code) {
		List<DesignFuncElementQueryModel>  querys = dao.getFuncElementQueryList(func_code, opt_code);
		//查询条件元素不为空
		if(!BlankUtils.isBlank(querys)){
			/**
			 * 第1步：查询匹配的查询字段
			 */
			//查询功能所有的查询条件字段信息
			List<DesignFuncElementQueryFieldModel>  queryFieldList = dao.getFuncElementQueryFieldList(func_code, opt_code);
			//检查不为空
			if(!BlankUtils.isBlank(queryFieldList)){
				/**
				 * 第2步：匹配字段完成字段属性
				 */
				//查询功能所有自动完成字段信息
				List<DesignAutoCompleteFieldModel>  field_auto_list = dao.getDesignAutoFieldList(func_code, opt_code);
				//检查不为空
				if(!BlankUtils.isBlank(field_auto_list)){
					//循环查询条件字段
					for (DesignFuncElementQueryFieldModel fieldModel : queryFieldList) {
						//循环自动完成字段
						for (DesignAutoCompleteFieldModel autoFieldModel : field_auto_list) {
							//比较关联字段
							if(fieldModel.getTable_guid().equals(autoFieldModel.getTarget_guid())){
								fieldModel.setField_auto(autoFieldModel);
								break;
							}
						}
					}
				}
				/**
				 * 第3步：匹配查询条件
				 */
				List<DesignFuncElementQueryFieldModel>  queryFieldListTmp = null;
				//循环查询条件元素
				for (DesignFuncElementQueryModel queryModel : querys) {
					queryFieldListTmp = new ArrayList<DesignFuncElementQueryFieldModel>();
					//循环查询条件字段
					for (DesignFuncElementQueryFieldModel queryFieldModel : queryFieldList) {
						//比较关联字段
						if(queryModel.getQuery_guid().equals(queryFieldModel.getQuery_guid())){
							queryFieldListTmp.add(queryFieldModel);
						}
					}
					queryModel.setQuery_field_list(queryFieldListTmp);
				}
			}
		}
		return querys;
	}
	
	@Override
	public List<Map<String,String>> getSQLParserColumnList(DesignFuncElementQueryModel model,boolean isFilter){
		
		if (!BlankUtils.isBlank(model.getElement_related_guid())) {
			DesignFuncElementWidgetModel widgetModel = new DesignFuncElementWidgetModel();
			// 获取查询区域关联组件对象
			widgetModel.setElement_guid(model.getElement_related_guid());
			DesignFuncElementWidgetModel eWidgetModel = widgetDao.getModel(widgetModel);
			// 如果组件SQL不为空，则使用Freemarker进行去除条件处理
			if (!BlankUtils.isBlank(eWidgetModel) && !BlankUtils.isBlank(eWidgetModel.getWidget_sql())) {
				//如果组件SQL不为空，则使用Freemarker进行去除条件处理
				if(!BlankUtils.isBlank(eWidgetModel.getWidget_sql())){
					try {
						String data_sql = FormatUtils.toTextStatic(null,eWidgetModel.getFunc_widget_guid(), eWidgetModel.getWidget_sql());
						//解析查询列
						List<String> selectItems =  JSQLParserUtils.getSelectItems(data_sql);
						//解析查询表名称
						List<String> selectTables =  JSQLParserUtils.getTables(data_sql);
						
						//根据解析出的SQL中使用的查询表名称，得到不重复的字段描述信息
						eWidgetModel.setQueryList(selectTables);
						List<BaseMap>  tableColumnList = widgetDao.getSQLParserColumnList(eWidgetModel);
						if(isFilter){
							//查询已设置条件集合
							List<String> columnList = dao.getQueryFieldNameList(model);
							if(BlankUtils.isBlank(columnList)){
								return FuncDataUtils.getDataColumnList(selectItems,tableColumnList);
							}else{
								return FuncDataUtils.getDataColumnList(selectItems,tableColumnList,columnList.toArray(new String[columnList.size()]));
							}
						}else{
							return FuncDataUtils.getDataColumnList(selectItems,tableColumnList);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				return new ArrayList<Map<String, String>>();
			}
		} else {
			return new ArrayList<Map<String, String>>();
		}
		return new ArrayList<Map<String,String>>();
	}
	
	@Override
	public List<Map<String,String>> getReportParserColumnList(DesignFuncElementQueryModel model,boolean isFilter){
		BaseReportDetailModel detailModel = reportDetailDao.getReportDetailModel(model.getReport_guid());
		if(!BlankUtils.isBlank(detailModel) && !BlankUtils.isBlank(detailModel.getReport_alias())){
			try {
				//应用程序根目录
				String reportletPath = WebContext.getServletContext().getRealPath(DirectoryUtils.getResolvePath("/WEB-INF/reportlets"));
				//动态生成的静态文件存储目录
				DirectoryUtils.getExistDir(reportletPath);
				//获得报表文件
				FRContext.setCurrentEnv(new LocalEnv(reportletPath));
				TemplateWorkBook workbook = TemplateWorkBookIO.readTemplateWorkBook(FRContext.getCurrentEnv(), detailModel.getReport_alias() + ".cpt" );
				//读取报表参数
				Parameter[] paras = ReportParserUtils.uniqueParameters(workbook.getParameters()) ;
				if(isFilter){
					//查询已设置条件集合
					List<String> columnList = dao.getQueryFieldNameList(model);
					if(BlankUtils.isBlank(columnList)){
						return ReportParserUtils.getDataColumnList(paras);
					}else{
						return ReportParserUtils.getDataColumnList(paras,columnList.toArray(new String[columnList.size()]));
					}
				}else{
					return ReportParserUtils.getDataColumnList(paras);
				}
				
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}else{
			return new ArrayList<Map<String, String>>();
		}
		return new ArrayList<Map<String, String>>();
	}
	
	@Override
	public List<BaseMap> getFieldDataList(String querySQL){
		return dao.getFieldDataListByScope(querySQL);
	}

	public IDesignFuncElementWidgetDao getWidgetDao() {
		return widgetDao;
	}

	public void setWidgetDao(IDesignFuncElementWidgetDao widgetDao) {
		this.widgetDao = widgetDao;
	}

	public IBaseReportDetailDao getReportDetailDao() {
		return reportDetailDao;
	}

	public void setReportDetailDao(IBaseReportDetailDao reportDetailDao) {
		this.reportDetailDao = reportDetailDao;
	}
	
}
