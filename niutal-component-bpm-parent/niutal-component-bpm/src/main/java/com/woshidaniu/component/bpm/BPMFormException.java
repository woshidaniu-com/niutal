/**
 * 
 */
package com.woshidaniu.component.bpm;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：自定义表单异常基类
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.form.FormException.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月22日上午9:18:18
 */
public class BPMFormException extends BPMException {

	private static final long serialVersionUID = 1L;

	public BPMFormException(String exceptionCode, String message, Throwable cause) {
		super(exceptionCode, message, cause);
	}

	public BPMFormException(String exceptionCode, String message) {
		super(exceptionCode, message);
	}

	public BPMFormException(String exceptionCode, Throwable cause) {
		super(exceptionCode, cause);
	}

	public BPMFormException(String message) {
		super(message);
	}

	public BPMFormException(Throwable cause) {
		super(cause);
	}

}
