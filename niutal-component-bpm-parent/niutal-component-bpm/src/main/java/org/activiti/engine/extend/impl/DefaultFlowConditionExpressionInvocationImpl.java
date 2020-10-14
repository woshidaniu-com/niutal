/**
 * 
 */
package org.activiti.engine.extend.impl;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：扩展流程Flow条件判断，用于以后动态判断使用，目前调用了activiti的uel表达式
 * <p>
 * 
 * @className:org.activiti.engine.extend.impl.DefaultFlowConditionExpressionInvocationImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月29日下午4:42:34
 */
public class DefaultFlowConditionExpressionInvocationImpl implements FlowConditionExpressionInvocation {
	private static final Logger logger = LoggerFactory.getLogger(DefaultFlowConditionExpressionInvocationImpl.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.extend.impl.FlowConditionExpressionInvocation#invoke(
	 * java.lang.String, org.activiti.engine.delegate.DelegateExecution,
	 * java.lang.String)
	 */
	@Override
	public Object invoke(String elExpression, DelegateExecution execution, String authenticatedUserId) {
		if (logger.isDebugEnabled()) {
			logger.debug("<<==========================>>");
			logger.debug("		  elExpression  ==>>  [" + elExpression + "]");
			logger.debug("			 execution	==>>  [" + execution + "]");
			logger.debug("authenticatedUserId 	==>>  [" + authenticatedUserId + "]");
			logger.debug("<<==========================>>");
		}
		
		String conditionExpression = elExpression;

		/**
		 * 如果表达式为空，默认通过
		 */
		if(conditionExpression == null || conditionExpression.trim().length() == 0){
			return true;
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

}
