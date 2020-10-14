/**
 * 
 */
package org.activiti.engine.extend.cmd.tasksubmit;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.extend.task.DecisionInfo;
import org.activiti.engine.extend.task.impl.DecisionPersistenceHandler;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：判断当前情况下历史节点是否可退回
 * <p>
 * @className:org.activiti.engine.extend.cmd.tasksubmit.NeedFallbackableHisActivityInstaceDecisionHandler.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月7日下午4:49:57
 */
public abstract class NeedFallbackableHisActivityInstanceDecisionHandler extends DecisionPersistenceHandler {
	
	@Override
	protected void doHandle(DecisionInfo decisionInfo) {
		
		CommandContext commandContext = Context.getCommandContext();
		
		HistoricActivityInstanceQuery historicActivityInstanceQuery = new HistoricActivityInstanceQueryImpl(
				commandContext);

		historicActivityInstanceQuery.activityInstanceId(decisionInfo.getHistoricActivityId())
				.processInstanceId(decisionInfo.getProcessInstanceId());

		HistoricActivityInstance historicActivityInstance = historicActivityInstanceQuery.singleResult();
		
		String historicExecutionId = historicActivityInstance.getExecutionId();

		TaskEntity task = (TaskEntity) decisionInfo.getTaskInfo();		
		
		ExecutionEntity execution = task.getExecution();
		
		// 判断当前activity和历史退回节点activiy是否处于同一个执行中

		if (!StringUtils.equals(historicExecutionId, execution.getId())) {
			throw new ActivitiException("BPM_EX_41");
			//当前任务节点无法退回到您指定的历史节点！
		}
		
		
		doFallback(historicActivityInstance, decisionInfo);
		
	}


	/**
	 * 
	 * <p>方法说明：退回<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月7日下午4:51:15<p>
	 * @param decisionInfo
	 */
	protected abstract void doFallback(HistoricActivityInstance historicActivityInstance, DecisionInfo decisionInfo);

}
