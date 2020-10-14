package com.woshidaniu.dataauth.controller;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.dataauth.dao.entities.DataRule;
import com.woshidaniu.dataauth.service.svcinterface.DataRuleService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dataauth/data/rule")
public class DataRuleController extends BaseController{
	
	@Autowired
    DataRuleService dataRuleService;
	
	@PostMapping("/ruleList")
	@RequiresPermissions("dataauth:datarule:cx")
	@ResponseBody
	public Object ruleList(DataRule rule, Integer offset, Integer limit) {
		rule.getQueryModel().setShowCount(limit);
		rule.getQueryModel().setCurrentPage(offset / limit + 1);
		List<DataRule> items = dataRuleService.getPagedList(rule);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (DataRule item : items) {
			mapList.add(item.toMap());
		}
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", mapList);res.put("total", rule.getQueryModel().getTotalResult());
		return res;
	}
	
	@PostMapping(value = "/form", produces = "text/html; charset=UTF-8")
	@RequiresPermissions(value = {"dataauth:datarule:zj","dataauth:datarule:xg"}, logical = Logical.OR)
	public String form(String groupId, String ruleId, ModelMap model) {
		if(StringUtils.isNoneBlank(ruleId)) {
			model.addAttribute("rule", dataRuleService.getModel(ruleId));
		}
		model.addAttribute("groupId", groupId);
		return "dataauth/ruleForm";
	}
	
	@PostMapping(value = "/save")
	@RequiresPermissions(value = {"dataauth:datarule:zj","dataauth:datarule:xg"}, logical = Logical.OR)
	@ResponseBody
	public Object save(DataRule rule) {
		return dataRuleService.saveRule(rule);
	}
	
	@PostMapping("/delete.zf")
	@RequiresPermissions("dataauth:datarule:sc")
	public @ResponseBody Object delete(String ruleId) {
		if(dataRuleService.deleteRule(ruleId) > 0){
			return MessageKey.DELETE_SUCCESS.getJson();			
		}else{
			return MessageKey.DELETE_FAIL.getJson();
		}
	}
	
	@PostMapping("/disable.zf")
	@RequiresPermissions("dataauth:datarule:xg")
	public @ResponseBody Object disable(String ruleId) {
		if(dataRuleService.updateEnable(ruleId, false) > 9){
			return MessageKey.MODIFY_SUCCESS.getJson();			
		}else{
			return MessageKey.MODIFY_FAIL.getJson();
		}
	}
	
	@PostMapping("/enable.zf")
	@RequiresPermissions("dataauth:datarule:xg")
	@ResponseBody
	public Object enable(String ruleId) {
		if(dataRuleService.updateEnable(ruleId, true) > 0){
			return MessageKey.MODIFY_SUCCESS.getJson();			
		}else{
			return MessageKey.MODIFY_FAIL.getJson();
		}
	}
}
