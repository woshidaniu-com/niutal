/**
 * 
 */
package org.activiti.engine.extend.task;

import java.util.Map;

import org.activiti.engine.task.TaskInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：调用api使用，需要设置值
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日上午10:53:23
 */
public interface Decision extends DecisionInfo {
	
	/**
	 * 
	 * <p>方法说明：设置流程变量<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午6:24:23<p>
	 * @param vaiables
	 * @param isLocalScope
	 */
	void setVariables(Map<String, Object> vaiables, boolean isVariableScopeLocal);
	
	/**
	 * 
	 * <p>方法说明：设置用户操作<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日上午10:54:10<p>
	 * @param decision
	 */
	void setDecision(String decision);
	
	/**
	 * 
	 * <p>方法说明：设置用户操作类型<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日下午3:08:29<p>
	 * @param type
	 */
	void setDecisionType(DecisionType type);
	
	/**
	 * 
	 * <p>方法说明：设置用户操作详情<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日上午10:54:12<p>
	 * @param decisionMessage
	 */
	void setDecisionMessage(String decisionMessage);
	
	/**
	 * 
	 * <p>方法说明：设置decision类型<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午4:46:46<p>
	 * @param decisionType
	 */
	
	/**
	 * 
	 * <p>方法说明：设置操作人<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:30:08<p>
	 * @param userId
	 */
	void setUserId(String userId);
		
	/**
	 * 
	 * <p>方法说明：设置当前任务ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:33:35<p>
	 * @param taskId
	 */
	void setTaskId(String taskId);
	
	/**
	 * 
	 * <p>方法说明：设置任务信息对象<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午6:12:21<p>
	 * @param taskInfo
	 */
	void setTaskInfo(TaskInfo taskInfo);
	
	/**
	 * 
	 * <p>方法说明：设置历史活动ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:33:58<p>
	 * @param historicActivityId
	 */
	void setHistoricActivityId(String historicActivityId);
	
	/**
	 * 
	 * <p>方法说明：设置流程实例ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:34:29<p>
	 * @param processInstanceId
	 */
	void setProcessInstanceId(String processInstanceId);
	
	/**
	 * 
	 * <p>方法说明：设置任务执行实例ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:34:51<p>
	 * @param executionId
	 */
	void setExecutionId(String executionId);
	
	/**
	 * 
	 * <p>方法说明：设置父执行ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月29日上午10:42:50<p>
	 * @param parentExecutionId
	 */
	void setParentExecutionId(String parentExecutionId);

}
