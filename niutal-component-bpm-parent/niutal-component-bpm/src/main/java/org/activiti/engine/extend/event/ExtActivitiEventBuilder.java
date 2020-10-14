/**
 * 
 */
package org.activiti.engine.extend.event;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.extend.event.impl.ActivitiCustomEventImpl;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：事件创建扩展
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月27日上午9:23:56
 */
public class ExtActivitiEventBuilder extends ActivitiEventBuilder {

	/**
	 * 
	 * <p>
	 * 方法说明：创建自定义事件
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年3月27日上午9:25:34
	 * <p>
	 * 
	 * @param type
	 * @param executionId
	 * @param processInstanceId
	 * @param processDefinitionId
	 * @return
	 */
	public static ActivitiEntityEvent createCustomEvent(ExtendActivitiEventType customEventType, Object entity, String processBusinessKey) {
		return new ActivitiCustomEventImpl(entity, customEventType, processBusinessKey);
	}
	
	public static ActivitiEntityEvent createCustomEvent(ExtendActivitiEventType customEventType, Object entity) {
		return new ActivitiCustomEventImpl(entity, customEventType);
	}

	public static ActivitiEntityEvent createCustomEvent(ExtendActivitiEventType customEventType, Object entity,
			String executionId, String processInstanceId, String processDefinitionId, String processBusinessKey) {
		ActivitiCustomEventImpl activitiCustomEventImpl = new ActivitiCustomEventImpl(entity, customEventType, processBusinessKey);
		activitiCustomEventImpl.setExecutionId(executionId);
		activitiCustomEventImpl.setProcessDefinitionId(processDefinitionId);
		activitiCustomEventImpl.setProcessInstanceId(processInstanceId);
		return activitiCustomEventImpl;
	}

}
