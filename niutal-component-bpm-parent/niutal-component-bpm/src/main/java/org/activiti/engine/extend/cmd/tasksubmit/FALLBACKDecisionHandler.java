/**
 * 
 */
package org.activiti.engine.extend.cmd.tasksubmit;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.extend.event.ExtActivitiEventBuilder;
import org.activiti.engine.extend.event.ExtendActivitiEventType;
import org.activiti.engine.extend.persistence.entity.ExtSuspensionStateImpl;
import org.activiti.engine.extend.task.DecisionInfo;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：历史任务退回CMD
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月22日上午10:51:11
 */
public class FALLBACKDecisionHandler extends NeedFallbackableHisActivityInstanceDecisionHandler {

	private static final Logger logger = LoggerFactory.getLogger(FALLBACKDecisionHandler.class);
	
	/**
	 * 用于标记历史活动表数据类别
	 */
	protected static final String ACTIVITY_CATEGOTY = "FALLBACK";
	
	@Override
	protected void doFallback(HistoricActivityInstance historicActivityInstance, DecisionInfo decisionInfo) {
		CommandContext commandContext = Context.getCommandContext();
		TaskEntity task = (TaskEntity) decisionInfo.getTaskInfo();
		ExecutionEntity execution = task.getExecution();
		
		// 1.完成当前任务节点
		if (task.getDelegationState() != null && task.getDelegationState().equals(DelegationState.PENDING)) {
			throw new ActivitiException("BPM_EX_13");
		}
		task.fireEvent(TaskListener.EVENTNAME_COMPLETE);
		if (Authentication.getAuthenticatedUserId() != null && task.getProcessInstanceId() != null) {
			task.getProcessInstance().involveUser(Authentication.getAuthenticatedUserId(),
					IdentityLinkType.PARTICIPANT);
		}
		if (Context.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
			Context.getProcessEngineConfiguration().getEventDispatcher()
					.dispatchEvent(ActivitiEventBuilder.createEntityWithVariablesEvent(ActivitiEventType.TASK_COMPLETED,
							task, decisionInfo.getVariables(), decisionInfo.isVariableScopeLocal()));
		}
		//删除当前任务
		if(logger.isDebugEnabled()){
			logger.debug("delete task entity : {}" , task);
		}
		Context.getCommandContext().getTaskEntityManager().deleteTask(task, TaskEntity.DELETE_REASON_COMPLETED, false);
		// 2.触发事件
		if (Context.getProcessEngineConfiguration() != null
				&& Context.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
			Context.getProcessEngineConfiguration().getEventDispatcher()
					.dispatchEvent(ActivitiEventBuilder.createActivityEvent(ActivitiEventType.ACTIVITY_COMPLETED,
							execution.getActivity() != null ? execution.getActivity().getId()
									: execution.getActivityId(),
							execution.getActivity() != null
									? (String) execution.getActivity().getProperties().get("name") : null,
							execution.getId(), execution.getProcessInstanceId(), execution.getProcessDefinitionId(),
							execution.getActivity() != null
									? (String) execution.getActivity().getProperties().get("type") : null,
							execution.getActivity() != null
									? execution.getActivity().getActivityBehavior().getClass().getCanonicalName()
									: null));
		}
		// 3.变更历史库数据
		if(logger.isDebugEnabled()){
			logger.debug("record activity end : {}" , execution.getActivity());
		}
		Context.getCommandContext().getHistoryManager().recordActivityEnd((ExecutionEntity) execution);
		// 4.执行目标节点*********************************
		ProcessDefinitionImpl processDefinition = execution.getProcessDefinition();
		ActivityImpl targetActivity = processDefinition.findActivity(historicActivityInstance.getActivityId());
		execution.executeActivity(targetActivity);
		if(logger.isDebugEnabled()){
			logger.debug("execute target activity : {}" , targetActivity);
		}
		// 4.执行目标节点*********************************
		/***********************删除历史活动和历史任务数据***********************/
		/*if(logger.isDebugEnabled()){
			logger.debug("开始删除历史活动和历史任务数据");
		}
		HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = Context.getCommandContext()
				.getHistoricActivityInstanceEntityManager();
		HistoricActivityInstanceEntity findHistoricActivityInstance = historicActivityInstanceEntityManager.findHistoricActivityInstance(
						historicActivityInstance.getActivityId(), execution.getProcessInstanceId());
		historicActivityInstanceEntityManager.delete(findHistoricActivityInstance);
		
		HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = Context.getCommandContext()
				.getHistoricTaskInstanceEntityManager();
		HistoricTaskInstanceEntity findHistoricTaskInstanceById = historicTaskInstanceEntityManager.findHistoricTaskInstanceById(historicActivityInstance.getTaskId());
		historicTaskInstanceEntityManager.delete(findHistoricTaskInstanceById);
		
		CommentEntityManager commentEntityManager = Context.getCommandContext().getCommentEntityManager();
		commentEntityManager.deleteCommentsByTaskId(findHistoricTaskInstanceById.getId());*/
		
		/***********************删除历史活动和历史任务数据***********************/
		// 5.1.如果退回的节点是开始节点
		if (targetActivity.getActivityBehavior() instanceof NoneStartEventActivityBehavior
				|| BpmnXMLConstants.ELEMENT_EVENT_START.equals(targetActivity.getProperty("type"))) {
			
			if(logger.isDebugEnabled()){
				logger.debug("fire INITIATOR_SUSPENSION_EVENT");
			}
			
			// 5.1.1锁定流程 、触发‘退回到审核人’扩展事件
			execution.setSuspensionState(ExtSuspensionStateImpl.INITIATOR_SUSPENDED.getStateCode());
			Context.getCommandContext().getEventDispatcher()
					.dispatchEvent(ExtActivitiEventBuilder.createCustomEvent(
							ExtendActivitiEventType.INITIATOR_SUSPENSION_EVENT, execution, execution.getId(),
							execution.getProcessInstanceId(), execution.getProcessDefinitionId(),
							execution.getProcessBusinessKey()));
			// 5.2.如果退回的节点不是开始节点、触发‘退回’扩展事件
			
		} else {
			
			if(logger.isDebugEnabled()){
				logger.debug("fire PROCESS_FALLBACK_EVENT");
			}
			
			TaskInfo hisTaskInfo = commandContext.getHistoricTaskInstanceEntityManager()
					.findHistoricTaskInstanceById(task.getId());

			Context.getCommandContext().getEventDispatcher().dispatchEvent(
					ExtActivitiEventBuilder.createCustomEvent(ExtendActivitiEventType.PROCESS_FALLBACK_EVENT,
							hisTaskInfo, execution.getId(), execution.getProcessInstanceId(),
							execution.getProcessDefinitionId(), execution.getProcessBusinessKey()));
		}

	}

	@Override
	public String getType() {
		return "FALLBACK";
	}

}
