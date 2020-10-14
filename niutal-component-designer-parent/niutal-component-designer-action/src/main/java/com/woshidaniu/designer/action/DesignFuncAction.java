package com.woshidaniu.designer.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.URLUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.constant.JCSJConstant;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncDataModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuButtonModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignFuncWidgetJQGridColumnModel;
import com.woshidaniu.designer.dao.entities.DesignWidgetResourceModel;
import com.woshidaniu.designer.dao.entities.MapRowModel;
import com.woshidaniu.designer.service.svcinterface.IBaseQueryFieldService;
import com.woshidaniu.designer.service.svcinterface.IBaseReportDetailService;
import com.woshidaniu.designer.service.svcinterface.IBaseWidgetDetailService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncDataService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementFieldService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementQueryService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementWidgetService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncService;
import com.woshidaniu.designer.service.svcinterface.IDesignWidgetResourceService;
import com.woshidaniu.designer.utils.FuncCacheKeyUtils;
import com.woshidaniu.designer.utils.FuncPageUtils;
import com.woshidaniu.designer.utils.FuncXMLDataUtils;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.service.svcinterface.IAncdService;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.util.xml.BaseDataReader;
import com.woshidaniu.yuicompressor.YUICompressUtils;
/**
 * 
 *@类名称		： DesignFuncAction.java
 *@类描述		：功能页面:功能设计操作action：为指定功能代码+操作代码维护操作功能数据
 *@创建人		：kangzhidong
 *@创建时间	：2015-4-28 上午09:43:43
 *@修改人		：
 *@修改时间	：
 *@版本号		： v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncAction extends BaseAction implements ModelDriven<DesignFuncMenuButtonModel> {

	//功能关联功能按钮信息
	protected DesignFuncMenuButtonModel model = new DesignFuncMenuButtonModel();
	//功能元素model
	protected DesignFuncElementModel elementModel = new DesignFuncElementModel();
	//元素关联的查询条件信息
	protected DesignFuncElementQueryModel elementEueryModel = new DesignFuncElementQueryModel();
	//元素关联的字段信息
	protected DesignFuncElementFieldModel fieldModel = new DesignFuncElementFieldModel();
	//元素关联的字段信息集合
	protected List<DesignFuncElementFieldModel> fieldList = new ArrayList<DesignFuncElementFieldModel>();
	//功能关联资源集合
	protected DesignWidgetResourceModel resourceModel = new DesignWidgetResourceModel();
	protected List<DesignWidgetResourceModel> resourceList = new ArrayList<DesignWidgetResourceModel>();
	//元素关联js组件信息
	protected DesignFuncElementWidgetModel widgetModel = new DesignFuncElementWidgetModel();
	//功能关联功能菜单信息
	protected DesignFuncMenuModel menuModel = new DesignFuncMenuModel();
	protected DesignFuncDataModel dataModel = new DesignFuncDataModel();
	@Resource
	protected IAncdService  ancdService;
	@Resource(name="designFuncService")
	protected IDesignFuncService service;
	@Resource(name="designFuncElementQueryService")
	protected IDesignFuncElementQueryService queryService;
	@Resource(name="designFuncElementService")
	protected IDesignFuncElementService elementService;
	@Resource
	protected IBaseQueryFieldService baseFieldService;
	@Resource(name="designFuncElementFieldService")
	protected IDesignFuncElementFieldService fieldService;
	@Resource
	protected IDesignFuncElementWidgetService widgetService;
	@Resource
	protected IBaseWidgetDetailService widgetDetailservice;
	@Resource(name="designFuncDataService")
	protected IDesignFuncDataService dataService;
	@Resource(name="designWidgetResourceService")
	protected IDesignWidgetResourceService resourceService;
	@Resource
	protected IBaseReportDetailService reportDetailService;
	
	protected List<DesignFuncElementModel> func_element_list = null;
	protected List<DesignFuncElementQueryModel> func_element_query_list = null;
	protected List<DesignFuncElementFieldModel> func_element_field_list = null;
	protected List<DesignFuncElementWidgetModel> func_element_widget_list = null;
	protected List<DesignFuncWidgetJQGridColumnModel> func_widget_jqgrid_column_list = null;
	
	protected List<MapRowModel> requestList = null;
	
	/** 公共service */
	@Resource
	private ICommonSqlService commonSqlService;

	// resourceFile属性用来封装上传的文件
	protected File resource;
	// resourceFileContentType属性用来封装上传文件的类型
	protected String resourceContentType;
	// resourceFileFileName属性用来封装上传文件的文件名
	protected String resourceFileName;
	//有无选择行记录
	protected String hasSelect;
	
	// --------------------------自定义功能菜单逻辑开始---------------------------------------------------

	public boolean isHas(List<AncdModel>  list,String opt_code){
		boolean isHasAncd = false;
		for(AncdModel _model  : list){
			if( _model.getCzdm().equals(opt_code)){
				isHasAncd = true;
				break;
			}
		}
		return isHasAncd;
	}
	
	/**
	 * 
	 *@描述：跳转至【自定义功能设计】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-20下午03:56:44
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxDesignFuncIndex() {
		try {
			List<PairModel> topFuncList = getService().getTopFuncList();
			getValueStack().set("topFuncList", topFuncList);
			getValueStack().set("topFuncModel",BlankUtils.isBlank(topFuncList) ? null : topFuncList.get(0));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：根据功能代码查询相应的菜单和按钮并跳转至【自定义功能设计菜单树】页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-12上午08:45:34
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxDesignFuncDisplay() {
		try {
			getValueStack().set("funcModel", getService().getFuncMenuModel(model.getFunc_code()));
			//查询用户指定功能的功能操作权限
			List<AncdModel>  list  = getAncdService().cxButtonGroupList(getUser(), "N0320");
			if(!BlankUtils.isBlank(list)){
				getValueStack().set("func_zjzdycd",isHas(list, "zjzdycd") ? 1 : 0);
				getValueStack().set("func_xgzdycd",isHas(list, "xgzdycd") ? 1 : 0);
				getValueStack().set("func_sczdycd",isHas(list, "sczdycd") ? 1 : 0);
				getValueStack().set("func_zjzdyan",isHas(list, "zjzdyan") ? 1 : 0);
				getValueStack().set("func_xgzdyan",isHas(list, "xgzdyan") ? 1 : 0);
				getValueStack().set("func_sczdyan",isHas(list, "sczdyan") ? 1 : 0);
			}else{
				getValueStack().set("func_zjzdycd",0);
				getValueStack().set("func_xgzdycd",0);
				getValueStack().set("func_sczdycd",0);
				getValueStack().set("func_zjzdyan",0);
				getValueStack().set("func_xgzdyan",0);
				getValueStack().set("func_sczdyan",0);
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：增加自定义菜单
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:23
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjzdycdMenu() {
		try {
			getValueStack().set("funcRoleList", FuncXMLDataUtils.getCachedBaseDataList("funcRoleList"));
			getValueStack().set("isSfxs", FuncXMLDataUtils.getCachedBaseDataList("isSfxs"));
			getModel().setFunc_level(String.valueOf(Integer.parseInt(StringUtils.getSafeStr(getModel().getFunc_level(), "1")) + 1));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String zjzdycdMenuData() {
		try {
			getService().insertMenu(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "增加自定义菜单" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "增加自定义菜单" }));
		}
		return DATA;
	}
	/**
	 * 
	 *@描述：增加自定义功能和菜单
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:23
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjzdycdFuncMenu() {
		try {
			getModel().setFunc_level(String.valueOf(Integer.parseInt(StringUtils.getSafeStr(getModel().getFunc_level(), "1")) + 1));
			getValueStack().set("reportList", reportDetailService.getModelList());
			getValueStack().set("funtTypeList", FuncXMLDataUtils.getCachedBaseDataList("funtTypeList"));
			getValueStack().set("funcRoleList", FuncXMLDataUtils.getCachedBaseDataList("funcRoleList"));
			getValueStack().set("isNot", FuncXMLDataUtils.getCachedBaseDataList("isNot"));
			getValueStack().set("editStatusList", FuncXMLDataUtils.getCachedBaseDataList("editStatusList"));
			getValueStack().set("queryTypeList", FuncXMLDataUtils.getCachedBaseDataList("queryTypeList"));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String zjzdycdFuncMenuData() {
		try {
			getService().insertFuncMenu(model);
			//查询当前角色的角色功能代码集合
			getUser().setJsgnmkdmList(getService().getFuncCodeListOfRole(getUser().getJsdm()));
			getValueStack().set(DATA, getText("I99007", new String[] { "增加自定义功能" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "增加自定义功能" }));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：修改自定义菜单
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:34
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgzdycdMenu() {
		try {
			getValueStack().set("funcRoleList", FuncXMLDataUtils.getCachedBaseDataList("funcRoleList"));
			getValueStack().set("isSfxs", FuncXMLDataUtils.getCachedBaseDataList("isSfxs"));
			BeanUtils.copyProperties(model, getService().getSystemFuncModel(model.getFunc_code()));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String xgzdycdMenuData() {
		try {
			getService().updateMenu(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "修改自定义菜单" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "修改自定义菜单" }));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：修改自定义功能和菜单
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:34
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgzdycdFuncMenu() {
		try {
			getValueStack().set("funtTypeList", FuncXMLDataUtils.getCachedBaseDataList("funtTypeList"));
			getValueStack().set("funcRoleList", FuncXMLDataUtils.getCachedBaseDataList("funcRoleList"));
			getValueStack().set("isNot", FuncXMLDataUtils.getCachedBaseDataList("isNot"));
			getValueStack().set("editStatusList", FuncXMLDataUtils.getCachedBaseDataList("editStatusList"));
			getValueStack().set("queryTypeList", FuncXMLDataUtils.getCachedBaseDataList("queryTypeList"));
			BeanUtils.copyProperties(model, getService().getFuncMenuModel(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String xgzdycdFuncMenuData() {
		try {
			model.setRelease_time(System.currentTimeMillis() + "");
			getService().updateFuncMenu(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "修改自定义功能" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "修改自定义功能" }));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：删除自定义菜单
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:46
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String sczdycdFuncMenu() {
		DesignFuncMenuModel modelTmp = null;
		try {
			modelTmp = getService().getSystemFuncModel(model.getFunc_code());
			if(BlankUtils.isBlank(modelTmp)){
				getValueStack().set(DATA, "要删除的功能菜单不存在!");
				return DATA;
			}
			if("1".equals(modelTmp.getFunc_user_defined())){
				getService().deleteFuncMenu(model);
				//查询当前角色的角色功能代码集合
				getUser().setJsgnmkdmList(getService().getFuncCodeListOfRole(getUser().getJsdm()));
				getValueStack().set(DATA, getText("I99007", new String[] { "1".equals(modelTmp.getFunc_designed())?"删除自定义功能":"删除功能菜单"}));
			}else{
				getValueStack().set(DATA, "非自定义功能菜单，不允许通过此方法删除!");
			}
		} catch (Exception e) {
			logException(e);
			if(BlankUtils.isBlank(modelTmp)){
				getValueStack().set(DATA, "要删除的功能菜单不存在!");
			}else{
				getValueStack().set(DATA, getText("I99008", new String[] { "1".equals(modelTmp.getFunc_designed())?"删除自定义功能":"删除功能菜单" }));
			}
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26上午10:25:34
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xzzdycdFuncSQLScript() {
		try {
			model.setFunc_name(URLUtils.unescape(StringUtils.getSafeStr(model.getFunc_name(), "")));
			//this.fileName = new String(CharsetUtils.getBytesUtf8(model.getFunc_name() + "(初始化SQL脚本).sql"), "ISO8859-1");
			this.file = getService().getSqlscriptFile(model);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return Result.FILE_TEMP;
	}

	/**
	 * 
	 *@描述：自定义功能页面重新生成功能
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:31:11
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String sczdyymFuncMenu() {
		try {
			if(!BlankUtils.isBlank(menuModel.getFunc_guid())){
				//查询绑定的功能对象
				DesignFuncModel funcModel = getService().getModel(menuModel);
				menuModel.setFunc_code(funcModel.getFunc_code());
				menuModel.setFunc_type(funcModel.getFunc_type());
				menuModel.setOpt_code(funcModel.getOpt_code());
				FuncPageUtils.buildFuncPage(menuModel);
				getValueStack().set(DATA, getText("I99007", new String[] { "自定义功能页面重新生成" }));
			}
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "自定义功能页面重新生成" }));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：增加自定义按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:58
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjzdyanFuncOpt() {
		try {
			getModel().setFunc_level(String.valueOf(Integer.parseInt(StringUtils.getSafeStr(getModel().getFunc_level(), "1")) + 2));
			getValueStack().set("optList", getService().getOptList());
			getValueStack().set("isNot", BaseDataReader.getCachedBaseDataList("isNot"));
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String zjzdyanFuncOptData() {
		try {
			getService().insertFuncOpt(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "增加自定义功能按钮" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "增加自定义功能按钮" }));
		}
		return DATA;
	}
	
	
	
	/**
	 * 
	 *@描述：按钮绑定自定义功能
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:58
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjzdyanFuncSubPage() {
		try {
			getValueStack().set("reportList", reportDetailService.getModelList());
			getValueStack().set("funtTypeList", FuncXMLDataUtils.getCachedBaseDataList("funtTypeList"));
			getValueStack().set("preCheckList", FuncXMLDataUtils.getCachedBaseDataList("preCheckList"));
			getValueStack().set("isNot", FuncXMLDataUtils.getCachedBaseDataList("isNot"));
			getValueStack().set("editStatusList", FuncXMLDataUtils.getCachedBaseDataList("editStatusList"));
			getValueStack().set("queryTypeList", FuncXMLDataUtils.getCachedBaseDataList("queryTypeList"));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String zjzdyanFuncSubPageData() {
		try {
			if("3".equals(model.getFunc_type()) && BlankUtils.isBlank(model.getReport_guid())){
				getValueStack().set(DATA, "请选择要关联的报表信息!");
				return DATA;
			}
			getService().insertFuncOptLink(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "绑定按钮自定义功能" }));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "绑定按钮自定义功能" }));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：修改自定义按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:03:07
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgzdyanFuncOpt() {
		try {
			DesignFuncMenuButtonModel tmp = getService().getFuncOptModel(model);
			if(tmp != null){
				BeanUtils.copyProperties(model, tmp);
			}
			getValueStack().set("isNot", BaseDataReader.getCachedBaseDataList("isNot"));
			getValueStack().set("optList", getService().getOptList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String xgzdyanFuncOptData() {
		try {
			getService().updateFuncOpt(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "修改自定义功能按钮" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "修改自定义功能按钮" }));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：修改功能按钮绑定的自定义功能
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:02:58
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgzdyanFuncSubPage() {
		try {
			getValueStack().set("reportList", reportDetailService.getModelList());
			getValueStack().set("name_text", model.getFunc_name() + " - " + model.getBtn_text());
			getValueStack().set("funtTypeList", FuncXMLDataUtils.getCachedBaseDataList("funtTypeList"));
			getValueStack().set("preCheckList", FuncXMLDataUtils.getCachedBaseDataList("preCheckList"));
			getValueStack().set("isNot", FuncXMLDataUtils.getCachedBaseDataList("isNot"));
			getValueStack().set("editStatusList", FuncXMLDataUtils.getCachedBaseDataList("editStatusList"));
			getValueStack().set("queryTypeList", FuncXMLDataUtils.getCachedBaseDataList("queryTypeList"));
			BeanUtils.copyProperties(model, getService().getFuncOptLinkModel(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String xgzdyanFuncSubPageData() {
		try {
			model.setRelease_time(System.currentTimeMillis() + "");
			getService().updateFuncOptLink(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "编辑按钮自定义功能" }));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "编辑按钮自定义功能" }));
		}
		return DATA;
	}
	
	
	public String sczdyanFuncSubPageData() {
		try {
			getService().deleteFuncOptLink(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "删除按钮自定义功能" }));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "删除按钮自定义功能" }));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：删除自定义按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-8上午10:03:17
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String sczdyanFuncOpt() {
		DesignFuncMenuButtonModel modelTmp = null;
		try {
			modelTmp = getService().getSystemFuncOptModel(model.getFunc_code(),model.getOpt_code());
			if(BlankUtils.isBlank(modelTmp)){
				getValueStack().set(DATA, "要删除的功能操作按钮不存在!");
				return DATA;
			}
			if("1".equals(modelTmp.getBtn_user_defined())){
				getService().deleteFuncOpt(model);
				getValueStack().set(DATA, getText("I99007", new String[] { BlankUtils.isBlank(modelTmp.getFunc_guid()) ? "删除操作按钮": "删除操作按钮对应自定义功能" }));
			}else{
				getValueStack().set(DATA, "非自定义操作按钮，不允许通过此方法删除!");
			}
		} catch (Exception e) {
			logStackException(e);
			if(BlankUtils.isBlank(modelTmp)){
				getValueStack().set(DATA, "要删除的功能操作按钮不存在!");
			}else{
				getValueStack().set(DATA, getText("I99008", new String[] { BlankUtils.isBlank(modelTmp.getFunc_guid()) ? "删除操作按钮": "删除操作按钮对应自定义功能"}));
			}
		}
		return DATA;
	}
	
	public String cxDesignFuncToolBar() {
		List<DesignFuncMenuButtonModel> funcBtnList = getService().getSystemFuncOptList(model.getFunc_code());
		getValueStack().set("funcBtnList", funcBtnList);
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26上午08:56:36
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String bjzdyanzFuncOptData() {
		DesignFuncMenuButtonModel modelTmp = null;
		try {
			modelTmp = getService().getSystemFuncOptModel(model.getFunc_code(),model.getOpt_code());
			if(BlankUtils.isBlank(modelTmp)){
				getValueStack().set(DATA, "要删除的功能操作按钮不存在!");
				return DATA;
			}
			if("1".equals(modelTmp.getBtn_user_defined())){
				getService().deleteFuncOpt(model);
				getValueStack().set(DATA, getText("I99007", new String[] { BlankUtils.isBlank(modelTmp.getFunc_guid()) ? "删除操作按钮": "删除操作按钮对应自定义功能" }));
			}else{
				getValueStack().set(DATA, "非自定义操作按钮，不允许通过此方法删除!");
			}
		} catch (Exception e) {
			logException(e);
			if(BlankUtils.isBlank(modelTmp)){
				getValueStack().set(DATA, "要删除的功能操作按钮不存在!");
			}else{
				getValueStack().set(DATA, getText("I99008", new String[] { BlankUtils.isBlank(modelTmp.getFunc_guid()) ? "删除操作按钮": "删除操作按钮对应自定义功能"}));
			}
		}
		return DATA;
	}
	
	// ---------元素对象操作-----------------
	
	/**
	 * 
	 *@描述：增加自定义元素
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-19下午07:28:37
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjzdyysFuncElement() {
		//获取请求基本路基
		elementModel.setFunc_code(model.getFunc_code());
		elementModel.setOpt_code(model.getOpt_code());
		getValueStack().set("elementTypeList", FuncXMLDataUtils.getCachedBaseDataList("elementTypeList"));
		if(BlankUtils.isBlank(model.getReport_guid())){
			getValueStack().set("elementList", elementService.getRelatedElementList(elementModel));
			
		}else{
			elementModel.setReport_guid(model.getReport_guid());
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：增加自定义元素数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:47:28
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjzdyysFuncElementData() {
		try {
			elementService.insert(elementModel);
			getValueStack().set(DATA, getText("I99007", new String[] { "新增功能元素" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "新增功能元素" }));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：编辑自定义元素
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:42:08
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgzdyysFuncElement() {
		try {
			String report_guid = elementModel.getReport_guid();
			BeanUtils.copyProperties(elementModel, elementService.getModel(elementModel));
			elementModel.setReport_guid(report_guid);
			getValueStack().set("elementTypeList", FuncXMLDataUtils.getCachedBaseDataList("elementTypeList"));
			if(BlankUtils.isBlank(elementModel.getReport_guid())){
				getValueStack().set("elementList", elementService.getRelatedElementList(elementModel));
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：编辑自定义元素数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:42:49
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String xgzdyysFuncElementData() {
		try {
			elementService.update(elementModel);
			getValueStack().set(DATA, getText("I99007", new String[] { "修改功能元素" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "修改功能元素" }));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：删除自定义元素数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:42:25
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String sczdyysFuncElementData() {
		try {
			elementService.delete(elementModel);
			getValueStack().set(DATA, getText("I99007", new String[] { "删除功能元素" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "删除功能元素" }));
		}
		return DATA;
	}
	

	// ---------元素实体对象操作-----------------
	
	/**
	 * 
	 *@描述：跳转至自定义元素基础字段实体页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:49:02
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjysstFuncElementEntityFields() {
		try {
			BeanUtils.copyProperties(elementModel, elementService.getModel(elementModel));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至自定义元素基础字段实体关联下拉元素页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:55:37
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjysstFuncElementEntityFieldMoreParams(){
		getValueStack().set("fieldModel", fieldModel);
		getValueStack().set("isUsed", FuncXMLDataUtils.getCachedBaseDataList("isUsed"));
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至自定义元素基础字段实体更多设置页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:56:01
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjysstFuncElementEntityFieldBaseElement(){
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
			getValueStack().set("fieldModel", fieldModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：跳转至自定义元素查询条件实体页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:48:40
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjysstFuncElementEntityQuery() {
		try {
			BeanUtils.copyProperties(elementModel, elementService.getModel(elementModel));
			
			if(BlankUtils.isBlank(elementModel.getElement_query())){
				elementEueryModel.setQuery_name(StringUtils.getSafeStr(widgetModel.getWidget_desc(), "查询条件"));
				elementEueryModel.setQuery_column(StringUtils.getSafeStr(widgetModel.getWidget_desc(), "4"));
				elementModel.setElement_query(elementEueryModel);
			}
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：跳转至自定义元素组件实体页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:47:48
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjysstFuncElementEntityWidget() {
		try {
			BeanUtils.copyProperties(elementModel, elementService.getModel(elementModel));
			
			if(BlankUtils.isBlank(elementModel.getElement_widget())){
				widgetModel.setWidget_desc(StringUtils.getSafeStr(widgetModel.getWidget_desc(), "jqgrid列表"));
				widgetModel.setWidget_title(StringUtils.getSafeStr(widgetModel.getWidget_title(), "jqgrid列表"));
				widgetModel.setWidget_loadAtOnce(StringUtils.getSafeStr(widgetModel.getWidget_loadAtOnce(), "0"));
				elementModel.setElement_widget(widgetModel);
			}
			getValueStack().set("isPagination", FuncXMLDataUtils.getCachedBaseDataList("isPagination"));
			getValueStack().set("isNot", FuncXMLDataUtils.getCachedBaseDataList("isNot"));
			getValueStack().set("widgetList", widgetDetailservice.getWidgetDetailList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String zjysstFuncElementEntityReport() {
		try {
			BeanUtils.copyProperties(elementModel, elementService.getModel(elementModel));
			
			if(BlankUtils.isBlank(elementModel.getElement_widget())){
				widgetModel.setWidget_desc(StringUtils.getSafeStr(widgetModel.getWidget_desc(), "jqgrid列表"));
				widgetModel.setWidget_title(StringUtils.getSafeStr(widgetModel.getWidget_title(), "jqgrid列表"));
				widgetModel.setWidget_loadAtOnce(StringUtils.getSafeStr(widgetModel.getWidget_loadAtOnce(), "0"));
				elementModel.setElement_widget(widgetModel);
			}
			getValueStack().set("widgetList", widgetDetailservice.getWidgetDetailList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：设置元素实体对象数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-21下午02:43:05
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjysstFuncElementEntityData() {
		try {
			elementModel.setElement_query(elementEueryModel);
			elementModel.setElement_widget(widgetModel);
			elementModel.setElement_field_list(fieldList);
			elementService.insertElementEntity(elementModel);
			getValueStack().set(DATA, getText("I99007", new String[] { "绑定/编辑元素实体对象数据" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "绑定/编辑元素实体对象数据" }));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：移除元素上绑定的实体对象
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-20下午02:40:00
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String scysstFuncElementEntityData() {
		try {
			elementService.deleteElementEntity(elementModel);
			getValueStack().set(DATA, getText("I99007", new String[] { "删除元素实体对象数据" }));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "删除元素实体对象数据" }));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：更新功能关联资源信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-28下午07:13:13
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String bjfunczyResourceData() {
		try {
			resourceModel.setFunc_guid(getModel().getFunc_guid());
			if(resource!=null&&resource.length() > 0 ){
				String extension = FilenameUtils.getExtension(resource.getName());
				if("css".equalsIgnoreCase(extension)||"js".equalsIgnoreCase(extension)){
					resourceModel.setResource_name(resource.getName());
					//进行压缩进而，判断是否是真实的js或者css文件
					try {
						//创建目标输出缓存writer
						StringBuilderWriter out = new StringBuilderWriter();
						YUICompressUtils.compressFile(resource, out);
						resourceModel.setResource_text(out.toString());
					} catch (Exception e) {
						getValueStack().set(DATA, "不是有效的*.js或者*.css文件；请勿其他文件修改后缀后直接上传!");
						return DATA;
					}
					resourceModel.setResource_type("css".equalsIgnoreCase(extension)?"2":"1");
					getService().updateFuncResource(resourceModel,resourceList);
					getValueStack().set(DATA, getText("I99007", new String[] { "绑定、编辑（js/css）资源数据" }));
				}else{
					getValueStack().set(DATA, "仅可以上传*.js或者*.css文件");
				}
			}else{
				getService().updateFuncResource(resourceModel,resourceList);
				getValueStack().set(DATA, getText("I99007", new String[] { "绑定、编辑（js/css）资源数据" }));
			}
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "绑定、编辑（js/css）资源数据" }));
		}
		return DATA;
	}
	
	// --------------------------自定义功能菜单逻辑结束---------------------------------------------------


	// --------------------------自定义功能页面渲染逻辑开始---------------------------------------------------

	protected void initResponse(String func_code,String opt_code){
		try {
		   if(isCacheSupport()){
			    Cache cache = this.getCache();
				String autoKey1 = FuncCacheKeyUtils.getCacheKey(func_code,opt_code,"func-element-querys");
				ValueWrapper valueWrapper = cache.get(autoKey1);
				if(valueWrapper != null){
					func_element_query_list = (List<DesignFuncElementQueryModel>) valueWrapper.get();
				}
				if(BlankUtils.isBlank(func_element_query_list)){
					//缓存过期重新查询
					func_element_query_list = queryService.getFuncElementQueryList(func_code, opt_code);
					//缓存
					cache.put(autoKey1, func_element_query_list);
				}
		   }else{
			   //缓存过期重新查询
			   func_element_query_list = queryService.getFuncElementQueryList(func_code, opt_code);
		   }
		   if(!BlankUtils.isBlank(func_element_query_list)){
				//字段数据取值索引
				String field_list;
				//字段值对象
				Object field_obj = null;
				//循环查询条件
				for (DesignFuncElementQueryModel element_query : func_element_query_list) {
					if( !BlankUtils.isBlank(element_query.getQuery_field_list())){
						//循环自定义字段信息
						for (DesignFuncElementQueryFieldModel fieldModel : element_query.getQuery_field_list()) {
							field_obj = null;
							/**
							 * 字段值来源： 
							 * 程序设置 	：此时如果field_list值不为空且field_shape=2，需要开发者在进入页面前指定field_index对应的对象结果
							 * 数据库 	：格式如 SQL:查询SQL 例如 SQL:select id as key,name as value from table_name
							 * XML数据 	：格式如 XML:(baseData.xml)文件中的id 例如 XML:isValid Spring集合对象 ：格式如
							 * Spring	:文件中的id 例如 Spring:field_list 
							 * 固定值 	：格式如 Fixed:固定值1,固定值2,...(多个用,隔开) 例如 Fixed:aaa,bbb,ccc 或 Fixed:1#aaa,2#bbb,3#ccc
							 */
							String field_source  = fieldModel.getField_source();
							 //字段数据取值索引
							field_list = fieldModel.getField_list();
							//取值
							if(field_source != null && field_source.startsWith("APP:")){
								//年级
								if(field_list.indexOf("njdm_id") > -1){
									field_obj = this.getCommonSqlService().queryAllNj();
								}
								//机构
								else if(field_list.indexOf("jg_id") > -1){
									field_obj = this.getCommonSqlService().queryAllXy();
								}
								//学年
								else if(field_list.indexOf("xnm") > -1){
									field_obj = this.getCommonSqlService().getXnMapList();
								}
								//学期
								else if(field_list.indexOf("xqm") > -1){
									field_obj = this.getCommonSqlService().queryJcsjList(JCSJConstant.XQDMB);
								}
								//校区
								else if(field_list.indexOf("xqh_id") > -1){
									field_obj = this.getCommonSqlService().queryAllXq();
								}
								// 学生学院
								else if(field_list.indexOf("xy_id") > -1){
									field_obj = this.getCommonSqlService().queryAllXy();
								}
								// 开课部门
								else if(field_list.indexOf("kkbm_id") > -1){
									field_obj = this.getCommonSqlService().queryAllKkbm();
								}
								getValueStack().set(field_list,field_obj);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logException(e);
		}
	}
	
	public String cxDesignFuncSubIndex() {
		try {
			if(!BlankUtils.isBlank(model.getFunc_guid())){
				//查询绑定的功能对象
				DesignFuncModel funcModel = getService().getModel(model);
				if(!BlankUtils.isBlank(funcModel)){
					FuncPageUtils.buildPageWhenNotFound(funcModel);
					//系统自定义功能类型；默认：数据展示; 可选值 ：1:'数据展示',2:'数据维护',3:'预览打印',4:'快速打印',5:'数据导出',6:'数据删除'
				    if("1".equals(funcModel.getFunc_type()) || "3".equals(funcModel.getFunc_type())){
				    	initResponse(funcModel.getFunc_code(), funcModel.getOpt_code());
				   	}
					getValueStack().set("func_code",funcModel.getFunc_code());
				    getValueStack().set("opt_code",funcModel.getOpt_code());
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
	 *@描述：跳转至【功能代码+操作代码】对应的自定义功能页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21下午07:39:23
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxDesignFuncPageIndex() {
		try{
			//获取请求基本路基
			String path = getRequest().getServletPath();
			//从Action url中解析得到操作代码
	        String opt_code  = WebRequestUtils.getOptCode(path,"html");
	        //获取功能模块代码
	        String func_code = getRequest().getParameter("gnmkdm");
				   func_code = BlankUtils.isBlank(func_code) ?  getRequest().getParameter("gnmkdmKey") : func_code;
		    getValueStack().set("func_code",func_code);
		    getValueStack().set("opt_code",opt_code);
		    //功能主页
		    if("cx".equals(opt_code)){
		    	//查询绑定的功能对象
				DesignFuncModel funcModel = getService().getFuncModel(func_code,opt_code);
				FuncPageUtils.buildPageWhenNotFound(funcModel);
		    	initResponse(func_code, opt_code);
		   	}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	// --------------------------自定义功能页面渲染逻辑结束---------------------------------------------------

	
	// --------------------------属性对象get,set方法---------------------------------------------------

	@Override
	public DesignFuncMenuButtonModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public DesignFuncElementModel getElementModel() {
		return elementModel;
	}

	public void setElementModel(DesignFuncElementModel elementModel) {
		this.elementModel = elementModel;
	}

	public DesignFuncElementQueryModel getElementEueryModel() {
		return elementEueryModel;
	}

	public void setElementEueryModel(DesignFuncElementQueryModel elementEueryModel) {
		this.elementEueryModel = elementEueryModel;
	}

	public DesignFuncElementFieldModel getFieldModel() {
		return fieldModel;
	}

	public void setFieldModel(DesignFuncElementFieldModel fieldModel) {
		this.fieldModel = fieldModel;
	}

	public List<DesignFuncElementFieldModel> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<DesignFuncElementFieldModel> fieldList) {
		this.fieldList = fieldList;
	}

	public DesignFuncElementWidgetModel getWidgetModel() {
		return widgetModel;
	}

	public void setWidgetModel(DesignFuncElementWidgetModel widgetModel) {
		this.widgetModel = widgetModel;
	}

	public DesignFuncMenuModel getMenuModel() {
		return menuModel;
	}

	public void setMenuModel(DesignFuncMenuModel menuModel) {
		this.menuModel = menuModel;
	}

	public IAncdService getAncdService() {
		return ancdService;
	}

	public void setAncdService(IAncdService ancdService) {
		this.ancdService = ancdService;
	}

	public IDesignFuncService getService() {
		return service;
	}

	public void setService(IDesignFuncService service) {
		this.service = service;
	}

	public IDesignFuncElementQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IDesignFuncElementQueryService queryService) {
		this.queryService = queryService;
	}

	public IDesignFuncElementService getElementService() {
		return elementService;
	}

	public void setElementService(IDesignFuncElementService elementService) {
		this.elementService = elementService;
	}

	public IBaseQueryFieldService getBaseFieldService() {
		return baseFieldService;
	}

	public void setBaseFieldService(IBaseQueryFieldService baseFieldService) {
		this.baseFieldService = baseFieldService;
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

	public IBaseWidgetDetailService getWidgetDetailservice() {
		return widgetDetailservice;
	}

	public void setWidgetDetailservice(IBaseWidgetDetailService widgetDetailservice) {
		this.widgetDetailservice = widgetDetailservice;
	}

	public IDesignFuncDataService getDataService() {
		return dataService;
	}

	public void setDataService(IDesignFuncDataService dataService) {
		this.dataService = dataService;
	}

	public List<DesignFuncElementModel> getFunc_element_list() {
		return func_element_list;
	}

	public void setFunc_element_list(List<DesignFuncElementModel> funcElementList) {
		func_element_list = funcElementList;
	}

	public List<DesignFuncElementQueryModel> getFunc_element_query_list() {
		return func_element_query_list;
	}

	public void setFunc_element_query_list(
			List<DesignFuncElementQueryModel> funcElementQueryList) {
		func_element_query_list = funcElementQueryList;
	}

	public List<DesignFuncElementFieldModel> getFunc_element_field_list() {
		return func_element_field_list;
	}

	public void setFunc_element_field_list(
			List<DesignFuncElementFieldModel> funcElementFieldList) {
		func_element_field_list = funcElementFieldList;
	}

	public List<DesignFuncElementWidgetModel> getFunc_element_widget_list() {
		return func_element_widget_list;
	}

	public void setFunc_element_widget_list(
			List<DesignFuncElementWidgetModel> funcElementWidgetList) {
		func_element_widget_list = funcElementWidgetList;
	}

	public List<DesignFuncWidgetJQGridColumnModel> getFunc_widget_jqgrid_column_list() {
		return func_widget_jqgrid_column_list;
	}

	public void setFunc_widget_jqgrid_column_list(
			List<DesignFuncWidgetJQGridColumnModel> funcWidgetJqgridColumnList) {
		func_widget_jqgrid_column_list = funcWidgetJqgridColumnList;
	}

	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}

	public void setModel(DesignFuncMenuButtonModel model) {
		this.model = model;
	}

	public IDesignWidgetResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(IDesignWidgetResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public List<DesignWidgetResourceModel> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<DesignWidgetResourceModel> resourceList) {
		this.resourceList = resourceList;
	}

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getResourceContentType() {
		return resourceContentType;
	}

	public void setResourceContentType(String resourceContentType) {
		this.resourceContentType = resourceContentType;
	}

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	public DesignWidgetResourceModel getResourceModel() {
		return resourceModel;
	}

	public void setResourceModel(DesignWidgetResourceModel resourceModel) {
		this.resourceModel = resourceModel;
	}

	public List<MapRowModel> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<MapRowModel> requestList) {
		this.requestList = requestList;
	}

	public IBaseReportDetailService getReportDetailService() {
		return reportDetailService;
	}

	public void setReportDetailService(IBaseReportDetailService reportDetailService) {
		this.reportDetailService = reportDetailService;
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
	
	@Override
	public boolean isCacheSupport() {
		return true;
	}
	
	
	
}
