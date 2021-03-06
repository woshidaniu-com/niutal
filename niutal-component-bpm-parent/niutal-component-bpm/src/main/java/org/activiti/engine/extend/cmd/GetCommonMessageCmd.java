/**
 * 
 */
package org.activiti.engine.extend.cmd;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.persistence.entity.CommonMessageEntityManager;
import org.activiti.engine.extend.task.CommonMessageInfo;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：获取用户常用意见Cmd
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.GetCommonMessagesCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午9:41:01
 */
public class GetCommonMessageCmd extends AbstractCommand<CommonMessageInfo> {

	protected String id;

	public GetCommonMessageCmd(String id) {
		super();
		this.id = id;
	}

	@Override
	protected CommonMessageInfo doExecute(CommandContext commandContext) {

		if (id == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		CommonMessageEntityManager commonMessageEntityManager = commandContext
				.getSession(CommonMessageEntityManager.class);

		return commonMessageEntityManager.findCommonMessageEntityById(id);

	}

}
