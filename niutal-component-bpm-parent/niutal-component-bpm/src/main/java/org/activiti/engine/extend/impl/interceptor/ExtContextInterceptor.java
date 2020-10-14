/**
 * 
 */
package org.activiti.engine.extend.impl.interceptor;

import org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration;
import org.activiti.engine.extend.impl.context.ExtContext;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：
 * <p>
 * 
 * @className:org.activiti.engine.extend.impl.interceptor.ExtContextInterceptor.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午3:47:37
 */
public class ExtContextInterceptor extends AbstractCommandInterceptor {

	@Override
	public <T> T execute(CommandConfig config, Command<T> command) {
		try {
			// Push on stack
			//decision handler congfig
			ExtendSpringProcessEnginConfiguration processEngineConfiguration = (ExtendSpringProcessEnginConfiguration)Context.getProcessEngineConfiguration();
			ExtContext.setDecisionHandlerConfig(processEngineConfiguration.getDecisionHandlerConfig());
			
			//process log manager
			ExtContext.setProcessLoggerManager(processEngineConfiguration.getProcessLoggerManager());
			return next.execute(config, command);
		} catch (Exception e) {
			Context.getCommandContext().exception(e);
		} finally {
			ExtContext.removeDecisionHandlerConfig();
			ExtContext.removeProcessLoggerManager();
		}
		return null;
	}
}
