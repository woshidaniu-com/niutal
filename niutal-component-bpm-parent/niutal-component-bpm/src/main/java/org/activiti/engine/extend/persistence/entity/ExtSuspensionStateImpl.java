/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.extend.event.ExtActivitiEventBuilder;
import org.activiti.engine.extend.event.ExtendActivitiEventType;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月27日上午8:47:01
 */
public class ExtSuspensionStateImpl implements SuspensionState {

	/**
	 * 流程退回到申请人流程挂起状态
	 */
	public static SuspensionState INITIATOR_SUSPENDED = new ExtSuspensionStateImpl(3, "initiator_suspended");

	protected SuspensionStateImpl suspentionStateImpl;

	public ExtSuspensionStateImpl(int suspensionCode, String string) {
		this.suspentionStateImpl = new SuspensionStateImpl(suspensionCode, string);
	}

	public int getStateCode() {
		return suspentionStateImpl.getStateCode();
	}

	@Override
	public int hashCode() {
		return suspentionStateImpl.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return suspentionStateImpl.equals(obj);
	}

	/**
	 * 
	 * <p>
	 * <h3>niutal框架
	 * <h3>说明：
	 * <p>
	 * 
	 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * @version 2017年3月27日上午8:55:33
	 */
	public static class ExtSuspensionStateUtil {

		public static void setSuspensionState(ProcessDefinitionEntity processDefinitionEntity, SuspensionState state) {
			if (processDefinitionEntity.getSuspensionState() == state.getStateCode()) {
				throw new ActivitiException("Cannot set suspension state '" + state + "' for " + processDefinitionEntity
						+ "': already in state '" + state + "'.");
			}
			processDefinitionEntity.setSuspensionState(state.getStateCode());
			dispatchStateChangeEvent(processDefinitionEntity, state);
		}

		public static void setSuspensionState(ExecutionEntity executionEntity, SuspensionState state) {
			if (executionEntity.getSuspensionState() == state.getStateCode()) {
				throw new ActivitiException("Cannot set suspension state '" + state + "' for " + executionEntity
						+ "': already in state '" + state + "'.");
			}
			executionEntity.setSuspensionState(state.getStateCode());
			dispatchStateChangeEvent(executionEntity, state);
		}

		public static void setSuspensionState(TaskEntity taskEntity, SuspensionState state) {
			if (taskEntity.getSuspensionState() == state.getStateCode()) {
				throw new ActivitiException("Cannot set suspension state '" + state + "' for " + taskEntity
						+ "': already in state '" + state + "'.");
			}
			taskEntity.setSuspensionState(state.getStateCode());
			dispatchStateChangeEvent(taskEntity, state);
		}

		protected static void dispatchStateChangeEvent(Object entity, SuspensionState state) {
			if (Context.getCommandContext() != null && Context.getCommandContext().getEventDispatcher().isEnabled()) {
				ActivitiEntityEvent eventInstance = null;
				if (state == SuspensionState.ACTIVE) {
					eventInstance = ExtActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_ACTIVATED,
							entity);
				} else if (state == SuspensionState.SUSPENDED) {
					eventInstance = ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_SUSPENDED, entity);
				} else if (state == ExtSuspensionStateImpl.INITIATOR_SUSPENDED) {
					eventInstance = ExtActivitiEventBuilder
							.createCustomEvent(ExtendActivitiEventType.INITIATOR_SUSPENSION_EVENT, entity);
				}

				Context.getCommandContext().getEventDispatcher().dispatchEvent(eventInstance);
			}

		}
	}

}
