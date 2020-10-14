/**
 * 
 */
package org.activiti.engine.extend.biz;

import java.io.Serializable;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：模拟路径上的activity
 * <p>
 * 
 * @className:org.activiti.engine.extend.biz.InstancePath.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月11日上午10:07:07
 */
public interface SimulationActivity extends Serializable {

	String getId();

	String getProcessDefinitionId();

	String getBusinessKey();

	String getActivityId();

	String getActivityName();

	String getActivityType();

	Integer getOrder();
}
