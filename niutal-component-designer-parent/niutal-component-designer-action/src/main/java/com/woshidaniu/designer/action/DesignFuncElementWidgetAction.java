package com.woshidaniu.designer.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.BaseWidgetParameterModel;
import com.woshidaniu.designer.dao.entities.DesignFuncDataModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.dao.entities.DesignFuncWidgetJQGridColumnModel;
import com.woshidaniu.designer.service.svcinterface.IBaseWidgetParameterService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncDataService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementWidgetService;
import com.woshidaniu.designer.utils.FuncDataUtils;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.freemarker.utils.FormatUtils;

/**
 * 
 *@类名称: DesignFuncElementWidgetAction.java
 *@类描述：功能页面:组件初始化参数信息action
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午04:08:05
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementWidgetAction extends BaseAction implements ModelDriven<DesignFuncElementWidgetModel>  { 

	protected DesignFuncElementWidgetModel model = new DesignFuncElementWidgetModel();
	protected DesignFuncWidgetJQGridColumnModel jqgridModel = new DesignFuncWidgetJQGridColumnModel();
	protected DesignFuncDataModel dataModel = new DesignFuncDataModel();
	protected List<BaseWidgetParameterModel> parameterList = null;
	@Resource(name="designFuncElementWidgetService")
	protected IDesignFuncElementWidgetService service;
	@Resource
	protected IBaseWidgetParameterService baseWidgetParameterService;
	@Resource(name="designFuncDataService")
	protected IDesignFuncDataService funcDataService;
	/**
	 * 
	 *@描述：跳转至【组件初始化参数】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午04:10:16
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxFuncElementWidgetIndex(){
		try {
			DesignFuncElementWidgetModel eWidgetModel = getService().getModel(model);
			if(eWidgetModel == null){
				
			}else{
				if (QUERY.equals(model.getDoType())) {
					QueryModel queryModel = model.getQueryModel();
					queryModel.setItems(getService().getJQGridColumnList(eWidgetModel));
					getValueStack().set(DATA, queryModel);
					return DATA;
				}else{
					if(eWidgetModel.getWidget_name().toLowerCase().indexOf("jqgrid") > -1){
						getValueStack().set("widget_name","jqgrid");
					}
					//扩展参数
					//getValueStack().set("parameterList",getBaseWidgetParameterService().getWidgetParameterList(eWidgetModel.getWidget_guid()));
					try {
						BeanUtils.copyProperties(model, eWidgetModel);
					} catch (Exception e) {
						logStackException(e);
					}
				}
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-13下午03:25:52
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxSQLParserColumnIndex(){
		QueryModel queryModel = model.getQueryModel();
		queryModel.setItems(getService().getSQLParserColumnList(model,true));
		getValueStack().set(DATA, queryModel);
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
	public String cxParserColumnWhithSQL(){
		try {
			if(!BlankUtils.isBlank(model.getWidget_sql())){
				String widget_sql = URLDecoder.decode(model.getWidget_sql(), "utf-8");
				//如果组件SQL不为空，则使用Freemarker进行去除条件处理
				if(!BlankUtils.isBlank(widget_sql)){
					Map<String,Object> paramMap = dataModel.getParamMap() == null ? new HashMap<String, Object>() : dataModel.getParamMap() ;
					paramMap.put("user", new User());
					String data_sql = FormatUtils.toTextStatic(paramMap,model.getFunc_widget_guid(), widget_sql);
					try {
						getValueStack().set(DATA, FuncDataUtils.getDataColumnList(data_sql));
					} catch (Throwable e) {
						logStackException(e);
						getValueStack().set(DATA,"SQL无法正确被解析,请检查标点符号或者语法是否正确!");
					}
					//执行SQl进行测试
					try {
						dataModel.setData_sql(data_sql);
						dataModel.setParamMap(paramMap);
						getFuncDataService().getFuncDataListForTest(dataModel);
					} catch (Throwable e) {
						logStackException(e);
						getValueStack().set(DATA,"SQL语法错误:" + e.getCause().getMessage());
					} 
				}
			}else{
				getValueStack().set(DATA,new ArrayList<Map<String,String>>());
			}
		} catch (Throwable e) {
			logStackException(e);
			getValueStack().set(DATA,"SQL无法正确被解析,请检查标点符号或者语法是否正确!");
		}
		return DATA;
	}
	
	public String cxFuncElementWidgetMoreParams(){
		getValueStack().set("jqgridModel", jqgridModel);
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至【组件初始化参数】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午04:10:21
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjFuncElementWidget(){
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21上午11:56:10
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjFuncElementWidgetData(){
		try {
			getService().insert(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础字段信息新增"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础字段信息新增"}));
		}
		return DATA;
	}
 
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21上午11:56:10
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgFuncElementWidgetData(){
		try {
			if(!BlankUtils.isBlank(model.getWidget_sql())){
				model.setWidget_sql(URLDecoder.decode(model.getWidget_sql(), "utf-8"));
			}
			getService().update(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"功能组件设置"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"功能组件设置"}));
		}
		return DATA;
	}

	 
	@Override
	public DesignFuncElementWidgetModel getModel() {
		return model;
	}

	public IDesignFuncElementWidgetService getService() {
		return service;
	}

	public void setService(IDesignFuncElementWidgetService service) {
		this.service = service;
	}

	public DesignFuncWidgetJQGridColumnModel getJqgridModel() {
		return jqgridModel;
	}

	public void setJqgridModel(DesignFuncWidgetJQGridColumnModel jqgridModel) {
		this.jqgridModel = jqgridModel;
	}

	public void setModel(DesignFuncElementWidgetModel model) {
		this.model = model;
	}

	public IBaseWidgetParameterService getBaseWidgetParameterService() {
		return baseWidgetParameterService;
	}

	public void setBaseWidgetParameterService(
			IBaseWidgetParameterService baseWidgetParameterService) {
		this.baseWidgetParameterService = baseWidgetParameterService;
	}

	public IDesignFuncDataService getFuncDataService() {
		return funcDataService;
	}

	public void setFuncDataService(IDesignFuncDataService funcDataService) {
		this.funcDataService = funcDataService;
	}
	
	
}

