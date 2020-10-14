package com.woshidaniu.globalweb.action;



import java.util.List;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.JsgnmkModel;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.IJsgnmkService;

/**
 * 
 *@类名称:JsgnmkAction.java
 *@类描述：角色功能模块action
 *@创建人：kangzhidong
 *@创建时间：2014-10-20 上午09:22:14
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class JsgnmkAction extends CommonBaseAction implements ModelDriven<JsgnmkModel> {

	// 全局MODEL
	protected JsgnmkModel model = new JsgnmkModel();
	protected JsglModel jsModel = new JsglModel();
	//角色管理SERVICE
	@Resource
	private IJsglService jsglService;
	//角色功能模块service
	@Resource
	private IJsgnmkService jsgnmkService;

	/**
	 * 
	 *@描述：功能授权页面跳转
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午07:51:16
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJsgnsqIndex() {

		try {
			
			// 查询当前登录用户正激活角色信息
			boolean sfersq = !getUser().isAdmin() ? getJsglService().getYhEjsq(getUser().getJsdm()) : true;
			//判断是否允许二级授权
			if(sfersq){
				//当前用户角色代码
				model.setFjsdm(getUser().getJsdm());
				//根据角色代码查询角色的所以父级才是是否任意一个具有二级授权功能
				sfersq = getJsgnmkService().getParentSfejsq(model);
				//有权限
				if(!sfersq ){
				
					getValueStack().set("sfejsqFlag",1);
					// 所有功能模块：生成tab
					model.setSqrJsdm(getUser().getJsdm());
					
					List<JsgnmkModel> list = getJsgnmkService().cxYjGnmkdmList(model);
					getValueStack().set("allGnmkList", list);
				
					//清空功能模块代码，防止权限验证的干扰
					model.setGnmkdm(null);
					//设置要进行授权操作的角色
					jsModel.setJsdm(model.getJsdm());
					// 查询当前登录用户正激活角色信息
					JsglModel jsdmModel = getJsglService().getModel(jsModel);
					// 用户正登录的角色信息
					getValueStack().set("model", jsdmModel);
				}else{
					getValueStack().set("sfejsqFlag",2);
				}
			}else{
				getValueStack().set("sfejsqFlag", 0);
			}
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
		
	/**
	 * 
	 *@描述：根据用户+角色+功能模块代码查询下属所有子级菜单
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午09:42:31
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxJsgnmkDisplay() {
		try {
			//设置授权人角色代码
			model.setSqrJsdm(getUser().getJsdm());
			//清空功能模块代码，防止权限验证的干扰
			model.setGnmkdm(null);
			//返回当前用户登录角色的功能菜单
			getValueStack().set(DATA, getJsgnmkService().cxGnmkdmList(model));
		} catch (Exception e) {
			logStackException(e);
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：角色功能授权更新：无则保存，有则更新
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午09:28:43
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String jsgnsqUpdate(){
		try {
			//设置授权人角色代码
			model.setSqrJsdm(getUser().getJsdm());
			
			
			jsModel.setJsdm(getUser().getJsdm());
			getJsglService().getModel(jsModel);
			
			//更新角色功能授权信息
			getJsgnmkService().updateJsgnsqxx(model);
			getValueStack().set(DATA, getText("I99001"));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99002"));
		}
		return DATA;
	}
	/**
	 * 查询学生和教师功能模块
	 *@创建人:马俊
	 *@创建时间:2015-04-22 14：46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 * @return
	 */
	public String cxJsmkIndex(){
		try {
			// 查询当前登录用户正激活角色信息
			boolean sfersq = !getUser().isAdmin() ? getJsglService().getYhEjsq(getUser().getJsdm()) : true;
			//判断是否允许二级授权
			if(sfersq){
				getValueStack().set("jsgnList", getJsgnmkService().getJsgnList(model.getJsdm(),"0"));
				getValueStack().set("sfejsqFlag",1);
			}else{
				getValueStack().set("sfejsqFlag", 0);
			}
		} catch (Exception e) {
			logException(e);
		}
		
		return SUCCESS;
	}
	/***
	 * 
	 * @return
	 */
	public String jsgnsqUpdate1(){
		
		return DATA;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public IJsgnmkService getJsgnmkService() {
		return jsgnmkService;
	}

	public void setJsgnmkService(IJsgnmkService jsgnmkService) {
		this.jsgnmkService = jsgnmkService;
	}

	public JsgnmkModel getModel() {
		model.setModelBase(getUser());
		model.setUser(getUser());
		return model;
	}


	public void setModel(JsgnmkModel model) {
		this.model = model;
	}
	
}
