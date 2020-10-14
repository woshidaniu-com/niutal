/**
 * 
 */
package org.activiti.engine.extend.task;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.TaskInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：供查询使用
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日上午10:51:22
 */
public interface DecisionInfo extends Serializable {

	/**
	 * 
	 * <p>方法说明：获取流程参数<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午6:31:52<p>
	 * @return
	 */
	Map<String, Object> getVariables();
	
	/**
	 * 
	 * <p>方法说明：流程参数是否是本地范围<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午6:32:03<p>
	 * @return
	 */
	boolean isVariableScopeLocal();
	/**
	 * 
	 * <p>方法说明：获取任务操作类型<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午12:35:17<p>
	 * @return
	 */
	DecisionType getDecisionType();
	
	/**
	 * 
	 * <p>方法说明：获取用户操作<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日上午10:51:44<p>
	 * @return
	 */
	String getDecision();
	
	/**
	 * 
	 * <p>方法说明：获取用户操作详情<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日上午10:52:03<p>
	 * @return
	 */
	String getDecisionMessage();
	
	/**
	 * 
	 * <p>方法说明：获取操作人ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午4:57:00<p>
	 * @return
	 */
	String getUserId();
	
	/**
	 * 
	 * <p>方法说明：获取操作人用户信息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午4:58:50<p>
	 * @return
	 */
	User getUserInfo();
	
	/**
	 * 
	 * <p>方法说明：获取记录创建事件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午12:30:38<p>
	 * @return
	 */
	Date getCreateDate();
	
	/**
	 * 
	 * <p>方法说明：获取流程实例ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午12:30:59<p>
	 * @return
	 */
	String getProcessInstanceId();
	
	/**
	 * 
	 * <p>方法说明：获取流程实例<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午4:59:39<p>
	 * @return
	 */
	ProcessInstance getProcessInstance();
	
	/**
	 * 
	 * <p>方法说明：获取流程定义ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午12:31:08<p>
	 * @return
	 */
	String getProcessDefintionId();
	
	/**
	 * 
	 * <p>方法说明：获取流程执行实例ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午12:31:17<p>
	 * @return
	 */
	String getExecutionId();
	
	/**
	 * 
	 * <p>方法说明：获取父执行ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月29日上午10:44:49<p>
	 * @return
	 */
	String getParentExecutionId();
	
	/**
	 * 
	 * <p>方法说明：获取流程执行实例<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:00:43<p>
	 * @return
	 */
	Execution getExecution();
	
	/**
	 * 
	 * <p>方法说明：获取当前任务ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午12:31:31<p>
	 * @return
	 */
	String getTaskId();
	
	/**
	 * 
	 * <p>方法说明：获取当前任务<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:01:41<p>
	 * @return
	 */
	TaskInfo getTaskInfo();
	
	/**
	 * 
	 * <p>方法说明：获取当前任务定义Key<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午12:31:48<p>
	 * @return
	 */
	String getTaskDefinitionKey();
	
	/**
	 * 
	 * <p>方法说明：获取历史节点ID<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午4:13:07<p>
	 * @return
	 */
	String getHistoricActivityId();
	
	/**
	 * 
	 * <p>方法说明：获取历史节点实例<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午5:03:19<p>
	 * @return
	 */
	HistoricActivityInstance getHistoricActivityInstance();
	
	/**
	 * 
	 * <p>方法说明：获取历史节点定义Key<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午4:13:27<p>
	 * @return
	 */
	String getHistoricActivityDefinitionKey();
	
}
