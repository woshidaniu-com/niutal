package com.woshidaniu.common.validator.exception;


import com.woshidaniu.common.validator.error.IErrorBindingResult;

/**
 * <li>用于系统自身验证框架进行验证的checked异常</li>
 * 
 * @author Jiangdong.Yi
 * 
 */
public class ErrorValidationException extends Exception {

	private IErrorBindingResult result;
	private static final long serialVersionUID = 5965066699179960525L;

	/**
	 * 
	 */
	public ErrorValidationException(IErrorBindingResult result) {
		this.result = result;
	}

	/**
	 * @param message
	 */
	public ErrorValidationException(String message, IErrorBindingResult result) {
		super(message);
		this.result = result;
	}

	/**
	 * @param cause
	 */
	public ErrorValidationException(Throwable cause, IErrorBindingResult result) {
		super(cause);
		this.result = result;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorValidationException(String message, Throwable cause, IErrorBindingResult result) {
		super(message, cause);
		this.result = result;
	}

	public ErrorValidationException(String message) {
		super(message);
	}

	public ErrorValidationException(Throwable cause) {
		super(cause);
	}

	public IErrorBindingResult getResult() {
		return result;
	}

}
