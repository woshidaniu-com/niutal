package org.activiti.engine.extend.cmd;

import org.activiti.engine.extend.ExtActivitiException;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3>说明：封装异常信息
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.AbstractCommand.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月8日下午12:13:22
 */
public abstract class AbstractCommand<T> implements Command<T> {

	protected static final Logger log = LoggerFactory.getLogger(AbstractCommand.class);

	@Override
	public T execute(CommandContext commandContext) {

		if (log.isDebugEnabled()) {
			log.debug("执行activiti cmd: {}", this);
		}

		try {
			return doExecute(commandContext);
		} catch (Exception e) {
			ExtActivitiException ex = null;
			if (e instanceof ExtActivitiException) {
				ex = (ExtActivitiException) e;
			} else {
				ex = new ExtActivitiException(e.getMessage(), null, e);
			}
			throw ex;
		}
	}

	/**
	 * 
	 * <p>
	 * 方法说明：子类实现,子类抛出的任何异常,最终都会被封装为 ExtActivitiException
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年4月8日下午12:42:27
	 * <p>
	 * 
	 * @param commandContext
	 * @return
	 * 
	 * @see ExtActivitiException
	 */
	protected abstract T doExecute(CommandContext commandContext);

}
