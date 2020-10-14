package com.woshidaniu.workflow.action;

import com.woshidaniu.workflow.model.SpLine;
import com.woshidaniu.workflow.model.SpNode;
import com.woshidaniu.workflow.service.ISpLineService;
import com.woshidaniu.workflow.service.ISpNodeService;

/**
 * 
 * 类描述：流程走向维护
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:33:31
 */
public class SpLineAction extends WorkFlowBaseAction {
	private static final long serialVersionUID = 6535431275595307300L;
	
	private ISpLineService spLineService;
	private ISpNodeService spNodeService;
	private SpLine spLine;
	private String op="add";
	/**
	 * 请求增加走向
	 * @return
	 */
	public String addLine(){
		op="add";
		SpNode spNode=new SpNode();
		spNode.setPid(spLine.getPid());
		this.getValueStack().set("nodeList", spNodeService.findNodeList(spNode));
		return EDIT_PAGE;
	}
	/**
	 * 请求修改走向
	 * @return
	 */
	public String modifyLine(){
		op="modify";
		spLine=spLineService.findLineListById(spLine.getLineId());
		SpNode spNode=new SpNode();
		spNode.setPid(spLine.getPid());
		this.getValueStack().set("nodeList", spNodeService.findNodeList(spNode));
		return EDIT_PAGE;
	}
	/**
	 * 保存修改走向
	 * @return
	 */
	public String saveLine(){
		if(op.equals("add")){
			spLineService.insert(spLine);
		}else{
			spLineService.update(spLine);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * 删除流程走向类型
	 * @return
	 */
	public String removeLine(){
		spLineService.delete(spLine.getLineId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public SpLine getSpLine() {
		return spLine;
	}
	public void setSpLine(SpLine spLine) {
		this.spLine = spLine;
	}
	public void setSpLineService(ISpLineService spLineService) {
		this.spLineService = spLineService;
	}
	public void setSpNodeService(ISpNodeService spNodeService) {
		this.spNodeService = spNodeService;
	}
	
	
}
