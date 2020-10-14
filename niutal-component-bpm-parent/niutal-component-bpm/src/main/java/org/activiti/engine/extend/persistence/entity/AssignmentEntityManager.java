package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.impl.persistence.AbstractManager;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * class：org.activiti.engine.extend.persistence.entity.AssignmentEntityManager.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class AssignmentEntityManager extends AbstractManager {
	

	public AssignmentEntityManager() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Assignment> findAssignmentByProcessDefinitionIdAndTaskDefintionId(String processDefinitionId,
			String taskDefinitionId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("taskDefinitionId", taskDefinitionId);
		parameters.put("processDefinitionId", processDefinitionId);
		return (List) getDbSqlSession().getSqlSession()
				.selectList("selectAssignmentByProcessDefinitionIdAndTaskDefintionId", parameters);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Assignment> findAssignmentByProcessDefinitionId(String processDefinitionId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("processDefinitionId", processDefinitionId);
		return (List) getDbSqlSession().getSqlSession()
				.selectList("selectAssignmentByProcessDefinitionId", parameters);
	}

	public void deleteAssignmentByProcessDefinitionId(String processDefinitionId){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("processDefinitionId", processDefinitionId);
		getDbSqlSession().getSqlSession().delete("deleteAssignmentByProcessDefinitionId", parameters);
	}
	
	public void deleteAssignmentByTaskDefinitionId(String taskDefinitionId){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("taskDefinitionId", taskDefinitionId);
		getDbSqlSession().getSqlSession()
				.delete("deleteAssignmentByTaskDefinitionId", parameters);
	}
	
	public void deleteAssignmentByUserId(String processDefinitionId, String taskDefinitionId, String userId){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("processDefinitionId", processDefinitionId);
		parameters.put("taskDefinitionId", taskDefinitionId);
		parameters.put("userId", userId);
		getDbSqlSession().getSqlSession().delete("deleteAssignmentByUserId", parameters);
	}
	public void deleteAssignmentByGroupId(String processDefinitionId, String taskDefinitionId, String groupId){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("processDefinitionId", processDefinitionId);
		parameters.put("taskDefinitionId", taskDefinitionId);
		parameters.put("userId", groupId);
		getDbSqlSession().getSqlSession().delete("deleteAssignmentByGroupId", parameters);
	}
	public void deleteAssignmentByClazzId(String processDefinitionId, String taskDefinitionId, String clazzId){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("processDefinitionId", processDefinitionId);
		parameters.put("taskDefinitionId", taskDefinitionId);
		parameters.put("userId", clazzId);
		getDbSqlSession().getSqlSession().delete("deleteAssignmentByClazzId", parameters);
	}
}
