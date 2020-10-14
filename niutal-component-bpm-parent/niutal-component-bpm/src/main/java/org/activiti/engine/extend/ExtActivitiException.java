/**
 * 
 */
package org.activiti.engine.extend;

import org.activiti.engine.ActivitiException;

/**
 * <p>
 * <h3>niutal框架, 带有一个异常代码
 * <h3>说明：ExtActivitiException
 * <p>
 * 
 * @className:org.activiti.engine.extend.BPMException.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月7日上午9:34:12
 */
public class ExtActivitiException extends ActivitiException {

	/**
	 * 错误代码
	 */
	protected String exceptionCode;

	public ExtActivitiException(String exceptionCode) {
		super(null);
		this.exceptionCode = exceptionCode;
	}

	public ExtActivitiException(String exceptionCode, Throwable cause) {
		super(null, cause);
		this.exceptionCode = exceptionCode;
	}

	public ExtActivitiException(String exceptionCode, String message, Throwable cause) {
		super(message, cause);
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionCode;
	}

	private static final long serialVersionUID = 2665979553030517427L;

	
	public String getExceptionCode() {
		return exceptionCode;
	}

}
