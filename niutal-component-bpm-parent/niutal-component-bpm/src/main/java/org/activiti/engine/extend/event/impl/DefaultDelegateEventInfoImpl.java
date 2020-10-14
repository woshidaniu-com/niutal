/**
 * 
 */
package org.activiti.engine.extend.event.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.extend.event.DelegateEventInfo;
import org.activiti.engine.task.TaskInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月27日下午6:41:05
 */
public class DefaultDelegateEventInfoImpl implements DelegateEventInfo {
	
	protected DelegateExecution execution;
	
	protected DelegateTask task;
	
	protected ActivitiEvent event;

	protected TaskInfo taskInfo;
	
	public DefaultDelegateEventInfoImpl() {
		super();
	}

	
	public DefaultDelegateEventInfoImpl(DelegateExecution execution, DelegateTask task, ActivitiEvent event,
			TaskInfo taskInfo) {
		super();
		this.execution = execution;
		this.task = task;
		this.event = event;
		this.taskInfo = taskInfo;
	}



	public DefaultDelegateEventInfoImpl(ActivitiEvent event, TaskInfo taskInfo) {
		super();
		this.event = event;
		this.taskInfo = taskInfo;
	}



	public DefaultDelegateEventInfoImpl(ActivitiEvent event) {
		super();
		this.event = event;
	}

	public DefaultDelegateEventInfoImpl(DelegateTask task, ActivitiEvent event) {
		super();
		this.task = task;
		this.event = event;
	}

	public DefaultDelegateEventInfoImpl(DelegateExecution execution, ActivitiEvent event) {
		super();
		this.execution = execution;
		this.event = event;
	}

	
	public DefaultDelegateEventInfoImpl(DelegateExecution execution, DelegateTask task, ActivitiEvent event) {
		super();
		this.execution = execution;
		this.task = task;
		this.event = event;
	}

	public DelegateExecution getExecution() {
		return execution;
	}

	public void setExecution(DelegateExecution execution) {
		this.execution = execution;
	}

	public DelegateTask getTask() {
		return task;
	}

	public void setTask(DelegateTask task) {
		this.task = task;
	}

	public ActivitiEvent getEvent() {
		return event;
	}

	public void setEvent(ActivitiEvent event) {
		this.event = event;
	}

	@Override
	public DelegateExecution getDelegateExecution() {
		return this.execution;
	}

	@Override
	public DelegateTask getDelegateTask() {
		return this.task;
	}

	public TaskInfo getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(TaskInfo taskInfo) {
		this.taskInfo = taskInfo;
	}


	@Override
	public String getProcessInstanceId() {
		return execution != null ? execution.getProcessInstanceId() : null;
	}


	@Override
	public String getProcessDefinitionId() {
		return execution != null ? execution.getProcessDefinitionId() : null;
	}


	@Override
	public String getExecutionId() {
		return execution != null ? execution.getId() : null;
	}


	@Override
	public String getProcessBusinessKey() {
		return execution!=null ? execution.getProcessBusinessKey() : null;
	}


	@Override
	public String getProcessInstanceInitiator() {
		//TODO
		return null;
	}

}
