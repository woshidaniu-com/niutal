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
	private String sqr_yhm;           		  //申请人:用户名
	private String sqr_jsdm;           		  //申请人:角色代码
	private String sqr_sjhm;                  //申请人:手机号码
	private String sqr_dzyx;                  //申请人:电子邮箱
	private String messageType;               //消息类型
   
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
            
            spWorkNode.setSjfwztj(spNode.getSjfwztj());
			spWorkNode.setSjfwztj_qz(spNode.getSjfwztj_qz());
			spWorkNode.setSend_mes(spNode.getSend_mes());
			spWorkNode.setSend_mail(spNode.getSend_mail());
			spWorkNode.setSend_sms(spNode.getSend_sms());
			spWorkNode.setContent_mes(spNode.getContent_mes());
			spWorkNode.setContent_mail(spNode.getContent_mail());
			spWorkNode.setContent_sms(spNode.getContent_sms());
			spWorkNode.setContent_mes_end(spNode.getContent_mes_end());
			spWorkNode.setContent_mail_end(spNode.getContent_mail_end());
			spWorkNode.setContent_sms_end(spNode.getContent_sms_end());
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

	public String getSqr_yhm() {
		return sqr_yhm;
	}

	public void setSqr_yhm(String sqr_yhm) {
		this.sqr_yhm = sqr_yhm;
	}

	public String getSqr_jsdm() {
		return sqr_jsdm;
	}

	public void setSqr_jsdm(String sqr_jsdm) {
		this.sqr_jsdm = sqr_jsdm;
	}

	public String getSqr_sjhm() {
		return sqr_sjhm;
	}

	public void setSqr_sjhm(String sqr_sjhm) {
		this.sqr_sjhm = sqr_sjhm;
	}

	public String getSqr_dzyx() {
		return sqr_dzyx;
	}

	public void setSqr_dzyx(String sqr_dzyx) {
		this.sqr_dzyx = sqr_dzyx;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
}
