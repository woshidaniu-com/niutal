package org.activiti.engine.extend.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.extend.assignment.Assignment;
import org.activiti.engine.extend.biz.Biz;
import org.activiti.engine.extend.biz.BizField;
import org.activiti.engine.extend.biz.ProcessCategory;
import org.activiti.engine.extend.task.CommonMessageInfo;
import org.activiti.engine.extend.task.Decision;
import org.activiti.engine.extend.task.DecisionType;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：TODO <br>
 * class：org.activiti.engine.extend.service.ExtendService.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public interface ExtendService {

	/**
	 * 
	 * <p>方法说明：流程任务操作人撤销<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月9日上午11:27:13<p>
	 */
	void auditorRevocation(String processInstanceId, String executionId, String taskId);

	/**
	 * 
	 * <p>方法说明：流程任务操作人退回申请人【取消流程，申请人需重新提交流程】<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月27日下午5:01:37<p>
	 */
	void auditorProcessCancellation(String processInstanceId, String taskId, String reason);
	
	/**
	 * 
	 * <p>方法说明：流程发起人撤销<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月9日上午11:27:01<p>
	 */
	void initiatorRevocation(String processInstanceId);
	
	/**
	 * 
	 * <p>方法说明：获取任务人员设置信息<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月23日下午1:35:53<p>
	 */
	List<Assignment> getAssignmentForTask(String processDefinitionId, String taskDefinitionId);
	
	/**
	 * 
	 * <p>方法说明：获取流程所有任务人员设置信息<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月23日下午1:35:53<p>
	 */
	List<Assignment> getAssignmentForProcess(String processDefinitionId);
	
	/**
	 * 
	 * <p>方法说明：设置任务办理人<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月26日上午9:59:07<p>
	 */
	void setupTaskAssignment(String processDefinitionId, List<Assignment> taskAssignment);

	/**
	 * 
	 * <p>方法说明：增加任务办理人<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月26日上午9:59:07<p>
	 */
	void addTaskAssignment(String processDefinitionId, List<Assignment> taskAssignment);
	
	/**
	 * 
	 * <p>方法说明：删除任务办理人<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月26日上午9:59:07<p>
	 */
	void delTaskAssignment(List<Assignment> taskAssignment);
	
	/**
	 * 
	 * <p>方法说明：处理用户任务提交<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月23日下午5:16:31<p>
	 * @param handler
	 */
	void handleTaskSubmit(Decision decision);
	
	/**
	 * 
	 * <p>方法说明：历史任务退回<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月22日上午10:49:52<p>
	 * @param processInstanceId
	 * @param sourceTaskId
	 * @param destTaskId
	 */
	void fallbackTask(String processInstanceId, String sourceTaskId, String destTaskId);
	
	/**
	 * 
	 * <p>方法说明：获取可退回的节点<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月22日下午8:30:37<p>
	 * @param processInstanceId
	 * @param currentTaskId
	 * @return
	 */
	List<HistoricActivityInstance> getFallbackableTaskInfoList(String processInstanceId, String currentTaskId);
	
	/**
	 * 
	 * <p>方法说明：重新激活流程<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日上午11:46:12<p>
	 * @param processInstanceId
	 * @param parameters
	 * @return
	 */
	ProcessInstance activateProcessInstanceById(String processInstanceId, Map<String,Object> variables);
	
	/**
	 * 
	 * <p>方法说明：查询流程发起人<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年3月27日下午1:38:38<p>
	 * @param processInstanceId
	 * @return
	 */
	User getProcessInstanceInitiator(String processInstanceId);
	
	
	/****************************Biz&BizField************************************/
	List<Biz> getBizList();
	List<BizField> getBizFiledList(String bizId);
	/****************************Biz&BizField************************************/

	/****************************CommonMessage************************************/
	List<CommonMessageInfo> getUserCommonMessageList(String userId);
	CommonMessageInfo getCommonMessage(String id);
	void addCommonMessage(String userId, String message);
	void updateCommonMessage(String id, String userId, String message);
	void deleteCommonMessage(String id);
	/****************************CommonMessage************************************/
	
	/****************************ProcessCategory************************************/
	List<ProcessCategory> getProcessCategoryList();
	ProcessCategory getProcessCategory(String id);
	void addProcessCategory(String name, String decription);
	void updateProcessCategory(String id, String name, String description);
	void deleteProcessCategory(String id);
	/****************************ProcessCategory************************************/
	
	/****************************DecisionType************************************/
	//获取配置的decision类型
	List<DecisionType> getDecisionTypes();
	/****************************DecisionType************************************/
	
	/**
	 * 
	 * <p>方法说明：分析流程实例路劲<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年5月10日下午4:55:53<p>
	 * @param processDefintionId
	 * @param variables
	 * @return
	 */
	List<PvmActivity> pathAnalysis(String processDefinitionId, Map<String, Object> variables);
	
	void addSimulationActivities(String processDefinitionId, String businessKey, List<PvmActivity> activities);
	
	void deleteSimulationActivities(String businessKey);
	
}
