package com.woshidaniu.globalweb.action;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.entities.ZdpzModel;
import com.woshidaniu.service.svcinterface.IZdpzService;
/***
 * 
 *@类名称:ZdpzAction.java
 *@类描述：字段配置action
 *@创建人：kangzhidong
 *@创建时间：2014-7-1 下午07:05:14
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class ZdpzAction extends CommonBaseAction implements ModelDriven<ZdpzModel>{
	
	protected ZdpzModel model = new ZdpzModel();
	protected List<ZdpzModel> list;
	@Resource
	private IZdpzService service;
	
	/***
	 * 
	 *@描述：查询字段配置
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-1下午07:06:04
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxZdpzList(){
		try {
			model.setYhm(getUser().getYhm());
			getValueStack().set(Result.JSON,JSONArray.toJSONString(service.getModelList(model)));
		} catch (Exception e) {
			logStackException(e);
		}
		return Result.JSON;
	}
	
 
	/**
	 * 
	 *@描述：跳转至指定功能的字段修改
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-1下午07:07:36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxZdpz() {
		return SUCCESS;
	}
	
	/**
	 * 
	 *@描述：修改指定功能的字段信息；修改 hidden,resizable,sortable,align,number
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-1下午07:09:02
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String xgZdpz() {
		try {
			if (list!=null&&list.size()>0) {
				for (ZdpzModel model : list) {
					model.setYhm(getUser().getYhm());
				}
				getService().batchUpdate(list);
				getValueStack().set(DATA, getText("I99001"));
			}else{
				getValueStack().set(DATA, getText("I99002"));
			}
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}


	public ZdpzModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public void setModel(ZdpzModel model) {
		this.model = model;
	}

	public List<ZdpzModel> getList() {
		return list;
	}

	public void setList(List<ZdpzModel> list) {
		this.list = list;
	}

	public IZdpzService getService() {
		return service;
	}

	public void setService(IZdpzService service) {
		this.service = service;
	}
	
}
