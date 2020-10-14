/**
 * 
 */
package org.activiti.engine.extend.cmd.procat;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.biz.ProcessCategory;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.procat.GetProcessCategoryCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月8日上午9:12:07
 */
public class GetProcessCategoryCmd extends AbstractProcessCategoryCmd<ProcessCategory> {

	protected String id;

	public GetProcessCategoryCmd(String id) {
		super();
		this.id = id;
	}

	@Override
	protected ProcessCategory doExecute(CommandContext commandContext) {
		if (this.id == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		return getProcessCategoryEntityManager(commandContext).findProcessCategoryEntityById(id);
	}

}
