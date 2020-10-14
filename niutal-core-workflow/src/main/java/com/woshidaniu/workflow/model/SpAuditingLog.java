package com.woshidaniu.workflow.model;

import java.io.Serializable;
import java.util.Date;

import com.woshidaniu.workflow.enumobject.LogTypeEnum;
import com.woshidaniu.workflow.enumobject.WorkNodeStatusEnum;

/**
 * 类描述：审核日志实体对象
 * 
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-23 下午12:47:22
 */
public class SpAuditingLog extends BaseObject implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /* @property:日志ID */
    private String            logId;
    /* @property:日志类型 */
    private String            logType;
    /* @property:日志类型中文 */
    private String            logTypeCn;
    /* @property:日志时间 */
    private Date              logTime;
    /* @property:工作ID */
    private String            wid;
    /* @property:业务类型 */
    private String            btype;
    /* @property:操作类型 */
    private String            otype;
    /* @property:操作状态 */
    private String            ostatus;
    /* @property:操作状态中文 */
    private String            ostatusCn;
    /* @property:操作结果 */
    private String            oresult;
    /* @property:操作内容 */
    private String            ocontent;
    /* @property:操作角色 */
    private String            orole;
    /* @property:操作人 */
    private String            operator;
    /* @property:操作人name */
    private String            operatorCn;
    /* @property:操作意见 */
    private String            osuggestion;
    /* @property:工作节点ID*/
    private String			  oNodeId;
    /* @property:结果排序 */
    private String            seqencing;
    
    private String			  roleId;
    
    private String			  userId;
    
    private String    		  pId;
    
    private String 			  nodeId;

    private String 			  nodeBj;
    
    private String			  rNode;//退回节点
    private String			  rNodeCn;//退回节点名称
    
    /* Default constructor - creates a new instance with no values set. */
    public SpAuditingLog() {}

    /**
     * @return logId : return the property logId.
     */

    public String getLogId() {
        return logId;
    }

    /**
     * @param logId : set the property logId.
     */

    public void setLogId(String logId) {
        this.logId = logId;
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
     * @return oType : return the property oType.
     */

    public String getOtype() {
        return otype;
    }

    /**
     * @param oType : set the property oType.
     */

    public void setOtype(String otype) {
        this.otype = otype;
    }

    /**
     * @return oContent : return the property oContent.
     */

    public String getOcontent() {
        return ocontent;
    }

    /**
     * @param oContent : set the property oContent.
     */

    public void setOcontent(String ocontent) {
        this.ocontent = ocontent;
    }

    /**
     * @return operator : return the property operator.
     */

    public String getOperator() {
        return operator;
    }

    /**
     * @param operator : set the property operator.
     */

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return logType : return the property logType.
     */

    public String getLogType() {
        return logType;
    }

    /**
     * @param logType : set the property logType.
     */

    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * @return logTime : return the property logTime.
     */

    public Date getLogTime() {
        return logTime;
    }

    /**
     * @param logTime : set the property logTime.
     */

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    /**
     * @return btype : return the property btype.
     */

    public String getBtype() {
        return btype;
    }

    /**
     * @param btype : set the property btype.
     */

    public void setBtype(String btype) {
        this.btype = btype;
    }

    /**
     * @return ostatus : return the property ostatus.
     */

    public String getOstatus() {
        return ostatus;
    }

    /**
     * @param ostatus : set the property ostatus.
     */

    public void setOstatus(String ostatus) {
        this.ostatus = ostatus;
    }

    /**
     * @return oresult : return the property oresult.
     */

    public String getOresult() {
        return oresult;
    }

    /**
     * @param oresult : set the property oresult.
     */

    public void setOresult(String oresult) {
        this.oresult = oresult;
    }

    /**
     * @return orole : return the property orole.
     */

    public String getOrole() {
        return orole;
    }

    /**
     * @param orole : set the property orole.
     */

    public void setOrole(String orole) {
        this.orole = orole;
    }

    /**
     * @param osuggestion : set the property osuggestion.
     */

    public void setOsuggestion(String osuggestion) {
        this.osuggestion = osuggestion;
    }

    /**
     * @return osuggestion : return the property osuggestion.
     */

    public String getOsuggestion() {
        return osuggestion;
    }

    public String getOstatusCn() {
        if (WorkNodeStatusEnum.PASS_AUDITING.getKey().equals(this.getOstatus())) {
            ostatusCn = WorkNodeStatusEnum.PASS_AUDITING.getText();
        } else if (WorkNodeStatusEnum.NO_PASS_AUDITING.getKey().equals(this.getOstatus())) {
            ostatusCn = WorkNodeStatusEnum.NO_PASS_AUDITING.getText();
        } else if (WorkNodeStatusEnum.RETURN_AUDITING.getKey().equals(this.getOstatus())) {
            ostatusCn = WorkNodeStatusEnum.RETURN_AUDITING.getText();
        } else if (WorkNodeStatusEnum.CANCEL_AUDITING.getKey().equals(this.getOstatus())) {
            ostatusCn = WorkNodeStatusEnum.CANCEL_AUDITING.getText();
        }
        return ostatusCn;
    }

    public void setOstatusCn(String ostatusCn) {
        this.ostatusCn = ostatusCn;
    }

    public String getLogTypeCn() {
        if (LogTypeEnum.NODE_LOG.getKey().equals(this.getLogType())) {
            logTypeCn = LogTypeEnum.NODE_LOG.getText();
        } else if (LogTypeEnum.TASK_LOG.getKey().equals(this.getLogType())) {
            logTypeCn = LogTypeEnum.TASK_LOG.getText();
        }
        return logTypeCn;
    }

    public void setLogTypeCn(String logTypeCn) {
        this.logTypeCn = logTypeCn;
    }

    public String getSeqencing() {
        return seqencing;
    }

    public void setSeqencing(String seqencing) {
        this.seqencing = seqencing;
    }

    public String getOperatorCn() {
        return operatorCn;
    }

    public void setOperatorCn(String operatorCn) {
        this.operatorCn = operatorCn;
    }

	public String getoNodeId() {
		return oNodeId;
	}

	public void setoNodeId(String oNodeId) {
		this.oNodeId = oNodeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeBj() {
		return nodeBj;
	}

	public void setNodeBj(String nodeBj) {
		this.nodeBj = nodeBj;
	}

	public String getrNode() {
		return rNode;
	}

	public void setrNode(String rNode) {
		this.rNode = rNode;
	}

	public String getrNodeCn() {
		return rNodeCn;
	}

	public void setrNodeCn(String rNodeCn) {
		this.rNodeCn = rNodeCn;
	}

}
