/**
 * 
 */
package org.activiti.engine.extend.service;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.form.FormDefinitionQuery;
import org.activiti.engine.extend.form.FormDeploymentBuilder;
import org.activiti.engine.extend.form.NativeFormDefinitionQuery;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：表单服务
 * <p>
 * 
 * @className:org.activiti.engine.extend.service.ExtendFormService.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月27日上午10:43:30
 */
public interface ExtendFormService {

	/** Starts creating a new deployment */
	FormDeploymentBuilder createFormDeployment();

	/**
	 * Deletes the given deployment.
	 * 
	 * @param deploymentId
	 *            id of the deployment, cannot be null.
	 * @throwns RuntimeException if there are still runtime or history Form
	 *          instances or jobs.
	 */
	void deleteDeployment(String deploymentId);

	/**
	 * get Form Definition by id
	 */
	FormDefinition getFormDefinition(String formDefinitionId);
	
	/** Query form definitions. */
	FormDefinitionQuery createFormDefinitionQuery();

	/**
	 * Returns a new {@link org.activiti.engine.query.NativeQuery} for form
	 * definitions.
	 */
	NativeFormDefinitionQuery createNativeFormDefinitionQuery();

	/**
	 * Suspends the Form definition with the given id.
	 * 
	 * If a Form definition is in state suspended, it will not be possible to
	 * start new Form instances based on the Form definition.
	 * 
	 * <strong>Note: all the Form instances of the Form definition will still be
	 * active (ie. not suspended)!</strong>
	 * 
	 * @throws ActivitiObjectNotFoundException
	 *             if no such formDefinition can be found
	 * @throws ActivitiException
	 *             if the Form definition is already in state suspended.
	 */
	void suspendFormDefinitionById(String formDefinitionId);

	/**
	 * Suspends the <strong>all<strong> Form definitions with the given key
	 * 
	 * If a Form definition is in state suspended, it will not be possible to
	 * start new Form instances based on the Form definition.
	 * 
	 * @throws ActivitiObjectNotFoundException
	 *             if no such formDefinition can be found
	 * @throws ActivitiException
	 *             if the Form definition is already in state suspended.
	 */
	void suspendFormDefinitionByKey(String formDefinitionKey);

	/**
	 * Similar to {@link #suspendFormDefinitionByKey(String)}, but only
	 * applicable for the given tenant identifier.
	 */
	void suspendFormDefinitionByKey(String formDefinitionKey, String tenantId);

	/**
	 * Activates the Form definition with the given id.
	 * 
	 * @throws ActivitiObjectNotFoundException
	 *             if no such formDefinition can be found or if the Form
	 *             definition is already in state active.
	 */
	void activateFormDefinitionById(String formDefinitionId);

	/**
	 * Activates the Form definition with the given key (=id in the
	 * 
	 * @throws ActivitiObjectNotFoundException
	 *             if no such formDefinition can be found.
	 * @throws ActivitiException
	 *             if the Form definition is already in state active.
	 */
	void activateFormDefinitionByKey(String formDefinitionKey);

	/**
	 * Similar to {@link #activateFormDefinitionByKey(String)}, but only
	 * applicable for the given tenant identifier.
	 */
	void activateFormDefinitionByKey(String formDefinitionKey, String tenantId);

}
