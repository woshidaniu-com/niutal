package com.woshidaniu.globalweb.action;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.XtczmsModel;
import com.woshidaniu.service.svcinterface.IXtczmsService;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 *@类名称:XtczmsAction.java
 *@类描述：系统操作描述action
 *@创建人：kangzhidong
 *@创建时间：2015-3-5 下午06:01:33
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public class XtczmsAction extends CommonBaseAction implements ModelDriven<XtczmsModel> {

	private static final long serialVersionUID = 1L;

	protected XtczmsModel model = new XtczmsModel();
	@Resource
	private IXtczmsService service;
 
	/**
	 * 
	 *@描述：学校信息设置
	 *@创建人:majun
	 *@创建时间:2014-6-21下午04:01:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXtczmsIndex(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			this.setProperty("gnmkdmPairList", getService().getGnmkdmPairList());
			this.setProperty("czdmPairList", getService().getCzdmPairList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：界面请求：跳转至系统操作描述详情界面
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-6上午08:43:29
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxXtczms() {
		try {
			BeanUtils.copyProperties(this.model, getService().getModel(model));
			return SUCCESS;
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	/**
	 * 
	 *@描述：界面请求：跳转至系统操作描述修改界面
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-6上午08:44:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgXtczms(){
		try {
			this.setProperty("gnmkdmPairList", getService().getGnmkdmPairList());
			this.setProperty("czdmPairList", getService().getCzdmPairList());
			
			getValueStack().set("sfsyList", BaseDataReader.getCachedBaseDataList("isStart"));//是否被使用
			
			BeanUtils.copyProperties(this.model, getService().getModel(model));
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：更新系统操作描述
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-6上午08:44:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgBcXtczms(){
		try {
			getService().updateXtczms(model);
			getValueStack().set(DATA, getText("I99007",new String[]{"系统操作描述修改"}));
		}  catch (Exception e) {
			logStackException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"系统操作描述修改"}));
		}
		return DATA;
	}
	

	public IXtczmsService getService() {
		return service;
	}

	public void setService(IXtczmsService service) {
		this.service = service;
	}

	public void setModel(XtczmsModel model) {
		this.model = model;
	}

	public XtczmsModel getModel() {
		return model;
	}

}
