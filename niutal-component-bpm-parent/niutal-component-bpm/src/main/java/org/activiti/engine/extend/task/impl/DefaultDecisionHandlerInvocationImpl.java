/**
 * 
 */
package org.activiti.engine.extend.task.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.extend.impl.cfg.DecisionHandlerConfig;
import org.activiti.engine.extend.task.DecisionHandler;
import org.activiti.engine.extend.task.DecisionHandlerInvocation;
import org.activiti.engine.extend.task.DecisionInfo;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：decision handler invocation
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午2:39:54
 */
public class DefaultDecisionHandlerInvocationImpl implements DecisionHandlerInvocation {

	protected DecisionHandlerConfig config;

	protected DecisionInfo decisionInfo;

	public DefaultDecisionHandlerInvocationImpl(DecisionHandlerConfig config, DecisionInfo decisionInfo) {
		super();
		this.config = config;
		this.decisionInfo = decisionInfo;
	}
	
	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.task.impl.DecisionHandlerInvocation#invoke()
	 */
	@Override
	public void invoke() {
		
		if(config == null){
			throw new ActivitiException("decisionHandlerConfig not configed!");	
		}
		
		Map<String, DecisionHandler> decisionHandlers = config.getDecisionHandlers();
		if (decisionHandlers == null || (decisionHandlers.get(this.decisionInfo.getDecision()) == null)) {
			throw new ActivitiException("No handler is aviable for decision: " + this.decisionInfo);
		}

		/************************************ 1.前置处理 *********************************************/
		List<DecisionHandler> preHandlers = config.getPreHandlers();
		if (preHandlers.size() > 0) {
			for (DecisionHandler preHandler : preHandlers) {
				preHandler.handle(decisionInfo);
			}
		}

		/************************************ 2.处理decision *********************************************/
		config.getDecisionHandlers().get(decisionInfo.getDecision()).handle(decisionInfo);

		/************************************ 3.后置处理 *********************************************/
		List<DecisionHandler> postHanders = config.getPostHanders();
		if (postHanders.size() > 0) {
			for (DecisionHandler postHandler : postHanders) {
				postHandler.handle(decisionInfo);
			}
		}
	}

	public void setConfig(DecisionHandlerConfig config) {
		this.config = config;
	}

	public DecisionHandlerConfig getConfig() {
		return this.config;
	}

	public DecisionInfo getDecisionInfo() {
		return decisionInfo;
	}

	public void setDecisionInfo(DecisionInfo decisionInfo) {
		this.decisionInfo = decisionInfo;
	}
	
}
