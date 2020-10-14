/**
 * 
 */
package org.activiti.engine.extend.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：需要保证流程实例未结束
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.WrapNeedUnfinishedProcessInstanceCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月8日下午3:28:30
 * @param <T>
 */
public abstract class WrapNeedActiveProcessInstanceCmd<T> extends AbstractCommand<T> {

	protected String processInstanceId;

	public WrapNeedActiveProcessInstanceCmd(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Override
	protected T doExecute(CommandContext commandContext) {
		if (processInstanceId == null) {
			throw new ActivitiException("BPM_EX_02");
		}

		ExecutionEntity execution = commandContext.getExecutionEntityManager().findExecutionById(processInstanceId);

		if (execution == null || execution.isEnded()) {
			throw new ActivitiException("BPM_EX_09");
		}

		if(execution.isSuspended()){
			throw new ActivitiException("BPM_EX_08");
		}
		
		return doExecuteWithExecutinEntity(commandContext, execution);
	}

	/**
	 * 
	 * <p>
	 * 方法说明：processInstance 保证了流程实例未结束
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年4月8日下午3:34:46
	 * <p>
	 * 
	 * @param commandContext
	 * @param processInstance
	 * @return
	 */
	protected abstract T doExecuteWithExecutinEntity(CommandContext commandContext,
			ExecutionEntity processInstance);

}
