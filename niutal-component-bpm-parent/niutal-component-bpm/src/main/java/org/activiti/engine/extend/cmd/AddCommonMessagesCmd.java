/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.persistence.entity.CommonMessageEntity;
import org.activiti.engine.extend.persistence.entity.CommonMessageEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：添加用户常用意见cmd
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.AddCommonMessagesCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午9:45:47
 */
public class AddCommonMessagesCmd extends AbstractCommand<List<Void>> {

	protected String userId;
	protected String message;

	public AddCommonMessagesCmd(String userId, String message) {
		super();
		this.userId = userId;
		this.message = message;
	}

	@Override
	protected List<Void> doExecute(CommandContext commandContext) {
		if (userId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		if (message == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		CommonMessageEntityManager commonMessageEntityManager = commandContext
				.getSession(CommonMessageEntityManager.class);

		commonMessageEntityManager.insert(CommonMessageEntity.create(userId, message));

		return null;
	}

}
