/**
 * 
 */
package com.woshidaniu.component.bpm.management.process.definition.dao.entities;

import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.search.core.SearchModel;

/**
 * @author xiaokang
 * @desc 扩展了activiti用户信息实体对象
 *
 */
public class AssignmentUserEntity extends UserEntity {

	private static final long serialVersionUID = -8976618562377450838L;

	protected String assignmentId;
	protected String procDefId;
	protected String taskDefId;
	protected String groupId;
	protected SearchModel searchModel;

	/**
	 * 是否启用分页的标记
	 */
	protected boolean pageable = true;
	
	protected int totalresult;
	
	public int getTotalresult() {
		return totalresult;
	}

	public void setTotalresult(int totalresult) {
		this.totalresult = totalresult;
	}

	public QueryModel queryModel = new QueryModel();
	
	public SearchModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getTaskDefId() {
		return taskDefId;
	}

	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
