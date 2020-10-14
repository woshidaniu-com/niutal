/**
 * 
 */
package org.activiti.engine.extend.task.impl;

import org.activiti.engine.extend.impl.context.ExtContext;
import org.activiti.engine.extend.log.ProcessLoggerManager;
import org.activiti.engine.extend.task.DecisionHandler;
import org.activiti.engine.extend.task.DecisionInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：decision entity持久化处理器，【即：审核日志的持久化处理器】
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午2:29:45
 */
public abstract class DecisionPersistenceHandler implements DecisionHandler {
	
	protected boolean persistence = true;

	protected boolean isPersistence() {
		return persistence;
	}

	public void setPersistence(boolean persistence) {
		this.persistence = persistence;
	}

	/**
	 * 
	 * <p>方法说明：获取流程日志记录管理器<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日下午2:40:57<p>
	 * @return
	 */
	protected ProcessLoggerManager getProcessLoggerManager(){
		return ExtContext.getProcessLoggerManager();
	}
	

	
	@Override
	public void handle(DecisionInfo decisionInfo) {
		if(decisionInfo != null){
			//处理流程逻辑
			doHandle(decisionInfo);
			
			logDecision(decisionInfo);
		}
	}

	
	protected void logDecision(DecisionInfo decisionInfo){
		//如果没有异常，记录流程日志
		if(isPersistence()){
			getProcessLoggerManager().log(decisionInfo);
		}
	}

	/**
	 * 
	 * <p>方法说明：调用子类实现<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日下午1:53:20<p>
	 * @param decisionInfo
	 */
	protected abstract void doHandle(DecisionInfo decisionInfo);
	
}
