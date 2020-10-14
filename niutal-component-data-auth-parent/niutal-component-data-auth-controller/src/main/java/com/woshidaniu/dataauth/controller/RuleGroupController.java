package com.woshidaniu.dataauth.controller;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.dataauth.dao.entities.RuleGroup;
import com.woshidaniu.dataauth.service.svcinterface.RuleGroupService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dataauth/data/rule/group")
public class RuleGroupController extends BaseController{
	
	@Autowired
    private RuleGroupService ruleGroupService;
	
	@GetMapping("/index")
	@RequiresPermissions("dataauth:datarule:cx")
	public String index(Model model) {
		return "dataauth/group";
	}
	
	@PostMapping("/groupList")
	@RequiresPermissions("dataauth:datarule:cx")
	@ResponseBody
	public Object groupList(RuleGroup ruleGroup, Integer offset, Integer limit) {
		ruleGroup.getQueryModel().setShowCount(limit);
		ruleGroup.getQueryModel().setCurrentPage(offset / limit + 1);
		List<RuleGroup> items = ruleGroupService.getPagedList(ruleGroup);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (RuleGroup item : items) {
			mapList.add(item.toMap());
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", mapList);res.put("total", ruleGroup.getQueryModel().getTotalResult());
		return res;
	}
	
	@PostMapping("/checkList")
	@RequiresPermissions("dataauth:datarule:cx")
	@ResponseBody
	public Object checkList(String mapperId) {
		return ruleGroupService.checkList(mapperId);
	}
	
	@PostMapping(value = "/form", produces = "text/html; charset=UTF-8")
	@RequiresPermissions(value = {"dataauth:datarule:zj","dataauth:datarule:xg"}, logical = Logical.OR)
	public String tableForm(String groupId,ModelMap model) {
		if(StringUtils.isNoneBlank(groupId)) {
			model.addAttribute("ruleGroup", ruleGroupService.getModel(groupId));
		}
		return "dataauth/groupForm";
	}
	
	@PostMapping(value = "/save")
	@RequiresPermissions(value = {"dataauth:datarule:zj","dataauth:datarule:xg"}, logical = Logical.OR)
	@ResponseBody
	public Object save(RuleGroup ruleGroup) {
		Object ret = ruleGroupService.saveRuleGroup(ruleGroup);
		if(ret != null){
			return MessageKey.SAVE_SUCCESS.getJson();			
		}else{
			return MessageKey.SAVE_FAIL.getJson();
		}
	}
	
	@PostMapping("/delete")
	@RequiresPermissions("dataauth:datarule:sc")
	@ResponseBody
	public Object delete(String groupId) {
		if(ruleGroupService.deleteRuleGroup(groupId, true) > 0){
			return MessageKey.DELETE_SUCCESS.getJson();			
		}else{
			return MessageKey.DELETE_FAIL.getJson();
		}
	}
}