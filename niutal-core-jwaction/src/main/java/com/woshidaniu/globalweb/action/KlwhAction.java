package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.XsmmModel;
import com.woshidaniu.service.svcinterface.KlwhService;

/**
 * 
 *@类名称:KlwhAction.java
 *@类描述：口令维护action
 *@创建人：kangzhidong
 *@创建时间：2015-1-30 上午09:36:42
 *@版本号:v1.0
 */
public class KlwhAction extends CommonBaseAction implements ModelDriven<XsmmModel> {

	private static final long serialVersionUID = 1L;

	protected XsmmModel model = new XsmmModel();
	@Resource
	private KlwhService klwhService;

	/**
	 * 
	 *@描述：查询系统用户列表
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-30上午09:40:42
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxYhxx() {
		try {

			if (QUERY.equals(model.getDoType())) {//点击查询按钮时
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getKlwhService().getPagedList(model));
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
	 *@描述：跳转到初始化规则页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-30上午09:41:25
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxKlgz() {
		ValueStack vs = getValueStack();
		String pkValue = getRequest().getParameter("pkValue");
		String type = getRequest().getParameter("type");
		vs.set("pkValue", pkValue);
		vs.set("type", type);
		return "toCshgz";
	}

	/**
	 * 批量初始化
	 * 
	 * @return
	 */
	public String plcsh(){
		boolean result = false;
		try {
			//根据输入密码批量初始化
			result = getKlwhService().plCsh(model);
		} catch (Exception e) {
			logException(e);
		}
		
		String key = result ? "I99001" : "I99002";
		getValueStack().set(DATA, getText(key));
		return DATA;
	}

	/**
	 * 全部初始化
	 * 
	 * @return
	 */
	public String qbcsh(){
		boolean result = false;
		try {
			
			result = getKlwhService().qbCsh(model);
		} catch (Exception e) {
			logException(e);
		}
		
		String key = result ? "I99001" : "I99002";
		getValueStack().set(DATA, getText(key));
		return DATA;
	}

	public XsmmModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public void setModel(XsmmModel model) {
		this.model = model;
	}

	public KlwhService getKlwhService() {
		return klwhService;
	}

	public void setKlwhService(KlwhService klwhService) {
		this.klwhService = klwhService;
	}

}