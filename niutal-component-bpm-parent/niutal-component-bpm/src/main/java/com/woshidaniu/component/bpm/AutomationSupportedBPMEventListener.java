package com.woshidaniu.component.bpm;

import java.util.Collection;
import java.util.Set;

import org.activiti.engine.IdentityService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.extend.cmd.tasksubmit.AutomaticDecisionHandleCmd;
import org.activiti.engine.extend.context.BusinessContext;
import org.activiti.engine.extend.task.DecisionEntity;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.task.IdentityLink;

/**
 * 
 */

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：该监听实现了自动审核功能
 * <p>
 * 
 * @className:.AutomationSupportedBPMEventListenr.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月11日下午3:48:05
 */
public abstract class AutomationSupportedBPMEventListener extends BPMEventListener {

	private static final long serialVersionUID = 1L;

	protected boolean automaticEnabled = true;

	@Override
	public void onTaskCreated(ActivitiEvent event) {
		super.onTaskCreated(event);

		// 首先要判断在该任务创建之前是否有任务被处理
		DecisionEntity decisionInfo = (DecisionEntity) BusinessContext.getDecisionInfo();
		
		if (decisionInfo != null) {
			
			decisionInfo = decisionInfo.clone();
			
			ActivitiEntityEventImpl taskCompleteEvent = (ActivitiEntityEventImpl) event;

			DelegateTask delegateTask = (DelegateTask) taskCompleteEvent.getEntity();

			String assignee = delegateTask.getAssignee();

			Set<IdentityLink> candidates = delegateTask.getCandidates();

			String currentAuthenticatedUserId = Authentication.getAuthenticatedUserId();

			boolean identityMatched = matchingIdentity(currentAuthenticatedUserId, assignee, candidates);

			if (identityMatched && decisionInfo.getDecisionType().isAutomaticSupported()) {
				
				Collection<Session> sessions = Context.getCommandContext().getSessions().values();
				for (Session session : sessions) {
					session.flush();
				}
				delegateTask.setAssignee(currentAuthenticatedUserId);
				decisionInfo.setTaskId(delegateTask.getId());
				new AutomaticDecisionHandleCmd(decisionInfo, delegateTask).execute(Context.getCommandContext());
				System.out.println(decisionInfo);
			}

		}

	}

	protected boolean matchingIdentity(String currentAuthenticatedUserId, String assignee,
			Set<IdentityLink> candidates) {
		if (BPMUtils.isBlank(currentAuthenticatedUserId))
			return false;

		if (BPMUtils.equals(currentAuthenticatedUserId, assignee))
			return true;

		if (candidates == null || candidates.size() == 0)
			return false;

		IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();

		for (IdentityLink identityLink : candidates) {
			String userId = identityLink.getUserId();
			String groupId = identityLink.getGroupId();
			if (BPMUtils.isNotBlank(userId) && BPMUtils.equals(userId, currentAuthenticatedUserId)) {
				return true;
			}
			if (BPMUtils.isNotBlank(groupId)) {
				long count = identityService.createUserQuery().memberOfGroup(groupId).userId(currentAuthenticatedUserId)
						.count();
				if (count > 0) {
					return true;
				}
			}
		}

		return false;
	}

	protected void setAutomaticEnabled(boolean enabled) {
		this.automaticEnabled = enabled;
	}

	protected boolean isAutomaticEnabled() {
		return automaticEnabled;
	}
}
