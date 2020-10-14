package com.woshidaniu.component.bpm;

import org.activiti.engine.extend.ExtActivitiException;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：TODO <br>
 * class：com.woshidaniu.component.bpm.BPMException.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class BPMException extends ExtActivitiException {

	private static final long serialVersionUID = 1L;

	public BPMException(String message) {
		super(message);
	}
	
	public BPMException(Throwable cause) {
		super(cause.getMessage(), cause);
		if(cause instanceof ExtActivitiException){
			this.exceptionCode = ((ExtActivitiException)cause).getExceptionCode();
		}
	}

	public BPMException(String exceptionCode, String message) {
		super(exceptionCode, message, null);
	}
	
	public BPMException(String exceptionCode, String message, Throwable cause) {
		super(exceptionCode, message, cause);
	}

	public BPMException(String exceptionCode, Throwable cause) {
		super(exceptionCode, cause);
	}

	public String getErrorMessage() {
		if (BPMUtils.isBlank(exceptionCode)) {
			return BPMUtils.getMessage("BPM_EX_00");
		}
		String message = BPMUtils.getMessage(exceptionCode);
		return message == null ? BPMUtils.getMessage("BPM_EX_00") : message;
	}
}
