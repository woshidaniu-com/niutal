/**
 * 
 */
package com.woshidaniu.component.bpm.management.process.definition.dao.entities;

import org.activiti.engine.impl.persistence.entity.GroupEntity;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.search.core.SearchModel;

/**
 * @author xiaokang
 * @desc 扩展了activiti用户组实体对象
 */
public class AssignmentGroupEntity extends GroupEntity {

	private static final long serialVersionUID = 1592223211206558676L;

	protected SearchModel searchModel;

	protected String assignmentId;
	protected String procDefId;
	protected String taskDefId;
	
	public QueryModel queryModel = new QueryModel();
	
	/**
	 * 是否启用分页的标记
	 */
	protected boolean pageable = true;
	
	public boolean isPageable() {
		return pageable;
	}
	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}
	public int getTotalresult() {
		return totalresult;
	}
	public void setTotalresult(int totalresult) {
		this.totalresult = totalresult;
	}

	protected int totalresult;
	
	public SearchModel getSearchModel() {
		return this.searchModel;
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

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	
}
