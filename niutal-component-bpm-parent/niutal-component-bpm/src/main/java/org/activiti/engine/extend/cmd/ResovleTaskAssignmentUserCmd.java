/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.assignment.AssignmentInfo;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：计算assignment 对应的用户信息
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月20日上午9:28:25
 */
public class ResovleTaskAssignmentUserCmd extends AbstractCommand<List<User>> {
	protected AssignmentInfo assignement;

	public ResovleTaskAssignmentUserCmd(AssignmentInfo assignement) {
		super();
		this.assignement = assignement;
	}

	@Override
	protected List<User> doExecute(CommandContext commandContext) {

		if (assignement == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		List<User> userList = new ArrayList<User>();

		if (assignement.getUserId() != null) {
			userList.add(0, assignement.getUserEntity());
		}

		if (assignement.getGroupId() != null) {
			Group group = assignement.getGroupEntity();
			UserQuery createUserQuery = commandContext.getProcessEngineConfiguration().getIdentityService()
					.createUserQuery();
			userList.addAll(createUserQuery.memberOfGroup(group.getId()).list());
		}

		if (assignement.getClazzId() != null) {
			throw new ActivitiException("BPM_EX_FAIL");
		}

		return userList;

	}
}
