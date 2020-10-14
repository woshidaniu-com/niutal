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
}
