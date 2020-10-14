package org.activiti.engine.extend.event;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：扩展流程事件
 *	 <br>class：org.activiti.engine.extend.event.ExtendActivitiEvent.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public enum ExtendActivitiEventType {

	AUDITOR_REVOCATION_EVENT,
	
	REBOOT_AUDITOR_REVOCATION_EVENT,
	
	INITIATOR_REVOCATION_EVENT,
	
	AUDITOR_PROCESS_CANCELLATION_EVENT,
	
	//发起人挂起事件【即：流程被退回到发起人，流程被引擎自动挂起，等待发起人重新提交激活流程】
	INITIATOR_SUSPENSION_EVENT,
	
	//发起人激活时间【即：流程被申请人重新提交，流程激活】
	INITIATOR_ACTIVATION_EVENT,
	
	//流程退回事件
	PROCESS_FALLBACK_EVENT
}
