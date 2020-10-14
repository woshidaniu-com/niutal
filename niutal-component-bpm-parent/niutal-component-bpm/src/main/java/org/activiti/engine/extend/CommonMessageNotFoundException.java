/**
 * 
 */
package org.activiti.engine.extend;

import org.activiti.engine.ActivitiException;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：CommonMessageNotFoundException
 * <p>
 * @className:org.activiti.engine.extend.CommonMessageNotFoundException.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午10:04:54
 */
public class CommonMessageNotFoundException extends ActivitiException {

	public CommonMessageNotFoundException(String message) {
		super(message);
	}

	public CommonMessageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	private static final long serialVersionUID = -3467768329204135095L;

}
