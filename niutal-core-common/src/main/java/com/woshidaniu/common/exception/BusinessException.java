package com.woshidaniu.common.exception;

import com.woshidaniu.basicutils.exception.OverrideStackCheckedException;

/**
 * 
 * 类名称： 自定义异常
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-8-2
 */
@SuppressWarnings("serial")
public class BusinessException extends OverrideStackCheckedException {
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String message,Throwable cause) {
		super(message, cause);
	}
	
	public BusinessException(String message,Object... arguments){
		super(message, arguments);
	}

	public BusinessException(Throwable cause,String message,Object... arguments){
		super(cause, message, arguments);
	}

}
