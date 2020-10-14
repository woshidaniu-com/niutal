/**
 * 
 */
package org.activiti.engine.extend.task;

import java.io.Serializable;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：decision类型
 * <p>
 * 
 * @className:org.activiti.engine.extend.task.DecisionType.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月6日上午10:06:06
 */
public interface DecisionType extends Serializable {

	String getKey();
	
	String getValue();
	
	boolean isAutomaticSupported();

}
