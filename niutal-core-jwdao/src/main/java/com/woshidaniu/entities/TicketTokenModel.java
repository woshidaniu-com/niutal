package com.woshidaniu.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TicketTokenModel implements Serializable {

	// 业务系统ID
	protected String appid;
	//RSA的私钥，验证方法会通过对应的公钥进行验证
	protected String secret;
	// 系统双方约定的秘钥:基于Base64加密的值
	protected String token;
	// 32位MD5加密信息（大写）:格式为：卡号+用户类型+时间搓+token值组合的MD5值
	protected String verify;
	// 时间搓;格式: yyyyMMddHHmmss
	protected String time;
	// 账号ID
	protected String userid;
	// 角色ID，xs,js：方便区别用户角色
	protected String roleid;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}
