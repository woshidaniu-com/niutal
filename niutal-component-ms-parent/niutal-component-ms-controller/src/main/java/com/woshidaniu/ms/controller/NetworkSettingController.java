package com.woshidaniu.ms.controller;

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

/**
 * 
 *@类名称		： NetworkSettingController.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：2017年5月10日 上午9:51:59
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Controller
@RequestMapping(value = "/ms/network/")
public class NetworkSettingController extends BaseController{
	
	@Resource
	private CsszPropsProvider csszPropsProvider;
	
	@RequiresPermissions("ms-net:cx")
	@RequestMapping(value="settings")
	public String network_settings(HttpServletRequest request){
		
		return "/ms/network_settings";
	}
	
	@RequiresPermissions("ms-net:cx")
	@RequestMapping(value="list")
	@ResponseBody
	public Object sms_settings(HttpServletRequest request,String ssmk, String ssgnmkdm, String zs){
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
	 *@描述		：修改网络环境参数
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年5月10日上午10:20:17
	 *@param request
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	@BusinessLog(czmk = "系统管理", czms = "修改网络环境参数", ywmc = "消息服务", czlx = BusinessType.UPDATE)
	@ResponseBody
	@RequiresPermissions("ms-net:xg")
	@RequestMapping(value = "/update")
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
	
	
}
