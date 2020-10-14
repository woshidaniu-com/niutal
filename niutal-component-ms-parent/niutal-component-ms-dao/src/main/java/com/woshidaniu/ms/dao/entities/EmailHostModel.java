package com.woshidaniu.ms.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * @类名称 ： EmailHostModel.java
 * @类描述 ：
 * @创建人 ：kangzhidong
 * @创建时间 ：2017年4月17日 上午9:03:57
 * @修改人 ：
 * @修改时间 ：
 * @版本号 :v1.0
 */
@SuppressWarnings("serial")
public class EmailHostModel extends ModelBase {

	/**
	 * 邮件服务设置ID
	 */
	protected String id;
	/**
	 * 邮件服务器根域名
	 */
	protected String domain;
	/**
	 * 邮件服务器 地址
	 */
	protected String addr;
	/**
	 * 邮件服务器默认端口
	 */
	protected String port = "25";
	/**
	 * 发送超时时间，默认25000
	 */
	protected String timeout;
	/**
	 * 邮件服务器备注信息
	 */
	protected String desc;
	/**
	 * 邮件协议 smtp、nntp、pop3、pop4、imap
	 */
	protected String protocol = "smtp";
	/**
	 * 是否要求身份认证 (1:验证;0:不验证)
	 */
	protected String useauth = "0";
	/**
	 * 使用SSL加密通信(1:使用;0:不使用)
	 */
	protected String usessl = "0";
	/**
	 * 邮箱主机配置启用状态标记，1：启用，0：停用
	 */
	protected String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getUseauth() {
		return useauth;
	}

	public void setUseauth(String useauth) {
		this.useauth = useauth;
	}

	public String getUsessl() {
		return usessl;
	}

	public void setUsessl(String usessl) {
		this.usessl = usessl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
