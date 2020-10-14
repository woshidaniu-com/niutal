package com.woshidaniu.workflow.model.result;

import java.io.Serializable;

import com.woshidaniu.workflow.model.BaseObject;

/**
 * 
 * 类描述：查询符合条件的工作审核结果集合接口返回SQL对象
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-19 下午12:29:15
 */
public class NodeListSqlResult extends BaseObject implements Serializable {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -3053003984199613539L;
	
	private static final String sql_start_part = "SELECT W_ID, '' AS TASK_ID, P_ID, NODE_ID, NODE_NAME, NODE_TYPE, NODE_DESC, ROLE_ID, STATUS, AUDITOR_ID, AUDIT_TIME, AUDIT_RESULT, SUGGESTION, E_STATUS,NODE_BJ"
			+ " FROM SP_WORK_NODE "
			+ " WHERE p_id IN (SELECT a.p_id FROM sp_business a WHERE 1 = 1 ";

	
	private static final String sql_start_part_with_alisa = "SELECT x.W_ID, '' AS TASK_ID, x.P_ID, x.NODE_ID, x.NODE_NAME, x.NODE_TYPE, x.NODE_DESC, x.ROLE_ID, x.STATUS, x.AUDITOR_ID, x.AUDIT_TIME, x.AUDIT_RESULT, x.SUGGESTION, x.E_STATUS, x.NODE_BJ"
		+ " FROM SP_WORK_NODE x "
		+ " WHERE x.p_id IN (SELECT a.p_id FROM sp_business a WHERE 1 = 1 ";
	
	/**
	 * 查询退回数据的sql
	 */
	private static final String 
		sql_start_part_return_auditing 
			= 
			 "SELECT x.W_ID,'' AS TASK_ID , x.P_ID, x.NODE_ID, x.O_TYPE AS NODE_NAME, x.LOG_TIME AS AUDIT_TIME, '' AS NODE_TYPE, '' AS NODE_DESC, x.ROLE_ID, x.O_STATUS AS STATUS, x.OPERATOR AS AUDITOR_ID, '' AS AUDIT_RESULT, x.O_SUGGESTION AS SUGGESTION, '' AS E_SATTUS, x.NODE_BJ"
		   + " FROM SP_AUDITING_LOG x "
		   + " WHERE x.P_ID IN (SELECT a.P_ID FROM sp_business a WHERE 1 = 1 ";
	
	/* @property:SQL内容 */
	private String sqlContent;
	/* @property:表名称 */
	private String tableName = "workflow";
	/* @property:工作ID */
	private String wId = "W_ID";
	/* @property:流程ID */
	private String pId = "P_ID";
	/* @property:节点ID */
	private String nodeId = "NODE_ID";
	/* @property:流程ID */
	private String nodeName = "NODE_NAME";
	/* @property:流程ID */
	private String roleId = "ROLE_ID";
	/* @property:审核状态 */
	private String status = "STATUS";
	/* @property:审核人 */
	private String auditorId = "AUDITOR_ID";
	/* @property:审核时间 */
	private String auditTime = "AUDIT_TIME";
	/* @property:审核结果 */
	private String auditResult = "AUDIT_RESULT";
	/* @property:审核意见 */
	private String suggestion = "SUGGESTION";
	/* @property:执行状态 */
	private String eStatus  = "E_STATUS";
	/* @property:节点类型*/
	private String nodeType  = "NODE_TYPE";

	/* Default constructor - creates a new instance with no values set. */
	public NodeListSqlResult() {
	}

	
	/**
	 * @param sqlContent : set the property sqlContent.
	 */
	
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	
	/**
	 * @return sqlContent : return the property sqlContent.
	 */
	
	public String getSqlContent() {
		return sqlContent;
	}


	
	/**
	 * @param tableName : set the property tableName.
	 */
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	
	/**
	 * @return tableName : return the property tableName.
	 */
	
	public String getTableName() {
		return tableName;
	}


	/**
	 * @return wId : return the property wId.
	 */
	
	public String getwId() {
		return wId;
	}


	/**
	 * @param wId : set the property wId.
	 */
	
	public void setwId(String wId) {
		this.wId = wId;
	}


	/**
	 * @return pId : return the property pId.
	 */
	
	public String getpId() {
		return pId;
	}


	/**
	 * @param pId : set the property pId.
	 */
	
	public void setpId(String pId) {
		this.pId = pId;
	}


	/**
	 * @return nodeId : return the property nodeId.
	 */
	
	public String getNodeId() {
		return nodeId;
	}


	/**
	 * @param nodeId : set the property nodeId.
	 */
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}


	/**
	 * @return nodeName : return the property nodeName.
	 */
	
	public String getNodeName() {
		return nodeName;
	}


	/**
	 * @param nodeName : set the property nodeName.
	 */
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	/**
	 * @return roleId : return the property roleId.
	 */
	
	public String getRoleId() {
		return roleId;
	}


	/**
	 * @param roleId : set the property roleId.
	 */
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	/**
	 * @return status : return the property status.
	 */
	
	public String getStatus() {
		return status;
	}


	/**
	 * @param status : set the property status.
	 */
	
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return auditorId : return the property auditorId.
	 */
	
	public String getAuditorId() {
		return auditorId;
	}


	/**
	 * @param auditorId : set the property auditorId.
	 */
	
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}


	/**
	 * @return auditTime : return the property auditTime.
	 */
	
	public String getAuditTime() {
		return auditTime;
	}


	/**
	 * @param auditTime : set the property auditTime.
	 */
	
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}


	/**
	 * @return auditResult : return the property auditResult.
	 */
	
	public String getAuditResult() {
		return auditResult;
	}


	/**
	 * @param auditResult : set the property auditResult.
	 */
	
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}


	/**
	 * @return suggestion : return the property suggestion.
	 */
	
	public String getSuggestion() {
		return suggestion;
	}


	/**
	 * @param suggestion : set the property suggestion.
	 */
	
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}


	/**
	 * @return eStatus : return the property eStatus.
	 */
	
	public String geteStatus() {
		return eStatus;
	}

	/**
	 * @param eStatus : set the property eStatus.
	 */
	
	public void seteStatus(String eStatus) {
		this.eStatus = eStatus;
	}
	
	/**
	 * @return sqlStartPart : return the property sqlStartPart.
	 */
	
	public static String getSqlStartPart() {
		return sql_start_part;
	}


	/**
	 * 返回
	 */
	public String getNodeType() {
		return nodeType;
	}


	/**
	 * 设置
	 * @param nodeType 
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}


	public static String getSqlStartPartReturnAuditing() {
		return sql_start_part_return_auditing;
	}


	public static String getSqlStartPartWithAlisa() {
		return sql_start_part_with_alisa;
	}
	
	

}
