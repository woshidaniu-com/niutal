package com.woshidaniu.component.bpm.management.process.definition.dao.entities;

public class AssignmentClazzEntity {

	
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
}
