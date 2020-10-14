package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.JgxxwhModel;
import com.woshidaniu.service.svcinterface.IJgxxwhService;

/**
 * 
 * @类名称:JgxxwhAction.java
 * @类描述：机构信息维护
 * @创建人：wjy
 * @创建时间：2015-2-14 下午02:57:27
 * @版本号:v1.0
 */
public class JgxxwhAction extends CommonBaseAction implements ModelDriven<JgxxwhModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JgxxwhModel model = new JgxxwhModel();
	@Resource
	private IJgxxwhService service;

	public void cxPublicList() {
		getValueStack().set("xqList", this.getCommonSqlService().queryAllXq());// 校区
		getValueStack().set("jgList", this.getCommonSqlService().queryAllXy());// 学院
	}

	/**
	 * 
	 * @描述：查询机构信息
	 * @创建人:wjy
	 * @创建时间:2015-2-14下午03:14:03
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxJgxx() {
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
	 * @描述：增加机构信息
	 * @创建人:wjy
	 * @创建时间:2015-6-8下午04:47:11
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjJgxx() {
		this.cxPublicList();
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：增加保存机构信息
	 * @创建人:wjy
	 * @创建时间:2015-6-8下午04:48:15
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String zjBcJgxx() {
		try {
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
	 * @描述：修改机构信息
	 * @创建人:wjy
	 * @创建时间:2015-2-14下午03:15:42
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgJgxx() {
		try {
			this.cxPublicList();
			JgxxwhModel jgxxwhModel = getService().getModel(model);// 查询机构信息
			PropertyUtils.copyProperties(model, jgxxwhModel);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @描述：修改保存机构信息
	 * @创建人:wjy
	 * @创建时间:2015-2-14下午03:16:00
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String xgBcJgxx() {
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
	 * @描述：删除机构信息
	 * @创建人:wjy
	 * @创建时间:2015-6-8下午05:26:59
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String scJgxx() {
		try {
			getService().deleteJgxx(model);
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
	 * @创建人:wjy
	 * @创建时间:2015-6-8下午07:33:41
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public String cxCheckJg() {
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

	public JgxxwhModel getModel() {
		return model;
	}

	public void setModel(JgxxwhModel model) {
		this.model = model;
	}

	public IJgxxwhService getService() {
		return service;
	}

	public void setService(IJgxxwhService service) {
		this.service = service;
	}

}
