package com.woshidaniu.workflow.action;

import java.util.HashMap;

import com.woshidaniu.workflow.enumobject.PtypeEnum;
import com.woshidaniu.workflow.enumobject.StatusEnum;
import com.woshidaniu.workflow.html.flow.FlowViewHtmlCreator;
import com.woshidaniu.workflow.model.SpProcedure;
import com.woshidaniu.workflow.service.ISpProcedureService;

/**
 * 
 * 类描述：流程维护
 * 
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:20:48
 */
public class SpProcedureAction extends WorkFlowBaseAction {
	private static final long serialVersionUID = 6535431275595307300L;
	private static final String CONFIG_PAGE = "config";
	private ISpProcedureService spProcedureService;
//	private IMenuService menuService;
	private SpProcedure spProcedure;
	private SpProcedure query;
	private String pid;
	private String op = "add";

	private String[] commitBillIds;
	private String[] approveBillIds;

	/**
	 * 获取流程列表
	 * 
	 * @return
	 */
	public String list() {
		this.getValueStack().set("procedureList",
				spProcedureService.findSpProcedureList(new SpProcedure()));
		return LIST_PAGE;
	}

	/**
	 * 请求增加
	 * 
	 * @return
	 */
	public String addProcedure() {
		op = "add";
		spProcedure = new SpProcedure();
		spProcedure.setPstatus(StatusEnum.INVALID_STATUS.getKey());
		this.getValueStack().set("ptypes", PtypeEnum.values());
//		this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
		return EDIT_PAGE;
	}

	/**
	 * 请求修改
	 * 
	 * @return
	 */
	public String modifyProcedure() {
		op = "modify";
		spProcedure = spProcedureService.findSpProcedureByPid(query.getPid(),
				false);
//		this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
		this.getValueStack().set("ptypes", PtypeEnum.values());

		return EDIT_PAGE;
	}

	/**
	 * 保存修改
	 * 
	 * @return
	 */
	public String saveProcedure() {
		if (op.equals("add")) {
			spProcedureService.insert(spProcedure, commitBillIds,
					approveBillIds);
		} else {
			spProcedureService.update(spProcedure, commitBillIds,
					approveBillIds);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}

	/**
	 * 删除流程类型
	 * 
	 * @return
	 */
	public String removeProcedure() {
		spProcedureService.delete(query.getPid());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}

	public String enabledProcedure() {
		spProcedure = spProcedureService.findSpProcedureByPid(query.getPid(),
				false);
		spProcedure.setPstatus(StatusEnum.VALID_STATUS.getKey());
		spProcedureService.update(spProcedure);
		this.setSuccessMessage("设置成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}

	public String disabledProcedure() {
		spProcedure = spProcedureService.findSpProcedureByPid(query.getPid(),
				false);
		spProcedure.setPstatus(StatusEnum.INVALID_STATUS.getKey());
		spProcedureService.update(spProcedure);
		this.setSuccessMessage("设置成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}

	public String configProcedure() {
		spProcedure = spProcedureService.findSpProcedureByPid(query.getPid(),
				false);
		return CONFIG_PAGE;
	}

	/**
	 * 
	 * 方法描述：流程预览
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-2 下午01:04:37
	 */
	public String preview() {
		spProcedure = spProcedureService.findSpProcedureByPid(pid, true);
		if (spProcedure == null) {
//			throw new RuleException("流程信息未找到！");
		} else {
			FlowViewHtmlCreator creator = new FlowViewHtmlCreator(spProcedure);
			String html = creator.html();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("html", html);
			map.put("success", true);
			getValueStack().set(DATA, map);
		}
		return DATA;
	}

	public String detail() throws Exception {

		return "detail";
	}

	public SpProcedure getSpProcedure() {
		return spProcedure;
	}

	public void setSpProcedure(SpProcedure spProcedure) {
		this.spProcedure = spProcedure;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public void setSpProcedureService(ISpProcedureService spProcedureService) {
		this.spProcedureService = spProcedureService;
	}

	public SpProcedure getQuery() {
		return query;
	}

	public void setQuery(SpProcedure query) {
		this.query = query;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

//	public void setMenuService(IMenuService menuService) {
//		this.menuService = menuService;
//	}

	/**
	 * @return commitBillIds : return the property commitBillIds.
	 */

	public String[] getCommitBillIds() {
		return commitBillIds;
	}

	/**
	 * @param commitBillIds
	 *            : set the property commitBillIds.
	 */

	public void setCommitBillIds(String[] commitBillIds) {
		this.commitBillIds = commitBillIds;
	}

	/**
	 * @return approveBillIds : return the property approveBillIds.
	 */

	public String[] getApproveBillIds() {
		return approveBillIds;
	}

	/**
	 * @param approveBillIds
	 *            : set the property approveBillIds.
	 */

	public void setApproveBillIds(String[] approveBillIds) {
		this.approveBillIds = approveBillIds;
	}
}
