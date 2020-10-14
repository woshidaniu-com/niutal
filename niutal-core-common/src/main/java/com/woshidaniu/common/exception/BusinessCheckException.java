package com.woshidaniu.common.exception;

import com.woshidaniu.basicutils.exception.OverrideStackCheckedException;


/**
 * 
 *@类名称	: BusinessCheckException.java
 *@类描述	：被检查的异常(Checked Exception)
 *@创建人	：kangzhidong
 *@创建时间	：Mar 18, 2016 12:14:00 PM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 *@see com.woshidaniu.basicutils.exception.NestedCheckedException
 */
@SuppressWarnings("serial")
public class BusinessCheckException extends OverrideStackCheckedException {
	
	public BusinessCheckException() {
		super();
	}
	
	public BusinessCheckException(String message) {
		super(message);
	}
	
	public BusinessCheckException(Throwable cause) {
		super(cause);
	}
	
	public BusinessCheckException(String message,Throwable cause) {
		super(message, cause);
	}
	
	public BusinessCheckException(String message,Object... arguments){
		super(message, arguments);
	}

	public BusinessCheckException(Throwable cause,String message,Object... arguments){
		super(cause, message, arguments);
	}
	
}