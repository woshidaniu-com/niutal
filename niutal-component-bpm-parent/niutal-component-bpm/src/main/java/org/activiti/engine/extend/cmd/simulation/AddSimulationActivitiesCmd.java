/**
 * 
 */
package org.activiti.engine.extend.cmd.simulation;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.cmd.AbstractCommand;
import org.activiti.engine.extend.persistence.entity.SimulationActivityEntity;
import org.activiti.engine.extend.persistence.entity.SimulationActivityEntityManager;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.pvm.PvmActivity;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.cmd.simulation.AddSimulationActivitiesCmd.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月11日下午12:00:07
 */
public class AddSimulationActivitiesCmd extends AbstractCommand<Void> {

	protected String processDefinitionId;
	protected String businessKey;
	protected List<PvmActivity> pvmActivities;

	public AddSimulationActivitiesCmd(String processDefinitionId, String businessKey, List<PvmActivity> pvmActivities) {
		super();
		this.processDefinitionId = processDefinitionId;
		this.businessKey = businessKey;
		this.pvmActivities = pvmActivities;
	}

	@Override
	protected Void doExecute(CommandContext commandContext) {
		if (businessKey == null || processDefinitionId == null || pvmActivities == null) {
			throw new ActivitiIllegalArgumentException("BPM_EX_ARGS");
		}

		if (pvmActivities.size() == 0) {
			return null;
		}

		SimulationActivityEntityManager simulationActivityEntityManager = commandContext
				.getSession(SimulationActivityEntityManager.class);

		int order = 0;

		for (PvmActivity pvmActivity : pvmActivities) {
			SimulationActivityEntity entity = SimulationActivityEntity.create(processDefinitionId, businessKey,
					pvmActivity, ++order);
			simulationActivityEntityManager.insert(entity);
		}

		return null;
	}

}
