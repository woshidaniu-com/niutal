package com.woshidaniu.workflow.action;



import java.util.HashMap;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.workflow.enumobject.WorkNodeStatusEnum;
import com.woshidaniu.workflow.model.SpWorkNode;
import com.woshidaniu.workflow.service.ISpWorkFlowService;

public class WorkFlowDemoAction extends BaseAction {

	private static final long serialVersionUID = -7112233682465688074L;
	
	private ISpWorkFlowService workFlowService;
	
	
	public void setWorkFlowService(ISpWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public void submit(){
		
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("businessCode", "HXTY");
		map.put("procedureId", "3260A80E9B6018F2E050007F010028A9");
		map.put("workId", "8888888888888");//流程ID
		
		workFlowService.addSpWorkFlow(map);
	}
	
	public void cancel(){
		workFlowService.doCancelDeclare("8888888888888");
	}
	
	public void audit(){
		SpWorkNode node = new SpWorkNode();
		node.setWid("8888888888888");
		node.setNodeId("3264FFE1D1B099C7E050007F010019EB");
		node.setStatus(WorkNodeStatusEnum.PASS_AUDITING.getKey());
		node.setAuditorId("admin");
		node.setAuditResult("测试通过");
		node.setSuggestion("审核意见啊啊中了");
		
		workFlowService.doAuditingRsult(node, new String[]{"30BF2C020DFBFAFCE050007F01007F3C"}, new String[]{"admin"}, "");
	}
	
	public void cancelAudit(){
		SpWorkNode node = new SpWorkNode();
		node.setWid("8888888888888");
		node.setNodeId("3264FFE1D1B099C7E050007F010019EB");
		node.setStatus(WorkNodeStatusEnum.PASS_AUDITING.getKey());
		node.setAuditorId("admin");
		node.setAuditResult("测试通过");
		node.setSuggestion("审核意见啊啊中了");
		
		workFlowService.doCancelAuditingRsult(node, new String[]{"30BF2C020DFBFAFCE050007F01007F3C"}, new String[]{"admin"});
	}
	
}
