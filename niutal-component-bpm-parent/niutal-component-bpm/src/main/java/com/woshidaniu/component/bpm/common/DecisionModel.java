/**
 * 
 */
package com.woshidaniu.component.bpm.common;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.extend.task.Decision;
import org.activiti.engine.extend.task.DecisionEntity;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：业务系统使用
 * <p>
 * @className:com.woshidaniu.component.bpm.common.TaskDecisonModel.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午7:25:56
 */
public class DecisionModel implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String userId;//
	protected String processInstanceId;//
	protected String executionId;//
	protected String taskId;//
	protected String historicActivityId;//
	protected String decision;//
	protected String decisionMessage;//
	protected Map<String,Object> variables;
	protected boolean variableScopeLocal;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getHistoricActivityId() {
		return historicActivityId;
	}
	public void setHistoricActivityId(String historicActivityId) {
		this.historicActivityId = historicActivityId;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getDecisionMessage() {
		return decisionMessage;
	}
	public void setDecisionMessage(String decisionMessage) {
		this.decisionMessage = decisionMessage;
	}
	
	public Map<String, Object> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Object> variables, boolean isVariableScopeLocal) {
		this.variables = variables;
		this.variableScopeLocal = isVariableScopeLocal;
	}
	public boolean isVariableScopeLocal() {
		return variableScopeLocal;
	}
	/**
	 * 
	 * <p>方法说明：转化为DecisionEntity<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月29日上午9:34:29<p>
	 * @return
	 */
	public Decision convert() {
		Decision decisionEntity = new DecisionEntity();
		decisionEntity.setDecision(decision);
		decisionEntity.setDecisionMessage(decisionMessage);
		decisionEntity.setExecutionId(executionId);
		decisionEntity.setHistoricActivityId(historicActivityId);
		decisionEntity.setProcessInstanceId(processInstanceId);
		decisionEntity.setTaskId(taskId);
		decisionEntity.setUserId(userId);
		decisionEntity.setVariables(variables, variableScopeLocal);
		return decisionEntity;
	}

}
