/**
 * 
 */
package org.activiti.engine.extend.cmd.procat;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.persistence.entity.ProcessCategoryEntity;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.procat.AddProcessCategoryCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月8日上午9:35:50
 */
public class AddProcessCategoryCmd extends AbstractProcessCategoryCmd<Void> {

	protected String name;
	protected String description;

	public AddProcessCategoryCmd(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {
		if (name == null || description.trim().length() == 0) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		ProcessCategoryEntity createProcessCategoryEntity = ProcessCategoryEntity.createProcessCategoryEntity();

		createProcessCategoryEntity.setName(name);
		createProcessCategoryEntity.setDescription(description);

		getProcessCategoryEntityManager(commandContext).insert(createProcessCategoryEntity);
		return null;
	}

}
