/**
 * 
 */
package com.woshidaniu.component.bpm.management.process.definition.service.svcinterface;

import java.util.List;

import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentGroupEntity;
import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentUserEntity;

/**
 * @author ZHANGXB
 *
 */
public interface IAssignmentQueryService {

	List<AssignmentUserEntity> getPagedUnassignedUserList(AssignmentUserEntity entity);
	List<AssignmentUserEntity> getPagedAssignedUserList(AssignmentUserEntity entity);
	
	List<AssignmentGroupEntity> getPagedUnassignedGroupList(AssignmentGroupEntity entity);
	List<AssignmentGroupEntity> getPagedAssignedGroupList(AssignmentGroupEntity entity);
}
