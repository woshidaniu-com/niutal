package com.woshidaniu.workflow.model;

import java.io.Serializable;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpBusiness extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* @property:业务ID */
	private String bid;
	/* @property:业务名称 */
	private String bname;
	/* @property:业务类型 */
	private String btype;
	/* @property:业务状态 */
	private String bstatus;
	/* @property:所属系统 */
	private String belongToSys="";
	/* @property:所属系统名称 */
	private String belongToSysName="";
	/* @property:业务编码 */
	private String bcode;
	/* @property:流程ID */
	private String pid;
	/* @property:动态表单ID */
	private String billId;
	/* @property:关联明细 */
	private String relDetail;
	
	/* @property:表单类型 */
	private String billType;
	/* @property:表单类权限串 */
	private String classesPrivilege;
	/* @property:关联流程对象 */
	private SpProcedure procedure;

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SpBusiness)) {
			return false;
		}
		final SpBusiness spbusiness = (SpBusiness) o;
		return this.hashCode() == spbusiness.hashCode();
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public String getBstatus() {
		return bstatus;
	}

	public void setBstatus(String bstatus) {
		this.bstatus = bstatus;
	}

	public String getBelongToSys() {
		return belongToSys;
	}

	public void setBelongToSys(String belongToSys) {
		this.belongToSys = belongToSys;
	}

	/**
	 * @return belongToSysName : return the property belongToSysName.
	 */
	
	public String getBelongToSysName() {
		return belongToSysName;
	}

	/**
	 * @param belongToSysName : set the property belongToSysName.
	 */
	
	public void setBelongToSysName(String belongToSysName) {
		this.belongToSysName = belongToSysName;
	}

	/**
	 * @return bcode : return the property bcode.
	 */

	public String getBcode() {
		return bcode;
	}

	/**
	 * @param bcode
	 *            : set the property bcode.
	 */

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return billId : return the property billId.
	 */

	public String getBillId() {
		return billId;
	}

	/**
	 * @param billId
	 *            : set the property billId.
	 */

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public SpProcedure getProcedure() {
		return procedure;
	}

	public void setProcedure(SpProcedure procedure) {
		this.procedure = procedure;
	}

	/**
	 * @param relDetail
	 *            : set the property relDetail.
	 */

	public void setRelDetail(String relDetail) {
		this.relDetail = relDetail;
	}

	/**
	 * @return relDetail : return the property relDetail.
	 */

	public String getRelDetail() {
		return relDetail;
	}

	/**
	 * @return billType : return the property billType.
	 */
	
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType : set the property billType.
	 */
	
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return classesPrivilege : return the property classesPrivilege.
	 */

	public String getClassesPrivilege() {
		return classesPrivilege;
	}

	/**
	 * @param classesPrivilege
	 *            : set the property classesPrivilege.
	 */

	public void setClassesPrivilege(String classesPrivilege) {
		this.classesPrivilege = classesPrivilege;
	}	
}
