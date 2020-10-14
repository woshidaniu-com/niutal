package com.woshidaniu.designer.action;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.BaseAutoCompleteFieldModel;
import com.woshidaniu.designer.service.svcinterface.IBaseAutoCompleteFieldService;

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
public class BaseAutoCompleteFieldAction extends BaseAction implements ModelDriven<BaseAutoCompleteFieldModel>  { 

	protected BaseAutoCompleteFieldModel model = new BaseAutoCompleteFieldModel();
	@Resource
	protected IBaseAutoCompleteFieldService service;

	/**
	 * 
	 *@描述：跳转至【基础自动完成字段】信息主页
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
			getValueStack().set(DATA, getText("I99007",new String[]{"基础自动完成字段新增"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础自动完成字段新增"}));
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
			getValueStack().set(DATA, getText("I99007",new String[]{"基础自动完成字段修改"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础自动完成字段修改"}));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：删除基础自动完成字段信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-5下午07:26:42
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String scAutoCompleteFieldData(){
		try {
			String ids = getRequest().getParameter("ids");
			if (null != ids && !ids.equals("")) {
				model.setDeleteList(Arrays.asList(ids.split(",")));
				if(getService().getUseCount(model) > 0  ){
					getValueStack().set(DATA, "要删除的记录中包含已被使用的字段!");
					return DATA;
				}
				getService().delete(model);
				getValueStack().set(DATA, getText("I99007",new String[]{"基础自动完成字段删除"}));
			}else{
				getValueStack().set(DATA, "请选择您要删除的记录！");
			}
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础自动完成字段删除"}));
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
	public BaseAutoCompleteFieldModel getModel() {
		return model;
	}

	public IBaseAutoCompleteFieldService getService() {
		return service;
	}

	public void setService(IBaseAutoCompleteFieldService service) {
		this.service = service;
	}
	
}

