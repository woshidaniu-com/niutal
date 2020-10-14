package com.woshidaniu.workflow.html.flow;

import java.util.List;

import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.workflow.enumobject.WorkNodeEStatusEnum;
import com.woshidaniu.workflow.model.SpWorkNode;
import com.woshidaniu.workflow.model.SpWorkProcedure;
import com.woshidaniu.workflow.model.SpWorkTask;

/** 
 * 
 * 类描述：工作流预览创建容器
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:39:17
 */
public class WorkFlowViewHtmlCreator {

	private SpWorkProcedure procedure;
	private List<SpWorkNode> nodeList;
	private StringBuilder sb = new StringBuilder();
	private SpWorkNode startNode;
	private SpWorkNode endNode;
	private SpWorkNode currentNode;
	private static String INITIAL = "0";
	
	private static String TASK_EXCUTED_ICO = "pjlc_ic13_green.png";
	private static String TASK_UNEXCUTED_ICO = "pjlc_ic09_gray.png";
	private static String TASK_WAITED_ICO = "pjlc_ic07_blue.png";
	
	public WorkFlowViewHtmlCreator(SpWorkProcedure procedure){
		this.procedure = procedure;
		nodeList = procedure.getSpWorkNodeList();
		processNode();
	}
	
	private void processNode(){
		startNode = new SpWorkNode();//创建开始节点图例
		startNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
		startNode.setNodeName("流程开始");
		
		endNode = new SpWorkNode();//创建结束节点图例
		endNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
		endNode.setNodeName("流程结束");
		
		SpWorkNode node = nodeList.get(nodeList.size()-1);//设置结束节点状态
		if(!WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
			endNode.setEstatus(INITIAL);
		}
	}
	
	public String html(){
		sb.append("<div class=\"awards_process\" style='width:100%'>");
		sb.append("<h3 class=\"awards_process_h3\">"+procedure.getPname()+"</h3>");
		createTip();
		createNodeInfo();
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 创建状态图例提示
	 */
	private void createTip(){
		sb.append("<div class='check_process' style='width:100%;margin:0 20px'><div class='dirct' style='width:100%'><h6 class='h6_green'><span></span>已完成</h6><h6 class='h6_blue'><span></span>进行中</h6><h6 class='h6_gray'><span></span>未完成</h6></div></div>");
	}
	
	/**
	 * 创建节点信息
	 */
	private void createNodeInfo(){
		sb.append("<div class=\"awards_process_inbox\">");
		sb.append("<ul>");
		createNode(startNode,true);
		for(SpWorkNode node:nodeList){
			createNode(node,true);
		}
		createNode(endNode,false);
		sb.append("</ul>");
		sb.append("</div>");
	}
	
	/**
	 * 生成节点
	 * @param node
	 * @param direction
	 */
	private void createNode(SpWorkNode node,boolean direction){
		String style = " processbox_unsubmit";
		if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(node.getEstatus())){
			style = " processbox_submitting";
		}
		if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
			style = " processbox_submited";
		}
		sb.append("<li><div class=\"processbox_submit"+style+"\" style='width:100%'>");
		sb.append("<div style='width:140px;'>");
		sb.append("<span style='height:auto;'>"+node.getNodeName()+"</span>");
		sb.append("</div>");
		currentNode = node;
		createTaskInfo(node.getSpWorkTaskList());
		sb.append("</div>");
		if(direction){
			createDirection(node);
		}
		sb.append("</li>");
	}
	
	private void createDirection(SpWorkNode node){
		String style = " ico_list_unsubmit";
		if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(node.getEstatus())){
			style = " ico_list_submitting";
		}
		if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(node.getEstatus())){
			style = " ico_list_submited";
		}
		sb.append("<div class=\"ico_list_submit"+style+"\" style='width:100%'></div>");
	}
	
	private void createTaskInfo(List<SpWorkTask> taskList){
		if(taskList == null){
			return;
		}
		sb.append("<div>");
		for(SpWorkTask task:taskList){
			createTask(task);
		}
		sb.append("</div>");
	}
	
	private void createTask(SpWorkTask task){
		String ico = TASK_UNEXCUTED_ICO;
		if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(currentNode.getEstatus())||
				WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(currentNode.getEstatus())){
			if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(task.getEstatus())){
				ico = TASK_WAITED_ICO;
			}
			if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(task.getEstatus())){
				ico = TASK_EXCUTED_ICO;
			}
		}
		sb.append("<a href='#'>");
		sb.append("<img height=\"24\" wdith=\"24\" src=\""+MessageUtil.getText("system.stylePath")+"/images/"+ico+"\">");
		sb.append(task.getTaskName());
		sb.append("</a>");
	}	
}
