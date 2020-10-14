package com.woshidaniu.workflow.model;

import java.io.Serializable;
import java.util.List;

import com.woshidaniu.workflow.enumobject.NodeTypeEnum;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class SpNode extends BaseObject implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /* @property:节点ID */
    private String            nodeId;
    /* @property:流程ID */
    private String            pid;
    /* @property:节点名称 */
    private String            nodeName;
    /* @property:节点状态 */
    private String            nodeStatus;
    /* @property:节点类型 */
    private String            nodeType;
    /* @property:节点描述 */
    private String            nodeDesc;
    /* @property:角色ID */
    private String            roleId;
    /* @property:用户ID */
    private String            userId;
    /* @property:角色名称 */
    private String            roleName;
    /* @property:流入类型 */
    private String            inType;
    /* @property:流出类型 */
    private String            outType;
    /* @property:是否自动 */
    private String            isAuto;
    /* @property:业务类型 */
    private String            btype;

    /* @property:任务对象集合 */
    private List<SpTask>      spTaskList;

    // 用户集合名称
    private String            userName;
    
    //环节标记
    private String            node_bj;
    private String            node_bjmc;
    //经办类型
    private String            bzlx; 
    
	private String sjfwztj;              	  //数据范围条件值
	private String sjfwztj_qz;                //数据范围条件值取值
	private String send_mes;                  //平台信息是否发送:0-否,1-是
	private String send_mail;                 //邮件是否发送:0-否,1-是'
	private String send_sms;                  //短信是否发送:0-否,1-是
	private String content_mes;            	  //台信息内容(审核中):发给审核人
	private String content_mail;              //邮件内容(审核中):发给审核人
	private String content_sms;               //短信内容(审核中):发给审核人
	private String content_mes_end;           //平台信息内容(结束):发给申请人
	private String content_mail_end;          //平台信息内容(结束):发给申请人
	private String content_sms_end;           //平台信息内容(结束):发给申请人
	private String messageType;               //消息类型
    
    /* Default constructor - creates a new instance with no values set. */
    public SpNode() {}

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
     * @return pId : return the property pId.
     */

    public String getPid() {
        return pid;
    }

    /**
     * @param pId : set the property pId.
     */

    public void setPid(String pid) {
        this.pid = pid;
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
     * @return nodeStatus : return the property nodeStatus.
     */

    public String getNodeStatus() {
        return nodeStatus;
    }

    /**
     * @param nodeStatus : set the property nodeStatus.
     */

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    /**
     * @return nodeType : return the property nodeType.
     */

    public String getNodeType() {
        return nodeType;
    }

    public String getNodeTypeStr() {
        return NodeTypeEnum.valueOf(nodeType).getText();
    }

    /**
     * @param nodeType : set the property nodeType.
     */

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * @return nodeDesc : return the property nodeDesc.
     */

    public String getNodeDesc() {
        return nodeDesc;
    }

    /**
     * @param nodeDesc : set the property nodeDesc.
     */

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return inType : return the property inType.
     */

    public String getInType() {
        return inType;
    }

    /**
     * @param inType : set the property inType.
     */

    public void setInType(String inType) {
        this.inType = inType;
    }

    /**
     * @return outType : return the property outType.
     */

    public String getOutType() {
        return outType;
    }

    /**
     * @param outType : set the property outType.
     */

    public void setOutType(String outType) {
        this.outType = outType;
    }

    /**
     * @return spTaskList : return the property spTaskList.
     */

    public List<SpTask> getSpTaskList() {
        return spTaskList;
    }

    /**
     * @param spTaskList : set the property spTaskList.
     */

    public void setSpTaskList(List<SpTask> spTaskList) {
        this.spTaskList = spTaskList;
    }

    /**
     * @return bType : return the property bType.
     */

    public String getBtype() {
        return btype;
    }

    /**
     * @param bType : set the property bType.
     */

    public void setBtype(String btype) {
        this.btype = btype;
    }

    /**
     * @return isAuto : return the property isAuto.
     */

    public String getIsAuto() {
        return isAuto;
    }

    /**
     * @param isAuto : set the property isAuto.
     */

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

	public void setNode_bj(String node_bj) {
		this.node_bj = node_bj;
	}

	public String getNode_bj() {
		return node_bj;
	}

	public void setBzlx(String bzlx) {
		this.bzlx = bzlx;
	}

	public String getBzlx() {
		return bzlx;
	}

	public void setNode_bjmc(String node_bjmc) {
		this.node_bjmc = node_bjmc;
	}

	public String getNode_bjmc() {
		return node_bjmc;
	}

	public String getSjfwztj() {
		return sjfwztj;
	}

	public void setSjfwztj(String sjfwztj) {
		this.sjfwztj = sjfwztj;
	}

	public String getSjfwztj_qz() {
		return sjfwztj_qz;
	}

	public void setSjfwztj_qz(String sjfwztj_qz) {
		this.sjfwztj_qz = sjfwztj_qz;
	}

	public String getSend_mes() {
		return send_mes;
	}

	public void setSend_mes(String send_mes) {
		this.send_mes = send_mes;
	}

	public String getSend_mail() {
		return send_mail;
	}

	public void setSend_mail(String send_mail) {
		this.send_mail = send_mail;
	}

	public String getSend_sms() {
		return send_sms;
	}

	public void setSend_sms(String send_sms) {
		this.send_sms = send_sms;
	}

	public String getContent_mes() {
		return content_mes;
	}

	public void setContent_mes(String content_mes) {
		this.content_mes = content_mes;
	}

	public String getContent_mail() {
		return content_mail;
	}

	public void setContent_mail(String content_mail) {
		this.content_mail = content_mail;
	}

	public String getContent_sms() {
		return content_sms;
	}

	public void setContent_sms(String content_sms) {
		this.content_sms = content_sms;
	}

	public String getContent_mes_end() {
		return content_mes_end;
	}

	public void setContent_mes_end(String content_mes_end) {
		this.content_mes_end = content_mes_end;
	}

	public String getContent_mail_end() {
		return content_mail_end;
	}

	public void setContent_mail_end(String content_mail_end) {
		this.content_mail_end = content_mail_end;
	}

	public String getContent_sms_end() {
		return content_sms_end;
	}

	public void setContent_sms_end(String content_sms_end) {
		this.content_sms_end = content_sms_end;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
}
