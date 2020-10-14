/**
 * 
 */
package org.activiti.engine.extend.impl;

import java.util.List;
import java.util.Set;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.form.FormDefinitionQuery;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntityManager;
import org.activiti.engine.impl.AbstractQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.SuspensionState;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.impl.FormDefinitionQueryImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日下午3:25:47
 */
public class FormDefinitionQueryImpl extends AbstractQuery<FormDefinitionQuery, FormDefinition>
		implements FormDefinitionQuery {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected Set<String> ids;
	protected String category;
	protected String categoryLike;
	protected String categoryNotEquals;
	protected String name;
	protected String nameLike;
	protected String deploymentId;
	protected Set<String> deploymentIds;
	protected String key;
	protected String keyLike;
	protected String resourceName;
	protected String resourceNameLike;
	protected Integer version;
	protected Integer versionGt;
	protected Integer versionGte;
	protected Integer versionLt;
	protected Integer versionLte;
	protected boolean latest = false;
	protected SuspensionState suspensionState;
	protected String authorizationUserId;
	protected String formDefId;
	protected String tenantId;
	protected String tenantIdLike;
	protected boolean withoutTenantId;

	public FormDefinitionQueryImpl() {
	  }

	public FormDefinitionQueryImpl(CommandContext commandContext) {
	    super(commandContext);
	  }

	public FormDefinitionQueryImpl(CommandExecutor commandExecutor) {
	    super(commandExecutor);
	  }

	public FormDefinitionQueryImpl formDefinitionId(String formDefinitionId) {
		this.id = formDefinitionId;
		return this;
	}

	@Override
	public FormDefinitionQuery formDefinitionIds(Set<String> formDefinitionIds) {
		if (formDefinitionIds == null) {
			throw new ActivitiIllegalArgumentException("formDefinitionIds is null");
		}
		if (formDefinitionIds.isEmpty()) {
			throw new ActivitiIllegalArgumentException("formDefinitionIds is empty");
		}
		this.ids = formDefinitionIds;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionCategory(String category) {
		if (category == null) {
			throw new ActivitiIllegalArgumentException("category is null");
		}
		this.category = category;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionCategoryLike(String categoryLike) {
		if (categoryLike == null) {
			throw new ActivitiIllegalArgumentException("categoryLike is null");
		}
		this.categoryLike = categoryLike;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionCategoryNotEquals(String categoryNotEquals) {
		if (categoryNotEquals == null) {
			throw new ActivitiIllegalArgumentException("categoryNotEquals is null");
		}
		this.categoryNotEquals = categoryNotEquals;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionName(String name) {
		if (name == null) {
			throw new ActivitiIllegalArgumentException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionNameLike(String nameLike) {
		if (nameLike == null) {
			throw new ActivitiIllegalArgumentException("nameLike is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FormDefinitionQueryImpl deploymentId(String deploymentId) {
		if (deploymentId == null) {
			throw new ActivitiIllegalArgumentException("id is null");
		}
		this.deploymentId = deploymentId;
		return this;
	}

	public FormDefinitionQueryImpl deploymentIds(Set<String> deploymentIds) {
		if (deploymentIds == null) {
			throw new ActivitiIllegalArgumentException("deploymentIds is null");
		}
		if (deploymentIds.isEmpty()) {
			throw new ActivitiIllegalArgumentException("deploymentIds is empty");
		}
		this.deploymentIds = deploymentIds;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionKey(String key) {
		if (key == null) {
			throw new ActivitiIllegalArgumentException("key is null");
		}
		this.key = key;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionKeyLike(String keyLike) {
		if (keyLike == null) {
			throw new ActivitiIllegalArgumentException("keyLike is null");
		}
		this.keyLike = keyLike;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionResourceName(String resourceName) {
		if (resourceName == null) {
			throw new ActivitiIllegalArgumentException("resourceName is null");
		}
		this.resourceName = resourceName;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionResourceNameLike(String resourceNameLike) {
		if (resourceNameLike == null) {
			throw new ActivitiIllegalArgumentException("resourceNameLike is null");
		}
		this.resourceNameLike = resourceNameLike;
		return this;
	}

	public FormDefinitionQueryImpl formDefinitionVersion(Integer version) {
		checkVersion(version);
		this.version = version;
		return this;
	}

	public FormDefinitionQuery formDefinitionVersionGreaterThan(Integer formDefinitionVersion) {
		checkVersion(formDefinitionVersion);
		this.versionGt = formDefinitionVersion;
		return this;
	}

	public FormDefinitionQuery formDefinitionVersionGreaterThanOrEquals(Integer formDefinitionVersion) {
		checkVersion(formDefinitionVersion);
		this.versionGte = formDefinitionVersion;
		return this;
	}

	public FormDefinitionQuery formDefinitionVersionLowerThan(Integer formDefinitionVersion) {
		checkVersion(formDefinitionVersion);
		this.versionLt = formDefinitionVersion;
		return this;
	}

	public FormDefinitionQuery formDefinitionVersionLowerThanOrEquals(Integer formDefinitionVersion) {
		checkVersion(formDefinitionVersion);
		this.versionLte = formDefinitionVersion;
		return this;
	}

	protected void checkVersion(Integer version) {
		if (version == null) {
			throw new ActivitiIllegalArgumentException("version is null");
		} else if (version <= 0) {
			throw new ActivitiIllegalArgumentException("version must be positive");
		}
	}

	public FormDefinitionQueryImpl latestVersion() {
		this.latest = true;
		return this;
	}

	public FormDefinitionQuery active() {
		this.suspensionState = SuspensionState.ACTIVE;
		return this;
	}

	public FormDefinitionQuery suspended() {
		this.suspensionState = SuspensionState.SUSPENDED;
		return this;
	}

	public FormDefinitionQuery formDefinitionTenantId(String tenantId) {
		if (tenantId == null) {
			throw new ActivitiIllegalArgumentException("formDefinition tenantId is null");
		}
		this.tenantId = tenantId;
		return this;
	}

	public FormDefinitionQuery formDefinitionTenantIdLike(String tenantIdLike) {
		if (tenantIdLike == null) {
			throw new ActivitiIllegalArgumentException("form definition tenantId is null");
		}
		this.tenantIdLike = tenantIdLike;
		return this;
	}

	public FormDefinitionQuery formDefinitionWithoutTenantId() {
		this.withoutTenantId = true;
		return this;
	}


	// sorting ////////////////////////////////////////////

	public FormDefinitionQuery orderByDeploymentId() {
		return orderBy(FormDefinitionQueryProperty.DEPLOYMENT_ID);
	}

	public FormDefinitionQuery orderByFormDefinitionKey() {
		return orderBy(FormDefinitionQueryProperty.FORM_DEFINITION_KEY);
	}

	public FormDefinitionQuery orderByFormDefinitionCategory() {
		return orderBy(FormDefinitionQueryProperty.FORM_DEFINITION_CATEGORY);
	}

	public FormDefinitionQuery orderByFormDefinitionId() {
		return orderBy(FormDefinitionQueryProperty.FORM_DEFINITION_ID);
	}

	public FormDefinitionQuery orderByFormDefinitionVersion() {
		return orderBy(FormDefinitionQueryProperty.FORM_DEFINITION_VERSION);
	}

	public FormDefinitionQuery orderByFormDefinitionName() {
		return orderBy(FormDefinitionQueryProperty.FORM_DEFINITION_NAME);
	}

	public FormDefinitionQuery orderByTenantId() {
		return orderBy(FormDefinitionQueryProperty.FORM_DEFINITION_TENANT_ID);
	}

	// results ////////////////////////////////////////////

	public long executeCount(CommandContext commandContext) {
		checkQueryOk();
		return getFormDefinitionEntityManager().findFormDefinitionCountByQueryCriteria(this);
	}

	public List<FormDefinition> executeList(CommandContext commandContext, Page page) {
		checkQueryOk();
		return getFormDefinitionEntityManager().findFormDefinitionsByQueryCriteria(this, page);
	}

	protected FormDefinitionEntityManager getFormDefinitionEntityManager(){
		return Context.getCommandContext().getSession(FormDefinitionEntityManager.class);
	}
	
	public void checkQueryOk() {
		super.checkQueryOk();
	}

	// getters ////////////////////////////////////////////

	public String getDeploymentId() {
		return deploymentId;
	}

	public Set<String> getDeploymentIds() {
		return deploymentIds;
	}

	public String getId() {
		return id;
	}

	public Set<String> getIds() {
		return ids;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public String getKey() {
		return key;
	}

	public String getKeyLike() {
		return keyLike;
	}

	public Integer getVersion() {
		return version;
	}

	public Integer getVersionGt() {
		return versionGt;
	}

	public Integer getVersionGte() {
		return versionGte;
	}

	public Integer getVersionLt() {
		return versionLt;
	}

	public Integer getVersionLte() {
		return versionLte;
	}

	public boolean isLatest() {
		return latest;
	}

	public String getCategory() {
		return category;
	}

	public String getCategoryLike() {
		return categoryLike;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getResourceNameLike() {
		return resourceNameLike;
	}

	public SuspensionState getSuspensionState() {
		return suspensionState;
	}

	public void setSuspensionState(SuspensionState suspensionState) {
		this.suspensionState = suspensionState;
	}

	public String getCategoryNotEquals() {
		return categoryNotEquals;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getTenantIdLike() {
		return tenantIdLike;
	}

	public boolean isWithoutTenantId() {
		return withoutTenantId;
	}

	public FormDefinitionQueryImpl startableByUser(String userId) {
		if (userId == null) {
			throw new ActivitiIllegalArgumentException("userId is null");
		}
		this.authorizationUserId = userId;
		return this;
	}

}
