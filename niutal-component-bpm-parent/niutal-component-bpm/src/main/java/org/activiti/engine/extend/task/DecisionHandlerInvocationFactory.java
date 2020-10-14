/**
 * 
 */
package org.activiti.engine.extend.task;

import org.activiti.engine.extend.impl.cfg.DecisionHandlerConfig;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：DecisionHandlerInvocationFactory
 * <p>
 * @className:org.activiti.engine.extend.task.DecisionHandlerInvocationFactory.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月6日上午9:14:01
 */
public interface DecisionHandlerInvocationFactory {

	/**
	 * 
	 * <p>方法说明：创建调用实体<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日上午9:23:53<p>
	 * @param config
	 * @param decisionInfo
	 * @return
	 */
	DecisionHandlerInvocation createInstance(DecisionInfo decisionInfo);
	
	/**
	 * 
	 * <p>方法说明：设置decision handler config<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日上午9:37:51<p>
	 * @param config
	 */
	void setDecisionHandlerConfig(DecisionHandlerConfig config);
	
}
