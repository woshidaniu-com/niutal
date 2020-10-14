/**
 * 
 */
package org.activiti.engine.extend.service;

import java.util.Collection;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明： 多实例任务内部服务：
 *   1. 获取多实例任务的操作人集合
 *   2. 计算多实例任务结束条件
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月17日下午3:54:47
 */
public interface MultiInstanceService {

	/**
	 * 
	 * <p>方法说明：resolve multi instance collection<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月17日下午3:53:06<p>
	 * @return
	 */
	public Collection<String> resolveCollection(DelegateExecution execution);
	
	
	/**
	 * 
	 * <p>方法说明：resole multi instance completion condition<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月17日下午3:54:23<p>
	 * @return
	 */
	public boolean resolveCompletionCondition(String expression, DelegateExecution execution);
	
}
