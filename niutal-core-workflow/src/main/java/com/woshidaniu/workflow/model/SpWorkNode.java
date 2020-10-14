package com.woshidaniu.workflow.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpWorkNode extends SpNode implements Serializable {

    /* serialVersionUID: serialVersionUID */

    private static final long serialVersionUID = 1L;
    /* @property:ID */
    private String            id;
    /* @property:工作审核流程ID */
    private String            wpid;
    /* @property:工作ID */
    private String            wid;
    /* @property:审核状态 */
    private String            status;
    /* @property:审核人 */
    private String            auditorId;
    /* @property:审核时间 */
    private Date              auditTime;
    /* @property:审核结果 */
    private String            auditResult;
    /* @property:审核意见 */
    private String            suggestion;
    /* @property:执行状态 */
    private String            estatus;

    /* @property:业务编码 */
    private String            bcode;
    /* @property:工作审核任务对象集合 */
    private List<SpWorkTask>  spWorkTaskList;

    /* @property:审核状态 */
    private String[]          statusArray;
    /* @property:角色ID数组 */
    private String[]          roleIdArray;
    /* @property:用户ID数组 */
    private String[]          userIdArray;
    /* @property:是否可编辑 */
    private boolean           edit;

    /* @property:操作人name */
    private String            auditorName;  
    private String 			  returnNodeId;//退回节点
   
    /* Default constructor - creates a new instance with no values set. */
    public SpWorkNode() {}

    /**
     * @return id : return the property id.
     */

    public String getId() {
        return id;
    }

    /**
     * @param id : set the property id.
     */

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return wPId : return the property wPId.
     */

    public String getWpid() {
        return wpid;
    }

    /**
     * @param wpid : set the property wPId.
     */

    public void setWpid(String wpid) {
        this.wpid = wpid;
    }

    /**
     * @return wId : return the property wId.
     */

    public String getWid() {
        return wid;
    }

    /**
     * @param wId : set the property wId.
     */

    public void setWid(String wid) {
        this.wid = wid;
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

    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * @param auditTime : set the property auditTime.
     */

    public void setAuditTime(Date auditTime) {
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
     * @return spWorkTaskList : return the property spWorkTaskList.
     */

    public List<SpWorkTask> getSpWorkTaskList() {
        return spWorkTaskList;
    }

    /**
     * @param spWorkTaskList : set the property spWorkTaskList.
     */

    public void setSpWorkTaskList(List<SpWorkTask> spWorkTaskList) {
        this.spWorkTaskList = spWorkTaskList;
    }

    /**
     * @return estatus : return the property estatus.
     */

    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus : set the property estatus.
     */

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * @return bcode : return the property bcode.
     */

    public String getBcode() {
        return bcode;
    }

    /**
     * @param bcode : set the property bcode.
     */

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    /**
     * 将节点对象填充到工作审核节点对象中
     * 
     * @param spNode
     * @return
     */
    public static SpWorkNode putNodeObject(SpNode spNode) {
        SpWorkNode spWorkNode = new SpWorkNode();
        if (spNode != null) {
            spWorkNode.setNodeId(spNode.getNodeId());
            spWorkNode.setPid(spNode.getPid());
            spWorkNode.setNodeName(spNode.getNodeName());
            spWorkNode.setNodeType(spNode.getNodeType());
            spWorkNode.setNodeDesc(spNode.getNodeDesc());
            spWorkNode.setRoleId(spNode.getRoleId());
            spWorkNode.setUserId(spNode.getUserId());
            spWorkNode.setOutType(spNode.getOutType());
            spWorkNode.setInType(spNode.getInType());
            spWorkNode.setNode_bj(spNode.getNode_bj());
        }
        return spWorkNode;
    }

    public String[] getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(String[] statusArray) {
        this.statusArray = statusArray;
    }

    /**
     * @param roleIdArray : set the property roleIdArray.
     */

    public void setRoleIdArray(String[] roleIdArray) {
        this.roleIdArray = roleIdArray;
    }

    /**
     * @return roleIdArray : return the property roleIdArray.
     */

    public String[] getRoleIdArray() {
        return roleIdArray;
    }

    public String[] getUserIdArray() {
        return userIdArray;
    }

    public void setUserIdArray(String[] userIdArray) {
        this.userIdArray = userIdArray;
    }

    /**
     * @return edit : return the property edit.
     */

    public boolean isEdit() {
        return edit;
    }

    /**
     * @param edit : set the property edit.
     */

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

	public String getReturnNodeId() {
		return returnNodeId;
	}

	public void setReturnNodeId(String returnNodeId) {
		this.returnNodeId = returnNodeId;
	}
    
}
