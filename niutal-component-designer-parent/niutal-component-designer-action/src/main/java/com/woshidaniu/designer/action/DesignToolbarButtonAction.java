package com.woshidaniu.designer.action;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.DesignToolbarButtonModel;
import com.woshidaniu.designer.service.svcinterface.IDesignToolbarButtonService;

/**
 * 
 *@类名称: AutoCompleteFieldAction.java
 *@类描述：基础自动完成字段操作action
 *@创建人：kangzhidong
 *@创建时间：2015-4-24 下午04:06:16
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignToolbarButtonAction extends BaseAction implements ModelDriven<DesignToolbarButtonModel>  { 

	protected DesignToolbarButtonModel model = new DesignToolbarButtonModel();
	@Resource(name="designToolbarButtonService")
	protected IDesignToolbarButtonService service;
	
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
	public String cxAutoCompleteFieldIndex(){
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
	public String zjAutoCompleteField(){
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
	public String zjAutoCompleteFieldData(){
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
	public String xgAutoCompleteField(){
		try {
			BeanUtils.copyProperties(model, getService().getModel(model));
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
	public String xgAutoCompleteFieldData(){
		try {
			getService().update(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础字段信息修改"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础字段信息修改"}));
		}
		return DATA;
	}
	
	public String scAutoCompleteFieldData(){
		try {
			getService().update(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础字段信息修改"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础字段信息修改"}));
		}
		return DATA;
	}

	/**
	 * 
	 *@描述：跳转至基础数据字段查看页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-21下午05:23:22
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxAutoCompleteField(){
		try {
			getValueStack().set("model", getService().getModel(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Override
	public DesignToolbarButtonModel getModel() {
		return model;
	}

	public IDesignToolbarButtonService getService() {
		return service;
	}

	public void setService(IDesignToolbarButtonService service) {
		this.service = service;
	}
	
}

