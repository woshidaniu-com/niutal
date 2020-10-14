package org.activiti.engine.extend.event.impl;

import org.activiti.engine.extend.event.ExtendActivitiEventType;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：流程发起人撤销事件 <br>
 * class：org.activiti.engine.extend.event.impl.ExtendInitiatorRevocationEventImpl.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class ExtendInitiatorRevocationEventImpl extends ActivitiCustomEventImpl {
	
	public ExtendInitiatorRevocationEventImpl(Object entity, String processBusinessKey) {
		super(entity,ExtendActivitiEventType.INITIATOR_REVOCATION_EVENT, processBusinessKey);
	}
	
}
