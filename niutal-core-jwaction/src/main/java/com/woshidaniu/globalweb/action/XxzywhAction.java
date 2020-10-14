package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.XxzywhModel;
import com.woshidaniu.service.svcinterface.IXxzywhService;

/**
 * 
 * @类名称:XxzyxxwhAction.java
 * @类描述：学信专业维护
 * @创建人：gc
 * @创建时间：2015-11-6 上午11:54:59
 * @版本号:v1.0
 */
public class XxzywhAction extends CommonBaseAction implements ModelDriven<XxzywhModel> {
	
	private static final long serialVersionUID = 1L;
	protected XxzywhModel model = new XxzywhModel();
	@Resource
	private IXxzywhService service;

	/**
	 * 
	 * @描述：查询学信专业信息
	 * @创建人:gc
	 * @创建时间:2015-11-6下午03:28:10
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxXxzyxx() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getPagedByScope(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			getValueStack().set("njList",
					this.getCommonSqlService().queryAllNj());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：增加专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-22下午04:46:10
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjXxzyxx() {
		getValueStack().set("njList", getCommonSqlService().queryAllNj());
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：增加保存专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-22下午04:47:07
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjBcZyxx() {
		try {
			model.setZjr(getUser().getYhm());
			getService().insert(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：修改专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-22下午04:48:28
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgXxzyxx() {
		try {
			XxzywhModel xxzywhModel = getService().getModel(model);// 查询专业信息
			PropertyUtils.copyProperties(model, xxzywhModel);
			getValueStack().set("njList", this.getCommonSqlService().queryAllNj());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：修改保存专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-22下午04:49:21
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgBcZyxx() {
		try {
			getService().update(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：删除专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-22下午04:49:39
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 * @throws Exception
	 */
	public String scZyxx() throws Exception {
		try {
			model.setPks(model.getXxzydmb_id().split(","));
			getService().delete(model);
			getValueStack().set(DATA, getText("I99005"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99006"));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：查询专业是否已被使用
	 * @创建人:wjy
	 * @创建时间:2014-11-12下午04:29:00
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxCheckZy() {
		try {
			model.setPks(model.getXxzydmb_id().split(","));
			int count = getService().getCount(model);
			setProperty(DATA, count);
		} catch (Exception e) {
			logStackException(e);
			setProperty(DATA, 1);
		}
		return DATA;
	}

	public XxzywhModel getModel() {
		return model;
	}

	public void setModel(XxzywhModel model) {
		this.model = model;
	}

	public IXxzywhService getService() {
		return service;
	}

	public void setService(IXxzywhService service) {
		this.service = service;
	}

}
