package org.activiti.engine.extend.log.impl;

import java.util.List;

import org.activiti.engine.extend.log.ProcessLogger;
import org.activiti.engine.extend.log.ProcessLoggerManager;
import org.activiti.engine.extend.task.DecisionInfo;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：工作流日志管理 <br>
 * class：org.activiti.engine.extend.log.impl.DefaultProcessLoggerManager.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class DefaultProcessLoggerManager implements ProcessLoggerManager{

	protected List<ProcessLogger> processLoggers;

	@Override
	public void log(DecisionInfo decisionInfo) {
		if (decisionInfo == null) {
			return;
		}
		
		ProcessLogger processLogger = getProcessLogger();
		
		if (processLogger == null)
			return;

		processLogger.log(decisionInfo);
	}

	protected ProcessLogger getProcessLogger(){
		if(processLoggers == null || processLoggers.size() == 0)
			return null;
		return processLoggers.get(0);
	}
	
	@Override
	public void init(){
		if(processLoggers != null && processLoggers.size() > 0){
			for (ProcessLogger processLogger : processLoggers) {
				
			}
		}
	}

	public List<ProcessLogger> getProcessLoggers() {
		return processLoggers;
	}

	public void setProcessLoggers(List<ProcessLogger> processLoggers) {
		this.processLoggers = processLoggers;
	}

}
