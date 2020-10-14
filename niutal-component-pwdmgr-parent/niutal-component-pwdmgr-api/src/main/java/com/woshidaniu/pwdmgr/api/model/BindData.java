package com.woshidaniu.pwdmgr.api.model;

import java.util.HashMap;
import java.util.Map;

public class BindData {
	//验证方式
	protected String verifiType;
	//邮箱地址
	protected String email;
	//手机号码
	protected String phone;
	//用户名
	protected String username;
	protected String uuid;
	protected Map<String, String> map = new HashMap<String, String>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVerifiType() {
		return verifiType;
	}

	public void setVerifiType(String verifiType) {
		this.verifiType = verifiType;
	}
	
}
