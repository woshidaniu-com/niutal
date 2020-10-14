/**
 * 
 */
package com.woshidaniu.component.bpm.management.biz.dao.entities;

import java.util.List;

import org.activiti.engine.extend.persistence.entity.BizEntity;
import org.activiti.engine.extend.persistence.entity.BizFieldEntity;

import com.woshidaniu.common.query.QueryModel;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：ProcessBizModel
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.biz.controller.dao.entities.ProcessBizModel.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月10日上午11:24:06
 */
public class ProcessBizModel extends BizEntity {
	private static final long serialVersionUID = 1L;

	protected List<BizFieldEntity> processBizFieldList;
	
	public List<BizFieldEntity> getProcessBizFieldList() {
		return processBizFieldList;
	}

	public void setProcessBizFieldList(List<BizFieldEntity> processBizFieldList) {
		this.processBizFieldList = processBizFieldList;
	}

	// **************************************************//
	public QueryModel queryModel = new QueryModel();

	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}
	// **************************************************//
	
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
