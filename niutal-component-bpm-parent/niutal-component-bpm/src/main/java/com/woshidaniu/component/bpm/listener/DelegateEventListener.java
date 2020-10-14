/**
 * 
 */
package com.woshidaniu.component.bpm.listener;

import java.io.Serializable;

import org.activiti.engine.extend.event.DelegateEventInfo;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：需要业务实现的流程事件监听器
 * <p>
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年3月27日下午6:44:28
 */
public interface DelegateEventListener extends Serializable{
	
	/***************************流 程 实 例 事 件***************************************/
	/**
	 * 
	 * <p>方法说明：流程启动事件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:15:51<p>
	 * 
	 */
	void onProcessInstanceStarted(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：流程结束事件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:33:52<p>
	 */
	void onProcessInstanceEnded(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：流程取消事件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:34:16<p>
	 */
	void onProcessInstanceCancelled(DelegateEventInfo delegateEventInfo);
	
	/***************************流 程 实 例 事 件***************************************/
	
	
	/***************************流 程 任 务 实 例 事 件***************************************/
	/**
	 * 
	 * <p>方法说明：任务被创建事件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:45:48<p>
	 * 
	 */
	void onTaskCreated(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：任务签发事件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:46:45<p>
	 */
	void onTaskAssigned(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：任务完成事件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:47:31<p>
	 */
	void onTaskCompleted(DelegateEventInfo delegateEventInfo);
	/***************************流 程 任 务 实 例 事 件***************************************/
	
	
	
	/***************************撤 销 事 件***************************************/
	/** 
	 * <p>方法说明：当申请人撤消申请【如果已经存在审核记录，不能撤销】<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:15:51<p>
	 */
	
	void onInitiatorRevocation(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：当审核人撤消操作【如果当前撤销操作的下面已经存在审核记录，不能撤销】<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:15:51<p>
	 */
	void onAuditorRevocation(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：当流程结束后，审核人撤消操作<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月8日下午10:15:51<p>
	 */
	void onRebootAuditorRevocation(DelegateEventInfo delegateEventInfo);
	
	/***************************撤 销 事 件***************************************/
	
	
	
	
	/***************************扩 展 事 件***************************************/
	/**
	 * 
	 * <p>方法说明：当任务操作人取消流程是【退回到申请人】<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月27日下午4:42:25<p>
	 * <p>退回到申请人同意并入了退回操作中
	 */
	@Deprecated
	void onAuditorProcessCancellation(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：当流程被退回到发起人，流程挂起等待流程发起人再次激活流程<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午2:53:51<p>
	 * @param delegateEventInfo
	 */
	void onInitiatorSuspension(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：当流程被发起人再次激活【即：流程发起人重新提交被挂起的流程，流程被重新激活】<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午4:39:03<p>
	 * @param delegateEventInfo
	 */
	void onInitiatorActivation(DelegateEventInfo delegateEventInfo);
	
	/**
	 * 
	 * <p>方法说明：当流程退回时【退回到流程中的节点，非流程发起人】<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午3:16:31<p>
	 * @param delegateEventInfo 被退回到的流程节点，不一定是用户任务，还有可能是其他的类型
	 * @param eventObject
	 */
	void onProcessFallback(DelegateEventInfo delegateEventInfo);
	
	/***************************扩 展 事 件***************************************/
	
}
