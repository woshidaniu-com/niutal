/**
 * 
 */
package org.activiti.engine.extend.cmd.tasksubmit;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.extend.cmd.AbstractCommand;
import org.activiti.engine.extend.impl.context.ExtContext;
import org.activiti.engine.extend.task.Decision;
import org.activiti.engine.extend.task.DecisionHandlerInvocation;
import org.activiti.engine.extend.task.DecisionType;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.tasksubmit.AutomaticDecisionHandleCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月11日下午6:53:22
 */
public class AutomaticDecisionHandleCmd extends AbstractCommand<Void> {

	protected Decision decision;

	// protected fields
	protected TaskEntity taskEntity;
	// protected fields

	public AutomaticDecisionHandleCmd(Decision decision, DelegateTask taskEntity) {
		super();
		this.decision = decision;
		this.taskEntity = (TaskEntity) taskEntity;
	}

	/**
	 * <p>
	 * 方法说明：TODO
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年3月29日上午8:50:52
	 * <p>
	 * 
	 * @param taskEntity
	 */
	private void initializeDecision() {
		decision.setExecutionId(taskEntity.getExecutionId());
		decision.setProcessInstanceId(taskEntity.getProcessInstanceId());

		// resolve decision type
		Map<String, DecisionType> decisionTypes = ExtContext.getDecisionHandlerConfig().getDecisionTypes();
		decision.setDecisionType(decisionTypes.get(decision.getDecision()));
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {
		try {
			initializeDecision();

			// 1.插入流程参数
			// {p_decision:xxx,p_decision_message:xxx}
			Map<String, Object> taskLocalVariables = new HashMap<String, Object>();
			taskLocalVariables.put("p_task_decision", decision.getDecision());
			taskLocalVariables.put("p_task_decision_message", decision.getDecisionMessage());
			taskEntity.setVariablesLocal(taskLocalVariables);

			if (decision.getVariables() != null && decision.getVariables().size() > 0) {
				if (decision.isVariableScopeLocal()) {
					taskEntity.setVariablesLocal(decision.getVariables());
				} else if (taskEntity.getExecutionId() != null) {
					taskEntity.setExecutionVariables(decision.getVariables());
				} else {
					taskEntity.setVariables(decision.getVariables());
				}
			}

			// 2.初始化：设置当前任务实例
			decision.setTaskInfo(taskEntity);

			// 3.调用处理器
			DecisionHandlerInvocation decisionHandlerInvocation = ExtContext.getDecisionHandlerInvocationFactory()
					.createInstance(decision);

			decisionHandlerInvocation.invoke();

		} finally {
			// 清除线程变量
		}
		return null;
	}

}
