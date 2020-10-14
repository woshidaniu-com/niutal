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
 *   <h3>niutal框架<h3>
 *   说明：CommonMessageEntityManager
 * <p>
 * @className:org.activiti.engine.extend.persistence.entity.CommonMessageEntityManager.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月5日上午9:24:25
 */
public class CommonMessageEntityManager extends AbstractManager {

	/**
	 * 
	 * <p>方法说明：findCommonMessageEntityById<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月5日上午9:27:47<p>
	 * @param id
	 * @return
	 */
	public CommonMessageEntity findCommonMessageEntityById(String id){
		if (id == null) {
			throw new ActivitiIllegalArgumentException("Invalid CommonMessageEntity id : null");
		}
		return getDbSqlSession().selectById(CommonMessageEntity.class, id);
	}
	
	/**
	 * 
	 * <p>方法说明：findCommonMessageEntitiesByUserId<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月5日上午9:35:59<p>
	 * @param userId
	 * @return
	 */
	public List<CommonMessageEntity> findCommonMessageEntitiesByUserId(String userId){
		if (userId == null) {
			throw new ActivitiIllegalArgumentException("Invalid CommonMessageEntity userId : null");
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("userId", userId);
		
		return getDbSqlSession().getSqlSession().selectList("selectCommonMessagesByUserId", parameters);
	}
	
}
