/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.extend.event.ExtActivitiEventBuilder;
import org.activiti.engine.extend.event.ExtendActivitiEventType;
import org.activiti.engine.extend.persistence.entity.ExtSuspensionStateImpl;
import org.activiti.engine.extend.persistence.entity.ExtSuspensionStateImpl.ExtSuspensionStateUtil;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：ExtActivateProcessInstanceCmd
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月27日上午11:47:20
 */
public class ExtActivateProcessInstanceCmd extends AbstractCommand<ProcessInstance> {

	protected String processInstanceId;

	protected Map<String, Object> variables;

	protected SuspensionState getNewState() {
		return ExtSuspensionStateImpl.ACTIVE;
	}

	public ExtActivateProcessInstanceCmd(String processInstanceId, Map<String, Object> variables) {
		super();
		this.processInstanceId = processInstanceId;
		this.variables = variables;
	}

	@Override
	protected ProcessInstance doExecute(CommandContext commandContext) {
		if (processInstanceId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		ExecutionEntity executionEntity = commandContext.getExecutionEntityManager()
				.findExecutionById(processInstanceId);

		if (executionEntity == null) {
			throw new ActivitiObjectNotFoundException("BPM_EX_FAIL");
		}
		if (!executionEntity.isProcessInstanceType()) {
			throw new ActivitiException("BPM_EX_FAIL");
		}

		ExtSuspensionStateUtil.setSuspensionState(executionEntity, getNewState());

		// set extra variables
		if (variables != null) {
			executionEntity.setVariables(variables);
		}

		// 发布流程被重新激活事件
		if (commandContext.getEventDispatcher().isEnabled()) {
			commandContext.getEventDispatcher()
					.dispatchEvent(ExtActivitiEventBuilder.createCustomEvent(
							ExtendActivitiEventType.INITIATOR_ACTIVATION_EVENT, executionEntity,
							executionEntity.getId(), executionEntity.getProcessInstanceId(),
							executionEntity.getProcessDefinitionId(), executionEntity.getProcessBusinessKey()));
		}

		return executionEntity;

	}

}
