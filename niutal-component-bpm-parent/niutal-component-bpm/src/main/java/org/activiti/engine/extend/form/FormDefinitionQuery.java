/**
 * 
 */
package org.activiti.engine.extend.form;

import java.util.Set;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.query.Query;
import org.activiti.engine.repository.DeploymentBuilder;


/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:org.activiti.engine.extend.form.FormDefinitionQuery.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日下午3:21:01
 */
public interface FormDefinitionQuery extends Query<FormDefinitionQuery, FormDefinition>{


	  
	  /** Only select form definiton with the given id.  */
	  FormDefinitionQuery formDefinitionId(String formDefinitionId);
	  
	  /** Only select form definitions with the given ids. */
	  FormDefinitionQuery formDefinitionIds(Set<String> formDefinitionIds);
	  
	  /** Only select form definitions with the given category. */
	  FormDefinitionQuery formDefinitionCategory(String formDefinitionCategory);
	  
	  /**
	   * Only select form definitions where the category matches the given parameter.
	   * The syntax that should be used is the same as in SQL, eg. %activiti%
	   */
	  FormDefinitionQuery formDefinitionCategoryLike(String formDefinitionCategoryLike);

	  /** Only select deployments that have a different category then the given one. 
	   * @see DeploymentBuilder#category(String) */
	  FormDefinitionQuery formDefinitionCategoryNotEquals(String categoryNotEquals);

	  /** Only select form definitions with the given name. */
	  FormDefinitionQuery formDefinitionName(String formDefinitionName);
	  
	  /**
	   * Only select form definitions where the name matches the given parameter.
	   * The syntax that should be used is the same as in SQL, eg. %activiti%
	   */
	  FormDefinitionQuery formDefinitionNameLike(String formDefinitionNameLike);

	  /**
	   * Only select form definitions that are deployed in a deployment with the
	   * given deployment id
	   */
	  FormDefinitionQuery deploymentId(String deploymentId);
	  
	  /** Select form definitions that are deployed in deployments with the given set of ids */
	  FormDefinitionQuery deploymentIds(Set<String> deploymentIds);

	  /**
	   * Only select form definition with the given key.
	   */
	  FormDefinitionQuery formDefinitionKey(String formDefinitionKey);

	  /**
	   * Only select form definitions where the key matches the given parameter.
	   * The syntax that should be used is the same as in SQL, eg. %activiti%
	   */
	  FormDefinitionQuery formDefinitionKeyLike(String formDefinitionKeyLike);
	  
	  /**
	   * Only select form definition with a certain version.
	   * Particulary useful when used in combination with {@link #formDefinitionKey(String)}
	   */
	  FormDefinitionQuery formDefinitionVersion(Integer formDefinitionVersion);
	  
	  /**
	   * Only select form definitions which version are greater than a certain version.
	   */
	  FormDefinitionQuery formDefinitionVersionGreaterThan(Integer formDefinitionVersion);
	  
	  /**
	   * Only select form definitions which version are greater than or equals a certain version.
	   */
	  FormDefinitionQuery formDefinitionVersionGreaterThanOrEquals(Integer formDefinitionVersion);
	  
	  /**
	   * Only select form definitions which version are lower than a certain version.
	   */
	  FormDefinitionQuery formDefinitionVersionLowerThan(Integer formDefinitionVersion);
	  
	  /**
	   * Only select form definitions which version are lower than or equals a certain version.
	   */
	  FormDefinitionQuery formDefinitionVersionLowerThanOrEquals(Integer formDefinitionVersion);
	  
	  /**
	   * Only select the form definitions which are the latest deployed
	   * (ie. which have the highest version number for the given key).
	   * 
	   * Can also be used without any other criteria (ie. query.latest().list()), which
	   * will then give all the latest versions of all the deployed form definitions.
	   * 
	   * @throws ActivitiIllegalArgumentException if used in combination with  {@link #groupId(string)}, {@link #formDefinitionVersion(int)}
	   *                           or {@link #deploymentId(String)}
	   */
	  FormDefinitionQuery latestVersion();
	  
	  /**
	   * Only selects form definitions which are suspended
	   */
	  FormDefinitionQuery suspended();
	  
	  /**
	   * Only selects form definitions which are active
	   */
	  FormDefinitionQuery active();
	  
		/**
		 * Only select form definitions that have the given tenant id.
		 */
	  FormDefinitionQuery formDefinitionTenantId(String tenantId);

		/**
		 * Only select form definitions with a tenant id like the given one.
		 */
	  FormDefinitionQuery formDefinitionTenantIdLike(String tenantIdLike);
		
		/**
		 * Only select form definitions that do not have a tenant id.
		 */
	  FormDefinitionQuery formDefinitionWithoutTenantId();
	  
	  
	  /** Order by the category of the form definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */
	  FormDefinitionQuery orderByFormDefinitionCategory();
	  
	  /** Order by form definition key (needs to be followed by {@link #asc()} or {@link #desc()}). */
	  FormDefinitionQuery orderByFormDefinitionKey();

	  /** Order by the id of the form definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */
	  FormDefinitionQuery orderByFormDefinitionId();
	  
	  /** Order by the version of the form definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */
	  FormDefinitionQuery orderByFormDefinitionVersion();
	  
	  /** Order by the name of the form definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */
	  FormDefinitionQuery orderByFormDefinitionName();
	  
	  /** Order by deployment id (needs to be followed by {@link #asc()} or {@link #desc()}). */
	  FormDefinitionQuery orderByDeploymentId();
	  
		/**
		 * Order by tenant id (needs to be followed by {@link #asc()} or
		 * {@link #desc()}).
		 */
	  FormDefinitionQuery orderByTenantId();
	  

	
}
