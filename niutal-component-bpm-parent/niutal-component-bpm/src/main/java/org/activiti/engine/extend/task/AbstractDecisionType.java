/**
 * 
 */
package org.activiti.engine.extend.task;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：PASSDecisionType
 * <p>
 * @className:org.activiti.engine.extend.task.PASSDecisionType.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月6日上午10:10:01
 */
public abstract class AbstractDecisionType implements DecisionType {

	static class KeyValuePair{
		protected String key;
		protected String value;
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
		public KeyValuePair(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		
	};
	
	private static final long serialVersionUID = -7353901879420600416L;
	
	protected String key;
	protected String value;
	
	protected abstract KeyValuePair getInfo();
	/**
	 * 
	 * <p>方法说明：获取key<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日上午10:08:50<p>
	 * @return
	 */
	public String getKey(){
		return getInfo().getKey();
	}

	/**
	 * 
	 * <p>方法说明：获取value<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日上午10:08:57<p>
	 * @return
	 */
	public String getValue(){
		return getInfo().getValue();
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
