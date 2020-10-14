package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.exception.BusinessRuntimeException;
import com.woshidaniu.common.global.GlobalXtszx;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.utils.ResultUtils;
import com.woshidaniu.entities.XtszModel;
import com.woshidaniu.service.svcinterface.IXtszService;

/**
 * 
 *@类名称		： XtszAction.java
 *@类描述		：系统设置
 *@创建人		：kangzhidong
 *@创建时间	：Sep 6, 2016 10:25:28 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
public class XtszAction extends CommonBaseAction implements ModelDriven<XtszModel> {

	private static final long serialVersionUID = 1L;

	protected XtszModel model = new XtszModel();
	@Resource
	private IXtszService xtszService;
	
	/**
	 * 
	 *@描述		：查询系统设置
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 201610:21:19 AM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxXtsz(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getXtszService().getModelList(model));
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
	 *@描述		：更新系统设置
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 201610:18:42 AM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String bcXtsz(){
		try {
			//取当前学年、学期
			model.setXnm(getCommonSqlService().cxXtszxz(GlobalXtszx.DQXNM));
			model.setXqm(getCommonSqlService().cxXtszxz(GlobalXtszx.DQXQM));
			getXtszService().updateXtsz(model);
			getValueStack().set(DATA, ResultUtils.statusMap(SUCCESS, getText("I99001")));
		} catch (Exception e) {
			logStackException(e);
			if (e instanceof BusinessRuntimeException) {
				getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText(e.getMessage())));
			} else {
				getValueStack().set(DATA, ResultUtils.statusMap(ERROR, getText("I99002")));
			}
		} 
		return DATA;
	}
	
	public XtszModel getModel() {
		return model;
	}

	public IXtszService getXtszService() {
		return xtszService;
	}

	public void setXtszService(IXtszService xtszService) {
		this.xtszService = xtszService;
	}

}
