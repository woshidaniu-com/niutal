package org.activiti.engine.extend.cmd.procat;

import org.activiti.engine.extend.cmd.AbstractCommand;
import org.activiti.engine.extend.persistence.entity.ProcessCategoryEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

public abstract class AbstractProcessCategoryCmd<T> extends AbstractCommand<T> {

	/**
	 * 
	 * <p>方法说明：获取EntityManager<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月8日上午9:17:10<p>
	 * @param commandContext
	 * @return
	 */
	protected ProcessCategoryEntityManager getProcessCategoryEntityManager(CommandContext commandContext){
		return commandContext
				.getSession(ProcessCategoryEntityManager.class);
	}
	
}
