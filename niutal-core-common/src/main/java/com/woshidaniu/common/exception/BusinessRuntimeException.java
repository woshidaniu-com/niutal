package com.woshidaniu.common.exception;

import com.woshidaniu.basicutils.exception.OverrideStackRuntimeException;

/**
 * 
 * 类名称： 自定义异常
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-8-2
 */
@SuppressWarnings("serial")
public class BusinessRuntimeException extends OverrideStackRuntimeException {
	
	public BusinessRuntimeException() {
		super();
	}
	
	public BusinessRuntimeException(String message) {
		super(message);
	}
	
	public BusinessRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public BusinessRuntimeException(String message,Throwable cause) {
		super(message, cause);
	}
	
	public BusinessRuntimeException(String message,Object... arguments){
		super(message, arguments);
	}

	public BusinessRuntimeException(Throwable cause,String message,Object... arguments){
		super(cause, message, arguments);
	}

}
