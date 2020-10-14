package com.woshidaniu.ms.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class MessageBody implements Serializable {
	
	protected MessageType type;
	protected String uuid;
	protected Map<String, String> header = new HashMap<String, String>();
	protected Map<String, String> body = new HashMap<String, String>();

	public MessageBody(MessageType type, String uuid) {
		this.type = type;
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public Map<String, String> getBody() {
		return body;
	}

	public void setBody(Map<String, String> body) {
		this.body = body;
	}
	
}
