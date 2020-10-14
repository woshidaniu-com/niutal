/**
 * 
 */
package org.activiti.engine.extend.task;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:org.activiti.engine.extend.task.NOPASSDecisionType.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月6日上午11:02:33
 */
public class NOPASSDecisionType extends AbstractDecisionType {

	private static final long serialVersionUID = 1492920836336613838L;

	protected static final String DEFAULT_VALUE = "\u4e0d \u901a \u8fc7";//不 通 过
	
	@Override
	protected KeyValuePair getInfo() {
		return new KeyValuePair("NOPASS", this.value == null ? DEFAULT_VALUE : this.value);
	}

	@Override
	public boolean isAutomaticSupported() {
		return true;
	}
	
}
