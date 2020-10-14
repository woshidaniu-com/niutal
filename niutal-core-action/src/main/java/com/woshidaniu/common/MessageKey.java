package com.woshidaniu.common;

import com.woshidaniu.util.base.MessageUtil;

import net.sf.json.JSONObject;

public enum MessageKey {
	SAVE_SUCCESS("I99001","success"),
	SAVE_FAIL("I99002","fail"),
	
	MODIFY_SUCCESS("I99003","success"),
	MODIFY_FAIL("I99004","fail"),
	
	DELETE_SUCCESS("I99005","success"),
	DELETE_FAIL("I99006","fail"),
	
	;
	
	private String key;
	private String status;
	
	MessageKey(String key,String status){
		this.key = key;
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		String message = MessageUtil.getText(key);
		JSONObject json = new JSONObject();
		json.put("message", message);
		json.put("status", status);
		return json.toString();
	}
}
