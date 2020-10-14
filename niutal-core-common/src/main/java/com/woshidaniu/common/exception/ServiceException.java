package com.woshidaniu.common.exception;

import com.woshidaniu.basicutils.exception.OverrideStackRuntimeException;

/**
 * 
 * 类名称： 自定义异常
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-8-2
 */
@SuppressWarnings("serial")
public class ServiceException extends OverrideStackRuntimeException {
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String message,Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message,Object... arguments){
		super(message, arguments);
	}

	public ServiceException(Throwable cause,String message,Object... arguments){
		super(cause, message, arguments);
	}

	
}
