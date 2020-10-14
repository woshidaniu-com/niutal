package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.impl.db.HasRevision;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.persistence.entity.SuspensionState;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:org.activiti.engine.extend.persistence.entity.FormDefinitionEntity.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日下午4:39:53
 */
public class FormDefinitionEntity implements FormDefinition, PersistentObject, HasRevision {

	protected String id;
	protected String name;
	protected String key;
	protected String description;
	protected int revision = 1;
	protected int version;
	protected String category;
	protected String deploymentId;
	protected String tenantId = ProcessEngineConfiguration.NO_TENANT_ID;
	protected int suspensionState = SuspensionState.ACTIVE.getStateCode();

	public static FormDefinitionEntity createInstance(){
		FormDefinitionEntity entity = new FormDefinitionEntity();
		
		return entity;
	}
	
	@Override
	public void setRevision(int revision) {
		this.revision = revision;
	}

	@Override
	public int getRevision() {
		return revision;
	}

	@Override
	public int getRevisionNext() {
		return revision + 1;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("suspensionState", this.suspensionState);
		persistentState.put("category", this.category);
		return persistentState;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public String getDeploymentId() {
		return deploymentId;
	}

	@Override
	public boolean isSuspended() {
		return suspensionState == SuspensionState.SUSPENDED.getStateCode();
	}

	@Override
	public String getTenantId() {
		return tenantId;
	}

	public int getSuspensionState() {
		return suspensionState;
	}

	public void setSuspensionState(int suspensionState) {
		this.suspensionState = suspensionState;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
