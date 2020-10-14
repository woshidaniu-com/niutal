package com.woshidaniu.component.bpm.management.process.definition.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentGroupEntity;
import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentUserEntity;

@Repository("assignmentQueryDao")
public interface IAssignmentQueryDao {

	List<AssignmentUserEntity> getPagedUnassignedUserList(AssignmentUserEntity entity);
	List<AssignmentUserEntity> getPagedAssignedUserList(AssignmentUserEntity entity);
	
	List<AssignmentGroupEntity> getPagedUnassignedGroupList(AssignmentGroupEntity entity);
	List<AssignmentGroupEntity> getPagedAssignedGroupList(AssignmentGroupEntity entity);
	
}
