package com.woshidaniu.component.bpm.management.process.definition.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.component.bpm.management.process.definition.dao.daointerface.IAssignmentQueryDao;
import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentGroupEntity;
import com.woshidaniu.component.bpm.management.process.definition.dao.entities.AssignmentUserEntity;
import com.woshidaniu.component.bpm.management.process.definition.service.svcinterface.IAssignmentQueryService;

@Service("assignmentQueryService")
public class AssignmentQueryServiceImpl implements IAssignmentQueryService{

	@Autowired
	protected IAssignmentQueryDao dao;
	
	@Override
	public List<AssignmentUserEntity> getPagedUnassignedUserList(AssignmentUserEntity entity) {
		return dao.getPagedUnassignedUserList(entity);
	}

	@Override
	public List<AssignmentUserEntity> getPagedAssignedUserList(AssignmentUserEntity entity) {
		return dao.getPagedAssignedUserList(entity);
	}

	@Override
	public List<AssignmentGroupEntity> getPagedUnassignedGroupList(AssignmentGroupEntity entity) {
		return dao.getPagedUnassignedGroupList(entity);
	}

	@Override
	public List<AssignmentGroupEntity> getPagedAssignedGroupList(AssignmentGroupEntity entity) {
		return dao.getPagedAssignedGroupList(entity);
	}

}
