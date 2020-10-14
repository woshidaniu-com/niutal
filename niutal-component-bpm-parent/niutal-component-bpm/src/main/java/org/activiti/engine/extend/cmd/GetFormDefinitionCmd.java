/**
 * 
 */
package org.activiti.engine.extend.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.persistence.deploy.ExtendFormDeploymentManager;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntity;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.GetFormDefinitionCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月7日下午3:03:08
 */
public class GetFormDefinitionCmd implements Command<FormDefinition> {

	protected String formDefinitionId;

	public GetFormDefinitionCmd(String formDefinitionId) {
		super();
		this.formDefinitionId = formDefinitionId;
	}

	protected ExtendFormDeploymentManager getFormDeploymentManager(CommandContext commandContext) {
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
		if (processEngineConfiguration instanceof ExtendSpringProcessEnginConfiguration) {
			return ((ExtendSpringProcessEnginConfiguration) processEngineConfiguration).getFormDeploymentManager();
		}

		throw new ActivitiException("can not get form deployment manager");
	}

	@Override
	public FormDefinition execute(CommandContext commandContext) {
		if (this.formDefinitionId == null) {
			throw new ActivitiIllegalArgumentException("form definition id can not be null");
		}
		ExtendFormDeploymentManager formDeploymentManager = getFormDeploymentManager(commandContext);
		FormDefinitionEntity findDeployedFormDefinitionById = formDeploymentManager
				.findDeployedFormDefinitionById(formDefinitionId);
		if (findDeployedFormDefinitionById == null) {
			throw new ActivitiException("can not get form definition for id : " + formDefinitionId);
		}
		return findDeployedFormDefinitionById;
	}

}
