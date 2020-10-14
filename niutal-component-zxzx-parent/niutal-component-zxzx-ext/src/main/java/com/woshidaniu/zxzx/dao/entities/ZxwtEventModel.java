/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.dao.entities;

import java.io.Serializable;

/**
 * @类名 ZxwtEventModel.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月31日 下午5:29:25
 * @功能描述 咨询问题点击事件
 * 
 */
public class ZxwtEventModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6537813264341757908L;
	
	private String zxid;
	
	private String bkdm;
	
	private String djsj;
	
	private String event = "click";

	public String getZxid() {
		return zxid;
	}

	public void setZxid(String zxid) {
		this.zxid = zxid;
	}

	public String getBkdm() {
		return bkdm;
	}

	public void setBkdm(String bkdm) {
		this.bkdm = bkdm;
	}

	public String getDjsj() {
		return djsj;
	}

	public void setDjsj(String djsj) {
		this.djsj = djsj;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	

}
