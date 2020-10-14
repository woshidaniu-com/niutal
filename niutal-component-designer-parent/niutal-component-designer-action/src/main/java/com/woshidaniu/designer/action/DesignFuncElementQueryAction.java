package com.woshidaniu.designer.action;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.service.svcinterface.IBaseQueryFieldService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementFieldService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementQueryService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementWidgetService;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.service.common.ICommonSqlService;

/**
 * 
 *@类名称: DesignFuncElementQueryAction.java
 *@类描述：功能查询页面字段action
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午03:10:21
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementQueryAction extends BaseAction implements ModelDriven<DesignFuncElementQueryModel> {

	protected DesignFuncElementQueryModel model = new DesignFuncElementQueryModel();
	protected DesignFuncElementModel elementModel = new DesignFuncElementModel();
	protected DesignFuncElementWidgetModel widgetModel = new DesignFuncElementWidgetModel();
	protected DesignFuncElementQueryFieldModel queryFieldModel = new DesignFuncElementQueryFieldModel();
	@Resource(name="designFuncElementQueryService")
	protected IDesignFuncElementQueryService service;
	@Resource(name="designFuncElementService")
	protected IDesignFuncElementService elementService;
	@Resource
	protected IBaseQueryFieldService baseFieldService;
	@Resource(name="designFuncElementFieldService")
	protected IDesignFuncElementFieldService fieldService;
	@Resource
	protected IDesignFuncElementWidgetService widgetService;
	/** 公共service */
	@Resource
	private ICommonSqlService commonSqlService;

	/**
	 * 
	 *@描述：跳转至【查询字段设计】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-20下午03:56:44
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:cxFuncElementWidgetIndex
	 */
	public String cxFuncElementQueryIndex() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getFuncElementQueryFieldList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			} else {
				String report_guid = model.getReport_guid();
				// 扩展参数
				try {
					// 查询query_guid对应的DesignFuncElementQueryFieldModel对象
					DesignFuncElementQueryModel queryModel = getService().getModel(model);
					if (queryModel != null) {
						BeanUtils.copyProperties(model, queryModel);
					}

				} catch (Exception e) {
					logStackException(e);
				}
				model.setReport_guid(report_guid);
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String xgFuncElementQueryData(){
		try {
			getService().update(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"查询条件编辑"}));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"查询条件编辑"}));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：解析SQL并返回对应的列信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-13下午03:11:26
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxSQLParserColumnIndex() {
		try {
			//判断报表ID是否为空，以便区别编辑的功能是自定义报表还是自定义功能
			if(BlankUtils.isBlank(model.getReport_guid())){
				getValueStack().set(DATA,getService().getSQLParserColumnList(model,true));
			}else{
				getValueStack().set(DATA,getService().getReportParserColumnList(model,true));
			}
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, new ArrayList<Map<String, String>>());
		}
		return DATA;
	}

	public String cxFuncElementQueryMoreParams(){
		getValueStack().set("queryFieldModel", queryFieldModel);
		return SUCCESS;
	}
	
	public String cxFuncElementQueryBaseElement(){
		try {
			if (QUERY.equals(model.getDoType()) ) {
				//设置功能模块代码，操作代码到request中
				String func_code = getRequest().getParameter("gnmkdmKey");
				func_code = BlankUtils.isBlank(func_code) ?  getRequest().getParameter("gnmkdm") : func_code;
				QueryModel queryModel = model.getQueryModel();
				if( !BlankUtils.isBlank(func_code)){
					queryModel.setItems(baseFieldService.getFuncModelList(func_code));
				}else{
					queryModel.setItems(new ArrayList<Map<String,String>>());
				}
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	@Override
	public DesignFuncElementQueryModel getModel() {
		return model;
	}

	public DesignFuncElementModel getElementModel() {
		return elementModel;
	}

	public void setElementModel(DesignFuncElementModel elementModel) {
		this.elementModel = elementModel;
	}

	public DesignFuncElementWidgetModel getWidgetModel() {
		return widgetModel;
	}

	public void setWidgetModel(DesignFuncElementWidgetModel widgetModel) {
		this.widgetModel = widgetModel;
	}

	public IDesignFuncElementQueryService getService() {
		return service;
	}

	public void setService(IDesignFuncElementQueryService service) {
		this.service = service;
	}

	public IDesignFuncElementService getElementService() {
		return elementService;
	}

	public void setElementService(IDesignFuncElementService elementService) {
		this.elementService = elementService;
	}

	public IDesignFuncElementFieldService getFieldService() {
		return fieldService;
	}

	public void setFieldService(IDesignFuncElementFieldService fieldService) {
		this.fieldService = fieldService;
	}

	public IDesignFuncElementWidgetService getWidgetService() {
		return widgetService;
	}

	public void setWidgetService(IDesignFuncElementWidgetService widgetService) {
		this.widgetService = widgetService;
	}

	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}

	public void setModel(DesignFuncElementQueryModel model) {
		this.model = model;
	}

	public DesignFuncElementQueryFieldModel getQueryFieldModel() {
		return queryFieldModel;
	}

	public void setQueryFieldModel(DesignFuncElementQueryFieldModel queryFieldModel) {
		this.queryFieldModel = queryFieldModel;
	}

	public IBaseQueryFieldService getBaseFieldService() {
		return baseFieldService;
	}

	public void setBaseFieldService(IBaseQueryFieldService baseFieldService) {
		this.baseFieldService = baseFieldService;
	}
	
}
