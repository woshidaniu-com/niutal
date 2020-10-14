/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.extend.biz.Biz;
import org.activiti.engine.impl.db.BulkDeleteable;
import org.activiti.engine.impl.db.PersistentObject;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：BizEntity
 * <p>
 * 
 * @className:org.activiti.engine.extend.persistence.entity.BizEntity.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月30日下午2:37:18
 */
public class BizEntity implements Biz, PersistentObject, BulkDeleteable {

	private static final long serialVersionUID = 8488827850676445846L;

	protected String id;
	protected String name;
	protected String description;

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

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
