package com.woshidaniu.designer.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementFieldModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementFieldService;

/**
 * 
 *@类名称: DesignFuncFieldsAction.java
 *@类描述： 功能页面:对应页面自定义字段设计action
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:28:39
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementFieldAction extends BaseAction implements ModelDriven<DesignFuncElementFieldModel>  { 

	protected DesignFuncElementFieldModel model = new DesignFuncElementFieldModel();
	@Resource(name="designFuncElementFieldService")
	protected IDesignFuncElementFieldService service;

	/**
	 * 
	 *@描述：跳转至【基础查询字段】信息主页
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-20下午03:56:44
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxFuncElementFieldIndex(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getModelList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
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
	 *@创建时间:2015-4-21上午11:56:10
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String zjDesignFuncFields(){
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
	public String zjDesignFuncFieldsData(){
		try {
			getService().insert(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础字段信息新增"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础字段信息新增"}));
		}
		return DATA;
	}
	
	@Override
	public DesignFuncElementFieldModel getModel() {
		return model;
	}

	public IDesignFuncElementFieldService getService() {
		return service;
	}

	public void setService(IDesignFuncElementFieldService service) {
		this.service = service;
	}
}

