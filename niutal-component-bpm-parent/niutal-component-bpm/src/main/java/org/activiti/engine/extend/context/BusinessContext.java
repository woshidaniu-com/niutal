package org.activiti.engine.extend.context;

import org.activiti.engine.extend.event.listener.AbstractEventListener;
import org.activiti.engine.extend.task.DecisionInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：业务逻辑上下文
 *	 <br>class：org.activiti.engine.extend.context.BusinessContext.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class BusinessContext{
	
	/**
	 * 业务回调线程绑定對象
	 */
	protected static ThreadLocal<AbstractEventListener> businessCallbackThreadLocal = new ThreadLocal<AbstractEventListener>();

	/**
	 * 流程任务处理对象
	 */
	protected static ThreadLocal<DecisionInfo> decisionInfoThreadLocal = new ThreadLocal<DecisionInfo>();
	
	public static AbstractEventListener getBusinessCallback(){
		return businessCallbackThreadLocal.get();
	}
	
	public static void setBusinessCallback(AbstractEventListener call){
		businessCallbackThreadLocal.set(call);
	}
	
	public static void removeBusinessCallback(){
		businessCallbackThreadLocal.remove();
	}
	
	public static DecisionInfo getDecisionInfo(){
		return decisionInfoThreadLocal.get();
	}
	
	public static void setDecisionInfo(DecisionInfo decisionInfo){
		decisionInfoThreadLocal.set(decisionInfo);
	}
	
	public static void removeDecisionInfo(){
		decisionInfoThreadLocal.remove();
	}
}
