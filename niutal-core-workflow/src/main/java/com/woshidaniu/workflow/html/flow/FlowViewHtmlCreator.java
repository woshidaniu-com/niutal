package com.woshidaniu.workflow.html.flow;

import java.util.List;

import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.workflow.model.SpNode;
import com.woshidaniu.workflow.model.SpProcedure;
import com.woshidaniu.workflow.model.SpTask;

/** 
 * 
 * 类描述：构造流程预览页面
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-5-2 下午01:51:17
 */
public class FlowViewHtmlCreator {

	private SpProcedure procedure;
	private List<SpNode> nodeList;
	private StringBuilder sb = new StringBuilder();
	private SpNode startNode;
	private SpNode endNode;
	
	private static String TASK_EXCUTED_ICO = "pjlc_ic13_green.png";
	
	public FlowViewHtmlCreator(SpProcedure procedure){
		this.procedure = procedure;
		nodeList = procedure.getSpNodeList();
		processNode();
	}
	
	private void processNode(){
		startNode = new SpNode();//创建开始节点图例
		startNode.setNodeName("流程开始");
		
		endNode = new SpNode();//创建结束节点图例
		endNode.setNodeName("流程结束");
	}
	
	public String html(){
		sb.append("<div class=\"awards_process\" style='width:100%'>");
		sb.append("<h3 class=\"awards_process_h3\">"+procedure.getPname()+"</h3>");
		createNodeInfo();
		createBottom();
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 创建节点信息
	 */
	private void createNodeInfo(){
		sb.append("<div class=\"awards_process_inbox\">");
		sb.append("<ul>");
		createNode(startNode,true);
		for(SpNode node:nodeList){
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
	private void createNode(SpNode node,boolean direction){
		String style = " processbox_submited";
		sb.append("<li><div class=\"processbox_submit"+style+"\" style='width:100%'>");
		sb.append("<div style='width:140px;'>");
		sb.append("<span style='height:auto;'>"+node.getNodeName()+"</span>");
		sb.append("</div>");
		createTaskInfo(node.getSpTaskList());
		sb.append("</div>");
		if(direction){
			createDirection(node);
		}
		sb.append("</li>");
	}
	
	private void createDirection(SpNode node){
		String style = " ico_list_submited";
		sb.append("<div class=\"ico_list_submit"+style+"\" style='width:100%'></div>");
	}
	
	private void createTaskInfo(List<SpTask> taskList){
		if(taskList == null){
			return;
		}
		sb.append("<div>");
		for(SpTask task:taskList){
			createTask(task);
		}
		sb.append("</div>");
	}
	
	private void createTask(SpTask task){
		String ico = TASK_EXCUTED_ICO;
		sb.append("<a href='#'>");
		sb.append("<img height=\"24\" wdith=\"24\" src=\""+MessageUtil.getText("system.stylePath")+"/images/"+ico+"\">");
		sb.append(task.getTaskName());
		sb.append("</a>");
	}
	
	private void createBottom(){
		sb.append("<div class=\"over_bt_box\" style='padding:0;background:none;'>");
		sb.append("<button class=\"bt_blue bt_gray\" type='button' onclick='divClose();'>关闭窗口</button>");
		sb.append("</div>");
	}
	
}
