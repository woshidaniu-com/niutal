/**
 * 
 */
package com.woshidaniu.component.bpm;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：审批流异常枚举
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月27日下午2:00:36
 */
public enum BPMExceptionType {
	
	EX_UNKNOWN("EX_UNKNOWN","EX_UNKNOWN");
	
	private String exceptionCode;
	private String messageKey;
	
	
	
	private BPMExceptionType(String exceptionCode, String messageKey) {
		this.exceptionCode = exceptionCode;
		this.messageKey = messageKey;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

}
