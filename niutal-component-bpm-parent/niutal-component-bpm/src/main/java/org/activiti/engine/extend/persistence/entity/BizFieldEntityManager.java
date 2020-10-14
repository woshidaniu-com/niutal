/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.persistence.AbstractManager;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：BizFieldEntityManager
 * <p>
 * 
 * @className:org.activiti.engine.extend.persistence.entity.BizFieldEntityManager.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月30日下午3:02:17
 */
public class BizFieldEntityManager extends AbstractManager {

	/**
	 * 
	 * <p>方法说明：findBizFieldEntityById<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月5日上午9:30:11<p>
	 * @param id
	 * @return
	 */
	public BizFieldEntity findBizFieldEntityById(String id) {
		if (id == null) {
			throw new ActivitiIllegalArgumentException("Invalid bizEntity id : null");
		}
		return getDbSqlSession().selectById(BizFieldEntity.class, id);
	}
	
	/**
	 * 
	 * <p>方法说明：findBizFieldEntitiesByBizId<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月5日上午9:30:05<p>
	 * @param bizId
	 * @return
	 */
	public List<BizFieldEntity> findBizFieldEntitiesByBizId(String bizId) {
		if (bizId == null) {
			throw new ActivitiIllegalArgumentException("Invalid bizId id : null");
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("bizId", bizId);
		return getDbSqlSession().getSqlSession().selectList("selectBizFieldEntitiesByBizId", parameters);
	}
}
