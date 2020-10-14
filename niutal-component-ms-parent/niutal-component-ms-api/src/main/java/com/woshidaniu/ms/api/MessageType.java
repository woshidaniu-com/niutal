package com.woshidaniu.ms.api;

import java.util.Locale;

public enum MessageType {
	
	/**
	 * 邮件消息
	 */
	EMAIL("email","邮件消息"),
	/**
	 * 短信消息
	 */
	SMS("sms","短信消息"),
	/**
	 * socket消息
	 */
	SOCKET("socket","Socket消息"),
	/**
	 * 消息队列
	 */
	MQ("mq","消息队列"); 
	
	
	protected String name;
	protected String desc;

	private MessageType(String name,String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	static MessageType valueOfIgnoreCase(String parameter,String desc) {
		MessageType parm = valueOf(parameter.toUpperCase(Locale.ENGLISH).trim());
		parm.desc = desc;
		return parm;
	}
	
}
