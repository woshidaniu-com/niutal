package org.activiti.engine.extend.cmd;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.persistence.entity.AssignmentEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：获取任务操作人员设置信息Cmd <br>
 * class：org.activiti.engine.extend.cmd.GetTaskAssignmentCmd.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class GetProcessAssignmentCmd extends AbstractCommand<List<Assignment>> {

	protected String processDefinitionId;

	protected void postSetup(List<Assignment> findAssignmentByProcessDefinitionId) {
		if (findAssignmentByProcessDefinitionId != null) {
			for (Assignment assignment : findAssignmentByProcessDefinitionId) {
				assignment.getGroupEntity();
				assignment.getUserEntity();
			}
		}
	}

	public GetProcessAssignmentCmd(String processDefinitionId) {
		super();
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	protected List<Assignment> doExecute(CommandContext commandContext) {

		AssignmentEntityManager assignmentEntityManager = commandContext.getSession(AssignmentEntityManager.class);
		if (processDefinitionId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		List<Assignment> findAssignmentByProcessDefinitionId = assignmentEntityManager
				.findAssignmentByProcessDefinitionId(processDefinitionId);
		postSetup(findAssignmentByProcessDefinitionId);
		return findAssignmentByProcessDefinitionId;

	}
}
