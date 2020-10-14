/**
 * 
 */
package org.activiti.engine.extend.form;

import java.util.Map;
import java.util.Set;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.extend.cfg.ExtendSpringProcessEnginConfiguration;
import org.activiti.engine.extend.persistence.deploy.ExtendFormDeploymentManager;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntity;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntityManager;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.impl.form.FormDeployer.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日上午9:38:45
 */
public class FormDeployer implements Deployer {
	private static final Logger log = LoggerFactory.getLogger(FormDeployer.class);
	protected IdGenerator idGenerator;

	protected FormDefinitionEntityManager getFormDefinitionEntityManager() {
		return Context.getCommandContext().getSession(FormDefinitionEntityManager.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.impl.persistence.deploy.Deployer#deploy(org.activiti.
	 * engine.impl.persistence.entity.DeploymentEntity, java.util.Map)
	 */
	@Override
	public void deploy(DeploymentEntity deployment, Map<String, Object> deploymentSettings) {
		log.debug("Processing deployment {}", deployment.getName());
		Map<String, ResourceEntity> resources = deployment.getResources();
		Set<String> keySet = resources.keySet();
		byte[] resourceBytes = null;
		FormDefinitionEntity formDefinition;
		CommandContext commandContext = Context.getCommandContext();
		FormDefinitionEntityManager formDefinitionManager = commandContext
				.getSession(FormDefinitionEntityManager.class);
		// DbSqlSession dbSqlSession =
		// commandContext.getSession(DbSqlSession.class);
		// processDefinitionManager.insert(persistentObject);
		for (String resourceKey : keySet) {
			ResourceEntity resourceEntity = resources.get(resourceKey);
			resourceBytes = resourceEntity.getBytes();
			break;
		}
		if (resourceBytes != null) {
			FormPaser formPaserInstance = FormPaser.createFormPaserInstance();
			formPaserInstance.source(resourceBytes).execute();
			formDefinition = formPaserInstance.getFormDefinitionEntity();
			formDefinition.setTenantId(deployment.getTenantId());
			if (deployment.isNew()) {
				if (formDefinition.getKey() == null || formDefinition.getKey().length() == 0) {
					formDefinition.setKey(generateFormKey(idGenerator));
				}

				int formDefinitionVersion;
				FormDefinitionEntity latestFormDefinition = null;
				if (formDefinition.getTenantId() != null
						&& !ProcessEngineConfiguration.NO_TENANT_ID.equals(formDefinition.getTenantId())) {
					latestFormDefinition = getFormDefinitionEntityManager().findLatestFormDefinitionByKeyAndTenantId(
							formDefinition.getKey(), formDefinition.getTenantId());
				} else {
					latestFormDefinition = getFormDefinitionEntityManager()
							.findLatestFormDefinitionByKey(formDefinition.getKey());
				}
				if (latestFormDefinition != null) {
					formDefinitionVersion = latestFormDefinition.getVersion() + 1;
				} else {
					formDefinitionVersion = 1;
				}
				formDefinition.setVersion(formDefinitionVersion);

				String nextId = idGenerator.getNextId();
				String formDefinitionId = formDefinition.getKey() + ":" + formDefinition.getVersion() + ":" + nextId;
				if (formDefinitionId.length() > 64) {
					formDefinitionId = nextId;
				}
				formDefinition.setId(formDefinitionId);
				formDefinition.setDeploymentId(deployment.getId());
				if (commandContext.getProcessEngineConfiguration().getEventDispatcher().isEnabled()) {
					commandContext.getProcessEngineConfiguration().getEventDispatcher().dispatchEvent(
							ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_CREATED, formDefinition));
				}

				formDefinitionManager.insert(formDefinition);
			} else {
				String deploymentId = deployment.getId();
				formDefinition.setDeploymentId(deploymentId);

				FormDefinitionEntity persistedFormDefinition = null;
				if (formDefinition.getTenantId() == null
						|| ProcessEngineConfiguration.NO_TENANT_ID.equals(formDefinition.getTenantId())) {
					persistedFormDefinition = formDefinitionManager
							.findFormDefinitionByDeploymentAndKey(deploymentId, formDefinition.getKey());
				} else {
					persistedFormDefinition = formDefinitionManager.findFormDefinitionByDeploymentAndKeyAndTenantId(
							deploymentId, formDefinition.getKey(), formDefinition.getTenantId());
				}

				if (persistedFormDefinition != null) {
					formDefinition.setId(persistedFormDefinition.getId());
					formDefinition.setVersion(persistedFormDefinition.getVersion());
					formDefinition.setSuspensionState(persistedFormDefinition.getSuspensionState());
				}
			}
			// add cache
			addtoCache(formDefinition);

		}
	}

	/**
	 * 
	 * <p>
	 * 方法说明：自动生成的表单key
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年6月5日上午10:24:50
	 * <p>
	 * 
	 * @param idGenerator
	 * @return
	 */
	protected String generateFormKey(IdGenerator idGenerator) {
		return "fid-" + idGenerator.getNextId();
	}

	protected void addtoCache(FormDefinitionEntity formDefinition) {
		ExtendFormDeploymentManager formDeploymentManager = null;
		ProcessEngineConfigurationImpl processEngineConfiguration = Context.getCommandContext()
				.getProcessEngineConfiguration();
		ExtendSpringProcessEnginConfiguration extProcessEnginConf = null;
		if (processEngineConfiguration instanceof ExtendSpringProcessEnginConfiguration) {
			extProcessEnginConf = ((ExtendSpringProcessEnginConfiguration) processEngineConfiguration);
			formDeploymentManager = extProcessEnginConf.getFormDeploymentManager();
		}
		if (formDeploymentManager != null) {
			formDeploymentManager.getFormDefinitionCache().add(formDefinition.getId(), formDefinition);
		}
	}

	protected ExtendFormDeploymentManager getFormDeploymentManager() {
		ExtendFormDeploymentManager formDeploymentManager = null;
		ProcessEngineConfigurationImpl processEngineConfiguration = Context.getCommandContext()
				.getProcessEngineConfiguration();
		if (processEngineConfiguration instanceof ExtendSpringProcessEnginConfiguration) {
			formDeploymentManager = ((ExtendSpringProcessEnginConfiguration) processEngineConfiguration)
					.getFormDeploymentManager();
		}
		return formDeploymentManager;
	}

	protected void createResource(String name, byte[] bytes, DeploymentEntity deploymentEntity) {
		ResourceEntity resource = new ResourceEntity();
		resource.setName(name);
		resource.setBytes(bytes);
		resource.setDeploymentId(deploymentEntity.getId());

		resource.setGenerated(true);

		Context.getCommandContext().getDbSqlSession().insert(resource);
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

}
