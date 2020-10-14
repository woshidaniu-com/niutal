package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.ZyxxwhModel;
import com.woshidaniu.service.svcinterface.IZyxxwhService;

/**
 * 
 * @类名称:ZyxxwhAction.java
 * @类描述：专业信息维护
 * @创建人：wjy
 * @创建时间：2014-10-22 下午04:26:29
 * @版本号:v1.0
 */
public class ZyxxwhAction extends CommonBaseAction implements
		ModelDriven<ZyxxwhModel> {

	private static final long serialVersionUID = 1L;
	private ZyxxwhModel model = new ZyxxwhModel();
	@Resource
	private IZyxxwhService service;

	/**
	 * 
	 * @描述：查询专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-22下午04:45:53
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 */
	public void cxPublicList() {
		//getValueStack().set("bzkzyList", getCommonSqlService().getTableList("jw_jcdm_gjbzkzydmb","bzkzym", "bzkzymc"));// 国标本专科专业集合
		//getValueStack().set("yjszyList",getCommonSqlService().getTableList("jw_jcdm_gjyjszydmb","yjszym", "yjszymc"));// 国标研究生专业集合
		getValueStack().set("xkmlList", getService().getXkmlList());// 学科门类集合
		getValueStack().set("ccList", getService().getCcList());// 层次集合
	}

	public String cxZyxx() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getPagedByScope(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setProperty("jgList", getCommonSqlService().queryAllJgxx());
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
	public String zjZyxx() {
		this.cxPublicList();
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
			model.setSfty("0");
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
	public String xgZyxx() {
		try {
			this.cxPublicList();
			ZyxxwhModel zyxxwhModel = getService().getModel(model);// 查询专业信息
			PropertyUtils.copyProperties(model, zyxxwhModel);
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
			model.setPks(model.getZyh_id().split(","));
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
	 * @描述：停用专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-23下午03:17:25
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 * @throws Exception
	 */
	public String tyZyxx() throws Exception {
		try {
			getService().updateSfty(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "操作" }));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "操作" }));
		}
		return DATA;
	}

	/**
	 * 
	 * @描述：启用专业信息
	 * @创建人:wjy
	 * @创建时间:2014-10-23下午03:17:13
	 * @修改人:
	 * @修改时间:
	 * @修改描述:
	 * @return
	 * @throws Exception
	 */
	public String qyZyxx() throws Exception {
		try {
			getService().updateSfty(model);
			getValueStack().set(DATA, getText("I99007", new String[] { "操作" }));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99008", new String[] { "操作" }));
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
			model.setPks(model.getZyh_id().split(","));
			int count = getService().getCount(model);
			setProperty(DATA, count);
		} catch (Exception e) {
			logStackException(e);
			setProperty(DATA, 1);
		}
		return DATA;
	}

	public ZyxxwhModel getModel() {
		return model;
	}

	public void setModel(ZyxxwhModel model) {
		this.model = model;
	}

	public IZyxxwhService getService() {
		return service;
	}

	public void setService(IZyxxwhService service) {
		this.service = service;
	}

}
