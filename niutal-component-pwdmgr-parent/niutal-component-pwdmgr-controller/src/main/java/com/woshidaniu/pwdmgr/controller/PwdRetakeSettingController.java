package com.woshidaniu.pwdmgr.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.api.data.CsszPropsProvider;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.BusinessLog;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategyManager;
import com.woshidaniu.pwdmgr.dao.entities.StrategyModel;
import com.woshidaniu.pwdmgr.dao.entities.VerifiModel;
import com.woshidaniu.pwdmgr.service.svcinterface.StrategyService;
import com.woshidaniu.pwdmgr.service.svcinterface.VerifiService;

/**
 * 
 *@类名称		： PwdRetakeSettingController.java
 *@类描述		：找回密码预设参数管理控制器
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月19日 上午10:08:57
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Controller
@RequestMapping(value = "/pwdmgr/setting/")
public class PwdRetakeSettingController extends BaseController{
	
	@Resource(name = "pwdStrategyManager")
	protected PwdStrategyManager strategyManager;
	@Resource
	protected StrategyService strategyService;
	@Resource
	protected VerifiService verifiService;
	@Resource
	private CsszPropsProvider csszPropsProvider;
	
	/*--------------------密码找回方式设置--------------------------------------------------*/
	
	@RequiresPermissions("pwdmgr-strategy:cx")
	@RequestMapping(value="strategy")
	public String strategyIndex(HttpServletRequest request){
		
		return "/pwdmgr/strategyList";
	}
	
	@ResponseBody
	@RequiresPermissions("pwdmgr-strategy:cx")
	@RequestMapping(value="strategyList")
	public Object strategyList(StrategyModel strategyModel){
		
		try {
			
			QueryModel queryModel = strategyModel.getQueryModel();
			
			/*
			 * 查询所有的找回策略
			 */
			List<StrategyModel> strategyList = getStrategyService().getModelList(strategyModel);
			if(strategyList != null){
				for (StrategyModel strategy : strategyList) {
					if(!getStrategyManager().retakeStrategys().contains(strategy.getName())){
						strategy.setStatus("-1");
					}
				}
			}
			queryModel.setItems(strategyList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}
	
	@ResponseBody
	@RequiresPermissions("pwdmgr-strategy:xg")
	@RequestMapping(value="strategyUpdate")
	public Object strategyUpdate(StrategyModel strategyModel){
		try {
			boolean result = getStrategyService().update(strategyModel);
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		}  catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	/*--------------------校验字段维护--------------------------------------------------*/
	
	@RequiresPermissions("pwdmgr-verifi:cx")
	@RequestMapping(value="verifi")
	public String verifiIndex(HttpServletRequest request){
		return "/pwdmgr/verifiList";
	}
	
	@ResponseBody
	@RequiresPermissions("pwdmgr-verifi:cx")
	@RequestMapping(value="verifiList")
	public Object verifiList(VerifiModel verifiModel){
		try {
			
			QueryModel queryModel = verifiModel.getQueryModel();
			List<VerifiModel> verifiList = getVerifiService().getModelList(verifiModel);
			
			queryModel.setItems(verifiList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}
	
	@BusinessLog(czmk = "系统管理", czms = "更新身份校验字段", ywmc = "密码找回", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("pwdmgr-verifi:bc")
	@RequestMapping(value="updateVerifi")
	public Object updateVerifi(VerifiModel verifiModel){
		try {
			boolean result = getVerifiService().updateVerifi(verifiModel);
			MessageKey key = result ? MessageKey.MODIFY_SUCCESS : MessageKey.MODIFY_FAIL;
			return key.getJson();
		}  catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@BusinessLog(czmk = "系统管理", czms = "删除身份校验字段", ywmc = "密码找回", czlx = BusinessType.DELETE)
	@ResponseBody
	@RequiresPermissions("pwdmgr-verifi:sc")
	@RequestMapping(value="delVerifi")
	public Object delVerifi(VerifiModel verifiModel){
		try {
			boolean result = getVerifiService().delete(verifiModel);
			MessageKey key = result ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL;
			return key.getJson();
		}  catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	/*--------------------参数设置--------------------------------------------------*/
	
	@RequiresPermissions("pwdmgr-props:cx")
	@RequestMapping(value="props")
	public String props(HttpServletRequest request){
		return "/pwdmgr/props_settings";
	}
	
	@RequiresPermissions("pwdmgr-props:cx")
	@RequestMapping(value="/props/list")
	@ResponseBody
	public Object propsList(HttpServletRequest request,String ssmk, String ssgnmkdm, String zs){
		try {
			QueryModel queryModel = new QueryModel();
			List verifiList = getCsszPropsProvider().list(ssmk, ssgnmkdm, zs, request.getParameterMap());
			queryModel.setItems(verifiList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}
	
	/**
	 * 
	 *@描述		：修改设置参数
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年5月10日上午10:20:17
	 *@param request
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	@BusinessLog(czmk = "系统管理", czms = "修改设置参数", ywmc = "密码找回", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("pwdmgr-props:xg")
	@RequestMapping(value = "/props/update")
	public Object update(HttpServletRequest request,String ssmk, String ssgnmkdm, String zs) { 
		try {
			getCsszPropsProvider().update(ssmk, ssgnmkdm, zs, request.getParameterMap());
			return MessageKey.SAVE_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	public CsszPropsProvider getCsszPropsProvider() {
		return csszPropsProvider;
	}

	public void setCsszPropsProvider(CsszPropsProvider csszPropsProvider) {
		this.csszPropsProvider = csszPropsProvider;
	}

	public StrategyService getStrategyService() {
		return strategyService;
	}

	public void setStrategyService(StrategyService strategyService) {
		this.strategyService = strategyService;
	}

	public VerifiService getVerifiService() {
		return verifiService;
	}

	public void setVerifiService(VerifiService verifiService) {
		this.verifiService = verifiService;
	}

	public PwdStrategyManager getStrategyManager() {
		return strategyManager;
	}

	public void setStrategyManager(PwdStrategyManager strategyManager) {
		this.strategyManager = strategyManager;
	}
	
}
