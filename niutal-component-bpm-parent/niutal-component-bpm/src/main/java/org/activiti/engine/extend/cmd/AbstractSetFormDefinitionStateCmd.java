/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.impl.FormDefinitionQueryImpl;
import org.activiti.engine.extend.persistence.deploy.ExtendFormDeploymentManager;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntity;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntityManager;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.SuspensionState;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.AbstractSetFormDefinitionStateCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月6日上午9:15:50
 */
public abstract class AbstractSetFormDefinitionStateCmd implements Command<Void> {
	protected String formDefinitionId;
	protected String formDefinitionKey;
	protected String tenantId;

	public AbstractSetFormDefinitionStateCmd(String formDefinitionId, String formDefinitionKey, String tenantId) {
		this.formDefinitionId = formDefinitionId;
		this.formDefinitionKey = formDefinitionKey;
		this.tenantId = tenantId;
	}

	public Void execute(CommandContext commandContext) {

		List<FormDefinitionEntity> formDefinitions = findFormDefinition(commandContext);

		changeFormDefinitionState(commandContext, formDefinitions);

		return null;
	}

	protected FormDefinitionEntityManager getFormDefinitionEntityManager(CommandContext commandContext) {
		return commandContext.getSession(FormDefinitionEntityManager.class);
	}

	protected List<FormDefinitionEntity> findFormDefinition(CommandContext commandContext) {

		// Validation of input parameters
		if (formDefinitionId == null && formDefinitionKey == null) {
			throw new ActivitiIllegalArgumentException("Form definition id or key cannot be null");
		}

		List<FormDefinitionEntity> formDefinitionEntities = new ArrayList<FormDefinitionEntity>();
		FormDefinitionEntityManager formDefinitionManager = getFormDefinitionEntityManager(commandContext);

		if (formDefinitionId != null) {

			FormDefinitionEntity formDefinitionEntity = formDefinitionManager.findFormDefinitionById(formDefinitionId);
			if (formDefinitionEntity == null) {
				throw new ActivitiObjectNotFoundException(
						"Cannot find form definition for id '" + formDefinitionId + "'", FormDefinition.class);
			}
			formDefinitionEntities.add(formDefinitionEntity);

		} else {

			FormDefinitionQueryImpl query = new FormDefinitionQueryImpl(commandContext)
					.formDefinitionKey(formDefinitionKey);

			if (tenantId == null || ProcessEngineConfiguration.NO_TENANT_ID.equals(tenantId)) {
				query.formDefinitionWithoutTenantId();
			} else {
				query.formDefinitionTenantId(tenantId);
			}

			List<FormDefinition> formDefinitions = query.list();
			if (formDefinitions.isEmpty()) {
				throw new ActivitiException("Cannot find form definition for key '" + formDefinitionKey + "'");
			}

			for (FormDefinition formDefinition : formDefinitions) {
				formDefinitionEntities.add((FormDefinitionEntity) formDefinition);
			}

		}
		return formDefinitionEntities;
	}

	static class FormSuspensionStateUtil extends SuspensionState.SuspensionStateUtil {
		/**
		 * 
		 * <p>
		 * 方法说明：设置表单状态信息
		 * <p>
		 * <p>
		 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
		 * <p>
		 * <p>
		 * 时间：2017年6月6日上午9:27:07
		 * <p>
		 * 
		 * @param formDefinitionEntity
		 * @param state
		 */
		public static void setSuspensionState(FormDefinitionEntity formDefinitionEntity, SuspensionState state) {
			formDefinitionEntity.setSuspensionState(state.getStateCode());
			dispatchStateChangeEvent(formDefinitionEntity, state);
		}
	}

	protected ExtendFormDeploymentManager getFormDeploymentManager(CommandContext commandContext) {
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();

		if (processEngineConfiguration instanceof ExtendSpringProcessEnginConfiguration) {
			return ((ExtendSpringProcessEnginConfiguration) processEngineConfiguration).getFormDeploymentManager();
		}

		return null;
	}

	protected void changeFormDefinitionState(CommandContext commandContext,
			List<FormDefinitionEntity> formDefinitions) {
		for (FormDefinitionEntity formDefinition : formDefinitions) {

			FormSuspensionStateUtil.setSuspensionState(formDefinition, getFormDefinitionSuspensionState());

			// Evict cache

			getFormDeploymentManager(commandContext).getFormDefinitionCache().remove(formDefinition.getId());

		}
	}

	// ABSTRACT METHODS
	// ////////////////////////////////////////////////////////////////////

	/**
	 * Subclasses should return the wanted {@link SuspensionState} here.
	 */
	protected abstract SuspensionState getFormDefinitionSuspensionState();

}
