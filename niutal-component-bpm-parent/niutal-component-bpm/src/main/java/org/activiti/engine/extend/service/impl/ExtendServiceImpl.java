package org.activiti.engine.extend.service.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.biz.Biz;
import org.activiti.engine.extend.biz.BizField;
import org.activiti.engine.extend.biz.ProcessCategory;
import org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration;
import org.activiti.engine.extend.cmd.AddCommonMessagesCmd;
import org.activiti.engine.extend.cmd.AddTaskAssignmentCmd;
import org.activiti.engine.extend.cmd.AuditorProcessCancellationCmd;
import org.activiti.engine.extend.cmd.AuditorRevocationCmd;
import org.activiti.engine.extend.cmd.DelCommonMessageCmd;
import org.activiti.engine.extend.cmd.DelTaskAssignmentCmd;
import org.activiti.engine.extend.cmd.ExtActivateProcessInstanceCmd;
import org.activiti.engine.extend.cmd.GetBizCmd;
import org.activiti.engine.extend.cmd.GetBizFieldListCmd;
import org.activiti.engine.extend.cmd.GetCommonMessageCmd;
import org.activiti.engine.extend.cmd.GetDecisionTypesCmd;
import org.activiti.engine.extend.cmd.GetFallbakableHisTaskEntitiesCmd;
import org.activiti.engine.extend.cmd.GetProcessAssignmentCmd;
import org.activiti.engine.extend.cmd.GetProcessInstanceInitiatorCmd;
import org.activiti.engine.extend.cmd.GetTaskAssignmentCmd;
import org.activiti.engine.extend.cmd.GetUserCommonMessagesCmd;
import org.activiti.engine.extend.cmd.InitiatorRevocationCmd;
import org.activiti.engine.extend.cmd.PathAnalysisCmd;
import org.activiti.engine.extend.cmd.RebootHistoricProcessInstanceCmd;
import org.activiti.engine.extend.cmd.SetupTaskAssignmentCmd;
import org.activiti.engine.extend.cmd.UpdateCommonMessageCmd;
import org.activiti.engine.extend.cmd.procat.AddProcessCategoryCmd;
import org.activiti.engine.extend.cmd.procat.DeleteProcessCategoryCmd;
import org.activiti.engine.extend.cmd.procat.GetAllProcessCategoriesCmd;
import org.activiti.engine.extend.cmd.procat.GetProcessCategoryCmd;
import org.activiti.engine.extend.cmd.procat.UpdatepProcessCategoryCmd;
import org.activiti.engine.extend.cmd.simulation.AddSimulationActivitiesCmd;
import org.activiti.engine.extend.cmd.simulation.DeleteSimulationActivitiesCmd;
import org.activiti.engine.extend.cmd.tasksubmit.DecisionHandleCmd;
import org.activiti.engine.extend.service.ExtendService;
import org.activiti.engine.extend.task.CommonMessageInfo;
import org.activiti.engine.extend.task.Decision;
import org.activiti.engine.extend.task.DecisionType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：扩展activit服务 <br>
 * class：org.activiti.engine.extend.service.impl.ExtendServiceImpl.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class ExtendServiceImpl extends ServiceImpl implements ExtendService {
	
	public ExtendServiceImpl() {
		super();
	}

	public ExtendServiceImpl(ProcessEngineConfigurationImpl processEngineConfiguration) {
		super(processEngineConfiguration);
	}

	@Override
	public void auditorRevocation(String processInstanceId, String executionId, String taskId) {
		HistoryService historyService = processEngineConfiguration.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		/*如果结束日期不为空，说明流程已经结束*/
		if(historicProcessInstance.getEndTime() != null){
			boolean allowProcessInstanceReboot = ((ExtendSpringProcessEnginConfiguration) processEngineConfiguration)
					.isAllowProcessInstanceReboot();
			if(allowProcessInstanceReboot){
				commandExecutor.execute(new RebootHistoricProcessInstanceCmd(processInstanceId, executionId, taskId));
			}else{
				throw new ActivitiException("BPM_EX_20");
			}
		}else{
			commandExecutor.execute(new AuditorRevocationCmd(processInstanceId, executionId, taskId));
		}
	}

	@Override
	public void auditorProcessCancellation(String processInstanceId, String taskId, String reason) {
		commandExecutor.execute(new AuditorProcessCancellationCmd(processInstanceId, taskId, reason));
	}

	@Override
	public void initiatorRevocation(String processInstanceId) {
		commandExecutor.execute(new InitiatorRevocationCmd(processInstanceId));
	}

	@Override
	public List<Assignment> getAssignmentForTask(String processDefinitionId, String taskDefinitionId) {
		return commandExecutor.execute(new GetTaskAssignmentCmd(processDefinitionId, taskDefinitionId));
	}

	@Override
	public List<Assignment> getAssignmentForProcess(String processDefinitionId) {
		return commandExecutor.execute(new GetProcessAssignmentCmd(processDefinitionId));
	}

	@Override
	public void setupTaskAssignment(String processDefinitionId, List<Assignment> taskAssignment) {
		commandExecutor.execute(new SetupTaskAssignmentCmd(processDefinitionId, taskAssignment));
	}

	@Override
	public void addTaskAssignment(String processDefinitionId, List<Assignment> taskAssignment) {
		commandExecutor.execute(new AddTaskAssignmentCmd(processDefinitionId, taskAssignment));
	}

	@Override
	public void delTaskAssignment(List<Assignment> taskAssignment) {
		commandExecutor.execute(new DelTaskAssignmentCmd(taskAssignment));
	}

	@Override
	@Deprecated
	public void fallbackTask(String processInstanceId, String sourceTaskKey, String destTaskKey) {
		// commandExecutor.execute(new FALLBACKTaskSubmitCmd(processInstanceId,
		// sourceTaskKey, destTaskKey));
	}

	@Override
	public List<HistoricActivityInstance> getFallbackableTaskInfoList(String processInstanceId, String currentTaskId) {
		return commandExecutor.execute(new GetFallbakableHisTaskEntitiesCmd(processInstanceId, currentTaskId));
	}

	@Override
	public void handleTaskSubmit(Decision decision) {
		commandExecutor.execute(new DecisionHandleCmd(decision));
	}

	@Override
	public ProcessInstance activateProcessInstanceById(String processInstanceId, Map<String, Object> variables) {
		return commandExecutor.execute(new ExtActivateProcessInstanceCmd(processInstanceId, variables));
	}

	@Override
	public User getProcessInstanceInitiator(String processInstanceId) {
		return commandExecutor.execute(new GetProcessInstanceInitiatorCmd(processInstanceId));
	}

	@Override
	public List<Biz> getBizList() {
		return commandExecutor.execute(new GetBizCmd(null));
	}

	@Override
	public List<BizField> getBizFiledList(String bizId) {
		return commandExecutor.execute(new GetBizFieldListCmd(bizId));
	}

	/**************************** CommonMessage ************************************/
	@Override
	public List<CommonMessageInfo> getUserCommonMessageList(String userId) {
		return commandExecutor.execute(new GetUserCommonMessagesCmd(userId));
	}

	@Override
	public CommonMessageInfo getCommonMessage(String id) {
		return commandExecutor.execute(new GetCommonMessageCmd(id));
	}

	@Override
	public void addCommonMessage(String userId, String message) {
		commandExecutor.execute(new AddCommonMessagesCmd(userId, message));
	}

	@Override
	public void updateCommonMessage(String id, String userId, String message) {
		commandExecutor.execute(new UpdateCommonMessageCmd(id, userId, message));
	}

	@Override
	public void deleteCommonMessage(String id) {
		commandExecutor.execute(new DelCommonMessageCmd(id));
	}

	/**************************** CommonMessage ************************************/

	/**************************** DecisionType ************************************/
	@Override
	public List<DecisionType> getDecisionTypes() {
		return commandExecutor.execute(new GetDecisionTypesCmd());
	}
	/**************************** DecisionType ************************************/

	
	
	
	@Override
	public List<ProcessCategory> getProcessCategoryList() {
		return commandExecutor.execute(new GetAllProcessCategoriesCmd());
	}

	@Override
	public ProcessCategory getProcessCategory(String id) {
		return commandExecutor.execute(new GetProcessCategoryCmd(id));
	}

	@Override
	public void addProcessCategory(String name, String description) {
		commandExecutor.execute(new AddProcessCategoryCmd(name, description));
	}

	@Override
	public void updateProcessCategory(String id, String name, String description) {
		commandExecutor.execute(new UpdatepProcessCategoryCmd(id, name, description));
	}

	@Override
	public void deleteProcessCategory(String id) {
		commandExecutor.execute(new DeleteProcessCategoryCmd(id));
	}

	@Override
	public List<PvmActivity> pathAnalysis(String processDefinitionId, Map<String, Object> variables) {
		return commandExecutor.execute(new PathAnalysisCmd(processDefinitionId, variables));
	}

	@Override
	public void addSimulationActivities(String processDefinitionId, String businessKey, List<PvmActivity> activities) {
		commandExecutor.execute(new AddSimulationActivitiesCmd(processDefinitionId, businessKey, activities));
	}

	@Override
	public void deleteSimulationActivities(String businessKey) {
		commandExecutor.execute(new DeleteSimulationActivitiesCmd(businessKey));
	}
	
}
