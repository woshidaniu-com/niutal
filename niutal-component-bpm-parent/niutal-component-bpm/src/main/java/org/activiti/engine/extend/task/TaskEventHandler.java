/**
 * 
 */
package org.activiti.engine.extend.task;

import org.activiti.engine.delegate.DelegateTask;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：任务事件处理器
 * <p>
 * @className:org.activiti.engine.extend.task.TaskEventHandler.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月17日上午10:22:51
 */
public interface TaskEventHandler {

	/**
	 * 
	 * <p>方法说明：任务创建事件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月17日上午10:23:33<p>
	 * @param task
	 */
	void handleCreate(DelegateTask task);
	
	/**
	 * 
	 * <p>方法说明：任务完成事件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月17日上午10:23:33<p>
	 * @param task
	 */
	void handleComplete(DelegateTask task);
	
	/**
	 * 
	 * <p>方法说明：任务删除事件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月17日上午10:23:33<p>
	 * @param task
	 */
	void handleDelete(DelegateTask task);
	
	/**
	 * 
	 * <p>方法说明：任务人员设置事件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月17日上午10:23:33<p>
	 * @param task
	 */
	void handleAssignment(DelegateTask task);
	
}
