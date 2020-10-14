package com.woshidaniu.globalweb.action;

import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.JsgnmkModel;
import com.woshidaniu.entities.TimeSettingModel;
import com.woshidaniu.service.common.ICommonTimeSettingService;
import com.woshidaniu.service.svcinterface.IJsgnmkService;

/**
 * 
 *@类名称:CommonTimeSettingAction.java
 *@类描述：公共的功能时间控制
 *@创建人：kangzhidong
 *@创建时间：2015-2-9 下午07:06:57
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class CommonTimeSettingAction extends CommonBaseAction implements ModelDriven<TimeSettingModel>  {
	
	private TimeSettingModel model = new TimeSettingModel();
	private JsgnmkModel jsdnmkModel = new JsgnmkModel();
	//时间设置service
	@Resource
	private ICommonTimeSettingService service;
	//角色功能模块service
	@Resource
	private IJsgnmkService jsgnmkService;
	
	/**
	 * 
	 *@描述：跳转至功能时间控制Index
	 *@创建人:wwb
	 *@创建时间:2015-2-10下午03:25:11
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxSettingTimeIndex(){
		try {
			if (QUERY.equals(model.getDoType())) {
				User  user =  getUser();
				model.setYhm(user.getYhm());
				model.setJsdm(user.getJsdm());
				QueryModel queryModel = model.getQueryModel();
				//
				List<TimeSettingModel> timeSettingList = getService().getModelList(model);
				queryModel.setItems(timeSettingList);
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
	 *@描述：保存时间设置 表格形式
	 *@创建人:wwb
	 *@创建时间:2015-2-10下午06:12:45
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String bcSettingTime(){
		try {
			getService().updateSettingTime(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	/**
	 * 
	 *@描述：跳转至功能时间控制主页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10下午12:16:15
	 *@修改人:wwb
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxTimeSettingIndex(){
		try {
			User  user =  getUser();
			if (QUERY.equals(model.getDoType())) {
				
				 
				model.setYhm(user.getYhm());
				model.setJsdm(user.getJsdm());
				//
				QueryModel queryModel = model.getQueryModel();
				//
				List<TimeSettingModel> timeSettingList = getService().getTreeGridModelList(model);
				for (TimeSettingModel timeSettingModel : timeSettingList) {
					timeSettingModel.setExpanded(true);
				}
				queryModel.setItems(timeSettingList);
				getValueStack().set(DATA, queryModel);
				return DATA;
			}else{
				 // 所有功能模块：生成tab
				jsdnmkModel.setSqrJsdm(user.getJsdm());
					
				List<JsgnmkModel> list = getJsgnmkService().cxYjGnmkdmList(jsdnmkModel);
				getValueStack().set("gnmkList", list);
				return SUCCESS;
			}
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}
	
	/**
	 * 
	 *@描述：跳转至功能时间控制设置页面
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10下午12:29:45
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxTimeSetting(){
		User  user =  getUser();
		// 所有功能模块：生成tab
		jsdnmkModel.setSqrJsdm(user.getJsdm());
		
		model = getService().getModel(model);
		
		List<JsgnmkModel> list = getJsgnmkService().cxYjGnmkdmList(jsdnmkModel);
		getValueStack().set("gnmkList", list);
		
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：关闭设置时间控 
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10下午12:30:00
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String tySetting(){
		try {
			getService().updateStatus(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：开启时间控制
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10下午12:30:00
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String qySetting(){
		try {
			getService().updateStatus(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：设置功能对应的时间控制信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-2-10下午12:30:48
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String kzsjSetting(){
		try {
			getService().updateTimeSetting(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}

	public ICommonTimeSettingService getService() {
		return service;
	}

	public void setService(ICommonTimeSettingService service) {
		this.service = service;
	}

	public TimeSettingModel getModel() {
		return model;
	}

	public IJsgnmkService getJsgnmkService() {
		return jsgnmkService;
	}

	public void setJsgnmkService(IJsgnmkService jsgnmkService) {
		this.jsgnmkService = jsgnmkService;
	}

	
}

