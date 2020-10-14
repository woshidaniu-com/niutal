package com.woshidaniu.globalweb.action;

import java.util.ArrayList;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.entities.YwsjPxxxModel;
import com.woshidaniu.service.svcinterface.IYwsjPxxxService;

/**
 * 
 *@类名称:YwsjPxxxAction.java
 *@类描述：业务数据排序信息控制
 *@创建人：kangzhidong
 *@创建时间：2015-2-4 下午02:52:52
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class YwsjPxxxAction extends CommonBaseAction implements ModelDriven<YwsjPxxxModel> {

	// 全局MODEL
	protected YwsjPxxxModel model = new YwsjPxxxModel();
	//角色管理SERVICE
	@Resource
	private IYwsjPxxxService service;


	/**
	 * 
	 *@描述：业务数据排序信息查询
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-4下午06:15:58
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxYwsjPxxx() {
		//设置用户
		model.setYh_id(StringUtils.getSafeStr(getUser().getYhlybid(), getUser().getYhm()));
		if(!BlankUtils.isBlank(model.getGnmkdm())&&model.getGnmkdm().split(",").length>1){
			model.setGnmkdm(model.getGnmkdm().split(",")[0]);
		}
		if (QUERY.equals(model.getDoType())) {
			try {
				getValueStack().set(DATA, getService().getModelList(model));
			} catch (Exception e) {
				getValueStack().set(DATA, new ArrayList<YwsjPxxxModel>());
			}
			return DATA;
		}else{
			try {
				getValueStack().set("ywsjPxxxList", getService().getModelList(model));
			} catch (Exception e) {
				getValueStack().set("ywsjPxxxList", new ArrayList<YwsjPxxxModel>());
			}
			return SUCCESS;
		}
	}

	/**
	 * 
	 *@描述：更新业务数据排序信息查询
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-4下午06:19:45
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxYwsjPxxxForUpdate() {
		try {
			//设置用户
			model.setYh_id(StringUtils.getSafeStr(getUser().getYhlybid(), getUser().getYhm()));
			if(!BlankUtils.isBlank(model.getGnmkdm())&&model.getGnmkdm().split(",").length>1){
				model.setGnmkdm(model.getGnmkdm().split(",")[0]);
			}
			if(!BlankUtils.isBlank(model.getList())){
				for (YwsjPxxxModel element : model.getList()) {
					element.setYh_id(model.getYh_id());
					element.setYwsj_id(model.getYwsj_id());
					element.setGnmkdm(model.getGnmkdm());
				}
				service.update(model);
			}
		} catch (Exception e) {
		}finally{
			getValueStack().set(DATA, "1");
		}
		return DATA;
	}

	public YwsjPxxxModel getModel() {
		return model;
	}

	public void setModel(YwsjPxxxModel model) {
		this.model = model;
	}

	public IYwsjPxxxService getService() {
		return service;
	}

	public void setService(IYwsjPxxxService service) {
		this.service = service;
	}
	
}
