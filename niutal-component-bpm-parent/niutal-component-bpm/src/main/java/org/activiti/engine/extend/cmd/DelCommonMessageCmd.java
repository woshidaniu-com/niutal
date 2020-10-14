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
 * <h3>说明：DelCommonMessageCmd
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.DelCommonMessageCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午10:20:23
 */
public class DelCommonMessageCmd extends AbstractCommand<List<Void>> {

	protected String id;

	public DelCommonMessageCmd(String id) {
		super();
		this.id = id;
	}

	@Override
	protected List<Void> doExecute(CommandContext commandContext) {
		if (id == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		CommonMessageEntityManager commonMessageEntityManager = commandContext
				.getSession(CommonMessageEntityManager.class);
		CommonMessageEntity findCommonMessageEntityById = commonMessageEntityManager.findCommonMessageEntityById(id);
		if (findCommonMessageEntityById == null) {
			throw new CommonMessageNotFoundException("BPM_EX_FAIL");
		}
		commonMessageEntityManager.delete(findCommonMessageEntityById);
		return null;
	}

}
