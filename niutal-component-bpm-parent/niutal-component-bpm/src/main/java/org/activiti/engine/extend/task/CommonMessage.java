/**
 * 
 */
package org.activiti.engine.extend.task;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：CommonMessage
 * <p>
 * @className:org.activiti.engine.extend.task.CommonMessage.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午9:01:41
 */
public interface CommonMessage extends CommonMessageInfo{
	
	void setUserId(String userId);
	
	void setMessage(String message);
	
}
