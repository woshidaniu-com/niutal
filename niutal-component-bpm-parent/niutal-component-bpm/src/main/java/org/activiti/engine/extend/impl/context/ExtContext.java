/**
 * 
 */
package org.activiti.engine.extend.impl.context;

import java.util.Stack;

import org.activiti.engine.extend.impl.cfg.DecisionHandlerConfig;
import org.activiti.engine.extend.log.ProcessLoggerManager;
import org.activiti.engine.extend.task.DecisionHandlerInvocationFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：扩展context
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午3:25:39
 */
public class ExtContext {

	/**
	 * decisionHandlerConfig
	 */
	protected static ThreadLocal<Stack<DecisionHandlerConfig>> decisionHandlerConfigThreadLocal = new ThreadLocal<Stack<DecisionHandlerConfig>>();
	
	/**
	 * process log manager
	 */
	protected static ThreadLocal<Stack<ProcessLoggerManager>> processLoggerManagerhreadLocal = new ThreadLocal<Stack<ProcessLoggerManager>>();
	
	protected static <T> Stack<T> getStack(ThreadLocal<Stack<T>> threadLocal) {
		Stack<T> stack = threadLocal.get();
		if (stack == null) {
			stack = new Stack<T>();
			threadLocal.set(stack);
		}
		return stack;
	}

	public static DecisionHandlerConfig getDecisionHandlerConfig() {
		Stack<DecisionHandlerConfig> stack = getStack(decisionHandlerConfigThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	public static void setDecisionHandlerConfig(DecisionHandlerConfig decisionHandlerConfig) {
		getStack(decisionHandlerConfigThreadLocal).push(decisionHandlerConfig);
	}

	public static void removeDecisionHandlerConfig() {
		getStack(decisionHandlerConfigThreadLocal).pop();
	}

	////////////////////////////////////////////////////////////////////////////////////////
	
	public static ProcessLoggerManager getProcessLoggerManager() {
		Stack<ProcessLoggerManager> stack = getStack(processLoggerManagerhreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	public static void setProcessLoggerManager(ProcessLoggerManager processLoggerManager) {
		getStack(processLoggerManagerhreadLocal).push(processLoggerManager);
	}

	public static void removeProcessLoggerManager() {
		getStack(processLoggerManagerhreadLocal).pop();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public static DecisionHandlerInvocationFactory getDecisionHandlerInvocationFactory(){
		Stack<DecisionHandlerConfig> stack = getStack(decisionHandlerConfigThreadLocal);
		if (stack.isEmpty()) {
			return null;
		}
		DecisionHandlerConfig peek = stack.peek();
		return peek.getDecisionHandlerInvocationFactory();
	}
	
}
