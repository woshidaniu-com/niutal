package org.activiti.engine.extend.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.extend.event.impl.ExtendInitiatorRevocationEventImpl;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：申请人撤销CMD <br>
 * class：org.activiti.engine.extend.cmd.CancelProcessInstanceCmd.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class InitiatorRevocationCmd extends WrapNeedActiveProcessInstanceCmd<Void> {

	protected static String DELETE_REASON = "Initiator Cancelled";

	public InitiatorRevocationCmd(String processInstanceId) {
		super(processInstanceId);
	}

	@Override
	protected Void doExecuteWithExecutinEntity(CommandContext commandContext, ExecutionEntity execution) {
		String processInstanceId = execution.getProcessInstanceId();
		// 如果当前已经有流程任务被操作完成，则不允许流程申请人撤销流程实例

		HistoricTaskInstanceQueryImpl historicTaskInstanceQuery = new HistoricTaskInstanceQueryImpl();
		historicTaskInstanceQuery.processInstanceId(processInstanceId).finished();
		long finishedTaskInstanceCount = commandContext.getHistoricTaskInstanceEntityManager()
				.findHistoricTaskInstanceCountByQueryCriteria(historicTaskInstanceQuery);

		if (finishedTaskInstanceCount == 0) {
			commandContext.getExecutionEntityManager().deleteProcessInstance(processInstanceId, DELETE_REASON, false);
			if (commandContext.getEventDispatcher().isEnabled()) {
				ExtendInitiatorRevocationEventImpl event = new ExtendInitiatorRevocationEventImpl(execution,
						execution.getProcessBusinessKey());
				event.setExecutionId(execution.getId());
				event.setProcessDefinitionId(execution.getProcessDefinitionId());
				event.setProcessInstanceId(execution.getProcessInstanceId());
				commandContext.getEventDispatcher().dispatchEvent(event);
			}
		} else {
			throw new ActivitiException("BPM_EX_33");
		}

		return null;
	}

}
