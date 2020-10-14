package com.woshidaniu.globalweb.controller;

import java.util.Collections;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.service.svcinterface.IFuncMenuService;
import com.woshidaniu.service.svcinterface.IMutileRoleContextFuncMenuService;
import com.woshidaniu.service.svcinterface.ISystemConfigService;
import com.woshidaniu.web.context.WebContext;

@Controller
@RequestMapping(value = "/func/nav/")
public class FuncMenuController extends BaseController{

	@Resource(name  = "funcMenuService")
	protected IFuncMenuService funcMenuService;
	
	@Resource(name  = "mutileRoleContextFuncMenuService")
	protected IMutileRoleContextFuncMenuService mutileRoleContextFuncMenuService;
	
	@Autowired
	private ISystemConfigService systemConfigService; 
	
	@ResponseBody
	@RequestMapping("topMenuList")
	public Object getTopList(){
		try {
			// 获取登录用户
			User user = getUser();
			//Locale
	        Locale locale = WebContext.getLocale();
			String localeKey = locale.getLanguage() + "_" + locale.getCountry();
			
			if(this.systemConfigService.isEnableMutileRoleContext()){
				return this.mutileRoleContextFuncMenuService.getTopNavMenuList(user.getYhm(), user.getJsdms(), localeKey);
			}else{
				return this.funcMenuService.getTopNavMenuList(user.getYhm(), user.getJsdm(), localeKey);	
			}
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@ResponseBody
	@RequestMapping("childMenuList")
	public Object getChildList(@RequestParam("parent") String parentGnmkdm){
		try {
			if(StringUtils.isEmpty(parentGnmkdm)){
				return Collections.EMPTY_MAP;
			}
			
			// 获取登录用户
			User user = getUser();
			//Locale
	        Locale locale = WebContext.getLocale();
			String localeKey = locale.getLanguage() + "_" + locale.getCountry();
			
			if(this.systemConfigService.isEnableMutileRoleContext()){
				return this.mutileRoleContextFuncMenuService.getChildNavMenuList(user.getYhm(), user.getJsdms(), parentGnmkdm, localeKey);
			}else{
				return this.funcMenuService.getChildNavMenuList(user.getYhm(), user.getJsdm(), parentGnmkdm, localeKey);				
			}
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@ResponseBody
	@RequestMapping("childMenuTreeList")
	public Object getChildNavMenuTreeList(@RequestParam("parent") String parentGnmkdm){
		try {
			
			if(StringUtils.isEmpty(parentGnmkdm)){
				return Collections.EMPTY_MAP;
			}
			
			// 获取登录用户
			User user = getUser();
			//Locale
	        Locale locale = WebContext.getLocale();
			String localeKey = locale.getLanguage() + "_" + locale.getCountry();
			
			if(this.systemConfigService.isEnableMutileRoleContext()){
				return this.mutileRoleContextFuncMenuService.getChildNavMenuTreeList(user.getYhm(), user.getJsdms(),parentGnmkdm, localeKey);
			}else{
				return this.funcMenuService.getChildNavMenuTreeList(user.getYhm(), user.getJsdm(), parentGnmkdm, localeKey);				
			}
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@ResponseBody
	@RequestMapping("treeMenuList")
	public Object getTreeList(){
		try {
			// 获取登录用户
			User user = getUser();
			//Locale
	        Locale locale = WebContext.getLocale();
			String localeKey = locale.getLanguage() + "_" + locale.getCountry();
			
			if(this.systemConfigService.isEnableMutileRoleContext()){
				return this.mutileRoleContextFuncMenuService.getNavMenuTreeList(user.getYhm(), user.getJsdms(), localeKey);
			}else{
				return this.funcMenuService.getNavMenuTreeList(user.getYhm(), user.getJsdm(), localeKey);
			}
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

}
