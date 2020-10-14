package org.activiti.engine.extend.impl.repository;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.form.FormDeploymentBuilder;
import org.activiti.engine.extend.service.impl.ExtendFormServiceImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.repository.Deployment;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.impl.repository.DefaultFormDeploymentBuilderImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日上午8:59:37
 */
public class DefaultFormDeploymentBuilderImpl implements FormDeploymentBuilder, Serializable {

	private static final long serialVersionUID = 1L;
	protected static final String DEFAULT_ENCODING = "UTF-8";

	protected transient ExtendFormServiceImpl extendFormServiceImpl;
	protected DeploymentEntity deployment = new DeploymentEntity();

	public DefaultFormDeploymentBuilderImpl(ExtendFormServiceImpl extendFormService) {
		this.extendFormServiceImpl = extendFormService;
	}

	@Override
	public FormDeploymentBuilder addBytes(String resourceName, byte[] bytes) {
		if (bytes == null) {
			throw new ActivitiIllegalArgumentException("bytes is null");
		}
		ResourceEntity resource = new ResourceEntity();
		resource.setName(resourceName);
		resource.setBytes(bytes);
		deployment.addResource(resource);
		return this;
	}

	@Override
	public FormDeploymentBuilder addString(String resourceName, String text) {
		if (text == null) {
			throw new ActivitiIllegalArgumentException("text is null");
		}
		try {
			return addBytes(resourceName, text.getBytes(DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new ActivitiException("Unable to get form bytes.", e);
		}
	}

	@Override
	public FormDeploymentBuilder name(String name) {
		deployment.setName(name);
		return this;
	}

	@Override
	public FormDeploymentBuilder category(String category) {
		deployment.setCategory(category);
		return this;
	}

	@Override
	public FormDeploymentBuilder tenantId(String tenantId) {
		deployment.setTenantId(tenantId);
		return this;
	}

	@Override
	public Deployment deploy() {
		return extendFormServiceImpl.deploy(this);
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	public DeploymentEntity getDeployment() {
		return deployment;
	}

}
