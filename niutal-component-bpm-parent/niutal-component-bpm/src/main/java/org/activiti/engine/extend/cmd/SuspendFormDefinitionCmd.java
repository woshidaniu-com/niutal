/**
 * 
 */
package org.activiti.engine.extend.cmd;

import org.activiti.engine.impl.persistence.entity.SuspensionState;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.SuspendFormDefinitionCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月6日上午10:40:39
 */
public class SuspendFormDefinitionCmd extends AbstractSetFormDefinitionStateCmd {

	public SuspendFormDefinitionCmd(String formDefinitionId, String formDefinitionKey, String tenantId) {
		super(formDefinitionId, formDefinitionKey, tenantId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.cmd.AbstractSetFormDefinitionStateCmd#
	 * getFormDefinitionSuspensionState()
	 */
	@Override
	protected SuspensionState getFormDefinitionSuspensionState() {
		return SuspensionState.SUSPENDED;
	}

}
