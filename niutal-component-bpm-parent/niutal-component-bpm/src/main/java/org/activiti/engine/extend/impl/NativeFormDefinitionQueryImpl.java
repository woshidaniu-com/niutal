/**
 * 
 */
package org.activiti.engine.extend.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.form.NativeFormDefinitionQuery;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntityManager;
import org.activiti.engine.impl.AbstractNativeQuery;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.impl.NativeFormDefinitionQueryImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月6日上午8:39:43
 */
public class NativeFormDefinitionQueryImpl extends AbstractNativeQuery<NativeFormDefinitionQuery, FormDefinition>
		implements NativeFormDefinitionQuery {

	private static final long serialVersionUID = 1L;

	public NativeFormDefinitionQueryImpl(CommandContext commandContext) {
		super(commandContext);
	}

	public NativeFormDefinitionQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	protected FormDefinitionEntityManager getFormDefinitiionEntityManager() {
		return commandContext.getSession(FormDefinitionEntityManager.class);
	}

	@Override
	public long executeCount(CommandContext commandContext, Map<String, Object> parameterMap) {
		return getFormDefinitiionEntityManager().findFormDefinitionCountByNativeQuery(parameterMap);
	}

	@Override
	public List<FormDefinition> executeList(CommandContext commandContext, Map<String, Object> parameterMap,
			int firstResult, int maxResults) {
		return getFormDefinitiionEntityManager().findFormDefinitionsByNativeQuery(parameterMap, firstResult,
				maxResults);
	}

}
