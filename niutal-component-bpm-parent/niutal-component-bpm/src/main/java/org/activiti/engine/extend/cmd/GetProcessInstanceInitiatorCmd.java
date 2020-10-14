package org.activiti.engine.extend.cmd;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.activiti.engine.task.IdentityLinkType;

public class GetProcessInstanceInitiatorCmd extends AbstractCommand<User> {

	protected String processInstanceId;

	public GetProcessInstanceInitiatorCmd(String processInstanceId) {
		super();
		this.processInstanceId = processInstanceId;
	}

	@Override
	protected User doExecute(CommandContext commandContext) {

		User user = null;

		if (processInstanceId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		UserIdentityManager userIdentityManager = commandContext.getUserIdentityManager();

		HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = commandContext
				.getHistoricIdentityLinkEntityManager();

		List<HistoricIdentityLinkEntity> historicIdentityLinkEntityList = historicIdentityLinkEntityManager
				.findHistoricIdentityLinksByProcessInstanceId(processInstanceId);

		if (historicIdentityLinkEntityList != null && historicIdentityLinkEntityList.size() > 0) {
			for (HistoricIdentityLinkEntity historicIdentityLinkEntity : historicIdentityLinkEntityList) {
				if (historicIdentityLinkEntity.getType().equals(IdentityLinkType.STARTER)) {
					String userId = historicIdentityLinkEntity.getUserId();
					user = userIdentityManager.findUserById(userId);

				}
			}
		}

		return user;

	}

}
