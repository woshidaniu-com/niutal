package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.entities.XtszModel;
import com.woshidaniu.entities.XxxxModel;
import com.woshidaniu.service.svcinterface.IXxxxService;

/**
 * 
 * @类名称 : XxxxwhAction.java
 * @类描述 ：学校信息维护
 * @创建人 ：kangzhidong
 * @创建时间 ：2016年4月20日 下午5:21:14
 * @修改人 ：
 * @修改时间 ：
 * @版本号 :v1.0
 */
public class XxxxwhAction extends CommonBaseAction implements ModelDriven<XxxxModel> {

	private static final long serialVersionUID = 1L;

	protected XxxxModel model = new XxxxModel();
	@Resource
	private IXxxxService xxxxService;

	/**
	 * 
	 *@描述		：学校信息设置
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年4月20日下午5:23:34
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxXxxxszIndex() {
		model = getXxxxService().getModel("");
		return SUCCESS;
	}

	/**
	 * 
	 *@描述		：更新学校信息设置
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年4月20日下午5:23:03
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String xgXxxxsz() {
		try {
			XtszModel xtszModel = getXxxxService().getModel("");
			if (xtszModel == null) {
				getXxxxService().insert(model);
			} else {
				getXxxxService().update(model);
			}
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	public XxxxModel getModel() {
		return model;
	}

	public IXxxxService getXxxxService() {
		return xxxxService;
	}

	public void setXxxxService(IXxxxService xxxxService) {
		this.xxxxService = xxxxService;
	}

}
