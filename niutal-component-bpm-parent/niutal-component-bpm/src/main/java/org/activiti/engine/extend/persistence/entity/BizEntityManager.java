/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.persistence.AbstractManager;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：BizEntityManager
 * <p>
 * 
 * @className:org.activiti.engine.extend.persistence.entity.BizEntityManager.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月30日下午2:53:43
 */
public class BizEntityManager extends AbstractManager {
	
	public BizEntity findBizEntityById(String id) {
		if (id == null) {
			throw new ActivitiIllegalArgumentException("Invalid biz id : null");
		}
		return getDbSqlSession().selectById(BizEntity.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<BizEntity> findAllBizEntity(){
		return getDbSqlSession().selectList("selectAllBizEntities");
	}
	
}
