/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.engine.extend.biz.SimulationActivity;
import org.activiti.engine.impl.db.BulkDeleteable;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.pvm.PvmActivity;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.persistence.entity.SimulationActivityEntity.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月11日上午10:14:49
 */
public class SimulationActivityEntity implements SimulationActivity, BulkDeleteable, PersistentObject {

	private static final long serialVersionUID = 1L;
	protected String id;
	protected String processDefinitionId;
	protected String businessKey;
	protected String activityId;
	protected String activityName;
	protected String activityType;
	protected Integer order;

	public static SimulationActivityEntity create(String processDefinitionId, String businessKey, PvmActivity activity, int order){
		SimulationActivityEntity simulationActivityEntity = new SimulationActivityEntity();
		simulationActivityEntity.setActivity(activity);
		simulationActivityEntity.setBusinessKey(businessKey);
		simulationActivityEntity.setProcessDefinitionId(processDefinitionId);
		simulationActivityEntity.setOrder(order);
		return simulationActivityEntity;
	}
	
	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("processDefinitionId", this.processDefinitionId);
		persistentState.put("businessKey", this.businessKey);
		persistentState.put("activityId", this.activityId);
		persistentState.put("activityName", this.activityName);
		persistentState.put("activityType", this.activityType);
		persistentState.put("order", this.order);
		return persistentState;
	}

	public void setActivity(PvmActivity activity) {
		if (activity != null) {
			String activityId = (String) activity.getId();
			String activityName = (String) activity.getProperty(BpmnXMLConstants.ATTRIBUTE_NAME);
			String activityType = (String) activity.getProperty(BpmnXMLConstants.ATTRIBUTE_TYPE);

			if (this.activityId == null && activityId != null) {
				this.activityId = activityId;
			}

			if (this.activityName == null && activityName != null) {
				this.activityName = activityName;
			}

			if (this.activityType == null && activityType != null) {
				this.activityType = activityType;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.SimulationActivity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.extend.biz.SimulationActivity#getProcessDefinitionId(
	 * )
	 */
	@Override
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.SimulationActivity#getBusinessKey()
	 */
	@Override
	public String getBusinessKey() {
		return businessKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.SimulationActivity#getActivityId()
	 */
	@Override
	public String getActivityId() {
		return activityId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.SimulationActivity#getActivityName()
	 */
	@Override
	public String getActivityName() {
		return activityName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.SimulationActivity#getActivityType()
	 */
	@Override
	public String getActivityType() {
		return activityType;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Integer getOrder() {
		return this.order;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
