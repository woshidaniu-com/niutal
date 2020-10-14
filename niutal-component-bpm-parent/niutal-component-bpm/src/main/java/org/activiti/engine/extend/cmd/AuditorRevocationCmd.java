package org.activiti.engine.extend.cmd;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.extend.event.impl.ExtendAuditorRevocationEventImpl;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.task.DelegationState;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：任务撤销命令【只有在下一任务环节没有完成时才能撤销】 <br>
 * class：org.activiti.engine.extend.cmd.RevocationTaskCmd.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class AuditorRevocationCmd extends WrapNeedActiveProcessInstanceCmd<Void> {

	static final String DELETE_REASON_REVOCATION = "Auditor Revocation";

	protected String executionId;
	protected String hisTaskInstanceId;

	public AuditorRevocationCmd(String processInstanceId, String executionId, String hisTaskInstanceId) {
		super(processInstanceId);
		this.executionId = executionId;
		this.hisTaskInstanceId = hisTaskInstanceId;
	}

	@Override
	protected Void doExecuteWithExecutinEntity(CommandContext commandContext, ExecutionEntity processInstance) {

		HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = commandContext
				.getHistoricActivityInstanceEntityManager();
		HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = commandContext
				.getHistoricTaskInstanceEntityManager();
		HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = commandContext
				.getHistoricIdentityLinkEntityManager();
		HistoricVariableInstanceEntityManager historicVariableInstanceEntityManager = commandContext
				.getHistoricVariableInstanceEntityManager();
		DbSqlSession dbSqlSession = commandContext.getDbSqlSession();

		// 查询撤销任务所绑定的执行实例
		ExecutionEntity currentActiveExecution = null;
		if (processInstance.getId().equals(executionId)) {
			currentActiveExecution = processInstance;
		} else {
			currentActiveExecution = commandContext.getExecutionEntityManager().findExecutionById(executionId);
		}

		if (currentActiveExecution == null || currentActiveExecution.isSuspended()
				|| (!currentActiveExecution.isActive())) {
			throw new ActivitiException("BPM_EX_29");
		}

		String executionId = currentActiveExecution.getId();
		ProcessDefinitionImpl processDefinition = currentActiveExecution.getProcessDefinition();
		// 1.2查询历史任务实例对象
		HistoricTaskInstanceEntity hisTaskInstance = historicTaskInstanceEntityManager
				.findHistoricTaskInstanceById(hisTaskInstanceId);

		if (hisTaskInstance == null) {
			throw new ActivitiException("BPM_EX_24");
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("taskId", hisTaskInstance.getId());
		HistoricActivityInstanceEntity hisActivityInstance = (HistoricActivityInstanceEntity) dbSqlSession
				.selectOne("selectHistoricActivityInstanceByTaskId", parameter);

		// 撤销任务完成的时间
		Date hisTaskFinishedDate = hisTaskInstance.getEndTime();

		// 1.3查询当前需要撤销的节点完成后是否存在已经完成的任务，如果有，则说明已已经存在审核记录，不允许撤销，否则允许撤销
		HistoricTaskInstanceQueryImpl finishedTaskInstranceQuery = new HistoricTaskInstanceQueryImpl();
		finishedTaskInstranceQuery.executionId(executionId).taskCompletedAfter(hisTaskFinishedDate);
		List<HistoricTaskInstance> finishedTaskList = historicTaskInstanceEntityManager
				.findHistoricTaskInstancesByQueryCriteria(finishedTaskInstranceQuery);

		if (finishedTaskList != null && finishedTaskList.size() > 0) {
			throw new ActivitiException("BPM_EX_27");
		}

		// 2.查询当前活动任务实例
		List<TaskEntity> activeTaskInstanceEntities = commandContext.getTaskEntityManager()
				.findTasksByExecutionId(executionId);

		// 2.1 如果查询的活动任务实例个数为0,则说明撤销任务的执行实例和当前活动的执行实例不同,这就不能允许撤销
		if (activeTaskInstanceEntities == null || activeTaskInstanceEntities.size() == 0) {
			throw new ActivitiException("BPM_EX_29");
		}

		TaskEntity taskEntity = activeTaskInstanceEntities.get(0);

		// 3.1判断是不是相同的任务节点
		if (taskEntity.getTaskDefinitionKey().equals(hisTaskInstance.getTaskDefinitionKey())) {
			throw new ActivitiException("BPM_EX_25");
		}

		///////////////////////////////////////////////////////////////////////////////////////

		// 1.删除当前任务节点

		if (taskEntity.getDelegationState() != null
				&& taskEntity.getDelegationState().equals(DelegationState.PENDING)) {
			throw new ActivitiException("BPM_EX_28");
		}

		Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, TaskEntity.DELETE_REASON_COMPLETED,
				true);

		// 2.变更历史库数据,删除历史活动表中的数据
		//HistoricActivityInstanceEntity historicActivityInstance = historicActivityInstanceEntityManager
		//		.findHistoricActivityInstance(taskEntity.getTaskDefinitionKey(), executionId);

		Map<String, Object> parameter2 = new HashMap<String, Object>();
		parameter2.put("taskId", taskEntity.getId());
		parameter2.put("executionId", executionId);
		//select * from ${prefix}ACT_HI_ACTINST where TASK_ID_ = #{taskId} and EXECUTION_ID_ = #{executionId}
		HistoricActivityInstanceEntity historicActivityInstance = (HistoricActivityInstanceEntity) dbSqlSession
				.selectOne("selectHistoricActivityInstanceByTaskIDAndExecutionId", parameter2);
		historicActivityInstanceEntityManager.delete(historicActivityInstance);

		// 2.1删除撤销节点的数据

		historicVariableInstanceEntityManager.deleteHistoricVariableInstancesByTaskId(hisTaskInstanceId);

		historicIdentityLinkEntityManager.deleteHistoricIdentityLinksByTaskId(hisTaskInstanceId);

		historicActivityInstanceEntityManager.delete(hisActivityInstance);

		historicTaskInstanceEntityManager.delete(hisTaskInstance);

		// 3.执行目标节点
		ActivityImpl targetActivity = processDefinition.findActivity(hisTaskInstance.getTaskDefinitionKey());
		currentActiveExecution.executeActivity(targetActivity);

		// 4.触发事件
		if (commandContext.getEventDispatcher().isEnabled()) {
			ExtendAuditorRevocationEventImpl extendRevocationEvent = new ExtendAuditorRevocationEventImpl(
					currentActiveExecution, currentActiveExecution.getProcessBusinessKey());
			extendRevocationEvent.setExecutionId(currentActiveExecution.getId());
			extendRevocationEvent.setProcessDefinitionId(currentActiveExecution.getProcessDefinitionId());
			extendRevocationEvent.setProcessInstanceId(currentActiveExecution.getProcessInstanceId());
			extendRevocationEvent.setTaskEntity(taskEntity);
			extendRevocationEvent.setHisTaskEntity(hisTaskInstance);
			commandContext.getEventDispatcher().dispatchEvent(extendRevocationEvent);
		}

		return null;
	}

}
