/**
 * 
 */
package org.activiti.engine.extend.task.impl;

import org.activiti.engine.extend.impl.cfg.DecisionHandlerConfig;
import org.activiti.engine.extend.task.DecisionHandlerInvocation;
import org.activiti.engine.extend.task.DecisionHandlerInvocationFactory;
import org.activiti.engine.extend.task.DecisionInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：DefaultDecisionHandlerInvocationFactoryImpl
 * <p>
 * @className:org.activiti.engine.extend.task.impl.DefaultDecisionHandlerInvocationFactoryImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月6日上午9:24:23
 */
public class DefaultDecisionHandlerInvocationFactoryImpl implements DecisionHandlerInvocationFactory {

	protected DecisionHandlerConfig config;

	@Override
	public DecisionHandlerInvocation createInstance(DecisionInfo decisionInfo) {
		return new DefaultDecisionHandlerInvocationImpl(config, decisionInfo);
	}

	@Override
	public void setDecisionHandlerConfig(DecisionHandlerConfig config) {
		this.config = config;
	}

}
