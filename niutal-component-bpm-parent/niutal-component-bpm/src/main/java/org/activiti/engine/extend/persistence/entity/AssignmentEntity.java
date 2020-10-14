package org.activiti.engine.extend.persistence.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.cmd.ResovleTaskAssignmentUserCmd;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.BulkDeleteable;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.task.IdentityLinkType;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * class：org.activiti.engine.extend.persistence.entity.AssignmentEntity.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class AssignmentEntity implements Serializable, Assignment, BulkDeleteable ,PersistentObject {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected String userId;
	protected String groupId;
	protected String clazzId;
	//加入优先级
	protected int priority;
	protected String type;
	protected String taskDefinitionId;
	protected String processDefinitionId;

	protected User userEntity;
	protected Group groupEntity;
	
	protected TaskDefinition taskDefinition;
	protected ProcessDefinitionEntity processDefinition;

	public static AssignmentEntity create(String processDefintionId, String taskDefinitionId){
		AssignmentEntity entity = new AssignmentEntity();
		entity.setType(IdentityLinkType.CANDIDATE);
		entity.setProcessDefinitionId(processDefintionId);
		entity.setTaskDefinitionId(taskDefinitionId);
		return entity;
	}
	
	public static AssignmentEntity create(String assignmentId){
		AssignmentEntity entity = new AssignmentEntity();
		entity.setId(assignmentId);
		return entity;
	}
	
	@Override
	public User getUserEntity(){
		if(userEntity == null && userId != null){
			userEntity = Context.getCommandContext().getUserIdentityManager().findUserById(userId);
		}
		return userEntity;
	}
	
	@Override
	public Group getGroupEntity(){
		if(groupEntity == null && groupId != null){
			GroupQueryImpl groupQueryImpl = new GroupQueryImpl();
			groupQueryImpl.groupId(groupId);
			Page page = new Page(0,1);
			List<Group> findGroupByQueryCriteria = groupQueryImpl.executeList(Context.getCommandContext(), page);
			if(findGroupByQueryCriteria != null && findGroupByQueryCriteria.size() > 0){
				groupEntity = findGroupByQueryCriteria.get(0);
			}
		}
		return groupEntity;
	}
	
	public ProcessDefinitionEntity getProcessDefinition() {
		if ((processDefinition == null) && (processDefinitionId != null)) {
			this.processDefinition = Context.getCommandContext().getProcessDefinitionEntityManager()
					.findProcessDefinitionById(processDefinitionId);
		}
		return processDefinition;
	}

	public TaskDefinition getTaskDefinition(){
		if((taskDefinition == null) && (taskDefinitionId != null)){
			getProcessDefinition();
			if(processDefinition != null ){
				Map<String, TaskDefinition> taskDefinitions = processDefinition.getTaskDefinitions();
				this.taskDefinition = taskDefinitions.get(taskDefinitionId);
			}
		}
		return taskDefinition;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("type", this.type);
		persistentState.put("taskDefinitionId", this.taskDefinitionId);
		persistentState.put("processDefinitionId", this.processDefinitionId);
		persistentState.put("priority", this.priority);
		if (this.userId != null) {
			persistentState.put("userId", this.userId);
		}
		if (this.groupId != null) {
			persistentState.put("groupId", this.groupId);
		}
		if(this.clazzId != null){
			persistentState.put("clazzId", this.clazzId);
		}
		return persistentState;
	}

	@Override
	public String getGroupId() {
		return this.groupId;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String getTaskDefinitionId() {
		return this.taskDefinitionId;
	}

	@Override
	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTaskDefinitionId(String taskDefinitionId) {
		this.taskDefinitionId = taskDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public String getClazzId() {
		return this.clazzId;
	}

	@Override
	public void setClazzId(String clazzId) {	
		this.clazzId = clazzId;
	}

	@Override
	public List<User> resolveUser() {
		return new ResovleTaskAssignmentUserCmd(this).execute(Context.getCommandContext());
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

}
