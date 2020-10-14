/**
 * 
 */
package org.activiti.engine.extend.task;

import java.util.Collection;

import org.activiti.engine.delegate.DelegateTask;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @className:org.activiti.engine.extend.assignment.CandidatesResolver.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月16日下午4:22:14
 */
public interface CandidatesResolver {

	/**
	 * 
	 * <p>方法说明：resolve users<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月16日下午4:25:00<p>
	 * @param delegateTask
	 * @return
	 */
	Collection<String> resolveUsers(DelegateTask delegateTask);
	
	/**
	 * 
	 * <p>方法说明：resolve groups<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月16日下午4:26:47<p>
	 * @param delegateTask
	 * @return
	 */
	Collection<String> resolveGroups(DelegateTask delegateTask);
	
}
