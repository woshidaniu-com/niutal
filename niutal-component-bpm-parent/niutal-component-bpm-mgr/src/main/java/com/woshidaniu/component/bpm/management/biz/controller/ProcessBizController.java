/**
 * 
 */
package com.woshidaniu.component.bpm.management.biz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.extend.biz.BizField;
import org.activiti.engine.extend.service.ExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.component.bpm.management.BaseBPMController;
import com.woshidaniu.component.bpm.management.biz.dao.entities.ProcessBizModel;
import com.woshidaniu.component.bpm.management.biz.service.svcinterface.IProcessBizService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：流程业务对象Controller
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.biz.controller.ProcessBizController.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月7日上午9:06:23
 */
@Controller
@RequestMapping("/processBiz")
public class ProcessBizController extends BaseBPMController {

	@Autowired
	protected IProcessBizService processBizService;
	@Autowired
	protected ExtendService extendService;
	
	protected ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping("/list.zf")
	public String list(HttpServletRequest request) {
		try {
			return "/processManagement/biz/list";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/listData.zf")
	public Object listData(ProcessBizModel model) {
		try {
			QueryModel queryModel = model.getQueryModel();
			List<ProcessBizModel> pagedList = processBizService.getPagedList(model);
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}

	@RequestMapping("/{id}/ck.zf")
	public Object ck(@PathVariable String id) {
		try {
			ProcessBizModel model = processBizService.getModel(id);
			ModelAndView modelAndView = new ModelAndView("/processManagement/biz/ck");
			modelAndView.addObject("model", model);
			return modelAndView;
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	@ResponseBody
	@RequestMapping("/{id}/getProcessBizFields.zf")
	public Object getProcessBizFields(@PathVariable String id){
		List<BizField> bizFiledList = extendService.getBizFiledList(id);
		ArrayNode arrayNode = objectMapper.createArrayNode();
		if(bizFiledList != null){
			for (BizField bizField : bizFiledList) {
				ObjectNode objectNode = objectMapper.createObjectNode();
				objectNode.put("bizId", bizField.getBizId());
				objectNode.put("description", bizField.getDescription());
				objectNode.put("id", bizField.getId());
				objectNode.put("label", bizField.getLabel());
				objectNode.put("name", bizField.getName());
				objectNode.put("type", bizField.getType());
				objectNode.put("values", bizField.getValues());
				arrayNode.add(objectNode);
			}
		}
		return arrayNode;
	}

}
