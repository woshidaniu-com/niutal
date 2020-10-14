/**
 * 
 */
package org.activiti.engine.extend.task;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：decision handler
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月28日下午1:47:25
 */
public interface DecisionHandler {

	/**
	 * 
	 * <p>方法说明：处理decision<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月28日下午2:09:42<p>
	 * @param decisionInfo
	 */
	void handle(DecisionInfo decisionInfo);
	
	/**
	 * 
	 * <p>方法说明：获取该handler处理的decision类型<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月6日上午10:24:57<p>
	 * @return
	 */
	String getType();
	
}
