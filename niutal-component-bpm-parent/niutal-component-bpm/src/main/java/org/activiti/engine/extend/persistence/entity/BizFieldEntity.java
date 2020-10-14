/**
 * 
 */
package org.activiti.engine.extend.persistence.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.extend.biz.Biz;
import org.activiti.engine.extend.biz.BizField;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.BulkDeleteable;
import org.activiti.engine.impl.db.PersistentObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：BizFieldEntity
 * <p>
 * @className:org.activiti.engine.extend.persistence.entity.BizFieldEntity.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月30日下午2:42:09
 */
public class BizFieldEntity implements BizField, PersistentObject, BulkDeleteable {

	private static final long serialVersionUID = -6335812205475285817L;

	public static final int FIELD_REQUIRED = 1;
	public static final int FIELD_NOT_REQUIRED = 0;
	
	//字段类型
	public static final String TYPE_NUMBER = "number";
	public static final String TYPE_STRING = "string";
	public static final String TYPE_DATE = "date";
	public static final String TYPE_DATETIME = "datetime";
	public static final String TYPE_BOOLEAN = "boolean";
	public static final String TYPE_ENUM = "enum";
	//字段类型
	
	protected String id;
	protected String name;
	protected String label;
	protected String type;
	//如果是boolean或则enum类型的字段，该字段用于存放枚举值
	//以JSON数据格式存储：例如：[{'key':'k1', 'value':'v1'},{'key':'k2', 'value':'v2'}]
	protected String values;
	
	/**
	 * 
	 * <p>方法说明：获取values代表的数据<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月30日下午4:09:14<p>
	 * @return
	 * @throws Exception 
	 */
	public List<KVPair> getValueObjects() throws Exception{
		if(this.valueObjects == null && this.values != null){
			this.valueObjects = new ArrayList<BizFieldEntity.KVPair>();
			if(this.values != null){
				ObjectMapper objectMapper = new ObjectMapper();
				ArrayNode readTree = (ArrayNode) objectMapper.readTree(this.values);
				Iterator<JsonNode> iterator = readTree.iterator();
				while(iterator.hasNext()){
					JsonNode next = iterator.next();
					BizFieldEntity.KVPair pair = new BizFieldEntity.KVPair(next.get("key").asText(), next.get("value").asText());
					valueObjects.add(pair);
				}
			}
		}
		return valueObjects;
	}
	
	protected int required = FIELD_NOT_REQUIRED;
	protected String description;
	protected String bizId;
	
	protected Biz bizEntity;
	protected List<BizFieldEntity.KVPair> valueObjects;
	
	/**
	 * 
	 * <p>方法说明：获取bizEntity<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月30日下午3:14:50<p>
	 * @return
	 */
	public Biz getBizEntity(){
		if(bizEntity == null && bizId != null){
			BizEntityManager bizEntityManager = Context.getCommandContext().getSession(BizEntityManager.class);
			this.bizEntity = bizEntityManager.findBizEntityById(this.bizId);
		}
		return bizEntity;
	}
	
	/* (non-Javadoc)
	 * @see org.activiti.engine.impl.db.PersistentObject#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.impl.db.PersistentObject#getPersistentState()
	 */
	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("name", this.name);
		persistentState.put("label", this.label);
		persistentState.put("type", this.type);
		persistentState.put("values", this.values);
		persistentState.put("required", this.required);
		persistentState.put("bizId", this.bizId);
		persistentState.put("description", this.description);
		return persistentState;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.label;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#getValues()
	 */
	@Override
	public String getValues() {
		return this.values;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#isRequired()
	 */
	@Override
	public boolean isRequired() {
		return this.required == FIELD_REQUIRED;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.extend.biz.BizField#getBizId()
	 */
	@Override
	public String getBizId() {
		return this.bizId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public byte[] getValuesByte(){
		return values == null ? null : values.getBytes();
	}
	
	public void setValuesBytes(byte[] valuesBytes){
		this.values = (valuesBytes == null ? null : new String(valuesBytes));
	}
	
	public void setRequired(int required) {
		this.required = required;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	public void setBizEntity(Biz bizEntity) {
		this.bizEntity = bizEntity;
	}

	public void setValueObjects(List<BizFieldEntity.KVPair> valueObjects) {
		this.valueObjects = valueObjects;
	}

}
