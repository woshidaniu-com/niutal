/**
 * 
 */
package org.activiti.engine.extend.cmd.tasksubmit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.extend.task.DecisionInfo;
import org.activiti.engine.extend.task.impl.DecisionPersistenceHandler;
import org.activiti.engine.impl.bpmn.behavior.NoneEndEventActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.history.handler.ActivityInstanceEndHandler;
import org.activiti.engine.impl.history.handler.ActivityInstanceStartHandler;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLinkType;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：‘NOPASS’ 处理器
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.tasksubmit.NOPASSDecisionHandler.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午6:35:34
 */
public class NOPASSDecisionHandler extends DecisionPersistenceHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.task.DecisionHandler#handle(org.activiti.
	 * engine.extend.task.DecisionInfo)
	 */
	@Override
	public void doHandle(DecisionInfo decisionInfo) {
		TaskEntity task = (TaskEntity) decisionInfo.getTaskInfo();
		
		// 1.完成任务
		
		// 2.触发事件

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

		Context.getCommandContext().getTaskEntityManager().deleteTask(task, TaskEntity.DELETE_REASON_COMPLETED, false);

		ExecutionEntity execution = task.getExecution();

		// 3.如果该执行不是流程实例本身，那么不允许流程直接结束，继续让流程走下下去。可以通过信号的方式去实现流程结束。
		if (execution != null && (!execution.isProcessInstanceType())) {
			execution.removeTask(task);
			execution.signal(null, null);

			// 4.如果该执行就是流程实例本身，允许流程结束
		} else if (execution != null && (execution.isProcessInstanceType())) {

			// 4.1 动态创建一条流程结束路径
			String noneEventEndActivityName = "NOPASS-NoneEventEnd";
			ActivityImpl noneEventEndActivity = null;
			// DynamicTransitionImpl dynamicTransitionImpl = null;
			// 先查询内存中的流程定义是否已经定义过活动
			noneEventEndActivity = execution.getProcessDefinition().findActivity(noneEventEndActivityName);

			if (noneEventEndActivity == null) {
				noneEventEndActivity = execution.getProcessDefinition().createActivity(noneEventEndActivityName);

				NoneEndEventActivityBehavior noneEndEventActivityBehavior = new NoneEndEventActivityBehavior();

				noneEventEndActivity.setActivityBehavior(noneEndEventActivityBehavior);

				noneEventEndActivity.setAsync(false);

				noneEventEndActivity.setExclusive(false);

				noneEventEndActivity.setProperty("name", "结束[不通过]");

				noneEventEndActivity.setProperty("type", BpmnXMLConstants.ELEMENT_EVENT_END);

				Map<String, List<ExecutionListener>> executionListeners = noneEventEndActivity.getExecutionListeners();

				List<ExecutionListener> activityInstanceStartHandlers = new ArrayList<ExecutionListener>();
				List<ExecutionListener> activityInstanceEndHandlers = new ArrayList<ExecutionListener>();
				activityInstanceStartHandlers.add(new ActivityInstanceStartHandler());
				activityInstanceEndHandlers.add(new ActivityInstanceEndHandler());
				executionListeners.put("start", activityInstanceStartHandlers);
				executionListeners.put("end", activityInstanceEndHandlers);
			}

			// 4.2设置当前执行的路径为新创建的动态路径
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

			Context.getCommandContext().getHistoryManager().recordActivityEnd((ExecutionEntity) execution);

			//4.3设置流程变量，用于业务判断
			execution.setVariable("p_decision", decisionInfo.getDecision());
			execution.setVariable("p_decision_message", decisionInfo.getDecisionMessage());
			
			execution.executeActivity(noneEventEndActivity);
		}
	}

	@Override
	public String getType() {
		return "NOPASS";
	}
}
