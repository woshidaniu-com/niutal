/**
 * 
 */
package com.woshidaniu.component.bpm.management.process.message.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.extend.service.ExtendService;
import org.activiti.engine.extend.task.CommonMessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.component.bpm.management.BaseBPMController;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：CommonMessageController
 * <p>
 * @className:com.woshidaniu.component.bpm.management.process.message.controller.CommonMessageController.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午10:45:59
 */
@Controller
@RequestMapping("/processCommonMessage/")
public class CommonMessageController extends BaseBPMController{

	@Autowired
	protected ExtendService extendService;
	
	protected ObjectMapper objectMapper = new ObjectMapper();
	
	@ResponseBody
	@RequestMapping("/{userId}/getUserCommonMessageList")
	public Object getUserCommonMessageList(@PathVariable String userId){
		try {
			ArrayNode arrayNode = objectMapper.createArrayNode();
			List<CommonMessageInfo> userCommonMessageList = extendService.getUserCommonMessageList(userId);
			if(userCommonMessageList != null){
				for (CommonMessageInfo commonMessageInfo : userCommonMessageList) {
					ObjectNode objectNode = objectMapper.createObjectNode();
					objectNode.put("id", commonMessageInfo.getId());
					objectNode.put("userId", commonMessageInfo.getUserId());
					objectNode.put("message", commonMessageInfo.getMessage());
					arrayNode.add(objectNode);
				}
			}
			return arrayNode;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@ResponseBody
	@RequestMapping("/addCommonMessage")
	public Object addCommonMessage(HttpServletRequest request){
		try {
			String assignee = request.getParameter("userId");
			String message = request.getParameter("message");
			extendService.addCommonMessage(assignee, message);
			return BPMMessageKey.DO_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.DO_FAIL.getJson();
		}
	}
	
	@ResponseBody
	@RequestMapping("/{id}/deleteCommonMessage")
	public Object deleteCommonMessage(@PathVariable String id){
		try {
			extendService.deleteCommonMessage(id);
			return BPMMessageKey.DO_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.DO_FAIL.getJson();
		}
	}
}
