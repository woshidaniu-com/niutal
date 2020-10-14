package com.woshidaniu.component.bpm.management.process.definition.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.persistence.entity.AssignmentEntity;
import org.activiti.engine.extend.service.ExtendService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.component.bpm.BPMUtils;
import com.woshidaniu.component.bpm.management.BaseBPMController;
import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentGroupEntity;
import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentUserEntity;
import com.woshidaniu.component.bpm.management.process.definition.service.svcinterface.IAssignmentQueryService;
import com.woshidaniu.search.core.SearchParser;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：TODO <br>
 * class：com.woshidaniu.component.bpm.management.process.definition.controller.AssignmentController.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
@Controller
@RequestMapping("/processDefinition/assignment")
public class AssignmentController extends BaseBPMController {

	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	protected ExtendService extendService;
	@Autowired
	protected IAssignmentQueryService assignmentQueryService;

	protected ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping("/{processDefinitionId}/setup.zf")
	public ModelAndView setup(@PathVariable String processDefinitionId, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Map<String, TaskDefinition> taskDefinitions = null;
			ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
			if (processDefinition != null) {
				taskDefinitions = ((ProcessDefinitionEntity) processDefinition).getTaskDefinitions();
			}
			modelAndView.addObject("processDefinition", processDefinition);
			modelAndView.addObject("taskDefinitions", taskDefinitions);
			modelAndView.setViewName("/processManagement/definition/assignment_setup");
			return modelAndView;
		} catch (Exception e) {
			logException(e);
			modelAndView.setViewName(ERROR_VIEW);
			return modelAndView;
		}
	}

	@RequestMapping("/{processDefinitonId}/{taskDefinitionId}/list.zf")
	public ModelAndView list(@PathVariable String processDefinitonId, @PathVariable String taskDefinitionId,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("processDefinitonId", processDefinitonId);
			modelAndView.addObject("taskDefinitionId", taskDefinitionId);
			modelAndView.setViewName("/processManagement/definition/assignment_list");
			return modelAndView;
		} catch (Exception e) {
			logException(e);
			modelAndView.setViewName(ERROR_VIEW);
			return modelAndView;
		}
	}

	@RequestMapping("/{processDefinitonId}/{taskDefinitionId}/list/user.zf")
	public ModelAndView listUser(@PathVariable String processDefinitonId, @PathVariable String taskDefinitionId,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("processDefinitonId", processDefinitonId);
			modelAndView.addObject("taskDefinitionId", taskDefinitionId);
			modelAndView.setViewName("/processManagement/definition/assignment_user");
			return modelAndView;
		} catch (Exception e) {
			logException(e);
			modelAndView.setViewName(ERROR_VIEW);
			return modelAndView;
		}
	}

	@ResponseBody
	@RequestMapping("/{type}/list/userData.zf")
	public Object listUserData(@PathVariable String type, AssignmentUserEntity model, HttpServletRequest request) {
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<?> pagedList = null;
			if (StringUtils.equals("1", type)) {
				pagedList = assignmentQueryService.getPagedAssignedUserList(model);
			} else {
				pagedList = assignmentQueryService.getPagedUnassignedUserList(model);
			}
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/{assignmentId}/delAssignment.zf")
	public Object delAssignment(@PathVariable String assignmentId, HttpServletRequest request){
		try {
			List<Assignment> taskAssignment = new ArrayList<Assignment>();
			AssignmentEntity assignemntEntity = AssignmentEntity.create(assignmentId);
			taskAssignment.add(assignemntEntity);
			extendService.delTaskAssignment(taskAssignment);
			return BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/batchDelAssignment.zf")
	public Object batchDelAssignment(HttpServletRequest request){
		try {
			String[] assignmentIds = BPMUtils.split(request.getParameter("assignmentIds"), ",");
			List<Assignment> taskAssignment = new ArrayList<Assignment>();
			for (String assignmentId : assignmentIds) {
				AssignmentEntity assignemntEntity = AssignmentEntity.create(assignmentId);
				taskAssignment.add(assignemntEntity);
			}
			extendService.delTaskAssignment(taskAssignment);
			return BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/{userName}/addUser.zf")
	public Object addUser(@PathVariable String userName, HttpServletRequest request){
		try {
			String processDefinitionId = request.getParameter("processDefintionId");
			String taskDefinitonId = request.getParameter("taskDefintionId");
			List<Assignment> taskAssignment = new ArrayList<Assignment>();
			AssignmentEntity assignemntEntity = AssignmentEntity.create(processDefinitionId,
					taskDefinitonId);
			assignemntEntity.setUserId(userName);
			taskAssignment.add(assignemntEntity);
			extendService.addTaskAssignment(processDefinitionId, taskAssignment);
			return BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/batchAddUser.zf")
	public Object batchAddUser(HttpServletRequest request){
		try {
			String processDefinitionId = request.getParameter("processDefintionId");
			String taskDefinitonId = request.getParameter("taskDefintionId");
			String[] userNames = BPMUtils.split(request.getParameter("userNames"), ",");
			List<Assignment> taskAssignment = new ArrayList<Assignment>();
			for (String userName : userNames) {
				AssignmentEntity assignemntEntity = AssignmentEntity.create(processDefinitionId,
						taskDefinitonId);
				assignemntEntity.setUserId(userName);
				taskAssignment.add(assignemntEntity);
			}
			extendService.addTaskAssignment(processDefinitionId, taskAssignment);
			return BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
		
	}
	
	@RequestMapping("/{processDefinitonId}/{taskDefinitionId}/list/group.zf")
	public ModelAndView listGroup(@PathVariable String processDefinitonId, @PathVariable String taskDefinitionId,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("processDefinitonId", processDefinitonId);
			modelAndView.addObject("taskDefinitionId", taskDefinitionId);
			modelAndView.setViewName("/processManagement/definition/assignment_group");
			return modelAndView;
		} catch (Exception e) {
			logException(e);
			modelAndView.setViewName(ERROR_VIEW);
			return modelAndView;
		}
	}

	@ResponseBody
	@RequestMapping("/{type}/list/groupData.zf")
	public Object listGroupData(@PathVariable String type, AssignmentGroupEntity model, HttpServletRequest request) {
		try {
			SearchParser.parseMybatisSQL(model.getSearchModel());
			QueryModel queryModel = model.getQueryModel();
			List<?> pagedList = null;
			if (StringUtils.equals("1", type)) {
				pagedList = assignmentQueryService.getPagedAssignedGroupList(model);
			} else {
				pagedList = assignmentQueryService.getPagedUnassignedGroupList(model);
			}
			queryModel.setItems(pagedList);
			return queryModel;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/{group}/addGroup.zf")
	public Object addGroup(@PathVariable String group, HttpServletRequest request){
		try {
			String processDefinitionId = request.getParameter("processDefintionId");
			String taskDefinitonId = request.getParameter("taskDefintionId");
			List<Assignment> taskAssignment = new ArrayList<Assignment>();
			AssignmentEntity assignemntEntity = AssignmentEntity.create(processDefinitionId,
					taskDefinitonId);
			assignemntEntity.setGroupId(group);
			taskAssignment.add(assignemntEntity);
			extendService.addTaskAssignment(processDefinitionId, taskAssignment);
			return BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/batchAddGroup.zf")
	public Object batchAddGroup(HttpServletRequest request){
		try {
			String processDefinitionId = request.getParameter("processDefintionId");
			String taskDefinitonId = request.getParameter("taskDefintionId");
			String[] groups = BPMUtils.split(request.getParameter("groups"), ",");
			List<Assignment> taskAssignment = new ArrayList<Assignment>();
			for (String group : groups) {
				AssignmentEntity assignemntEntity = AssignmentEntity.create(processDefinitionId,
						taskDefinitonId);
				assignemntEntity.setGroupId(group);
				taskAssignment.add(assignemntEntity);
			}
			extendService.addTaskAssignment(processDefinitionId, taskAssignment);
			return BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
		
	}
	
	@RequestMapping("/{processDefinitonId}/{taskDefinitionId}/list/clazz.zf")
	public ModelAndView listClazz(@PathVariable String processDefinitonId, @PathVariable String taskDefinitionId,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("processDefinitonId", processDefinitonId);
			modelAndView.addObject("taskDefinitionId", taskDefinitionId);
			modelAndView.setViewName("/processManagement/definition/assignment_clazz");
			return modelAndView;
		} catch (Exception e) {
			logException(e);
			modelAndView.setViewName(ERROR_VIEW);
			return modelAndView;
		}
	}

	@ResponseBody
	@RequestMapping("/{processDefinitionId}/getAssignment.zf")
	public Object getAssignment(@PathVariable String processDefinitionId, HttpServletRequest request) {

		try {
			List<Assignment> assignmentForProcess = extendService.getAssignmentForProcess(processDefinitionId);
			ObjectNode json = BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
			ArrayNode assignmentArrayNode = json.putArray("assignment");
			for (Assignment assignment : assignmentForProcess) {
				ObjectNode assignmentObjectMapper = objectMapper.createObjectNode();
				assignmentObjectMapper.put("taskDefinitionId", assignment.getTaskDefinitionId());
				assignmentObjectMapper.put("processDefinitionId", assignment.getProcessDefinitionId());
				assignmentObjectMapper.put("userId", assignment.getUserId());
				assignmentObjectMapper.put("groupId", assignment.getGroupId());
				assignmentObjectMapper.put("type", assignment.getType());
				User userEntity = assignment.getUserEntity();
				Group groupEntity = assignment.getGroupEntity();
				assignmentObjectMapper.put("userName", userEntity == null ? null : userEntity.getFirstName());
				assignmentObjectMapper.put("groupName", groupEntity == null ? null : groupEntity.getName());
				assignmentArrayNode.add(assignmentObjectMapper);
			}
			return json.toString();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/{processDefinitionId}/{taskDefinitionId}/getTaskAssignmentCount.zf")
	public Object getTaskAssignmentCount(@PathVariable String processDefinitionId, @PathVariable String taskDefinitionId, HttpServletRequest request){
		try {
			List<Assignment> assignmentForTask = extendService.getAssignmentForTask(processDefinitionId, taskDefinitionId);
			ObjectNode json = BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
			json.put("assignmentCount", assignmentForTask==null ? 0 : assignmentForTask.size());
			return json;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
	}
	
	@ResponseBody
	@RequestMapping("/{processDefinitionId}/save.zf")
	public Object save(@PathVariable String processDefinitionId, @RequestBody String assignmentData,
			HttpServletRequest request) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode assignmentDataJson = objectMapper.readTree(assignmentData);
			Iterator<JsonNode> iterator = assignmentDataJson.iterator();

			List<Assignment> taskAssignment = new ArrayList<Assignment>();

			while (iterator.hasNext()) {
				JsonNode next = iterator.next();
				String taskDefintionId = next.get("taskDefinitionId").asText();
				ArrayNode candidateUsers = (ArrayNode) next.get("taskCandidateUsers");
				ArrayNode candidateGroups = (ArrayNode) next.get("taskCandidateGroups");

				if (candidateUsers != null && candidateUsers.size() > 0) {
					for (JsonNode jsonNode : candidateUsers) {
						String candidateUser = jsonNode.asText();
						AssignmentEntity assignemntEntity = AssignmentEntity.create(processDefinitionId,
								taskDefintionId);
						assignemntEntity.setUserId(candidateUser);
						taskAssignment.add(assignemntEntity);
					}
				}
				if (candidateGroups != null && candidateGroups.size() > 0) {
					for (JsonNode jsonNode : candidateGroups) {
						String candidateGroup = jsonNode.asText();
						AssignmentEntity assignemntEntity = AssignmentEntity.create(processDefinitionId,
								taskDefintionId);
						assignemntEntity.setGroupId(candidateGroup);
						taskAssignment.add(assignemntEntity);
					}
				}
			}
			extendService.setupTaskAssignment(processDefinitionId, taskAssignment);
			return BPMMessageKey.ASSIGNMENT_QUERY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.ASSIGNMENT_QUERY_FAIL.getJson();
		}
	}

}
