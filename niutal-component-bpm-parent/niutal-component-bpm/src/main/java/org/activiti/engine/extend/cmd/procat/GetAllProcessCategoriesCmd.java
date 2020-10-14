/**
 * 
 */
package org.activiti.engine.extend.cmd.procat;

import java.util.List;

import org.activiti.engine.extend.biz.ProcessCategory;
import org.activiti.engine.extend.cmd.AbstractCommand;
import org.activiti.engine.extend.persistence.entity.ProcessCategoryEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:org.activiti.engine.extend.cmd.procat.GetAllProcessCategoriesCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月8日上午9:09:08
 */
public class GetAllProcessCategoriesCmd extends AbstractProcessCategoryCmd<List<ProcessCategory>> {

	@Override
	protected List<ProcessCategory> doExecute(CommandContext commandContext) {
		return getProcessCategoryEntityManager(commandContext).findAllProcessCategoryEntity();
	}

}
