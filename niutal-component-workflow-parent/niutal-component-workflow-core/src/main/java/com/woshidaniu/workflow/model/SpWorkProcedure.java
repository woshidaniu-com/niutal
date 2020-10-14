package com.woshidaniu.workflow.model;

import java.io.Serializable;
import java.util.List;

/**
 * 审核工作流程对象
 * 
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpWorkProcedure extends SpProcedure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* @property:ID */
	private String id;
	/* @property:工作ID */
	private String wid;
	/* @property:业务编码 */
	private String bcode;

	/* @property:工作审核连线对象集合 */
	private List<SpWorkLine> spWorkLineList;
	/* @property:工作审核节点对象集合 */
	private List<SpWorkNode> spWorkNodeList;
	/* @property:工作审核日志对象集合 */
	private List<SpAuditingLog> spAuditingLogList;

	/* Default constructor - creates a new instance with no values set. */
	public SpWorkProcedure() {
	}

	/**
	 * @return id : return the property id.
	 */

	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            : set the property id.
	 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return wId : return the property wId.
	 */

	public String getWid() {
		return wid;
	}

	/**
	 * @param wId
	 *            : set the property wId.
	 */

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	 * @return bCode : return the property bCode.
	 */

	public String getBcode() {
		return bcode;
	}

	/**
	 * @param bCode
	 *            : set the property bCode.
	 */

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	/**
	 * @return spWorkLineList : return the property spWorkLineList.
	 */

	public List<SpWorkLine> getSpWorkLineList() {
		return spWorkLineList;
	}

	/**
	 * @param spWorkLineList
	 *            : set the property spWorkLineList.
	 */

	public void setSpWorkLineList(List<SpWorkLine> spWorkLineList) {
		this.spWorkLineList = spWorkLineList;
	}

	/**
	 * @return spWorkNodeList : return the property spWorkNodeList.
	 */

	public List<SpWorkNode> getSpWorkNodeList() {
		return spWorkNodeList;
	}

	/**
	 * @param spWorkNodeList
	 *            : set the property spWorkNodeList.
	 */

	public void setSpWorkNodeList(List<SpWorkNode> spWorkNodeList) {
		this.spWorkNodeList = spWorkNodeList;
	}

	/**
	 * @return spAuditingLogList : return the property spAuditingLogList.
	 */

	public List<SpAuditingLog> getSpAuditingLogList() {
		return spAuditingLogList;
	}

	/**
	 * @param spAuditingLogList
	 *            : set the property spAuditingLogList.
	 */

	public void setSpAuditingLogList(List<SpAuditingLog> spAuditingLogList) {
		this.spAuditingLogList = spAuditingLogList;
	}

	/*
	 * 将流程对象填充到工作审核流程对象中
	 * 
	 * @param spProcedure
	 * 
	 * @return
	 */
	public static SpWorkProcedure putProcedureObject(SpProcedure spProcedure) {
		SpWorkProcedure spWorkProcedure = new SpWorkProcedure();
		if (spProcedure != null) {
			spWorkProcedure.setPid(spProcedure.getPid());
			spWorkProcedure.setPname(spProcedure.getPname());
			spWorkProcedure.setPtype(spProcedure.getPtype());
			spWorkProcedure.setPdesc(spProcedure.getPdesc());
			spWorkProcedure.setLink(spProcedure.getLink());
		}
		return spWorkProcedure;
	}
}
