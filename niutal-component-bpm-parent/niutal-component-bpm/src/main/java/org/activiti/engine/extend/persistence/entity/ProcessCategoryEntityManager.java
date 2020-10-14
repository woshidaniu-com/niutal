/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.biz.ProcessCategory;
import org.activiti.engine.impl.persistence.AbstractManager;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：ProcessCategoryEntityManager
 * <p>
 * 
 * @className:org.activiti.engine.extend.persistence.entity.ProcessCategoryEntityManager.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月8日上午8:41:43
 */
public class ProcessCategoryEntityManager extends AbstractManager {
	public ProcessCategory findProcessCategoryEntityById(String id) {
		if (id == null) {
			throw new ActivitiIllegalArgumentException("Invalid process category id : null");
		}
		return getDbSqlSession().selectById(ProcessCategoryEntity.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ProcessCategory> findAllProcessCategoryEntity(){
		return getDbSqlSession().selectList("selectAllProcessCategories");
	}
}
