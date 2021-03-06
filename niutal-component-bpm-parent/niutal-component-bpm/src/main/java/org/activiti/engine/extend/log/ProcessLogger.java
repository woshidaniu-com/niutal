package org.activiti.engine.extend.log;

import org.activiti.engine.extend.task.DecisionInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：工作流日志记录组件
 *	 <br>class：org.activiti.engine.extend.log.ProcessLogger.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public interface ProcessLogger extends NamedProcessLogger{
	
	static final String LOG_TYPE_PREFIX = "process_log";

	void log(DecisionInfo decisionInfo);
	
}
