/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.CommonMessageNotFoundException;
import org.activiti.engine.extend.persistence.entity.CommonMessageEntity;
import org.activiti.engine.extend.persistence.entity.CommonMessageEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：更新用户常用意见
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.UpdateCommonMessageCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午9:52:19
 */
public class UpdateCommonMessageCmd extends AbstractCommand<List<Void>> {

	protected String id;
	protected String userId;
	protected String message;

	public UpdateCommonMessageCmd(String id, String userId, String message) {
		super();
		this.id = id;
		this.userId = userId;
		this.message = message;
	}

	@Override
	protected List<Void> doExecute(CommandContext commandContext) {

		if (id == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		if (userId == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		if (message == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		CommonMessageEntityManager commonMessageEntityManager = commandContext
				.getSession(CommonMessageEntityManager.class);

		CommonMessageEntity findCommonMessageEntityById = commonMessageEntityManager.findCommonMessageEntityById(id);

		if (findCommonMessageEntityById == null) {
			throw new CommonMessageNotFoundException("BPM_EX_FAIL");
		}

		findCommonMessageEntityById.setUserId(userId);
		findCommonMessageEntityById.setMessage(message);

		return null;

	}

}
