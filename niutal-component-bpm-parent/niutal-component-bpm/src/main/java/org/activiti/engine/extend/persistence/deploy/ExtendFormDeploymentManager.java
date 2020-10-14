/**
 * 
 */
package org.activiti.engine.extend.persistence.deploy;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.impl.FormDefinitionQueryImpl;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntity;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntityManager;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManager;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ModelEntityManager;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManager;
import org.activiti.engine.repository.Model;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：扩展了原生的部署管理器，用于支持表单部署
 * <p>
 * 
 * @className:org.activiti.engine.extend.form.FormDeploymentManager.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日上午10:13:28
 */
public class ExtendFormDeploymentManager {
	//

	protected DeploymentCache<FormDefinitionEntity> formDefinitionCache;
	protected List<Deployer> formDeployers;

	public void deploy(DeploymentEntity deployment) {
		deploy(deployment, null);
	}

	public void deploy(DeploymentEntity deployment, Map<String, Object> deploymentSettings) {
		for (Deployer deployer : formDeployers) {
			deployer.deploy(deployment, deploymentSettings);
		}
	}

	protected FormDefinitionEntityManager getFormDefinitionEntityManager() {
		return Context.getCommandContext().getSession(FormDefinitionEntityManager.class);
	}

	public FormDefinitionEntity findDeployedFormDefinitionById(String formDefinitionId) {
		if (formDefinitionId == null) {
			throw new ActivitiIllegalArgumentException("Invalid form definition id : null");
		}

		// first try the cache
		FormDefinitionEntity formDefinition = formDefinitionCache.get(formDefinitionId);

		if (formDefinition == null) {
			formDefinition = getFormDefinitionEntityManager().findFormDefinitionById(formDefinitionId);
			if (formDefinition == null) {
				throw new ActivitiObjectNotFoundException(
						"no deployed form definition found with id '" + formDefinitionId + "'", FormDefinition.class);
			}
			formDefinition = resolveFormDefinition(formDefinition);
		}
		return formDefinition;
	}

	public FormDefinitionEntity findFormDefinitionByIdFromDatabase(String formDefinitionId) {
		if (formDefinitionId == null) {
			throw new ActivitiIllegalArgumentException("Invalid form definition id : null");
		}

		FormDefinitionEntity formDefinition = getFormDefinitionEntityManager().findFormDefinitionById(formDefinitionId);

		if (formDefinition == null) {
			throw new ActivitiObjectNotFoundException(
					"no deployed form definition found with id '" + formDefinitionId + "'", FormDefinition.class);
		}

		return formDefinition;
	}

	public boolean isFormDefinitionSuspended(String formDefinitionId) {
		return findFormDefinitionByIdFromDatabase(formDefinitionId).isSuspended();
	}

	public FormDefinitionEntity findDeployedLatestFormDefinitionByKey(String formDefinitionKey) {
		FormDefinitionEntity formDefinition = getFormDefinitionEntityManager()
				.findLatestFormDefinitionByKey(formDefinitionKey);

		if (formDefinition == null) {
			throw new ActivitiObjectNotFoundException("no formes deployed with key '" + formDefinitionKey + "'",
					FormDefinition.class);
		}
		formDefinition = resolveFormDefinition(formDefinition);
		return formDefinition;
	}

	public FormDefinitionEntity findDeployedLatestFormDefinitionByKeyAndTenantId(String formDefinitionKey,
			String tenantId) {
		FormDefinitionEntity formDefinition = getFormDefinitionEntityManager()
				.findLatestFormDefinitionByKeyAndTenantId(formDefinitionKey, tenantId);
		if (formDefinition == null) {
			throw new ActivitiObjectNotFoundException(
					"no formes deployed with key '" + formDefinitionKey + "' for tenant identifier '" + tenantId + "'",
					FormDefinition.class);
		}
		formDefinition = resolveFormDefinition(formDefinition);
		return formDefinition;
	}

	public FormDefinitionEntity findDeployedFormDefinitionByKeyAndVersion(String formDefinitionKey,
			Integer formDefinitionVersion) {
		FormDefinitionEntity formDefinition = (FormDefinitionEntity) getFormDefinitionEntityManager()
				.findFormDefinitionByKeyAndVersion(formDefinitionKey, formDefinitionVersion);
		if (formDefinition == null) {
			throw new ActivitiObjectNotFoundException("no formes deployed with key = '" + formDefinitionKey
					+ "' and version = '" + formDefinitionVersion + "'", FormDefinition.class);
		}
		formDefinition = resolveFormDefinition(formDefinition);
		return formDefinition;
	}

	public FormDefinitionEntity resolveFormDefinition(FormDefinitionEntity formDefinition) {
		String formDefinitionId = formDefinition.getId();
		String deploymentId = formDefinition.getDeploymentId();
		formDefinition = formDefinitionCache.get(formDefinitionId);
		if (formDefinition == null) {
			DeploymentEntity deployment = Context.getCommandContext().getDeploymentEntityManager()
					.findDeploymentById(deploymentId);
			deployment.setNew(false);
			deploy(deployment, null);
			formDefinition = formDefinitionCache.get(formDefinitionId);

			if (formDefinition == null) {
				throw new ActivitiException("deployment '" + deploymentId + "' didn't put form definition '"
						+ formDefinitionId + "' in the cache");
			}
		}
		return formDefinition;
	}

	public void removeDeployment(String deploymentId) {
		DeploymentEntityManager deploymentEntityManager = Context.getCommandContext().getDeploymentEntityManager();

		DeploymentEntity deployment = deploymentEntityManager.findDeploymentById(deploymentId);
		if (deployment == null)
			throw new ActivitiObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.",
					DeploymentEntity.class);

		// Remove any form definition from the cache
		List<FormDefinition> formDefinitions = new FormDefinitionQueryImpl(Context.getCommandContext())
				.deploymentId(deploymentId).list();
		ActivitiEventDispatcher eventDispatcher = Context.getProcessEngineConfiguration().getEventDispatcher();

		for (FormDefinition formDefinition : formDefinitions) {

			// Since all form definitions are deleted by a single query, we
			// should dispatch the events in this loop
			if (eventDispatcher.isEnabled()) {
				eventDispatcher.dispatchEvent(
						ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_DELETED, formDefinition));
			}
		}

		// Delete data
		deleteDeployment(deploymentId);

		// Since we use a delete by query, delete-events are not automatically
		// dispatched
		if (eventDispatcher.isEnabled()) {
			eventDispatcher.dispatchEvent(
					ActivitiEventBuilder.createEntityEvent(ActivitiEventType.ENTITY_DELETED, deployment));
		}

		for (FormDefinition formDefinition : formDefinitions) {
			formDefinitionCache.remove(formDefinition.getId());
		}
	}

	protected void deleteDeployment(String deploymentId) {

		DbSqlSession dbSqlSession = Context.getCommandContext().getDbSqlSession();
		FormDefinitionEntityManager formDefinitionEntityManager = Context.getCommandContext()
				.getSession(FormDefinitionEntityManager.class);
		ModelEntityManager modelEntityManager = Context.getCommandContext().getModelEntityManager();
		ResourceEntityManager resourceEntityManager = Context.getCommandContext().getResourceEntityManager();

		// Remove the deployment link from any model.
		// The model will still exists, as a model is a source for a deployment
		// model and has a different lifecycle
		List<Model> models = dbSqlSession.createModelQueryImpl().deploymentId(deploymentId).list();

		for (Model model : models) {
			ModelEntity modelEntity = (ModelEntity) model;
			modelEntity.setDeploymentId(null);
			modelEntityManager.updateModel(modelEntity);
		}

		// delete form definitions from db
		formDefinitionEntityManager.deleteFormDefinitionsByDeploymentId(deploymentId);

		resourceEntityManager.deleteResourcesByDeploymentId(deploymentId);

		dbSqlSession.delete("deleteDeployment", deploymentId);
	}

	public DeploymentCache<FormDefinitionEntity> getFormDefinitionCache() {
		return formDefinitionCache;
	}

	public void setFormDefinitionCache(DeploymentCache<FormDefinitionEntity> formDefinitionCache) {
		this.formDefinitionCache = formDefinitionCache;
	}

	public List<Deployer> getFormDeployers() {
		return formDeployers;
	}

	public void setFormDeployers(List<Deployer> formDeployers) {
		this.formDeployers = formDeployers;
	}
}
