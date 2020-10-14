/**
 * 
 */
package org.activiti.engine.extend.task.impl;

import org.activiti.engine.extend.task.DecisionHandler;
import org.activiti.engine.extend.task.DecisionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：日志处理Handler
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午2:11:24
 */
public class DecisionLogHandler implements DecisionHandler {

	private static final Logger logger = LoggerFactory.getLogger(DecisionLogHandler.class);

	protected void logDebug(DecisionInfo decisionInfo) {
		String taskDefinitionKey = decisionInfo.getTaskDefinitionKey();
		StringBuilder sb = new StringBuilder();
		sb.append("process engine is handling task:{\n").append("ProcessDefinitionId:{}")
				.append(",ProcessInstanceId:{}").append(",ExecutionId:{}").append(",TaskId:{}")
				.append(",TaskDefinitionKey:{}").append(",DecisionType:{}").append(",Decision:{}")
				.append(",DecisionMessage:{}\n").append("}");

		logger.debug(sb.toString(), decisionInfo.getProcessDefintionId(), decisionInfo.getProcessInstanceId(), decisionInfo.getExecutionId(), decisionInfo.getTaskId(), taskDefinitionKey,
				decisionInfo.getDecisionType(), decisionInfo.getDecision(), decisionInfo.getDecisionMessage());
	}

	protected void logInfo(DecisionInfo decisionInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("process engine is handling task:{").append(",ExecutionId:{}").append(",TaskId:{}")
				.append(",DecisionType:{}").append(",Decision:{}").append(",DecisionMessage:{}").append("}");
		logger.info(sb.toString(), decisionInfo.getExecutionId(), decisionInfo.getTaskId(), decisionInfo.getDecisionType(), decisionInfo.getDecision());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.task.DecisionHandler#handle(org.activiti.
	 * engine.extend.task.DecisionInfo)
	 */
	@Override
	public void handle(DecisionInfo decisionInfo) {
		if (decisionInfo == null)
			return;
		if (logger.isDebugEnabled()) {
			logDebug(decisionInfo);
		} else {
			logInfo(decisionInfo);
		}
	}

	@Override
	public String getType() {
		return "LOG";
	}
}
