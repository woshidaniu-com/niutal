/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.biz.SimulationActivity;
import org.activiti.engine.impl.persistence.AbstractManager;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:SimulationActivityEntity.SimulationActivityEntityManager.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月11日上午10:46:04
 */
public class SimulationActivityEntityManager extends AbstractManager {

	/**
	 * 
	 * <p>
	 * 方法说明：TODO
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年5月11日上午11:39:03
	 * <p>
	 * 
	 * @param businessKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SimulationActivity> findSimulationActivitiesByBusinessKey(String businessKey) {
		if (businessKey == null) {
			throw new ActivitiIllegalArgumentException("Invalid businessKey id : null");
		}
		return getDbSqlSession().selectList("selectSimulationActivityByBusinessKey", businessKey);
	}

	/**
	 * 
	 * <p>
	 * 方法说明：TODO
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年5月11日上午11:43:33
	 * <p>
	 * 
	 * @param businessKey
	 */
	public void deleteSimulationActivitiesByBusinessKey(String businessKey) {
		if (businessKey == null) {
			throw new ActivitiIllegalArgumentException("Invalid businessKey id : null");
		}

		getDbSqlSession().delete("deleteSimulationActivityByBusinessKey", businessKey);

	}

}
