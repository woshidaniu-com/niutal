/**
 * 
 */
package org.activiti.engine.extend.impl;

import org.activiti.engine.delegate.DelegateTask;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:org.activiti.engine.extend.impl.TaskLifecycleEventListener.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月17日上午10:06:48
 */
public interface TaskLifecycleEventListener {

	void notify(DelegateTask delegateTask, String eventName);
	
}
