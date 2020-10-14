/**
 * 
 */
package org.activiti.engine.extend.form;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.form.FormDefinition.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月1日下午2:53:20
 */
public interface FormDefinition {
	
	/** unique identifier */
	String getId();

	/**
	 * category name which is derived from the targetNamespace attribute in the
	 * definitions element
	 */
	String getCategory();

	/** label used for display purposes */
	String getName();

	/** unique name for all versions this process definitions */
	String getKey();

	/** description of this process **/
	String getDescription();

	/** version of this process definition */
	int getVersion();

	/** The deployment in which this process definition is contained. */
	String getDeploymentId();

	/** Returns true if the process definition is in suspended state. */
	boolean isSuspended();

	/** The tenant identifier of this process definition */
	String getTenantId();

}
