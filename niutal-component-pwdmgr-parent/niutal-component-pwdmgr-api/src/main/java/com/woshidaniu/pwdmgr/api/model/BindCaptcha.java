package com.woshidaniu.pwdmgr.api.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BindCaptcha implements Serializable {

	protected String uuid;
	protected String captcha;
	protected String timestamp;

	public BindCaptcha(String uuid, String captcha, String timestamp) {
		this.uuid = uuid;
		this.captcha = captcha;
		this.timestamp = timestamp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
