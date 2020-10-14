/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.impl.FormDefinitionQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.AbstractManager;


/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.persistence.entity.FormDefinitionEntityManager.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日下午3:14:46
 */
public class FormDefinitionEntityManager extends AbstractManager {
	
	public FormDefinitionEntity findLatestFormDefinitionByKey(String formDefinitionKey) {
		return (FormDefinitionEntity) getDbSqlSession().selectOne("selectLatestFormDefinitionByKey",
				formDefinitionKey);
	}
	
	public FormDefinitionEntity findLatestFormDefinitionByKeyAndTenantId(String formDefinitionKey,
			String tenantId) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("formDefinitionKey", formDefinitionKey);
		params.put("tenantId", tenantId);
		return (FormDefinitionEntity) getDbSqlSession().selectOne("selectLatestFormDefinitionByKeyAndTenantId",
				params);
	}

	public void deleteFormDefinitionsByDeploymentId(String deploymentId) {
		getDbSqlSession().delete("deleteFormDefinitionsByDeploymentId", deploymentId);
	}

	public FormDefinitionEntity findFormDefinitionById(String formDefinitionId) {
		return (FormDefinitionEntity) getDbSqlSession().selectOne("selectFormDefinitionById",
				formDefinitionId);
	}

	@SuppressWarnings("unchecked")
	public List<FormDefinition> findFormDefinitionsByQueryCriteria(
			FormDefinitionQueryImpl formDefinitionQuery, Page page) {
		return getDbSqlSession().selectList("selectFormDefinitionsByQueryCriteria", formDefinitionQuery, page);
	}
	
	public long findFormDefinitionCountByQueryCriteria(FormDefinitionQueryImpl formDefinitionQuery) {
		return (Long) getDbSqlSession().selectOne("selectFormDefinitionCountByQueryCriteria",
				formDefinitionQuery);
	}

	public FormDefinitionEntity findFormDefinitionByDeploymentAndKey(String deploymentId,
			String formDefinitionKey) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("deploymentId", deploymentId);
		parameters.put("formDefinitionKey", formDefinitionKey);
		return (FormDefinitionEntity) getDbSqlSession().selectOne("selectFormDefinitionByDeploymentAndKey",
				parameters);
	}

	public FormDefinitionEntity findFormDefinitionByDeploymentAndKeyAndTenantId(String deploymentId,
			String formDefinitionKey, String tenantId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("deploymentId", deploymentId);
		parameters.put("formDefinitionKey", formDefinitionKey);
		parameters.put("tenantId", tenantId);
		return (FormDefinitionEntity) getDbSqlSession()
				.selectOne("selectFormDefinitionByDeploymentAndKeyAndTenantId", parameters);
	}

	public FormDefinition findFormDefinitionByKeyAndVersion(String formDefinitionKey,
			Integer formDefinitionVersion) {
		FormDefinitionQueryImpl FormDefinitionQuery = new FormDefinitionQueryImpl()
				.formDefinitionKey(formDefinitionKey).formDefinitionVersion(formDefinitionVersion);
		List<FormDefinition> results = findFormDefinitionsByQueryCriteria(FormDefinitionQuery, null);
		if (results.size() == 1) {
			return results.get(0);
		} else if (results.size() > 1) {
			throw new ActivitiException("There are " + results.size() + " process definitions with key = '"
					+ formDefinitionKey + "' and version = '" + formDefinitionVersion + "'.");
		}
		return null;
	}

	public List<FormDefinition> findFormDefinitionsStartableByUser(String user) {
		return new FormDefinitionQueryImpl().startableByUser(user).list();
	}

	@SuppressWarnings("unchecked")
	public List<FormDefinition> findFormDefinitionsByNativeQuery(Map<String, Object> parameterMap,
			int firstResult, int maxResults) {
		return getDbSqlSession().selectListWithRawParameter("selectFormDefinitionByNativeQuery", parameterMap,
				firstResult, maxResults);
	}

	public long findFormDefinitionCountByNativeQuery(Map<String, Object> parameterMap) {
		return (Long) getDbSqlSession().selectOne("selectFormDefinitionCountByNativeQuery", parameterMap);
	}

	public void updateFormDefinitionTenantIdForDeployment(String deploymentId, String newTenantId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("deploymentId", deploymentId);
		params.put("tenantId", newTenantId);
		getDbSqlSession().update("updateFormDefinitionTenantIdForDeploymentId", params);
	}
}
