/**
 * 
 */
package org.activiti.engine.extend.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.BulkDeleteable;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.TaskInfo;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：数据持久化用
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日上午11:01:21
 */
public class DecisionEntity implements Decision, PersistentObject, BulkDeleteable {

	private static final long serialVersionUID = 1L;
	protected String id;//
	protected String userId;//

	protected Date createDate;//
	protected String processInstanceId;//
	protected String executionId;//
	protected String parentExecutionId;//
	protected String taskId;//
	protected String historicActivityId;//
	protected String decision;//
	protected String decisionMessage;//
	protected int version;//

	@Override
	public DecisionEntity clone() {
		DecisionEntity clone = new DecisionEntity();
		clone.id = id;
		clone.userId = userId;
		clone.createDate = createDate;
		clone.processInstanceId = processInstanceId;
		clone.executionId = executionId;
		clone.parentExecutionId = parentExecutionId;
		clone.taskId = taskId;
		clone.historicActivityId = historicActivityId;
		clone.decision = decision;
		clone.decisionMessage = decisionMessage;
		clone.version = version;
		clone.processDefinitionId = processDefinitionId;
		clone.processInstance = processInstance;
		clone.executionEntity = executionEntity;
		clone.taskEntity = taskEntity;
		clone.historicActivityInstanceEntity = historicActivityInstanceEntity;
		clone.userEntity = userEntity;
		clone.variables = variables;
		clone.variableScopeLocal = variableScopeLocal;
		clone.decisionType = decisionType;
		return clone;
	}

	// ******************************************//
	protected String processDefinitionId;
	protected ExecutionEntity processInstance;
	protected ExecutionEntity executionEntity;
	protected TaskEntity taskEntity;
	protected HistoricActivityInstanceEntity historicActivityInstanceEntity;
	protected User userEntity;
	protected Map<String, Object> variables;
	protected boolean variableScopeLocal;

	protected DecisionType decisionType;
	// ******************************************//

	@Override
	public String getDecision() {
		return decision;
	}

	@Override
	public String getDecisionMessage() {
		return decisionMessage;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("userId", this.userId);
		persistentState.put("createDate", this.createDate);
		persistentState.put("processInstanceId", this.processInstanceId);
		persistentState.put("executionId", this.executionId);
		persistentState.put("taskId", this.taskId);
		persistentState.put("version", this.version);
		if (decision != null) {
			persistentState.put("decision", this.decision);
		}
		if (decisionMessage != null) {
			persistentState.put("decisionMessage", this.decisionMessage);
		}
		if (historicActivityId != null) {
			persistentState.put("historicActivityId", this.historicActivityId);
		}
		return persistentState;
	}

	@Override
	public void setDecision(String decision) {
		this.decision = decision;
	}

	@Override
	public void setDecisionMessage(String decisionMessage) {
		this.decisionMessage = decisionMessage;
	}

	@Override
	public Date getCreateDate() {
		return this.createDate;
	}

	@Override
	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	@Override
	public String getProcessDefintionId() {
		return this.processDefinitionId;
	}

	@Override
	public String getExecutionId() {
		return this.executionId;
	}

	@Override
	public String getTaskId() {
		return this.taskId;
	}

	@Override
	public String getTaskDefinitionKey() {
		if (taskEntity != null) {
			return taskEntity.getTaskDefinitionKey();
		}
		return null;
	}

	@Override
	public String getHistoricActivityId() {
		return this.historicActivityId;
	}

	@Override
	public String getHistoricActivityDefinitionKey() {
		if (this.historicActivityInstanceEntity != null) {
			return historicActivityInstanceEntity.getActivityId();
		}
		return null;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public User getUserInfo() {
		if (userEntity == null && userId != null) {
			userEntity = Context.getCommandContext().getUserIdentityManager().findUserById(userId);
		}
		return userEntity;
	}

	@Override
	public ProcessInstance getProcessInstance() {
		if (this.processInstance == null && this.processInstanceId != null) {
			this.processInstance = Context.getCommandContext().getExecutionEntityManager()
					.findExecutionById(this.processInstanceId);
		}
		return processInstance;
	}

	@Override
	public Execution getExecution() {
		if (this.executionEntity == null && this.executionId != null) {
			this.executionEntity = Context.getCommandContext().getExecutionEntityManager()
					.findExecutionById(this.executionId);
		}
		return executionEntity;
	}

	@Override
	public TaskInfo getTaskInfo() {
		if (this.taskEntity == null && this.taskId != null) {
			taskEntity = Context.getCommandContext().getTaskEntityManager().findTaskById(this.taskId);
		}
		return taskEntity;
	}

	@Override
	public HistoricActivityInstance getHistoricActivityInstance() {
		if (this.historicActivityInstanceEntity == null && this.historicActivityId != null
				&& this.processInstanceId != null) {
			this.historicActivityInstanceEntity = Context.getCommandContext().getHistoricActivityInstanceEntityManager()
					.findHistoricActivityInstance(historicActivityId, processInstanceId);
		}
		return historicActivityInstanceEntity;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;

	}

	@Override
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public void setHistoricActivityId(String historicActivityId) {
		this.historicActivityId = historicActivityId;
	}

	@Override
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Override
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Override
	public DecisionType getDecisionType() {
		return this.decisionType;
	}

	@Override
	public void setTaskInfo(TaskInfo taskInfo) {
		this.taskEntity = (TaskEntity) taskInfo;
	}

	@Override
	public void setVariables(Map<String, Object> vaiables, boolean variableScopeLocal) {
		this.variables = vaiables;
		this.variableScopeLocal = variableScopeLocal;
	}

	@Override
	public Map<String, Object> getVariables() {
		if (this.variables == null)
			return new HashMap<String, Object>();
		return this.variables;
	}

	@Override
	public boolean isVariableScopeLocal() {
		return this.variableScopeLocal;
	}

	@Override
	public void setParentExecutionId(String parentExecutionId) {
		this.parentExecutionId = parentExecutionId;
	}

	@Override
	public String getParentExecutionId() {
		return this.parentExecutionId;
	}

	@Override
	public void setDecisionType(DecisionType type) {
		this.decisionType = type;
	}

}
