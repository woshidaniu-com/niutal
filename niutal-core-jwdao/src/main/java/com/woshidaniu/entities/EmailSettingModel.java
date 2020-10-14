/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称:EmailSettingModel.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Dec 24, 2015 4:29:17 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class EmailSettingModel extends ModelBase {

	/**
	 * 邮件服务器根域名
	 */
	private String root;
	/**
	 * 邮件服务器 地址
	 */
	private String host;
	/**
	 * 邮件服务器默认端口
	 */
	private String port = "25";
	/**
	 * 邮件协议 smtp、nntp、pop3、pop4、imap
	 */
	private String protocol;
	/**
	 * 是否要求身份认证 (1:验证;0:不验证)
	 */
	private String isAuth;
	/**
	 * 使用SSL加密通信(1:使用;0:不使用)
	 */
	private String useSSL;
	/**
	 * 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
	 */
	private String useDebug;
	/**
	 * 发送超时时间，默认25000
	 */
	private String timeout;
	/**
	 * 邮件服务器备注信息
	 */
	private String mark;

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the isAuth
	 */
	public String getIsAuth() {
		return isAuth;
	}

	/**
	 * @param isAuth
	 *            the isAuth to set
	 */
	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	/**
	 * @return the useSSL
	 */
	public String getUseSSL() {
		return useSSL;
	}

	/**
	 * @param useSSL
	 *            the useSSL to set
	 */
	public void setUseSSL(String useSSL) {
		this.useSSL = useSSL;
	}

	/**
	 * @return the useDebug
	 */
	public String getUseDebug() {
		return useDebug;
	}

	/**
	 * @param useDebug
	 *            the useDebug to set
	 */
	public void setUseDebug(String useDebug) {
		this.useDebug = useDebug;
	}

	/**
	 * @return the timeout
	 */
	public String getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the mark
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

}
