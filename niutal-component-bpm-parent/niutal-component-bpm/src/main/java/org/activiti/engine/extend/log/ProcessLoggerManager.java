package org.activiti.engine.extend.log;

import org.activiti.engine.extend.task.DecisionInfo;

public interface ProcessLoggerManager {

	void log(DecisionInfo processLog);

	void init();

}
