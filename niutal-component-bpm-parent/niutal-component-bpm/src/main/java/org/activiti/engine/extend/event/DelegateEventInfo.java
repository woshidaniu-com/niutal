/**
 * 
 */
package org.activiti.engine.extend.event;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.extend.event.impl.DefaultDelegateEventInfoImpl;
import org.activiti.engine.task.TaskInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：流程事件消息封装
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月27日下午6:32:27
 */
public interface DelegateEventInfo {

	/**
	 * 
	 * <p>方法说明：获取流程实例ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午7:59:17<p>
	 * @return
	 */
	String getProcessInstanceId();
	
	/**
	 * 
	 * <p>方法说明：获取流程定义ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午7:59:40<p>
	 * @return
	 */
	String getProcessDefinitionId();
	
	/**
	 * 
	 * <p>方法说明：获取执行ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午7:59:57<p>
	 * @return
	 */
	String getExecutionId();
	
	/**
	 * 
	 * <p>方法说明：获取业务ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午8:00:11<p>
	 * @return
	 */
	String getProcessBusinessKey();
	
	/**
	 * 
	 * <p>方法说明：获取流程启动人<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午8:00:36<p>
	 * @return
	 */
	String getProcessInstanceInitiator();
	
	/**
	 * 
	 * <p>方法说明：流程执行对象,如果没有创建子流程,一般和getProcessInstance()一样<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午6:38:05<p>
	 * @return
	 */
	DelegateExecution getDelegateExecution();
	
	/**
	 * 
	 * <p>方法说明：流程任务对象<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午6:38:44<p>
	 * @return
	 */
	DelegateTask getDelegateTask();
	
	/**
	 * 
	 * <p>方法说明：用户历史任务查询时使用，例如退回操作中的目标任务节点<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午7:12:46<p>
	 * @return
	 */
	TaskInfo getTaskInfo();
	
	/**
	 * 
	 * <p>方法说明：流程事件对象<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午6:39:09<p>
	 * @return
	 */
	ActivitiEvent getEvent();
	
	
	/**
	 * 
	 * <p>
	 *   <h3>niutal框架<h3>
	 *   说明：
	 * <p>
	 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * @version 2017年3月27日下午7:02:10
	 */
	public static class DelegateEventInfoBuilder{
		public static DelegateEventInfo createDelegateEventInfo(DelegateExecution execution, DelegateTask task, TaskInfo taskInfo, ActivitiEvent event){
			DefaultDelegateEventInfoImpl impl = new DefaultDelegateEventInfoImpl();
			impl.setEvent(event);
			impl.setExecution(execution);
			impl.setTaskInfo(taskInfo);
			impl.setTask(task);
			return impl;
		}
		
		public static DelegateEventInfo createDelegateEventInfo(DelegateExecution execution, ActivitiEvent event){
			DefaultDelegateEventInfoImpl impl = new DefaultDelegateEventInfoImpl();
			impl.setEvent(event);
			impl.setExecution(execution);
			return impl;
		}
		
		public static DelegateEventInfo createDelegateEventInfo(DelegateTask task, ActivitiEvent event){
			DefaultDelegateEventInfoImpl impl = new DefaultDelegateEventInfoImpl();
			impl.setEvent(event);
			impl.setTask(task);
			return impl;
		}
		
		public static DelegateEventInfo createDelegateEventInfo(TaskInfo taskInfo, ActivitiEvent event){
			DefaultDelegateEventInfoImpl impl = new DefaultDelegateEventInfoImpl();
			impl.setEvent(event);
			impl.setTaskInfo(taskInfo);
			return impl;
		}
		
		public static DelegateEventInfo createDelegateEventInfo(ActivitiEvent event){
			DefaultDelegateEventInfoImpl impl = new DefaultDelegateEventInfoImpl();
			impl.setEvent(event);
			return impl;
		}
		
		public static DelegateEventInfo createDelegateEventInfo(DelegateExecution execution, DelegateTask task, ActivitiEvent event){
			DefaultDelegateEventInfoImpl impl = new DefaultDelegateEventInfoImpl();
			impl.setEvent(event);
			impl.setExecution(execution);
			impl.setTask(task);
			return impl;
		}
	}
	
}
