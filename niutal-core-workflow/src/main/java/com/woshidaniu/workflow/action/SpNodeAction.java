package com.woshidaniu.workflow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.workflow.enumobject.NodeTypeEnum;
import com.woshidaniu.workflow.model.SpNode;
import com.woshidaniu.workflow.model.SpNodeTask;
import com.woshidaniu.workflow.model.SpProcedure;
import com.woshidaniu.workflow.model.SpTask;
import com.woshidaniu.workflow.service.ISpNodeService;
import com.woshidaniu.workflow.service.ISpProcedureService;
import com.woshidaniu.workflow.service.ISpTaskService;

/**
 * 
 * 类描述：流程节点维护
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:33:44
 */
public class SpNodeAction extends WorkFlowBaseAction {
	private static final long serialVersionUID = 6535431275595307300L;
	private ISpProcedureService spProcedureService;
	private ISpNodeService spNodeService;
	private ISpTaskService spTaskService;
	@Resource
	private IYhglService yhglService;

	private SpNode spNode;
	private String op = "add";
	private String[] taskIds;
	private String[] commitBillClassIds;
	private String[] commitClassPrivilege;
	private String[] approveBillClassIds;
	private String[] approveClassPrivilege;
	private String[] taskIsMusts;
	private String[] taskIsAutos;

	private List<JsglModel> getRoleOfModule() {
		SpProcedure spProcedure = spProcedureService.findSpProcedureByPid(
				spNode.getPid(), false);
		YhglModel yhglModel = new YhglModel();
        yhglModel.setZgh(SessionFactory.getUser().getYhm());
		List<JsglModel> jsList = new ArrayList<JsglModel>();
		List<JsglModel> jsglList = yhglService.cxJsdmList(yhglModel);
		
		for (JsglModel js : jsglList) {
			if (spProcedure.getBelongToSys().equals(js.getGnmkdm())
					|| StringUtils.isEmpty(js.getGnmkdm())) {
				jsList.add(js);
			}
		}
		return jsList;
	}

	private List<SpTask> getTaskOfModule() {
		SpProcedure spProcedure = spProcedureService.findSpProcedureByPid(
				spNode.getPid(), false);
		List<SpTask> taskList = new ArrayList<SpTask>();
		for (SpTask spTask : spTaskService.findTaskList(new SpTask())) {
			if (spTask.getBelongToSys().equals(spProcedure.getBelongToSys())) {
				taskList.add(spTask);
			}
		}
		return taskList;
	}

	/**
	 * 请求增加节点
	 * 
	 * @return
	 */
	public String addNode() {
		op = "add";
		this.getValueStack().set("roleList", getRoleOfModule());
		List<SpNodeTask> nodeTasks = new ArrayList<SpNodeTask>();
		List<SpTask> tasks = getTaskOfModule();
		this.initTask(tasks, nodeTasks, op);
		this.getValueStack().set("taskList", nodeTasks);
		this.getValueStack().set("nodeTypes", NodeTypeEnum.values());
		return EDIT_PAGE;
	}

	/**
	 * 请求修改节点
	 * 
	 * @return
	 */
	public String modifyNode() {
		op = "modify";
		spNode = spNodeService.findNodeById(spNode.getNodeId());
		List<SpTask> tasks = getTaskOfModule();
		List<SpNodeTask> nodeTasks = new ArrayList<SpNodeTask>();
		if (spNode != null && spNode.getSpTaskList() != null) {
			this.initTask(tasks, nodeTasks, op);
		}
		this.getValueStack().set("roleList", getRoleOfModule());
		this.getValueStack().set("taskList", nodeTasks);
		this.getValueStack().set("nodeTypes", NodeTypeEnum.values());
		return EDIT_PAGE;
	}

	/*
	 * 
	 * 私有方法描述：共用方法
	 * 
	 * @param:
	 * 
	 * @return:
	 * 
	 * @version: 1.0
	 * 
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * 
	 * @since: 2013-4-23 下午02:22:03
	 */
	private void initTask(List<SpTask> tasks, List<SpNodeTask> nodeTasks,
			String oprate) {
		for (SpTask spTask : tasks) {
			SpNodeTask nodeTask = new SpNodeTask();
			nodeTask.setAuto("Y");
			nodeTask.setNeed("Y");
			nodeTask.setSpNode(spNode);
			nodeTask.setSpTask(spTask);
			nodeTask.setChecked(false);
			if (oprate.equals("modify")) {
				for (SpNodeTask existsTask : spTaskService
						.findNodeTaskListByNodeId(spNode.getNodeId())) {
					if (spTask.getTaskId().equals(
							existsTask.getSpTask().getTaskId())) {
						nodeTask = existsTask;
						nodeTask.setChecked(true);
					}
				}
			}
			nodeTasks.add(nodeTask);
		}
	}

	/**
	 * 保存修改节点
	 * 
	 * @return
	 */
	public String saveNode() {
		if (spNode.getRoleId() == "" || spNode.getRoleId() == null) {
			this.setErrorMessage("角色不能为空!");
			this.getValueStack().set(DATA, this.getMessage());
			return DATA;
		}
		// 设置是否自动执行状态：如果为分支节点则自动执行
		if (spNode.getNodeType().equals(NodeTypeEnum.BRANCH_NODE.getKey())) {
			spNode.setIsAuto("1");
		} else {
			spNode.setIsAuto("0");
		}
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("taskIds", taskIds);
		map.put("commitBillClassIds", commitBillClassIds);
		map.put("approveBillClassIds", approveBillClassIds);
		map.put("commitClassPrivilege", commitClassPrivilege);
		map.put("approveClassPrivilege", approveClassPrivilege);
		map.put("taskIsMusts", taskIsMusts);
		map.put("taskIsAutos", taskIsAutos);
		if (op.equals("add")) {
			spNodeService.insert(spNode, map);
		} else {
			spNodeService.update(spNode, map);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}

	/**
	 * 删除流程节点类型
	 * 
	 * @return
	 */
	public String removeNode() {
		spNodeService.delete(spNode.getNodeId());
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

	public SpNode getSpNode() {
		return spNode;
	}

	public void setSpNode(SpNode spNode) {
		this.spNode = spNode;
	}

	public void setSpNodeService(ISpNodeService spNodeService) {
		this.spNodeService = spNodeService;
	}

	public String[] getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(String[] taskIds) {
		this.taskIds = taskIds;
	}

	public void setSpTaskService(ISpTaskService spTaskService) {
		this.spTaskService = spTaskService;
	}

	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}
	
	public void setSpProcedureService(ISpProcedureService spProcedureService) {
		this.spProcedureService = spProcedureService;
	}

	public String[] getTaskIsMusts() {
		return taskIsMusts;
	}

	public void setTaskIsMusts(String[] taskIsMusts) {
		this.taskIsMusts = taskIsMusts;
	}

	public String[] getTaskIsAutos() {
		return taskIsAutos;
	}

	public void setTaskIsAutos(String[] taskIsAutos) {
		this.taskIsAutos = taskIsAutos;
	}

	/**
	 * @return commitBillClassIds : return the property commitBillClassIds.
	 */

	public String[] getCommitBillClassIds() {
		return commitBillClassIds;
	}

	/**
	 * @param commitBillClassIds
	 *            : set the property commitBillClassIds.
	 */

	public void setCommitBillClassIds(String[] commitBillClassIds) {
		this.commitBillClassIds = commitBillClassIds;
	}

	/**
	 * @return approveBillClassIds : return the property approveBillClassIds.
	 */

	public String[] getApproveBillClassIds() {
		return approveBillClassIds;
	}

	/**
	 * @param approveBillClassIds
	 *            : set the property approveBillClassIds.
	 */

	public void setApproveBillClassIds(String[] approveBillClassIds) {
		this.approveBillClassIds = approveBillClassIds;
	}

	/**
	 * @return commitClassPrivilege : return the property commitClassPrivilege.
	 */

	public String[] getCommitClassPrivilege() {
		return commitClassPrivilege;
	}

	/**
	 * @param commitClassPrivilege
	 *            : set the property commitClassPrivilege.
	 */

	public void setCommitClassPrivilege(String[] commitClassPrivilege) {
		this.commitClassPrivilege = commitClassPrivilege;
	}

	/**
	 * @return approveClassPrivilege : return the property
	 *         approveClassPrivilege.
	 */

	public String[] getApproveClassPrivilege() {
		return approveClassPrivilege;
	}

	/**
	 * @param approveClassPrivilege
	 *            : set the property approveClassPrivilege.
	 */

	public void setApproveClassPrivilege(String[] approveClassPrivilege) {
		this.approveClassPrivilege = approveClassPrivilege;
	}

}
