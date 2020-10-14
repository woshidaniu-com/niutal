package org.activiti.engine.extend.cmd.simulation;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.cmd.AbstractCommand;
import org.activiti.engine.extend.persistence.entity.SimulationActivityEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.simulation.DeleteSimulationActivitiesCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月11日上午11:56:22
 */
public class DeleteSimulationActivitiesCmd extends AbstractCommand<Void> {

	protected String businessKey;

	public DeleteSimulationActivitiesCmd(String businessKey) {
		super();
		this.businessKey = businessKey;
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {

		if (businessKey == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		SimulationActivityEntityManager simulationActivityEntityManager = commandContext
				.getSession(SimulationActivityEntityManager.class);

		simulationActivityEntityManager.deleteSimulationActivitiesByBusinessKey(businessKey);

		return null;
	}

}
