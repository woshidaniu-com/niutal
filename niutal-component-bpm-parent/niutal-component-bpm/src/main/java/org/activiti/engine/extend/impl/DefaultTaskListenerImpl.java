/**
 * 
 */
package org.activiti.engine.extend.impl;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.extend.task.TaskEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.impl.DefaultTaskListenerImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月17日上午10:09:48
 */
public class DefaultTaskListenerImpl implements TaskLifecycleEventListener {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultTaskListenerImpl.class);

	/**
	 * 用于处理任务事件的处理器
	 */
	protected List<TaskEventHandler> taskEventHandlers;

	@Override
	public void notify(DelegateTask delegateTask, String eventName) {
		
		if(log.isDebugEnabled()){
			log.debug("TaskLifecycleEventHandler:{}, handle event:{}, from task: {}.", new Object[]{this, eventName, delegateTask});
		}
		
		if (eventName != null && TaskListener.EVENTNAME_CREATE.equals(eventName)) {
			onCreate(delegateTask);
		}

		if (eventName != null && TaskListener.EVENTNAME_ASSIGNMENT.equals(eventName)) {
			onAssignment(delegateTask);
		}

		if (eventName != null && TaskListener.EVENTNAME_COMPLETE.equals(eventName)) {
			onComplete(delegateTask);
		}

		if (eventName != null && TaskListener.EVENTNAME_DELETE.equals(eventName)) {
			onDelete(delegateTask);
		}
	}

	protected void onDelete(DelegateTask delegateTask) {
		if(taskEventHandlers != null && taskEventHandlers.size() > 0){
			for (TaskEventHandler taskEventHandler : taskEventHandlers) {
				taskEventHandler.handleDelete(delegateTask);
			}
		}
	}

	protected void onComplete(DelegateTask delegateTask) {
		if(taskEventHandlers != null && taskEventHandlers.size() > 0){
			for (TaskEventHandler taskEventHandler : taskEventHandlers) {
				taskEventHandler.handleComplete(delegateTask);
			}
		}
	}

	protected void onAssignment(DelegateTask delegateTask) {
		if(taskEventHandlers != null && taskEventHandlers.size() > 0){
			for (TaskEventHandler taskEventHandler : taskEventHandlers) {
				taskEventHandler.handleAssignment(delegateTask);
			}
		}
	}

	protected void onCreate(DelegateTask delegateTask) {
		if(taskEventHandlers != null && taskEventHandlers.size() > 0){
			for (TaskEventHandler taskEventHandler : taskEventHandlers) {
				taskEventHandler.handleCreate(delegateTask);
			}
		}
	}

}
