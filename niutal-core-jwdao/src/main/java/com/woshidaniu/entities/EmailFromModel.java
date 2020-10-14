/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.entities;

/**
 * 
 *@类名称:EmailFromModel.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Dec 28, 2015 11:54:21 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class EmailFromModel extends EmailSettingModel {
	/**
	 * 发件人名称
	 */
	private String mailFrom;
	/**
	 * 登陆SMTP邮件发送服务器的用户名
	 */
	private String username;
	/**
	 * 登陆邮件发送服务器的密码
	 */
	private String password;

	/**
	 * @return the mailFrom
	 */
	public String getMailFrom() {
		return mailFrom;
	}

	/**
	 * @param mailFrom
	 *            the mailFrom to set
	 */
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


}
