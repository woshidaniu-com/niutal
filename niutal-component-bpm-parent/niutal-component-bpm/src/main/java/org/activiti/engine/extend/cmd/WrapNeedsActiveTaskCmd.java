/**
 * 
 */
package org.activiti.engine.extend.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.WrapNeedsActiveTaskCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月8日下午12:48:55
 */
public abstract class WrapNeedsActiveTaskCmd<T> extends AbstractCommand<T> {

	protected String taskId;

	public WrapNeedsActiveTaskCmd(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * Subclasses must implement in this method their normal command logic. The
	 * provided task is ensured to be active.
	 */
	protected abstract T doExecute(CommandContext commandContext, TaskEntity task);

	@Override
	protected T doExecute(CommandContext commandContext) {
		if (taskId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_03");
		}

		TaskEntity task = commandContext.getTaskEntityManager().findTaskById(taskId);

		if (task == null) {
			throw new ActivitiObjectNotFoundException("BPM_EX_14");
		}

		if (task.isSuspended()) {
			throw new ActivitiException("BPM_EX_15");
		}

		return doExecute(commandContext, task);
	}

}
