package com.woshidaniu.dataauth.controller;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.dataauth.service.svcinterface.DataAuthService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dataauth/data")
public class DataAuthController extends BaseController{
	
	@Autowired
    private DataAuthService dataAuthService;
	
	@GetMapping("/index")
	@RequiresPermissions("dataauth:dataauth:cx")
	public String list(Model model) {
		model.addAttribute("roles", dataAuthService.listRoles());
		model.addAttribute("users", dataAuthService.listUsers());
		return "dataauth/index";
	}
	
	@PostMapping("/listByType")
	@RequiresPermissions("dataauth:dataauth:cx")
	public @ResponseBody Object listByType(String groupType, String jsdm, String yhm) {
		return dataAuthService.listByType(groupType, jsdm, yhm);
	}
	
	@PostMapping("/itemsByRelation")
	@RequiresPermissions("dataauth:dataauth:cx")
	public @ResponseBody Object itemsByRelation(String groupId, String jsdm, String yhm, String type) {
		return dataAuthService.itemsByRelation(groupId, jsdm, yhm, type);
	}
	
	@PostMapping("/saveRuleGroupRelation")
	@RequiresPermissions("dataauth:dataauth:bc")
	@ResponseBody
	public Object saveRuleGroupRelation(String oper, String groupId,String jsdm, String yhm, String type) {
		if(dataAuthService.saveRuleGroupRelation(oper, groupId, jsdm, yhm, type) > 0){
			return MessageKey.SAVE_SUCCESS.getJson();			
		}else{
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@PostMapping("/saveItemValues")
	@RequiresPermissions("dataauth:dataauth:bc")
	public @ResponseBody Object saveItemValues(String groupId,String jsdm, String yhm, String itemValues, String type) {
		if(dataAuthService.saveItemValues(groupId, jsdm, yhm, itemValues, type) > 0){
			return MessageKey.SAVE_SUCCESS.getJson();			
		}else{
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
}
