/**
 * 
 */
package org.activiti.engine.extend.form;

import org.activiti.engine.repository.Deployment;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3>说明：表单部署构建器
 * <p>
 * 
 * @className:org.activiti.engine.extend.form.FormDeploymentBuilder.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日上午8:51:28
 */
public interface FormDeploymentBuilder {

	FormDeploymentBuilder addString(String resourceName, String text);

	FormDeploymentBuilder addBytes(String resourceName, byte[] bytes);

	/**
	 * Gives the deployment the given name.
	 */
	FormDeploymentBuilder name(String name);

	/**
	 * Gives the deployment the given category.
	 */
	FormDeploymentBuilder category(String category);

	/**
	 * Gives the deployment the given tenant id.
	 */
	FormDeploymentBuilder tenantId(String tenantId);

	/**
	 * Deploys all provided sources to the Activiti engine.
	 */
	Deployment deploy();
}
