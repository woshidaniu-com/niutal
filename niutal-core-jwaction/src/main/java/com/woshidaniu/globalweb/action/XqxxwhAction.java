package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.XqxxwhModel;
import com.woshidaniu.service.svcinterface.IXqxxwhService;

/**
 * 
 * @类名称:XqxxwhAction.java
 * @类描述：校区信息维护
 * @创建人：zzh
 * @创建时间:2016-3-31
 */
public class XqxxwhAction extends CommonBaseAction implements ModelDriven<XqxxwhModel> {
	 
	private static final long serialVersionUID = 1L;
	protected XqxxwhModel model = new XqxxwhModel();
	@Resource
	private IXqxxwhService service;

	public void cxPublicList() {
		getValueStack().set("xqList", getCommonSqlService().queryAllXq());// 校区
		// getValueStack().set("XqList",
		// this.getCommonSqlService().queryAllXy());//学院
	}

	/**
	 * 
	 * @描述：查询校区信息
	 * @创建人:zzh
	 * @创建时间:2016-3-31
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxXqxx() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getPagedByScope(model));
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
	 * @描述：增加校区信息
	 * @创建人:zzh
	 * @创建时间:2016-3-31
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjXqxx() {
		this.cxPublicList();
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：增加保存校区信息
	 * @创建人:zzh
	 * @创建时间:2016-3-31
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjBcXqxx() {
		try {
			User user = getUser();
			model.setZjr(user.getYhm());
			model.setZjsj(getCommonQueryService().getDatabaseTime());
			service.insert(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：修改校区信息
	 * @创建人:zzh
	 * @创建时间:2016-3-31
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgXqxx() {
		try {
			this.cxPublicList();
			XqxxwhModel XqxxwhModel = getService().getModel(model);// 查询校区信息
			PropertyUtils.copyProperties(model, XqxxwhModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：修改保存校区信息
	 * @创建人:zzh
	 * @创建时间:2016-3-31
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgBcXqxx() {
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
	 * @描述：删除校区信息
	 * @创建人:zzh
	 * @创建时间:2016-3-31
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String scXqxx() {
		try {
			getService().deleteXqxx(model);
			getValueStack().set(DATA, getText("I99005"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99006"));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：
	 * @创建人:zzh
	 * @创建时间:2016-3-31
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxCheckXq() {
		try {
			model.setPks(model.getZyh_id().split(","));
			int count = getService().getCount(model);
			setProperty(DATA, count);
		} catch (Exception e) {
			logStackException(e);
			setProperty(DATA, 1);
		}
		return DATA;
	}

	public XqxxwhModel getModel() {
		return model;
	}

	public void setModel(XqxxwhModel model) {
		this.model = model;
	}

	public IXqxxwhService getService() {
		return service;
	}

	public void setService(IXqxxwhService service) {
		this.service = service;
	}


}
