/**
 * 
 */
package org.activiti.engine.extend.service.impl;

import org.activiti.engine.extend.cmd.ActivateFormDefinitionCmd;
import org.activiti.engine.extend.cmd.DeleteFormDeploymentCmd;
import org.activiti.engine.extend.cmd.FormDeployCmd;
import org.activiti.engine.extend.cmd.GetFormDefinitionCmd;
import org.activiti.engine.extend.cmd.SuspendFormDefinitionCmd;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.form.FormDefinitionQuery;
import org.activiti.engine.extend.form.FormDeploymentBuilder;
import org.activiti.engine.extend.form.NativeFormDefinitionQuery;
import org.activiti.engine.extend.impl.FormDefinitionQueryImpl;
import org.activiti.engine.extend.impl.NativeFormDefinitionQueryImpl;
import org.activiti.engine.extend.impl.repository.DefaultFormDeploymentBuilderImpl;
import org.activiti.engine.extend.service.ExtendFormService;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.repository.Deployment;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.service.impl.ExtendFormServiceImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月27日下午5:18:01
 */
public class ExtendFormServiceImpl extends ServiceImpl implements ExtendFormService {

	@Override
	public FormDeploymentBuilder createFormDeployment() {
		return new DefaultFormDeploymentBuilderImpl(this);
	}

	@Override
	public void deleteDeployment(String deploymentId) {
		commandExecutor.execute(new DeleteFormDeploymentCmd(deploymentId));
	}

	public Deployment deploy(DefaultFormDeploymentBuilderImpl deploymentBuilder) {
		return commandExecutor.execute(new FormDeployCmd<Deployment>(deploymentBuilder));
	}

	@Override
	public FormDefinitionQuery createFormDefinitionQuery() {
		return new FormDefinitionQueryImpl(commandExecutor);
	}

	@Override
	public NativeFormDefinitionQuery createNativeFormDefinitionQuery() {
		return new NativeFormDefinitionQueryImpl(commandExecutor);
	}

	@Override
	public void suspendFormDefinitionById(String formDefinitionId) {
		commandExecutor.execute(new SuspendFormDefinitionCmd(formDefinitionId, null, null));
	}

	@Override
	public void suspendFormDefinitionByKey(String formDefinitionKey) {
		commandExecutor.execute(new SuspendFormDefinitionCmd(null, formDefinitionKey, null));
	}

	@Override
	public void suspendFormDefinitionByKey(String formDefinitionKey, String tenantId) {
		commandExecutor.execute(new SuspendFormDefinitionCmd(null, formDefinitionKey, tenantId));
	}

	@Override
	public void activateFormDefinitionById(String formDefinitionId) {
		commandExecutor.execute(new ActivateFormDefinitionCmd(formDefinitionId, null, null));
	}

	@Override
	public void activateFormDefinitionByKey(String formDefinitionKey) {
		commandExecutor.execute(new ActivateFormDefinitionCmd(null, formDefinitionKey, null));
	}

	@Override
	public void activateFormDefinitionByKey(String formDefinitionKey, String tenantId) {
		commandExecutor.execute(new ActivateFormDefinitionCmd(null, formDefinitionKey, tenantId));
	}

	@Override
	public FormDefinition getFormDefinition(String formDefinitionId) {
		return commandExecutor.execute(new GetFormDefinitionCmd(formDefinitionId));
	}

}
