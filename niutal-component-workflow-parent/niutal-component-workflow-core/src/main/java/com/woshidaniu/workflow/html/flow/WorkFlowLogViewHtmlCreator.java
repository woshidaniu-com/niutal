package com.woshidaniu.workflow.html.flow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.woshidaniu.util.base.StringUtil;
import com.woshidaniu.workflow.enumobject.WorkNodeEStatusEnum;
import com.woshidaniu.workflow.enumobject.WorkNodeStatusEnum;
import com.woshidaniu.workflow.model.SpAuditingLog;
import com.woshidaniu.workflow.model.SpWorkNode;
import com.woshidaniu.workflow.model.SpWorkProcedure;

/** 
 * 
 * 类描述：工作流日志预览创建容器
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:39:17
 */
public class WorkFlowLogViewHtmlCreator {

	private List<SpWorkNode> nodeList;
	private List<SpAuditingLog> logList;
	private StringBuilder sb = new StringBuilder();
	
	private static String HTML_START = "<body><div class='splc1' style='height:auto;'>";
	private static final String HTML_END = "</div></body>";
	public WorkFlowLogViewHtmlCreator(SpWorkProcedure procedure){
		nodeList = procedure.getSpWorkNodeList();
		logList = procedure.getSpAuditingLogList();
	}

	@SuppressWarnings("static-access")
	public String html(){
		sb.append(this.HTML_START);
		createNodeInfo();
		createLogInfo();
		sb.append(this.HTML_END);
		return sb.toString();
	}

	/**
	 * 
	 * 方法描述：创建节点信息
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-8-2 下午03:16:00
	 */
	private void createNodeInfo(){
//		sb.append("<div class='flow-steps flow-steps02'><div class='text'>");
//		for(SpWorkNode node:nodeList){
//			sb.append("<span>"+node.getNodeName()+"</span>");
//		}
//		sb.append("</div><div class='clearall'></div>");
//		sb.append("<ul class='num'>");
//		for(SpWorkNode node:nodeList){
//			if(node.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())){
//				sb.append("<li class='current'></li>");
//			}else{
//				sb.append("<li class='done'></li>");
//			}
//		}
//		sb.append("</ul>");
//		sb.append("</div>");
		
		if(nodeList != null && nodeList.size() > 0){
			sb.append("<div class='step'>");
		    sb.append("<ul>");
	        for (int i=0;i<nodeList.size();i++) {
	            SpWorkNode node = (SpWorkNode) nodeList.get(i);
	            int step = i+1;
	            if (node.getEstatus().equals(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey())) {
	                if(node.getStatus().equals(WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
	                    sb.append("<li class='ysh'><p class='user'>"+node.getNodeName()+"</p><p class='num'>"+step+"</p></li>");
	                }else  if(node.getStatus().equals(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey())) {
	                    sb.append("<li class='yzz'><p class='user'>"+node.getNodeName()+"</p><p class='num'>"+step+"</p></li>");
	                }
	            } else if (node.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())) {
	                sb.append("<li class='shz'><p class='user'>"+node.getNodeName()+"</p><p class='num'>"+step+"</p></li>");
	            }else {
	                sb.append("<li class='wsh'><p class='user'>"+node.getNodeName()+"</p><p class='num'>"+step+"</p></li>");
	            }
	        }
		    sb.append("</ul><div class='text'></div>");
		    sb.append("</div>");
		}else{
			sb.append("<div class='step text-center' style='font-size: 17px;color: rgb(221, 90, 67);'><strong>提示:   </strong>").append("无审核状态信息</div>");
		}
	}

	/**
	 * 
	 * 方法描述：创建日志信息对象
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-8-2 下午03:15:48
	 */
	private void createLogInfo(){
		String timeFormat = "yyyy-MM-dd HH:mm:ss";
		String img = "";
		String span_class_1 = "";
		String span_class_2 = "";
		sb.append("<div class='clearall'></div>");
		int i = 1;
		for(SpAuditingLog log:logList){
			sb.append("<div class='zt1'>");
			if(log.getOstatus().equals(WorkNodeStatusEnum.PASS_AUDITING.getKey())){
				sb.append("<div class='tg' id='"+log.getLogId()+"'>");
				sb.append("<a class='more' href='javascript:;'></a>");
				sb.append("<div class='type'><div class='title'>通过</div></div>");
				img = "knssh_tgnext.gif";
				span_class_1 = "green1";
				span_class_2 = "green";
			}else if(log.getOstatus().equals(WorkNodeStatusEnum.NO_PASS_AUDITING.getKey())){
				sb.append("<div class='wtg' id='"+log.getLogId()+"'>");
				sb.append("<a class='more' href='javascript:;'></a>");
				sb.append("<div class='type'><div class='title'>不通过</div></div>");
				img = "knssh_wtgnext.gif";
				span_class_1 = "red1";
				span_class_2 = "red";
			}else if(log.getOstatus().equals(WorkNodeStatusEnum.CANCEL_AUDITING.getKey())){
				sb.append("<div class='wtg' id='"+log.getLogId()+"'>");
				sb.append("<a class='more' href='javascript:;'></a>");
				sb.append("<div class='type'><div class='title'>撤销</div></div>");
				img = "knssh_wtgnext.gif";
				span_class_1 = "red1";
				span_class_2 = "red";
			}else if(log.getOstatus().equals(WorkNodeStatusEnum.RETURN_AUDITING.getKey())){
				sb.append("<div class='wtg' id='"+log.getLogId()+"'>");
				sb.append("<a class='more' href='javascript:;'></a>");
				sb.append("<div class='type'><div class='title'>退回</div></div>");
				img = "knssh_wtgnext.gif";
				span_class_1 = "red1";
				span_class_2 = "red";
			}
			String shr = log.getOperatorCn()==null?log.getOperator():log.getOperatorCn();
			String shyj = log.getOsuggestion()==null?"":log.getOsuggestion();
			sb.append("<div class='content'><div>审核环节：<span class='"+span_class_1+"'>");
			sb.append(log.getOtype());
			sb.append("</span>审核人：<span class='black1'>");
			sb.append(shr);
			sb.append("</span>审核时间：<span class='black1'>");
			sb.append(this.format(log.getLogTime(),timeFormat));
			sb.append("</span></div>");
			
			if(log.getOstatus().equals(WorkNodeStatusEnum.RETURN_AUDITING.getKey()) && !StringUtil.isNull(log.getrNodeCn())){
				sb.append("<div>退回环节：<span class='"+span_class_2+"'>");
				sb.append(log.getrNodeCn()); 
				sb.append("</span></div>");
			}
			
			sb.append("<div>审核意见：<span class='"+span_class_2+"'>");
			sb.append(shyj); 
			sb.append("</span></div></div><div style='clear:both'></div></div></div>");
			
			if(i != logList.size()){
				sb.append("<div class='next'><img src='<%=stylePath %>/assets/images/"+img+"' /></div>");
			}
			i++;
		}
	}

	/**
     * <p/>
     * 将日期格式化成相应格式的字符串，如：
     * <li>yyyy-MM-dd HH:mm:ss
     * <li>yyyy-MM-dd
     * <li>yyyy/MM/dd HH:mm:ss
     * <li>yyyy/MM/dd
     * </p>
     *
     * @param date
     * @param pattern
     * @return
     */
    private String format(Date date, String pattern) {
        if (date == null) return "";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
