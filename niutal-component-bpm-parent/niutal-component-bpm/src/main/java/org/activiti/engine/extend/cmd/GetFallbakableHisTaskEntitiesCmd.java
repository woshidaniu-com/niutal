/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：获取可退回的历史节点cmd
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月22日下午8:08:37
 */
public class GetFallbakableHisTaskEntitiesCmd extends AbstractCommand<List<HistoricActivityInstance>> {

	protected static final List<String> FITLERED_ACTIVITY_TYPE = new ArrayList<String>();

	static {
		FITLERED_ACTIVITY_TYPE.add(BpmnXMLConstants.ELEMENT_EVENT_START);
		FITLERED_ACTIVITY_TYPE.add(BpmnXMLConstants.ELEMENT_TASK_USER);
	}

	// TODO 需要过滤之前由于退回，中间节点在当前节点之后的的历史任务节点
	protected List<HistoricActivityInstance> extractHistoricTaskInfo(
			List<HistoricActivityInstance> hisActivityEntityList) {
		List<HistoricActivityInstance> taskInfoList = new ArrayList<HistoricActivityInstance>();
		if (hisActivityEntityList == null || hisActivityEntityList.size() == 0) {
			return taskInfoList;
		}

		for (HistoricActivityInstance hisActivityEntity : hisActivityEntityList) {
			if (FITLERED_ACTIVITY_TYPE.contains(hisActivityEntity.getActivityType())) {
				// 如果是子流程，过滤掉子流程中的开始节点，避免和流程的开始节点混淆
				if (isActivityFallbackable(hisActivityEntity))
					taskInfoList.add(hisActivityEntity);
			}
		}
		return taskInfoList;
	}

	//
	protected boolean isActivityFallbackable(HistoricActivityInstance historicActivity) {
		// 类型是否匹配
		boolean isTypeMatch = FITLERED_ACTIVITY_TYPE.contains(historicActivity.getActivityType());
		// 是否是特殊的开始节点
		boolean isNoneEventStart = BpmnXMLConstants.ELEMENT_EVENT_START.equals(historicActivity.getActivityType());
		// 当前执行实例是否是流程实例
		boolean isProcessInstanceType = currentExecutionEntity.isProcessInstanceType();

		if (isNoneEventStart && (!isProcessInstanceType))
			return false;

		return isTypeMatch;
	}

	protected String processInstanceId;

	protected String currentTaskId;

	protected TaskEntity currentTaskEntity;

	protected ExecutionEntity currentExecutionEntity;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getCurrentTaskId() {
		return currentTaskId;
	}

	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}

	public GetFallbakableHisTaskEntitiesCmd(String processInstanceId, String currentTaskId) {
		super();
		this.processInstanceId = processInstanceId;
		this.currentTaskId = currentTaskId;
	}

	@Override
	protected List<HistoricActivityInstance> doExecute(CommandContext commandContext) {

		if (processInstanceId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		if (currentTaskId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		// 获取当前任务对象
		currentTaskEntity = commandContext.getTaskEntityManager().findTaskById(currentTaskId);

		currentExecutionEntity = currentTaskEntity.getExecution();

		List<HistoricActivityInstance> hisActivityEntityList = null;
		if (currentTaskEntity != null) {
			// 获取历史任务列表
			// 判断当前任务的执行ID是否和历史任务的执行ID一样,如果一样,任务是可退回的任务节点
			HistoricActivityInstanceQuery hisActivityInstanceQuery = new HistoricActivityInstanceQueryImpl(
					commandContext);
			hisActivityEntityList = hisActivityInstanceQuery.finished().processInstanceId(processInstanceId)
					.executionId(currentTaskEntity.getExecutionId()).orderByHistoricActivityInstanceEndTime().desc()
					.list();
		}

		// 抽取可退回的历史节点
		List<HistoricActivityInstance> taskInfoList = extractHistoricTaskInfo(hisActivityEntityList);

		return taskInfoList;

	}

}
