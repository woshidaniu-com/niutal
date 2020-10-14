package org.activiti.engine.extend.cmd;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.persistence.entity.AssignmentEntityManager;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：保存办理人员设置 <br>
 * class：org.activiti.engine.extend.cmd.SetupAssignmentCmd.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class SetupTaskAssignmentCmd extends AbstractCommand<Void> {

	protected List<Assignment> taskAssignment;

	protected String processDefinitionId;

	public SetupTaskAssignmentCmd(String processDefinitionId, List<Assignment> taskAssignment) {
		super();
		this.processDefinitionId = processDefinitionId;
		this.taskAssignment = taskAssignment;
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {

		if (processDefinitionId == null || processDefinitionId.length() == 0) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		if (taskAssignment == null || taskAssignment.size() == 0) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		AssignmentEntityManager assignmentEntityManager = commandContext.getSession(AssignmentEntityManager.class);
		// 先删除老的设置
		assignmentEntityManager.deleteAssignmentByProcessDefinitionId(processDefinitionId);
		// 插入新的设置
		for (Assignment assignment : taskAssignment) {
			assignmentEntityManager.insert((PersistentObject) assignment);
		}
		return null;

	}
}
