/**
 * 
 */
package org.activiti.engine.extend.cmd;

import java.io.Serializable;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration;
import org.activiti.engine.extend.persistence.deploy.ExtendFormDeploymentManager;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.DeleteFormDeploymentCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月2日下午6:14:40
 */
public class DeleteFormDeploymentCmd implements Command<Void>, Serializable {

	private static final long serialVersionUID = 1L;
	protected String deploymentId;

	public DeleteFormDeploymentCmd(String deploymentId) {
		this.deploymentId = deploymentId;
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

	public Void execute(CommandContext commandContext) {
		if (deploymentId == null) {
			throw new ActivitiIllegalArgumentException("deploymentId is null");
		}
		ExtendFormDeploymentManager formDeploymentManager = getFormDeploymentManager(commandContext);

		formDeploymentManager.removeDeployment(deploymentId);

		return null;
	}

}
