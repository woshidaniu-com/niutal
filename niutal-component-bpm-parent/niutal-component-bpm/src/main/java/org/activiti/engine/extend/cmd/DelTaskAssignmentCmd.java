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
public class DelTaskAssignmentCmd extends AbstractCommand<Void> {

	protected List<Assignment> taskAssignment;

	public DelTaskAssignmentCmd(List<Assignment> taskAssignment) {
		super();
		this.taskAssignment = taskAssignment;
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {
		if (taskAssignment == null || taskAssignment.size() == 0) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		AssignmentEntityManager assignmentEntityManager = commandContext.getSession(AssignmentEntityManager.class);
		for (Assignment assignment : taskAssignment) {
			assignmentEntityManager.delete((PersistentObject) assignment);
		}
		return null;
	}
}
