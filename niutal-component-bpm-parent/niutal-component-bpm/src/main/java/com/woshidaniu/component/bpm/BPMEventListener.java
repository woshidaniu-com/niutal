package com.woshidaniu.component.bpm;

import java.util.Collection;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityWithVariablesEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.event.DelegateEventInfo.DelegateEventInfoBuilder;
import org.activiti.engine.extend.event.impl.ActivitiCustomEventImpl;
import org.activiti.engine.extend.event.impl.ExtendAuditorProcessCancellationEvent;
import org.activiti.engine.extend.event.impl.ExtendAuditorRevocationEventImpl;
import org.activiti.engine.extend.event.impl.ExtendInitiatorRevocationEventImpl;
import org.activiti.engine.extend.event.impl.ExtendRebootAuditorRevocationEventImpl;
import org.activiti.engine.extend.event.listener.AbstractEventListener;
import org.activiti.engine.extend.persistence.entity.AssignmentEntityManager;
import org.activiti.engine.extend.task.CandidatesResolver;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.TaskInfo;

import com.woshidaniu.component.bpm.listener.DelegateEventListener;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：流程事件监听接口 <br>
 * class：com.woshidaniu.component.bpm.BPMEventListener.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public abstract class BPMEventListener extends AbstractEventListener implements DelegateEventListener {

	private static final long serialVersionUID = -3697605213157716516L;

	@Override
	public void onAuditorRevocation(ActivitiEvent event) {
		ExtendAuditorRevocationEventImpl auditorRevocationEvent = (ExtendAuditorRevocationEventImpl) event;
		onAuditorRevocation(
				DelegateEventInfoBuilder.createDelegateEventInfo((DelegateExecution) auditorRevocationEvent.getEntity(),
						(DelegateTask) auditorRevocationEvent.getTaskEntity(), auditorRevocationEvent));
	}

	@Override
	public void onRebootAuditorRevocation(ActivitiEvent event) {
		ExtendRebootAuditorRevocationEventImpl rebootAuditorRevocationEvent = (ExtendRebootAuditorRevocationEventImpl) event;
		onRebootAuditorRevocation(
				DelegateEventInfoBuilder.createDelegateEventInfo((DelegateExecution) rebootAuditorRevocationEvent.getEntity(),
						(DelegateTask) rebootAuditorRevocationEvent.getTaskEntity(), rebootAuditorRevocationEvent));
	}

	@Override
	public void onAuditorProcessCancellation(ActivitiEvent event) {
		ExtendAuditorProcessCancellationEvent auditorCancellationEvent = (ExtendAuditorProcessCancellationEvent) event;
		onAuditorProcessCancellation(DelegateEventInfoBuilder.createDelegateEventInfo(
				(DelegateTask) auditorCancellationEvent.getEntity(), auditorCancellationEvent));
	}

	
	@Override
	public void onInitiatorRevocation(ActivitiEvent event) {
		ExtendInitiatorRevocationEventImpl initiatorRevocationEvent = (ExtendInitiatorRevocationEventImpl) event;
		onInitiatorRevocation(DelegateEventInfoBuilder.createDelegateEventInfo((DelegateExecution) initiatorRevocationEvent.getEntity(),initiatorRevocationEvent));
	}

	@Override
	public void onInitiatorSuspension(ActivitiEvent event) {
		ActivitiCustomEventImpl customEvent = (ActivitiCustomEventImpl) event;
		onInitiatorSuspension(DelegateEventInfoBuilder.createDelegateEventInfo((DelegateExecution) customEvent.getEntity(), customEvent));
	}

	@Override
	public void onInitiatorActivation(ActivitiEvent event) {
		ActivitiCustomEventImpl customEvent = (ActivitiCustomEventImpl) event;
		onInitiatorActivation(DelegateEventInfoBuilder
				.createDelegateEventInfo((DelegateExecution) customEvent.getEntity(), customEvent));
	}

	@Override
	public void onProcessFallback(ActivitiEvent event) {
		ActivitiCustomEventImpl customEvent = (ActivitiCustomEventImpl) event;
		onProcessFallback(DelegateEventInfoBuilder.createDelegateEventInfo((TaskInfo) customEvent.getEntity(), event));
	}

	@Override
	public void onTaskCreated(ActivitiEvent event) {
		ActivitiEntityEventImpl taskCompleteEvent = (ActivitiEntityEventImpl) event;

		DelegateTask delegateTask = (DelegateTask) taskCompleteEvent.getEntity();

		List<Assignment> assignments = Context.getCommandContext().getSession(AssignmentEntityManager.class)
				.findAssignmentByProcessDefinitionIdAndTaskDefintionId(delegateTask.getProcessDefinitionId(),
						delegateTask.getTaskDefinitionKey());

		// 设置任务操办人员，如果有的话
		if (assignments != null && assignments.size() > 0) {
			for (Assignment assignment : assignments) {
				if (BPMUtils.isNotBlank(assignment.getUserId())) {
					delegateTask.addCandidateUser(assignment.getUserId());
				}
				if (BPMUtils.isNotBlank(assignment.getGroupId())) {
					delegateTask.addCandidateGroup(assignment.getGroupId());
				}
				if(BPMUtils.isNoneBlank(assignment.getClazzId())){
					Object assignmentBean = Context.getProcessEngineConfiguration().getBeans().get(assignment.getClazzId());
					if(assignmentBean != null && assignmentBean instanceof CandidatesResolver){
						CandidatesResolver assignmentResolver = (CandidatesResolver)assignmentBean;
						Collection<String> resolveUsers = assignmentResolver.resolveUsers(delegateTask);
						Collection<String> resolveGroups = assignmentResolver.resolveGroups(delegateTask);
						if(resolveUsers != null && resolveUsers.size() > 0){
							delegateTask.addCandidateUsers(resolveUsers);
						}
						if(resolveGroups != null && resolveGroups.size() > 0){
							delegateTask.addCandidateGroups(resolveGroups);
						}
					}
				}
			}
		}

		onTaskCreated(DelegateEventInfoBuilder.createDelegateEventInfo(delegateTask, event));
	}

	@Override
	public void onTaskCompleted(ActivitiEvent event) {
		ActivitiEntityWithVariablesEventImpl taskCompleteEvent = (ActivitiEntityWithVariablesEventImpl) event;
		TaskEntity taskEntity = (TaskEntity) taskCompleteEvent.getEntity();
		// 如果不是用户撤销操作才记录操作日志
		if (!"1".equals(taskEntity.getVariableLocal("p_auditor_revocation"))) {

		}

		onTaskCompleted(
				DelegateEventInfoBuilder.createDelegateEventInfo((DelegateTask) taskCompleteEvent.getEntity(), event));

	}

	@Override
	public void onProcessStarted(ActivitiEvent event) {
		ActivitiProcessStartedEventImpl processStartedEvent = ((ActivitiProcessStartedEventImpl) event);
		ExecutionEntity entity = (ExecutionEntity) processStartedEvent.getEntity();
		onProcessInstanceStarted(DelegateEventInfoBuilder.createDelegateEventInfo(entity, event));
	}

	@Override
	public void onProcessCompleted(ActivitiEvent event) {
		ActivitiEntityEventImpl processCompleteEvent = ((ActivitiEntityEventImpl) event);
		ExecutionEntity entity = (ExecutionEntity) processCompleteEvent.getEntity();
		onProcessInstanceEnded(DelegateEventInfoBuilder.createDelegateEventInfo(entity, event));
	}

	@Override
	public void onProcessCancelled(ActivitiEvent event) {
		// super.onProcessCancelled(event);
		// ActivitiProcessCancelledEventImpl processCancelledEvent =
		// ((ActivitiProcessCancelledEventImpl)event);
		// ExecutionEntity entity = (ExecutionEntity)processCancelledEvent.
		// onProcessInstanceCancelled(entity, event);
	}

}
