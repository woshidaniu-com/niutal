/**
 * 
 */
package org.activiti.engine.extend.task;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:org.activiti.engine.extend.task.AuditorRevocationFallbackDecsionType.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月7日下午6:34:11
 */
public class AuditorRevocationFallbackDecsionType extends AbstractDecisionType {

	private static final long serialVersionUID = 4367380069298924561L;

	protected static final String DEFAULT_VALUE = "\u88ab \u64a4 \u9500 \u9000 \u56de";// 被 撤 销 退 回

	@Override
	protected KeyValuePair getInfo() {
		return new KeyValuePair("AUDITOR_REVOCATION_FALLBACK", this.value == null ? DEFAULT_VALUE : this.value);
	}

	@Override
	public boolean isAutomaticSupported() {
		return false;
	}

}
