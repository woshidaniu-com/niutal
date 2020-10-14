/**
 * 
 */
package com.woshidaniu.zxzx.auth.impl;

import com.woshidaniu.zxzx.auth.ZxzxAuthToken;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.auth.impl.DefaultZxzxAuthTokenImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月21日上午8:54:22
 */
public class DefaultZxzxAuthTokenImpl implements ZxzxAuthToken {

	protected String userName;

	protected String password;

	protected String CAPTCHA;

	public DefaultZxzxAuthTokenImpl() {
		super();
	}

	public DefaultZxzxAuthTokenImpl(String userName, String password, String cAPTCHA) {
		super();
		this.userName = userName;
		this.password = password;
		CAPTCHA = cAPTCHA;
	}

	public void setCAPTCHA(String cAPTCHA) {
		CAPTCHA = cAPTCHA;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.woshidaniu.zxzx.auth.ZxzxAuthToken#getUserName()
	 */
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.woshidaniu.zxzx.auth.ZxzxAuthToken#getPassword()
	 */
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.woshidaniu.zxzx.auth.ZxzxAuthToken#getCAPTCHA()
	 */
	@Override
	public String getCAPTCHA() {
		// TODO Auto-generated method stub
		return this.CAPTCHA;
	}

}
