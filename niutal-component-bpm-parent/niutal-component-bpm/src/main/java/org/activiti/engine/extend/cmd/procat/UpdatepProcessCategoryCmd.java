/**
 * 
 */
package org.activiti.engine.extend.cmd.procat;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.persistence.entity.ProcessCategoryEntity;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.procat.UpdatepProcessCategoryCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月8日上午9:23:19
 */
public class UpdatepProcessCategoryCmd extends AbstractProcessCategoryCmd<Void> {

	protected String id;
	protected String name;
	protected String description;

	public UpdatepProcessCategoryCmd(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {
		if (id == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}
		if (name == null || description.trim().length() == 0) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		ProcessCategoryEntity processCategoryEntity = (ProcessCategoryEntity) getProcessCategoryEntityManager(
				commandContext).findProcessCategoryEntityById(id);

		if (processCategoryEntity == null) {
			throw new ActivitiException("BPM_EX_41");
		}

		processCategoryEntity.setName(name);

		if (description != null && description.trim().length() > 0) {
			processCategoryEntity.setDescription(description);
		}

		return null;
	}

}
