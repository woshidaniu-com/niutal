/**
 * 
 */
package org.activiti.engine.extend.cmd.procat;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.procat.DeleteProcessCategoryCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月8日上午9:19:01
 * @param <T>
 */
public class DeleteProcessCategoryCmd extends AbstractProcessCategoryCmd<Void> {
	protected String id;

	public DeleteProcessCategoryCmd(String id) {
		super();
		this.id = id;
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {
		if(id == null){
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		return null;
	}

}
