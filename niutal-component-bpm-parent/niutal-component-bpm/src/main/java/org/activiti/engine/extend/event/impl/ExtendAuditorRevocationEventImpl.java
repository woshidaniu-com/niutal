package org.activiti.engine.extend.event.impl;

import org.activiti.engine.extend.event.ExtendActivitiEventType;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：流程任务操作人发起撤销事件 <br>
 * class：org.activiti.engine.extend.event.impl.ExtendAuditorRevocationEventImpl.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class ExtendAuditorRevocationEventImpl extends ActivitiCustomEventImpl {

	protected Object taskEntity;
	
	protected Object hisTaskEntity;
	
	public ExtendAuditorRevocationEventImpl(Object entity, String processBusinessKey) {
		super(entity, ExtendActivitiEventType.AUDITOR_REVOCATION_EVENT, processBusinessKey);
	}

	public Object getTaskEntity() {
		return taskEntity;
	}

	public void setTaskEntity(Object taskEntity) {
		this.taskEntity = taskEntity;
	}

	public Object getHisTaskEntity() {
		return hisTaskEntity;
	}

	public void setHisTaskEntity(Object hisTaskEntity) {
		this.hisTaskEntity = hisTaskEntity;
	}
}
