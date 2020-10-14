/**
 * 
 */
package com.woshidaniu.globalweb.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author xiaokang
 *
 */
public class ZFUsernamePasswordToken extends UsernamePasswordToken {

	public final static String TYPE_DEFAULE = "default";
	
	public final static String TYPE_OUTLOGIN = "outlogin";
	
	public final static String TYPE_SSOLOGIN = "ssologin";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5716127323894416659L;
	
	private String yzm;
	
	/*模式：defalut 代表正常登录，outLogin 代表外部系统调用框架服务登录*/
	private String type = TYPE_DEFAULE;
	
	public ZFUsernamePasswordToken(String username, char[] password,String host, String yzm) {
		super(username, password, host);
		this.yzm = yzm;
	}

	public ZFUsernamePasswordToken(String username, char[] password,String host, String yzm, String type) {
		super(username, password, host);
		this.yzm = yzm;
		this.type = type;
	}



	public ZFUsernamePasswordToken() {
		super();
	}

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
