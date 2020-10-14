/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.io.Serializable;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration;
import org.activiti.engine.extend.impl.repository.DefaultFormDeploymentBuilderImpl;
import org.activiti.engine.extend.persistence.deploy.ExtendFormDeploymentManager;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.FormDeployCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日上午9:27:28
 */
public class FormDeployCmd<T> implements Command<Deployment>, Serializable {

	private static final long serialVersionUID = 1L;

	private DefaultFormDeploymentBuilderImpl formDeploymentBuilder;

	public FormDeployCmd(DefaultFormDeploymentBuilderImpl formDeploymentBuilder) {
		super();
		this.formDeploymentBuilder = formDeploymentBuilder;
	}

	protected ExtendFormDeploymentManager getFormDeploymentManager(CommandContext commandContext) {
		ExtendFormDeploymentManager formDeploymentManager = null;
		ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
		if (processEngineConfiguration instanceof ExtendSpringProcessEnginConfiguration) {
			formDeploymentManager = ((ExtendSpringProcessEnginConfiguration) processEngineConfiguration)
					.getFormDeploymentManager();
		}
		return formDeploymentManager;
	}

	@Override
	public Deployment execute(CommandContext commandContext) {
		DeploymentEntity deployment = formDeploymentBuilder.getDeployment();
		deployment.setDeploymentTime(commandContext.getProcessEngineConfiguration().getClock().getCurrentTime());
		deployment.setNew(true);

		// Save the data
		commandContext.getDeploymentEntityManager().insertDeployment(deployment);

		if (commandContext.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
			commandContext.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(
					ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_CREATED, deployment));
		}

		// Actually deploy
		ExtendFormDeploymentManager formDeploymentManager = getFormDeploymentManager(commandContext);

		if(formDeploymentManager == null){
			throw new ActivitiException("formDeploymentManager not provided");
		}
		
		formDeploymentManager.deploy(deployment);
		
		if (commandContext.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
			commandContext.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(
					ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_INITIALIZED, deployment));
		}
		return deployment;
	}

}
