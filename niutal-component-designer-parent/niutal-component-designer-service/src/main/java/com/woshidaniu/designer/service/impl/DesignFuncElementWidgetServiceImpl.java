package com.woshidaniu.designer.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.db.core.utils.JSQLParserUtils;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncElementWidgetDao;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.dao.entities.DesignFuncWidgetJQGridColumnModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementWidgetService;
import com.woshidaniu.designer.utils.FuncDataUtils;
import com.woshidaniu.freemarker.utils.FormatUtils;

/**
 * 
 *@类名称: DesignFuncElementWidgetServiceImpl.javao
 *@类描述：功能页面：元素关联的组件初始化参数信息操作SERVICE接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午03:55:140
 *@修改人：o
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N03",business="N0320")
@Service("designFuncElementWidgetService")
public class DesignFuncElementWidgetServiceImpl extends BaseServiceImpl<DesignFuncElementWidgetModel, IDesignFuncElementWidgetDao> implements IDesignFuncElementWidgetService {
	
	@Resource
	protected IDesignFuncElementWidgetDao dao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	@Comment
	public boolean insert(DesignFuncElementWidgetModel model) {
		dao.insert(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean delete(DesignFuncElementWidgetModel model) {
		dao.delete(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean update(DesignFuncElementWidgetModel model) {
		//根据功能组件ID查询 JQGrid组件参数
		List<DesignFuncWidgetJQGridColumnModel> columnList = dao.getFuncJQGridColumnList(model);
		//去除前段传递来的字段为空的对象;防止下面报错
		model.setColumnList(CollectionUtils.killNull(model.getColumnList()));
		//判断JQGrid组件参数不为空
		if( !BlankUtils.isBlank(model.getColumnList()) && !BlankUtils.isBlank(columnList)){
			//删除移除的JQGrid组件参数
			for (DesignFuncWidgetJQGridColumnModel oldElement : columnList) {
				boolean isHas = false;
				for (DesignFuncWidgetJQGridColumnModel newElement : model.getColumnList()) {
					newElement.setFunc_widget_guid(model.getFunc_widget_guid());
					if(!BlankUtils.isBlank(newElement.getField_guid())  && !newElement.getField_guid().startsWith("field") &&  oldElement.getField_guid().equals(newElement.getField_guid())){
						isHas = true;
						break;
					}
				}
				//历史JQGrid组件参数被删除
				if(!isHas){
					dao.deleteJQGridColumn(oldElement);
				}
			}
			//添加和更新新的JQGrid组件参数
			for (DesignFuncWidgetJQGridColumnModel newElement : model.getColumnList()) {
				newElement.setFunc_widget_guid(model.getFunc_widget_guid());
				boolean isHas = false;
				if(!BlankUtils.isBlank(newElement.getField_guid()) && !newElement.getField_guid().startsWith("field") ){
					for (DesignFuncWidgetJQGridColumnModel oldElement : columnList) {
						if( newElement.getField_guid().equals(oldElement.getField_guid())){
							isHas = true;
							break;
						}
					}
				}
				newElement.setField_index(newElement.getField_name());
				//历史JQGrid组件参数更新
				if(isHas){
					dao.updateJQGridColumn(newElement);
				}else{
					newElement.setFunc_widget_guid(model.getFunc_widget_guid());
					dao.insertJQGridColumn(newElement);
				}
			}
		}else if( !BlankUtils.isBlank(model.getColumnList()) && BlankUtils.isBlank(columnList)){
			//添加和更新新的JQGrid组件参数
			for (DesignFuncWidgetJQGridColumnModel newElement : model.getColumnList()) {
				newElement.setFunc_widget_guid(model.getFunc_widget_guid());
				newElement.setField_index(newElement.getField_name());
				dao.insertJQGridColumn(newElement);
			}
		}else if(!BlankUtils.isBlank(columnList)){
			//没有JQGrid组件参数设置,且有历史资源，则删除历史JQGrid组件参数
			for (DesignFuncWidgetJQGridColumnModel oldElement : columnList) {
				dao.deleteJQGridColumn(oldElement);
			}
		}
		model.setWidget_params("{}");
		dao.update(model);
		return true;
	}
	
	@Override
	public List<DesignFuncElementWidgetModel> getFuncElementWidgetList(String func_code, String opt_code) {
		return dao.getFuncElementWidgetList(func_code, opt_code);
	}
	
	@Override
	public List<DesignFuncWidgetJQGridColumnModel> getJQGridColumnList(String func_code,String opt_code) {
		return dao.getJQGridColumnList(func_code, opt_code);
	}
	
	@Override
	public List<PairModel> getReportJQGridColumnList(String funcWidgetGuid) {
		return dao.getReportJQGridColumnList(funcWidgetGuid);
	}
	
	@Override
	public List<DesignFuncWidgetJQGridColumnModel> getJQGridColumnList(DesignFuncElementWidgetModel model){
		List<DesignFuncWidgetJQGridColumnModel> columnList = dao.getFuncJQGridColumnList(model);
		//判断有无设置过jqgrid列信息
		if(!BlankUtils.isBlank(columnList)){
			try {
				//如果组件SQL不为空，则使用Freemarker进行去除条件处理
				String data_sql = null;
				if(!BlankUtils.isBlank(model.getWidget_sql()) ){
					try {
						data_sql = FormatUtils.toTextStatic(null,model.getFunc_widget_guid(), model.getWidget_sql());
					} catch (Exception e) {
					}
				}else if(BlankUtils.isBlank(model.getWidget_data_url()) ){
					DesignFuncElementWidgetModel widgetModel = getDao().getModel(model);
					if(!BlankUtils.isBlank(widgetModel.getWidget_sql()) ){
						try {
							data_sql = FormatUtils.toTextStatic(null,widgetModel.getFunc_widget_guid(), widgetModel.getWidget_sql());
						} catch (Exception e) {
						}
					}
				}
				//解析组件SQL,并对jqgrid列信息进行是否已经移除的标记
				List<String> selectItems =  JSQLParserUtils.getSelectItems(data_sql);
				if(!BlankUtils.isBlank(selectItems)){
					for (DesignFuncWidgetJQGridColumnModel columnModel : columnList) {
						if(selectItems.contains(columnModel.getField_name())){
							columnModel.setField_removed("0");
						}else{
							columnModel.setField_removed("1");
						}
					}
				}
			} catch (Exception e) {
			}
		}
		return columnList;
	}
	
	@Override
	public List<String> getFuncJQGridColumnNameList(DesignFuncElementWidgetModel model){
		return dao.getFuncJQGridColumnNameList(model);
	}

	@Override
	@Comment
	public boolean deleteJQGridColumn(DesignFuncWidgetJQGridColumnModel model) {
		dao.deleteJQGridColumn(model);
		return true;
	}

	@Override
	@Comment
	public boolean insertJQGridColumn(DesignFuncWidgetJQGridColumnModel model) {
		dao.insertJQGridColumn(model);
		return true;
	}

	@Override
	@Comment
	public boolean updateJQGridColumn(DesignFuncWidgetJQGridColumnModel model) {
		dao.updateJQGridColumn(model);
		return true;
	}

	@Override
	public List<BaseMap> getWidgetDataList(Map<String, Object> params) {
		
		/*String func_code = params.get("func_code").toString();
		String opt_code = params.get("opt_code").toString();
		*/
		return dao.getPagedWidgetDataListByScope("");
	}
	
	@Override
	public List<Map<String,String>> getSQLParserColumnList(DesignFuncElementWidgetModel model,boolean isFilter){
		if(!BlankUtils.isBlank(model.getWidget_sql())){
			try {
				String widget_sql = URLDecoder.decode(model.getWidget_sql(), "utf-8");
				//如果组件SQL不为空，则使用Freemarker进行去除条件处理
				String data_sql = FormatUtils.toTextStatic(null,model.getFunc_widget_guid(), widget_sql);
				//解析查询列
				List<String> selectItems =  JSQLParserUtils.getSelectItems(data_sql);
				//解析查询表名称
				List<String> selectTables =  JSQLParserUtils.getTables(data_sql);
				//根据解析出的SQL中使用的查询表名称，得到不重复的字段描述信息
				model.setQueryList(selectTables);
				List<BaseMap>  tableColumnList = dao.getSQLParserColumnList(model);
				
				if(isFilter){
					//查询已设置字段集合
					List<String> columnList = dao.getFuncJQGridColumnNameList(model);
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
		}else{
			DesignFuncElementWidgetModel eWidgetModel = dao.getModel(model);
			if(!BlankUtils.isBlank(eWidgetModel) && !BlankUtils.isBlank(eWidgetModel.getWidget_sql())){
				//如果组件SQL不为空，则使用Freemarker进行去除条件处理
				try {
					String data_sql = FormatUtils.toTextStatic(null,eWidgetModel.getFunc_widget_guid(), eWidgetModel.getWidget_sql());
					//解析查询列
					List<String> selectItems =  JSQLParserUtils.getSelectItems(data_sql);
					//解析查询表名称
					List<String> selectTables =  JSQLParserUtils.getTables(data_sql);
					
					//根据解析出的SQL中使用的查询表名称，得到不重复的字段描述信息
					eWidgetModel.setQueryList(selectTables);
					List<BaseMap>  tableColumnList = dao.getSQLParserColumnList(eWidgetModel);
					if(isFilter){
						//查询已设置字段集合
						List<String> columnList = dao.getFuncJQGridColumnNameList(eWidgetModel);
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
		}
		return new ArrayList<Map<String,String>>();
	}

	
	
	
}
