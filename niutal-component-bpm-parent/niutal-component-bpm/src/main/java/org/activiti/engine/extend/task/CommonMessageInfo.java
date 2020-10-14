/**
 * 
 */
package org.activiti.engine.extend.task;

import java.io.Serializable;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：CommonMessageInfo
 * <p>
 * @className:org.activiti.engine.extend.task.CommonMessageInfo.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午9:02:13
 */
public interface CommonMessageInfo extends Serializable{

	String getId();
	
	String getUserId();
	
	String getMessage();
	
}
