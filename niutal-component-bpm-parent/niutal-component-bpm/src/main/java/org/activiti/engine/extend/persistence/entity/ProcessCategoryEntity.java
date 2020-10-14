/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.extend.biz.ProcessCategory;
import org.activiti.engine.impl.db.BulkDeleteable;
import org.activiti.engine.impl.db.PersistentObject;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.persistence.entity.ProcessCategoryEntity.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月8日上午8:38:47
 */
public class ProcessCategoryEntity implements ProcessCategory, PersistentObject, BulkDeleteable {

	private static final long serialVersionUID = 3629322282652068569L;
	protected String id;
	protected String name;
	protected String description;

	public static ProcessCategoryEntity createProcessCategoryEntity(){
		return new ProcessCategoryEntity();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.ProcessCategory#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.ProcessCategory#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.activiti.engine.extend.biz.ProcessCategory#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("name", this.name);
		persistentState.put("description", this.description);
		return persistentState;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
