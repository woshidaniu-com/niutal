package org.activiti.engine.extend.cmd.tasksubmit;

import org.activiti.engine.extend.task.DecisionInfo;
import org.activiti.engine.extend.task.impl.DecisionPersistenceHandler;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：‘PASS’ 处理器
 * <p>
 * @className:org.activiti.engine.extend.cmd.tasksubmit.PASSDecisionHandler.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午6:35:40
 */
public class PASSDecisionHandler extends DecisionPersistenceHandler{

	@Override
	public void doHandle(DecisionInfo decisionInfo) {
		TaskEntity taskEntity = (TaskEntity) decisionInfo.getTaskInfo();
		ExecutionEntity execution = taskEntity.getExecution();
		//设置流程变量，用于业务判断
		execution.setVariable("p_decision", decisionInfo.getDecision());
		execution.setVariable("p_decision_message", decisionInfo.getDecisionMessage());
		taskEntity.complete(decisionInfo.getVariables(), decisionInfo.isVariableScopeLocal());
	}
	
	@Override
	public String getType() {
		return "PASS";
	}
}
