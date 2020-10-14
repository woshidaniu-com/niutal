/**
 * 
 */
package com.woshidaniu.globalweb.shiro.ext;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：外部系统登录token
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年7月29日下午3:41:18
 */
public class OutAccountLoginToken implements HostAuthenticationToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3271060542153492837L;

	protected String userName;//用户名
	
	protected String host;		//主机名

	protected String invoker;	//调用者
	
	
	public OutAccountLoginToken(String userName) {
		super();
		this.userName = userName;
	}

	public OutAccountLoginToken() {
		super();
	}

	public OutAccountLoginToken(String userName, String host) {
		super();
		this.userName = userName;
		this.host = host;
	}

	@Override
	public Object getPrincipal() {
		return userName;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public String getHost() {
		return host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getInvoker() {
		return invoker;
	}

	public void setInvoker(String invoker) {
		this.invoker = invoker;
	}

}
