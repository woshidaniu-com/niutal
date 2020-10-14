/**
 * 
 */
package org.activiti.engine.extend.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.cmd.GetTaskAssignmentCmd;
import org.activiti.engine.extend.service.MultiInstanceService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：{@link org.activiti.engine.extend.service.MultiInstanceService}
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月17日下午3:56:05
 */
public class DefaultMultiInstanceServiceImpl extends ServiceImpl implements MultiInstanceService {

	protected static final Logger logger = LoggerFactory.getLogger(DefaultMultiInstanceServiceImpl.class);

	protected static final String DEFAULT_VARIABLE = "_multi_instance_assignee";

	protected static final String DEFAULT_COMPLETION_CONDITION = "_multi_instance_completion_condition";

	protected String completionCondition = DEFAULT_COMPLETION_CONDITION;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.extend.service.MultiInstanceService#resolveCollection
	 * ()
	 */
	@Override
	public Collection<String> resolveCollection(DelegateExecution execution) {
		Collection<String> assignmentsString = new ArrayList<String>();
		String processDefintionId = execution.getProcessDefinitionId();
		String taskDefintionId = execution.getCurrentActivityId();
		if (logger.isDebugEnabled()) {
			logger.debug("MutilInstance is trying to resolve collection expression, " + "process definiton id:{}, "
					+ "task defintion id: {}.", new Object[] { processDefintionId, taskDefintionId });
		}
		GetTaskAssignmentCmd getTaskAssignmentCmd = new GetTaskAssignmentCmd(processDefintionId, taskDefintionId);
		List<Assignment> assignments = getCommandExecutor().execute(getTaskAssignmentCmd);

		for (Assignment assignment : assignments) {
			List<User> resolveUser = assignment.resolveUser();
			if (resolveUser != null && resolveUser.size() > 0) {
				Iterator<User> iterator = resolveUser.iterator();
				while (iterator.hasNext()) {
					User next = iterator.next();
					assignmentsString.add(next.getId());
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"MutilInstance has been resolved collection expression, " + "process definiton id:{}, "
							+ "task defintion id: {}, " + "and the resovled user collection is {}.",
					new Object[] { processDefintionId, taskDefintionId, assignmentsString });
		}

		return assignmentsString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.service.MultiInstanceService#
	 * resolveCompletionCondition()
	 * 
	 * 默认是全部完成多任务流程实例，以后这里需要扩展为动态判断
	 */
	@Override
	public boolean resolveCompletionCondition(String elExpression, DelegateExecution execution) {
		if (logger.isDebugEnabled()) {
			logger.debug("<<==========================>>");
			logger.debug("		  elExpression  ==>>  [" + elExpression + "]");
			logger.debug("			 execution	==>>  [" + execution + "]");
			logger.debug("authenticatedUserId 	==>>  [" + Authentication.getAuthenticatedUserId() + "]");
			logger.debug("<<==========================>>");
		}

		String conditionExpression = elExpression;

		/**
		 * 如果表达式为空，默认通过
		 */
		if (conditionExpression == null || conditionExpression.trim().length() == 0) {
			return false;
		}

		Expression expression = Context.getProcessEngineConfiguration().getExpressionManager()
				.createExpression(conditionExpression);
		Object result = expression.getValue(execution);

		if (result == null) {
			throw new ActivitiException("condition expression returns null");
		}
		if (!(result instanceof Boolean)) {
			throw new ActivitiException(
					"condition expression returns non-Boolean: " + result + " (" + result.getClass().getName() + ")");
		}
		return (Boolean) result;
	}

	public String getCompletionCondition() {
		return completionCondition;
	}

	public void setCompletionCondition(String completionCondition) {
		this.completionCondition = completionCondition;
	}

}
