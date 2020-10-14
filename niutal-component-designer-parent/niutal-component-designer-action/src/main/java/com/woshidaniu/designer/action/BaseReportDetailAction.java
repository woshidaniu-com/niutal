package com.woshidaniu.designer.action;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.BaseReportDetailModel;
import com.woshidaniu.designer.service.svcinterface.IBaseReportDetailService;

/**
 * 
 *@类名称: BaseReportDetailAction.java
 *@类描述：高级报表基本信息操作action
 *@创建人：kangzhidong
 *@创建时间：2015-6-3 下午08:01:09
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class BaseReportDetailAction extends BaseAction implements ModelDriven<BaseReportDetailModel>  { 

	protected BaseReportDetailModel model = new BaseReportDetailModel();
	@Resource
	protected IBaseReportDetailService service;

	/**
	 * 
	 *@描述：跳转至【高级报表基本信息】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-20下午03:56:44
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxReportDetailIndex(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String zjReportDetail(){
		return SUCCESS;
	}
	
	public String zjReportDetailData(){
		try {
			getService().insert(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"高级报表基本信息新增"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"高级报表基本信息新增"}));
		}
		return DATA;
	}
	
	public String xgReportDetail(){
		try {
			BeanUtils.copyProperties(model, getService().getModel(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String xgReportDetailData(){
		try {
			getService().update(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"高级报表基本信息修改"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"高级报表基本信息修改"}));
		}
		return DATA;
	}
	
	public String scReportDetailData(){
		try {
			String ids = getRequest().getParameter("ids");
			if (null != ids && !ids.equals("")) {
				model.setDeleteList(Arrays.asList(ids.split(",")));
				if(getService().getUseCount(model) > 0  ){
					getValueStack().set(DATA, "要删除的记录中包含已被使用的字段!");
					return DATA;
				}
				getService().delete(model);
				getValueStack().set(DATA, getText("I99007",new String[]{"高级报表基本信息删除"}));
			}else{
				getValueStack().set(DATA, "请选择您要删除的记录！");
			}
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"高级报表基本信息删除"}));
		}
		return DATA;
	}

	
	@Override
	public BaseReportDetailModel getModel() {
		return model;
	}

	public IBaseReportDetailService getService() {
		return service;
	}

	public void setService(IBaseReportDetailService service) {
		this.service = service;
	}
	
}

