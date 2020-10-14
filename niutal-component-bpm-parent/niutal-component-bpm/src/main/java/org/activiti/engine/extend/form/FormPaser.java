/**
 * 
 */
package org.activiti.engine.extend.form;

import java.io.UnsupportedEncodingException;

import org.activiti.ActivitiFormParseException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.extend.persistence.entity.FormDefinitionEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:org.activiti.engine.extend.form.FormPaser.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月2日下午2:03:44
 */
public class FormPaser {

	public static final String COMPONENTS = "components";
	
	public static final String PAGE = "page";
	
	public static final String NAME = "name";
	
	public static final String KEY = "key";
	
	public static final String DESCRIPTION = "description";
	
	public static final String REVISION = "revision";
	
	public static final String DEFAULT_ENCODING = "utf-8";
	
	protected byte[] source;
	
	protected FormDefinitionEntity formDefinitionEntity;
	
	protected ObjectMapper objectMapper = new ObjectMapper();
	
	
	public FormDefinitionEntity getFormDefinitionEntity(){
		return this.formDefinitionEntity;
	}
	
	public static FormPaser createFormPaserInstance(){
		return new FormPaser();
	}
	
	public FormPaser source(String source){
		if(source != null){
			try {
				this.source = source.getBytes(DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException e) {
				throw new ActivitiIllegalArgumentException("can not parse source");
			}
		}
		return this;
	}
	
	public FormPaser source(String source, String encoding){
		if(source != null){
			try {
				this.source = source.getBytes(encoding == null ? DEFAULT_ENCODING : encoding);
			} catch (UnsupportedEncodingException e) {
				throw new ActivitiIllegalArgumentException("can not parse source");
			}
		}
		return this;
	}
	
	public FormPaser source(byte[] source){
		this.source = source;
		return this;
	}
	
	public void execute(){
		if(this.source != null){
			try {
				JsonNode jsonNode = objectMapper.readTree(source);
				
				String formName = jsonNode.get(NAME).asText();
				String formDesc = jsonNode.get(DESCRIPTION).asText();
				int revision = jsonNode.get(REVISION).asInt();
				String key = jsonNode.get(KEY).asText();
				
				formDefinitionEntity = FormDefinitionEntity.createInstance();
				formDefinitionEntity.setKey(key);
				formDefinitionEntity.setName(formName);
				formDefinitionEntity.setDescription(formDesc);
				formDefinitionEntity.setRevision(revision);
				
				
				//TODO
				//components的解析以后再做
			} catch (Exception e) {
				throw new ActivitiFormParseException("can not read form souce");
			}
		}
	}
	
}
