package com.woshidaniu.designer.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.service.svcinterface.IBaseWidgetDetailService;
import com.woshidaniu.designer.service.svcinterface.IDesignWidgetResourceService;

/**
 * 
 *@类名称: DesignWidgetResourceAction.java
 *@类描述：功能页面:功能组件脚本样式资源信息action
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:21:56
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignWidgetResourceAction extends BaseAction implements ModelDriven<DesignFuncModel>  { 

	protected DesignFuncModel model = new DesignFuncModel();
	@Resource(name="designWidgetResourceService")
	protected IDesignWidgetResourceService service;
	@Resource
	protected IBaseWidgetDetailService widgetDetailservice;
	/**
	 * 
	 *@描述：跳转至【功能脚本,样式编辑】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-20下午03:56:44
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxFuncResourceIndex(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getFuncResourceList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			getValueStack().set("widgetList", widgetDetailservice.getFuncWidgetDetailList(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
 
	
	@Override
	public DesignFuncModel getModel() {
		return model;
	}

	public IDesignWidgetResourceService getService() {
		return service;
	}

	public void setService(IDesignWidgetResourceService service) {
		this.service = service;
	}


	public IBaseWidgetDetailService getWidgetDetailservice() {
		return widgetDetailservice;
	}

	public void setWidgetDetailservice(IBaseWidgetDetailService widgetDetailservice) {
		this.widgetDetailservice = widgetDetailservice;
	}

	public void setModel(DesignFuncModel model) {
		this.model = model;
	}
	
	
}

