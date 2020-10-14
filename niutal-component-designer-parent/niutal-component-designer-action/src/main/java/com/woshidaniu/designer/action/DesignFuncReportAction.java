package com.woshidaniu.designer.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.MathUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncDataModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuButtonModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignReportModel;
import com.woshidaniu.designer.dao.entities.MapRowModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncDataService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementWidgetService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncService;
import com.woshidaniu.designer.utils.ReportRequestUtils;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.util.request.WebRequestUtils;

/**
 * 
 *@类名称: DesignFuncAction.java
 *@类描述：功能页面:功能设计操作action：为指定功能代码+操作代码维护操作功能数据
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:43:43
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncReportAction extends BaseAction implements ModelDriven<DesignFuncMenuButtonModel> {

	//功能关联功能按钮信息
	protected DesignFuncMenuButtonModel model = new DesignFuncMenuButtonModel();
	//功能关联功能菜单信息
	protected DesignFuncMenuModel menuModel = new DesignFuncMenuModel();
	protected DesignFuncDataModel dataModel = new DesignFuncDataModel();
	@Resource(name="designFuncService")
	protected IDesignFuncService service;
	@Resource
	protected IDesignFuncElementWidgetService widgetService;
	@Resource(name="designFuncDataService")
	protected IDesignFuncDataService dataService;
	
	protected DesignReportModel funcReport = new DesignReportModel();
	protected MapRowModel mapRow = new MapRowModel();
	protected List<MapRowModel> requestList = null;

	//有无选择行记录
	protected String hasSelect;
	
	/**
	 * 
	 *@描述			: 打开帆软报表预览页面
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016-6-28下午03:28:44
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxFineReportViewIndex() {
		try {
			String reportID = getRequest().getParameter("reportID");
			if(!BlankUtils.isBlank(model.getFunc_guid())){
				//查询绑定的功能对象
				DesignFuncModel funcModel = getService().getModel(model);
				if(!BlankUtils.isBlank(funcModel)){
					if( "3".equals(funcModel.getFunc_type())){
						//查询功能关联的报表信息
						funcReport = getService().getFuncReportModel(model.getFunc_guid());
						getValueStack().set("funcReport", funcReport);
						//解析请求的参数WebRequestUtils
						getValueStack().set("requestMap", ReportRequestUtils.getPairParameters( mapRow, requestList, funcModel.getFunc_type()));
					}
					getValueStack().set("funcModel",funcModel);
				}
			}
			else if(!BlankUtils.isBlank(reportID)){
				//查询功能关联的报表信息
				funcReport.setReport_alias(FilenameUtils.getBaseName(reportID));
				getValueStack().set("funcReport", funcReport);
				//解析请求的参数WebRequestUtils
				getValueStack().set("requestMap", ReportRequestUtils.getPairParameters( mapRow, requestList, "3"));
			}
			else{
				getValueStack().set(Result.MESSAGE, "请求中 无 func_guid 参数 !");
				return Result.EX_WARN;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述		: 打开【帆软报表】单报表打印页面
	 *@创建人	: kangzhidong
	 *@创建时间	: 2016-6-29上午09:27:51
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxFineReportBatchOut() {
		try {
			//打印格式：PDF,FLASH,APPLET
			getValueStack().set("format",StringUtils.getSafeStr(getRequest().getParameter("_format"), "APPLET").toUpperCase());
			//判断功能UID不为空
			if(!BlankUtils.isBlank(model.getFunc_guid())){
				//查询绑定的功能对象
				DesignFuncModel funcModel = getService().getModel(model);
				if(!BlankUtils.isBlank(funcModel)){
					//查询功能关联的报表信息
					DesignReportModel funcReport = getService().getFuncReportModel(model.getFunc_guid());
					getValueStack().set("funcReport", funcReport);
					//解析请求的参数WebRequestUtils
					getValueStack().set("requestMap", ReportRequestUtils.getPairParameters( mapRow, requestList, funcModel.getFunc_type()));
					getValueStack().set("funcModel",funcModel);
				}
				
			}else{
				getValueStack().set(Result.MESSAGE, "请求中 无 func_guid 参数 !");
				return Result.EX_WARN;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至批量报表打印页面
	 *@创建人:kangzhidong
	 *@创建时间:Jul 23, 20155:35:34 PM
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	@Deprecated
	public String cxBatchReportParams(){
		//查询功能关联的报表信息
		DesignReportModel funcReport = getService().getFuncReportModel(model.getFunc_guid());
		if(!BlankUtils.isBlank(funcReport)){
			List<Map<String, String>> reportletMapList = new ArrayList<Map<String, String>>();
			Map<String, String> reportletMap = null;
			//无选择行记录;需要根据条件查询数据
			if("0".equals(getHasSelect())){
				
				Map<String, Object> requestMap = WebRequestUtils.getObjectParameters(getRequest());
				Map<String, Object> newRequestMap = new HashMap<String, Object>();
				Object value = null;
				for (String key : requestMap.keySet()) {
					value = requestMap.get(key);
					if ((value instanceof String) && !BlankUtils.isBlank(value.toString())) {
						newRequestMap.put(key, value);
					}else if(value.getClass().isArray() && !BlankUtils.isBlank((Object[])value) ){
						newRequestMap.put(key, value);
					}
				}
				//收集参数
				if(BlankUtils.isBlank(dataModel.getParamMap())){
					dataModel.setParamMap(newRequestMap);
				}else{
					dataModel.getParamMap().putAll(newRequestMap);
				}
				//获取当前组件作为参数的列信息0
				List<PairModel> jqGridColumnList = getWidgetService().getReportJQGridColumnList(dataModel.getFunc_widget_guid());
				if(!BlankUtils.isBlank(jqGridColumnList)){
					//查询指定列的数据
					List<BaseMap> dataList = getDataService().getReportDataList(dataModel,jqGridColumnList);
					for (BaseMap rowMap : CollectionUtils.killNull(dataList)) {
						reportletMap = new HashMap<String, String>();
						reportletMap.put("reportlet", funcReport.getReport_alias()+".cpt");
						for (PairModel pairModel : jqGridColumnList) {
							reportletMap.put(pairModel.getKey(), String.valueOf(rowMap.get(pairModel.getKey())));
						}
						reportletMapList.add(reportletMap);
					}
				}
			}else{

				requestList = CollectionUtils.killNull(requestList);
				if(!BlankUtils.isBlank(requestList)){
					for (MapRowModel rowModel : requestList) {
						reportletMap = new HashMap<String, String>();
						reportletMap.put("reportlet", funcReport.getReport_alias()+".cpt");
						String tmpVal = null;
						//对model中的map进行处理
						for (String key : rowModel.getRow().keySet()) {
							tmpVal = rowModel.getRow().get(key);
							if(!BlankUtils.isBlank(tmpVal)){
								reportletMap.put(key, tmpVal);
							}
						}
						reportletMapList.add(reportletMap);
					}
				}
			}
			
			String groupSize = getRequest().getParameter("groupSize");
			if(groupSize != null && MathUtils.isDigit(groupSize) && Integer.valueOf(groupSize) > 0){
				if(reportletMapList.size() > 0 ){
					getValueStack().set(DATA, reportletMapList);
				}else{
					getValueStack().set(DATA, "未选择数据或当前条件下无可打印数据!");
				}
			}else{
				if(reportletMapList.size() > 0 ){
					getValueStack().set(DATA, JSONObject.toJSONString(reportletMapList));
				}else{
					getValueStack().set(DATA, "未选择数据或当前条件下无可打印数据!");
				}
			}
		}else{
			getValueStack().set(DATA, "没有正确的关联报表文件数据,请检查数据准确性!");
		}
		return DATA;
	}
	
	
	
	
	// --------------------------自定义功能页面渲染逻辑结束---------------------------------------------------

	
	// --------------------------属性对象get,set方法---------------------------------------------------

	@Override
	public DesignFuncMenuButtonModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public DesignFuncMenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(DesignFuncMenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public IDesignFuncService getService() {
		return service;
	}

	public void setService(IDesignFuncService service) {
		this.service = service;
	}

	public IDesignFuncElementWidgetService getWidgetService() {
		return widgetService;
	}

	public void setWidgetService(IDesignFuncElementWidgetService widgetService) {
		this.widgetService = widgetService;
	}

	public IDesignFuncDataService getDataService() {
		return dataService;
	}

	public void setDataService(IDesignFuncDataService dataService) {
		this.dataService = dataService;
	}


	public void setModel(DesignFuncMenuButtonModel model) {
		this.model = model;
	}

	public MapRowModel getMapRow() {
		return mapRow;
	}

	public void setMapRow(MapRowModel mapRow) {
		this.mapRow = mapRow;
	}

	public List<MapRowModel> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<MapRowModel> requestList) {
		this.requestList = requestList;
	}

	public String getHasSelect() {
		return hasSelect;
	}
	 
	public void setHasSelect(String hasSelect) {
		this.hasSelect = hasSelect;
	}

	public DesignFuncDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DesignFuncDataModel dataModel) {
		this.dataModel = dataModel;
	}
	
	
}
