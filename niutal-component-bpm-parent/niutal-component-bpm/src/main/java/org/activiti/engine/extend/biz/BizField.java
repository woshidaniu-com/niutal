/**
 * 
 */
package org.activiti.engine.extend.biz;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：BizField
 * <p>
 * @className:org.activiti.engine.extend.biz.BizFieldInfo.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月30日下午2:24:54
 */
public interface BizField extends Serializable {

	String getId();
	
	String getName();
	
	String getLabel();
	
	String getType();
	
	String getValues();
	
	List<KVPair> getValueObjects() throws Exception;
	
	boolean isRequired();
	
	String getDescription();
	
	String getBizId();
	
	static class KVPair implements Serializable{
		private static final long serialVersionUID = 1015959286543470582L;
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public KVPair(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
}
