/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.extend.event.impl.ExtendRebootAuditorRevocationEventImpl;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.CommentEntityManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：重新激活已经结束的流程实例，并跳转到指定的<i> '任务节点' </i>, <br>
 * <br>
 * 要求：指定的<i> '任务节点' </i>必须是结束该流程实例的任务节点
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.RebootHistoicProcessInstanceCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月2日下午5:49:53
 */
public class RebootHistoricProcessInstanceCmd extends AbstractCommand<Void> {

	protected String processInstanceId;

	protected String executionId;

	protected String taskId;

	public RebootHistoricProcessInstanceCmd(String processInstanceId, String executionId, String taskId) {
		super();
		this.processInstanceId = processInstanceId;
		this.executionId = executionId;
		this.taskId = taskId;
	}

	/**
	 * 
	 * <p>方法说明：抛出指定代码异常<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月3日上午10:34:04<p>
	 * @param exceptionCode
	 */
	protected void throwException(String exceptionCode){
		throw new ActivitiException(exceptionCode);
	}
	
	/**
	 * 
	 */
	@Override
	protected Void doExecute(CommandContext commandContext) {
		
		/****************************************entity managers************************************************/
		HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager = commandContext
				.getHistoricProcessInstanceEntityManager();
		HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager = commandContext
				.getHistoricTaskInstanceEntityManager();
		HistoricActivityInstanceEntityManager historicActivityInstanceEntityManager = commandContext
				.getHistoricActivityInstanceEntityManager();
		
		CommentEntityManager commentEntityManager = commandContext.getCommentEntityManager();
		
		TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
		
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		
		/****************************************entity managers************************************************/
		/**查询历史流程实例对象*   histoicProcessInstance*/
		HistoricProcessInstanceEntity historicProcessInstance = historicProcessInstanceEntityManager
				.findHistoricProcessInstance(processInstanceId);
		
		if(historicProcessInstance == null){
			throw new ActivitiException("BPM_EX_29");
		}
		
		String endActivityId = historicProcessInstance.getEndActivityId();
		
		/**查询目标任务节点， historicTaskInstance
		---- a).是否存在;
		-----b).是否是子流程任务或并发任务
		---- b).是否是最后执行执行的任务*/
		HistoricTaskInstanceEntity historicTaskInstance = historicTaskInstanceEntityManager.findHistoricTaskInstanceById(taskId);
		if(historicTaskInstance == null){
			throw new ActivitiException("BPM_EX_29");
		}
		String taskExecutionId = historicTaskInstance.getExecutionId();
		if(!processInstanceId.equals(taskExecutionId)){
			throw new ActivitiException("BPM_EX_29");
		}
		HistoricTaskInstanceQueryImpl historicTaskInstanceQuery = new HistoricTaskInstanceQueryImpl();
		historicTaskInstanceQuery.processInstanceId(processInstanceId).taskCompletedAfter(historicTaskInstance.getEndTime());
		long historicTaskInstanceCount = historicTaskInstanceEntityManager.findHistoricTaskInstanceCountByQueryCriteria(historicTaskInstanceQuery );
		if(historicTaskInstanceCount > 0 ){
			throw new ActivitiException("BPM_EX_29");
		}
		
		TaskEntity taskEntity = copyHistoricTaskInstance(historicTaskInstance);
		
		/**设置历史流程实例的结束日期为空,处理时间长度为空,结束活动为空*/
		markHistoricProcessInstanceActive(historicProcessInstance);
		
		/** 复制历史流程实例到运行库表中,设置运行库流程的当前活动ID为退回任务的定义ID*/
		ExtendExecutionEntity processInstance = copyHistoricProcessInstance(historicProcessInstance);
		processInstance.setActivityId(taskEntity.getTaskDefinitionKey());
		executionEntityManager.insert(processInstance);
		commandContext.getDbSqlSession().flush();
		
		/** 复制流程参数：流程实例参数*/
		Map<String, Object> processVariables = historicProcessInstance.getProcessVariables();
		processInstance.setVariables(processVariables);
		
		/**复制历史任务到运行库中*/
		taskEntityManager.insert(taskEntity);
		
		/**标记历史任务为未完成状态*/
		markHistoricTaskInstanceEntityInstanceActive(historicTaskInstance);
		
		/**删除历史结束活动表数据*/
		
		HistoricActivityInstanceEntity endActivity = historicActivityInstanceEntityManager.findHistoricActivityInstance(endActivityId, processInstanceId);
		historicActivityInstanceEntityManager.delete(endActivity);
		
		/**标记目标任务活动数据为未完成状态, 
		 * !这里可能存在多条记录的情况,由于退回这种操作,可能导致一个节点有多次任务操作!
		 * */
		Map<String, Object> parameter2 = new HashMap<String, Object>();
		parameter2.put("taskId", taskEntity.getId());
		parameter2.put("executionId", processInstanceId);
		//select * from ${prefix}ACT_HI_ACTINST where TASK_ID_ = #{taskId} and EXECUTION_ID_ = #{executionId}
		HistoricActivityInstanceEntity taskActivity = (HistoricActivityInstanceEntity) commandContext.getDbSqlSession()
				.selectOne("selectHistoricActivityInstanceByTaskIDAndExecutionId", parameter2);
		//HistoricActivityInstanceEntity taskActivity = historicActivityInstanceEntityManager
		//		.findHistoricActivityInstance(taskEntity.getTaskDefinitionKey(), processInstanceId);
		markHistoricTaskActivityInstanceActive(taskActivity);

		/** 删除comment表中对应任务节点的数据*/
		commentEntityManager.deleteCommentsByTaskId(taskEntity.getId());

		
		// 触发事件
		if (commandContext.getEventDispatcher().isEnabled()) {
			ExtendRebootAuditorRevocationEventImpl extendRevocationEvent = new ExtendRebootAuditorRevocationEventImpl(
					processInstance, processInstance.getProcessInstanceId());
			extendRevocationEvent.setExecutionId(processInstance.getProcessInstanceId());
			extendRevocationEvent.setProcessDefinitionId(processInstance.getProcessDefinitionId());
			extendRevocationEvent.setProcessInstanceId(processInstance.getProcessInstanceId());
			extendRevocationEvent.setHisTaskEntity(historicTaskInstance);
			commandContext.getEventDispatcher().dispatchEvent(extendRevocationEvent);
		}
		
		return null;
	}

	/**
	 * 
	 * <p>方法说明：标记历史任务为活动状态<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月3日下午3:14:53<p>
	 * @param historicTaskInstance
	 */
	protected void markHistoricTaskInstanceEntityInstanceActive(HistoricTaskInstanceEntity historicTaskInstance) {
		historicTaskInstance.setEndTime(null);
		historicTaskInstance.setDurationInMillis(0L);
		historicTaskInstance.setDeleteReason(null);
	}

	/**
	 * 
	 * <p>方法说明：标记历史活动为活动状态<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月3日下午2:32:34<p>
	 * @param taskActivity
	 */
	protected void markHistoricTaskActivityInstanceActive(HistoricActivityInstanceEntity taskActivity) {
		taskActivity.setEndTime(null);
		taskActivity.setDurationInMillis(0L);
	}

	/**
	 * 
	 * <p>方法说明：标记历史流程实例为活动状态<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月3日上午8:48:13<p>
	 * @param historicProcessInstance
	 */
	protected void markHistoricProcessInstanceActive(HistoricProcessInstanceEntity historicProcessInstance) {
		historicProcessInstance.setEndTime(null);
		historicProcessInstance.setDurationInMillis(0L);
		historicProcessInstance.setEndActivityId(null);
	}

	/**
	 * 
	 * <p>方法说明：复制历史流程实例<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月3日上午8:48:37<p>
	 * @param historicProcessInstance
	 * @return
	 */
	protected ExtendExecutionEntity copyHistoricProcessInstance(HistoricProcessInstanceEntity historicProcessInstance){
		ExtendExecutionEntity executionEntity = new ExtendExecutionEntity();
		executionEntity.setActive(true);
		executionEntity.setBusinessKey(historicProcessInstance.getBusinessKey());
		executionEntity.setConcurrent(false);
		executionEntity.setId(historicProcessInstance.getId());
		executionEntity.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
		executionEntity.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
		executionEntity.setProcessInstanceId(historicProcessInstance.getProcessInstanceId());
		return executionEntity;
	}

	protected TaskEntity copyHistoricTaskInstance(HistoricTaskInstanceEntity historicTaskInstance){
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setId(historicTaskInstance.getId());
		taskEntity.setProcessDefinitionId(historicTaskInstance.getProcessDefinitionId());
		taskEntity.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
		taskEntity.setExecutionId(historicTaskInstance.getExecutionId());
		taskEntity.setCreateTime(historicTaskInstance.getCreateTime());
		taskEntity.setSuspensionState(SuspensionState.ACTIVE.getStateCode());
		taskEntity.setPriority(historicTaskInstance.getPriority());
		taskEntity.setAssigneeWithoutCascade(historicTaskInstance.getAssignee());
		taskEntity.setTaskDefinitionKey(historicTaskInstance.getTaskDefinitionKey());
		taskEntity.setName(historicTaskInstance.getName());
		return taskEntity;
	}
	
	/**包装ExecutionEntity*/
	class ExtendExecutionEntity extends ExecutionEntity{
		private static final long serialVersionUID = -4510072827935728992L;
		public void setProcessInstanceId(String processInstanceId){
			this.processInstanceId = processInstanceId;
		}
		public void setActivityId(String activityId){
			this.activityId = activityId;
		}
	}
	
}
