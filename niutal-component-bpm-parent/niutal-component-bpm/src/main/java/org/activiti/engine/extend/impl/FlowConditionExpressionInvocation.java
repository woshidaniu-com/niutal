/**
 * 
 */
package org.activiti.engine.extend.impl;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：流程Flow条件调用器
 * <p>
 * @className:org.activiti.engine.extend.impl.FlowConditionInvocation.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月29日下午4:08:51
 */
public interface FlowConditionExpressionInvocation {

	/**
	 * 
	 * <p>方法说明：计算条件设置结果<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月29日下午4:11:51<p>
	 * @param conditionEpression
	 * @return
	 */
	Object invoke(String elExpression, DelegateExecution execution, String authenticatedUserId);
	
}
