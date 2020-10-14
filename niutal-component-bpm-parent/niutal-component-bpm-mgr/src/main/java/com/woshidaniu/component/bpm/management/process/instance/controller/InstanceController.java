package com.woshidaniu.component.bpm.management.process.instance.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.extend.service.ExtendService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woshidaniu.component.bpm.BPMUtils;
import com.woshidaniu.component.bpm.management.BaseBPMController;
import com.woshidaniu.component.bpm.management.process.instance.service.PorcessInstanceService;


/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：流程实例控制器
 *	 <br>class：com.woshidaniu.component.bpm.management.process.instance.controller.InstanceController.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
@Controller
@RequestMapping("/processInstance")
public class InstanceController extends BaseBPMController {

	@Autowired
	protected PorcessInstanceService service;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected ExtendService extendService;
	
	protected ObjectMapper objectMapper = new ObjectMapper();
	
	//流程任务处理的表单
	protected String taskProcessForm;
	
	@RequestMapping("/{taskId}/processTask")
	public String processTask(@PathVariable String taskId, HttpServletRequest request){
		
		return null;
	}
	
	@RequestMapping("/{processInstanceId}/{taskInstanceId}/getTaskProcessForm")
	public String doProcessTask(@PathVariable String processInstanceId, @PathVariable String taskInstanceId, HttpServletRequest request){
		if(taskInstanceId != null){
			Task taskInfo = taskService.createTaskQuery().taskId(taskInstanceId).singleResult();
			request.setAttribute("taskInfo", taskInfo);
		}
		return getTaskProcessForm() == null ? "/processManagement/task/taskProcessForm" : getTaskProcessForm();
	}

	@ResponseBody
	@RequestMapping(value="/{processInstanceId}/{taskInstanceId}/getFallbackableActivityList", produces="application/json;charset=utf-8")
	public Object getFallbackableActivityList(@PathVariable String processInstanceId, @PathVariable String taskInstanceId, HttpServletRequest request){
		try {
			List<HistoricActivityInstance> fallbackableActivityList = null;
			if(BPMUtils.isNotBlank(processInstanceId) && BPMUtils.isNotBlank(taskInstanceId)){
				fallbackableActivityList = extendService.getFallbackableTaskInfoList(processInstanceId, taskInstanceId);
			}
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			return objectMapper.writeValueAsString(fallbackableActivityList);
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}
	/**
	 * 
	 * <p>方法说明：简单流程跟踪，文字形式<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月12日上午11:18:55<p>
	 */
	@RequestMapping("/{processInstanceId}/simpleTrace")
	public String simpleTrace(@PathVariable String processInstanceId, HttpServletRequest request){
		List<Map<String, String>> traceProcess = service.traceProcessInstance(processInstanceId);
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		request.setAttribute("processInstnceId", processInstanceId);
		request.setAttribute("processDefinitionId", processInstance.getProcessDefinitionId());
		request.setAttribute("colorValue", request.getParameter("colorValue"));
		request.setAttribute("traceProcess", traceProcess);
		return "/processManagement/instance/simpleTrace";
	}
	
	/**
	 * 
	 * <p>方法说明：高级流程跟踪，图片形式<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月12日上午11:18:55<p>
	 */
	@RequestMapping("/{processInstanceId}/advancedTrace")
	public String advancedTrace(@PathVariable String processInstanceId, HttpServletRequest request){
		return null;
	}

	public String getTaskProcessForm() {
		return taskProcessForm;
	}

	public void setTaskProcessForm(String taskProcessForm) {
		this.taskProcessForm = taskProcessForm;
	}
	
	
}
