package com.woshidaniu.workflow.action;

import java.util.HashMap;

import com.woshidaniu.workflow.html.flow.WorkFlowViewHtmlCreator;
import com.woshidaniu.workflow.model.SpWorkProcedure;
import com.woshidaniu.workflow.service.ISpWorkFlowService;

/** 
 * 
 * 类描述：流程预览
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:34:01
 */
public class SpWorkFlowAction extends WorkFlowBaseAction {

	private static final long serialVersionUID = -2293067248035721056L;
	private ISpWorkFlowService spWorkFlowService;
	private String workId;
	private SpWorkProcedure procedure;
	
	public String detail() throws Exception{
		
		return "detail";
	}
	
	public String info() throws Exception{
		procedure = spWorkFlowService.queryWorkFlowByWorkId(workId);
		if(procedure == null){
			throw new Exception("流程信息未找到或者流程还未开始");
		}else{
			WorkFlowViewHtmlCreator creator = new WorkFlowViewHtmlCreator(procedure);
			String html=creator.html();
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("html", html);
			map.put("success", true);
			getValueStack().set(DATA, map);
		}
		return DATA;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}
	
}
