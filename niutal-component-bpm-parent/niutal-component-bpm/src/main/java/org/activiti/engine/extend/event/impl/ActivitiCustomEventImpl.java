package org.activiti.engine.extend.event.impl;

import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.extend.event.ActivitiCustomEvent;
import org.activiti.engine.extend.event.ExtendActivitiEventType;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明： <br>
 * class：org.activiti.engine.extend.event.ExtendActivityEvent.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class ActivitiCustomEventImpl extends ActivitiEntityEventImpl implements ActivitiCustomEvent {

	public ExtendActivitiEventType getExtendEvent() {
		return extendEvent;
	}

	protected ExtendActivitiEventType extendEvent;

	protected String processBusinessKey;
	
	public ActivitiCustomEventImpl(Object entity, ExtendActivitiEventType extendEvent, String processBusinessKey) {
		super(entity, ActivitiEventType.CUSTOM);
		this.extendEvent = extendEvent;
		this.processBusinessKey = processBusinessKey;
	}
	
	public ActivitiCustomEventImpl(Object entity, ExtendActivitiEventType extendEvent) {
		super(entity, ActivitiEventType.CUSTOM);
		this.extendEvent = extendEvent;
	}

	public void setExtendEvent(ExtendActivitiEventType extendEvent) {
		this.extendEvent = extendEvent;
	}

	public void setProcessBusinessKey(String processBusinessKey) {
		this.processBusinessKey = processBusinessKey;
	}

	public String getProcessBusinessKey() {
		return processBusinessKey;
	}

	
}
