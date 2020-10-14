package com.woshidaniu.designer.action;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.designer.dao.entities.BaseWidgetDetailModel;
import com.woshidaniu.designer.service.svcinterface.IBaseWidgetDetailService;

/**
 * 
 *@类名称: BaseWidgetDetailAction.java
 *@类描述：功能js组件描述信息action
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午02:07:10
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class BaseWidgetDetailAction extends BaseAction implements ModelDriven<BaseWidgetDetailModel>  { 

	protected BaseWidgetDetailModel model = new BaseWidgetDetailModel();
	@Resource
	protected IBaseWidgetDetailService service;

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
	public String cxWidgetDetailIndex(){
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
	public String zjWidgetDetail(){
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
	public String zjWidgetDetailData(){
		try {
			getService().insert(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础功能js组件新增"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础功能js组件新增"}));
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
	public String xgWidgetDetail(){
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
	public String xgWidgetDetailData(){
		try {
			getService().update(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"基础功能js组件修改"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础功能js组件修改"}));
		}
		return DATA;
	}
	
	public String scWidgetDetailData(){
		try {
			String ids = getRequest().getParameter("ids");
			if (null != ids && !ids.equals("")) {
				model.setDeleteList(Arrays.asList(ids.split(",")));
				if(getService().getUseCount(model) > 0  ){
					getValueStack().set(DATA, "要删除的记录中包含已被使用的字段!");
					return DATA;
				}
				getService().delete(model);
				getValueStack().set(DATA, getText("I99007",new String[]{"基础功能js组件删除"}));
			}else{
				getValueStack().set(DATA, "请选择您要删除的记录！");
			}
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"基础功能js组件删除"}));
		}
		return DATA;
	}

	
	@Override
	public BaseWidgetDetailModel getModel() {
		return model;
	}

	public IBaseWidgetDetailService getService() {
		return service;
	}

	public void setService(IBaseWidgetDetailService service) {
		this.service = service;
	}
	
}

