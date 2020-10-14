package com.woshidaniu.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.workflow.enumobject.BusinessEnum;
import com.woshidaniu.workflow.enumobject.NodeTypeEnum;
import com.woshidaniu.workflow.enumobject.TaskIsMustEnum;
import com.woshidaniu.workflow.enumobject.TaskNameEnum;
import com.woshidaniu.workflow.enumobject.WorkNodeEStatusEnum;
import com.woshidaniu.workflow.enumobject.WorkNodeStatusEnum;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.html.flow.WorkFlowLogViewHtmlCreator;
import com.woshidaniu.workflow.html.flow.WorkFlowViewHtmlCreator;
import com.woshidaniu.workflow.model.PendingAffairInfo;
import com.woshidaniu.workflow.model.SpAuditingLog;
import com.woshidaniu.workflow.model.SpLine;
import com.woshidaniu.workflow.model.SpNode;
import com.woshidaniu.workflow.model.SpProcedure;
import com.woshidaniu.workflow.model.SpTask;
import com.woshidaniu.workflow.model.SpWorkLine;
import com.woshidaniu.workflow.model.SpWorkNode;
import com.woshidaniu.workflow.model.SpWorkProcedure;
import com.woshidaniu.workflow.model.SpWorkTask;
import com.woshidaniu.workflow.model.query.WorkAuditingQuery;
import com.woshidaniu.workflow.model.query.WorkTaskQuery;
import com.woshidaniu.workflow.model.result.BaseResult;
import com.woshidaniu.workflow.model.result.NodeListSqlResult;
import com.woshidaniu.workflow.model.result.TaskListSqlResult;
import com.woshidaniu.workflow.service.IPendingAffairService;
import com.woshidaniu.workflow.service.ISpAuditingLogService;
import com.woshidaniu.workflow.service.ISpProcedureService;
import com.woshidaniu.workflow.service.ISpWorkFlowService;
import com.woshidaniu.workflow.service.ISpWorkLineService;
import com.woshidaniu.workflow.service.ISpWorkNodeService;
import com.woshidaniu.workflow.service.ISpWorkProcedureService;
import com.woshidaniu.workflow.service.ISpWorkTaskService;

/**
 * 
 * 类描述：工作审批管理实现类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 上午11:30:17
 */
@Service("spWorkFlowService")
public class SpWorkFlowServiceImpl extends BaseInterfaceServiceImpl implements ISpWorkFlowService {

	/* @model: 注入 */
	public ISpProcedureService spProcedureService;
	public ISpWorkProcedureService spWorkProcedureService;
	public ISpWorkNodeService spWorkNodeService;
	public ISpWorkTaskService spWorkTaskService;
	public ISpWorkLineService spWorkLineService;
	public ISpAuditingLogService spAuditingLogService;
	public IPendingAffairService pendingAffairService;

	public SpProcedure findSpProcedureByBCode(String bCode) {
	    return spProcedureService.findSpProcedureByBCode(bCode);
	}
	
	@Override
	public BaseResult addSpWorkFlow(HashMap<String, String> map) throws WorkFlowException {
		BaseResult result = new BaseResult();
		try {
			String businessCode = map.get("businessCode");
			String procedureId = map.get("procedureId");
			String workId = map.get("workId");
			if (StringUtils.isEmpty(businessCode) && StringUtils.isEmpty(procedureId)) {
				throw new WorkFlowException("异常：业务编码和流程ID不能都为空！");
			}
			if (StringUtils.isEmpty(workId)) {
				throw new WorkFlowException("异常：工作ID不能为空！");
			}

			// 清楚垃圾数据
			spWorkProcedureService.removeSpWorkProcedureByWid(workId);

			// 添加工作审核流程对象
			SpProcedure spProcedure = null;
			if(StringUtils.isNotEmpty(businessCode) && StringUtils.isEmpty(procedureId)){
				spProcedure = spProcedureService.findSpProcedureByBCode(businessCode);
			}else if(StringUtils.isEmpty(businessCode) && StringUtils.isNotEmpty(procedureId)){
				spProcedure = spProcedureService.findSpProcedureByPid(procedureId, true);
			}else if(StringUtils.isNotEmpty(businessCode) && StringUtils.isNotEmpty(procedureId)){
				spProcedure = spProcedureService.findSpProcedureByPid(procedureId, true);
			}
			if (spProcedure == null) {
				throw new WorkFlowException("异常：根据业务编码获取到的流程对象为空！");
			}
			SpWorkProcedure spWorkProcedure = SpWorkProcedure.putProcedureObject(spProcedure);
			spWorkProcedure.setWid(workId);
			spWorkProcedure.setBcode(businessCode);
			spWorkProcedureService.addSpWorkProcedure(spWorkProcedure);

			// 添加工作审核节点对象
			List<SpNode> spNodeList = spProcedure.getSpNodeList();
			if (spNodeList != null && spNodeList.size() > 0) {
				for (SpNode spNode : spNodeList) {
					if (spNode != null) {
						SpWorkNode spWorkNode = SpWorkNode.putNodeObject(spNode);
						spWorkNode.setWid(workId);
						spWorkNode.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
						// 如果是第一个节点就默认打开待执行状态
						if (spNode.getNodeType().equals(NodeTypeEnum.START_NODE.getKey())) {
							spWorkNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
							// 添加代办事宜
							this.addPendingAffairInfo(spWorkNode);
						} else {
							spWorkNode.setEstatus(WorkNodeEStatusEnum.CLOSE.getKey());
						}
						spWorkNodeService.addSpWorkNode(spWorkNode);
					}

					// 添加工作审核任务对象
					List<SpTask> spTaskList = spNode.getSpTaskList();
					if (spTaskList != null && spTaskList.size() > 0) {
						for (SpTask spTask : spTaskList) {
							if (spTask != null) {
								SpWorkTask spWorkTask = SpWorkTask.putTaskObject(spTask);
								spWorkTask.setWid(workId);
								spWorkTask.setNodeId(spNode.getNodeId());
								spWorkTaskService.addSpWorkTask(spWorkTask);
							}
						}
					}
				}
			} else {
				throw new WorkFlowException("异常：根据流程ID获取到的节点集合对象为空！");
			}

			// 添加工作审核节点连线对象
			List<SpLine> spLineList = spProcedure.getSpLineList();
			if (spLineList != null && spLineList.size() > 0) {
				for (SpLine spLine : spLineList) {
					SpWorkLine spWorkLine = SpWorkLine.putLineObject(spLine);
					spWorkLine.setWid(workId);
					spWorkLineService.addSpWorkLine(spWorkLine);
				}
			}

			result.setWorkId(workId);
			result.setWorkStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#doCancelDeclare(java.lang
	 * .String)
	 */

	@Override
	public boolean doCancelDeclare(String workId) throws WorkFlowException {
		try {
			if (StringUtils.isNotEmpty(workId)) {
				// 如果存在已执行的节点或任务，不允许取消
				SpWorkNode spWorkNode = new SpWorkNode();
				spWorkNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
				spWorkNode.setWid(workId);
				List<SpWorkNode> nodeList = spWorkNodeService.findWorkNodeList(spWorkNode);

//				SpWorkTask spWorkTask = new SpWorkTask();
//				spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
//				List<SpWorkTask> taskList = spWorkTaskService.findWorkTaskList(spWorkTask);
//				|| null != taskList || taskList.size() > 0

				if (nodeList != null && nodeList.size() > 0) {
					throw new WorkFlowException("异常：流程已开始，不能取消！");
				} else {
					spWorkProcedureService.removeSpWorkProcedureByWid(workId);
					// 删除代办事宜
					pendingAffairService.removePendingAffairInfoByBid(workId);
					return true;
				}
			} else {
				throw new WorkFlowException("异常：工作ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<SpProcedure> findSpProcedureListByBCode(String bCode)
			throws WorkFlowException {
		List<SpProcedure> list = null;
		try {
			if(StringUtils.isEmpty(bCode)){
				throw new WorkFlowException("异常：业务编码不能为空！");
			}
			list = spProcedureService.findSpProcedureListByBCode(bCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#queryWorkFlowListSql(com
	 * .woshidaniu.workflow.model.query.WorkAuditingQuery)
	 */
	@Override
	public NodeListSqlResult queryWorkFlowListSql(WorkAuditingQuery query) throws WorkFlowException {
		NodeListSqlResult result = new NodeListSqlResult();
		try {
			//老版本走老路
			if (query != null && query.getVersion() == 1) {
				String sqlContent = NodeListSqlResult.getSqlStartPart();
				String roleIdSql = "", statusSql = "", bCodeSql = "", bTypeSql = "";

				if (StringUtils.isEmpty(query.getBusinessCode()) && StringUtils.isEmpty(query.getBusinessType())) {
					throw new WorkFlowException("异常：业务类型和业务ID必须有一个不能为空！");
				} else {
					if (StringUtils.isNotEmpty(query.getBusinessCode()) && StringUtils.isEmpty(query.getBusinessType())) {
						bCodeSql = " AND a.b_code IN ('" + query.getBusinessCode() + "')) ";
					}
					if (StringUtils.isNotEmpty(query.getBusinessType()) && StringUtils.isEmpty(query.getBusinessCode())) {
						bTypeSql = " AND a.b_type = '" + query.getBusinessType() + "') ";
					}
					if (StringUtils.isNotEmpty(query.getBusinessType())
							&& StringUtils.isNotEmpty(query.getBusinessCode())) {
						bCodeSql = " AND a.b_code IN ('" + query.getBusinessCode() + "') ";
						bTypeSql = " AND a.b_type = '" + query.getBusinessType() + "') ";
					}
				}
				if (query.getRoleIdArray() == null && query.getUserIdArray() == null) {
					throw new WorkFlowException("异常：角色ID和用户ID不能都为空！");
				} else {
					if((query.getRoleIdArray() != null)&&(query.getUserIdArray() != null)){
						String roleScopeSql = "ROLE_ID IN (" + this.handleString(query.getRoleIdArray()) + ") ";
						String userScopeSql = "1 = (select array_compare(USER_ID,'" + this.handleMarkString(query.getUserIdArray()) + "') from dual) ";
						roleIdSql = "and ((" + roleScopeSql + ") or (" + userScopeSql + "))";
					}else if((query.getRoleIdArray() != null)){
						roleIdSql = " AND ROLE_ID IN (" + this.handleString(query.getRoleIdArray()) + ") ";
						//如果改成和user_id一致，则需要执行1个脚本，生成1个type，2个function
					}else if((query.getUserIdArray() != null)){
						roleIdSql = " AND 1 = (select array_compare(USER_ID,'" + this.handleMarkString(query.getUserIdArray()) + "') from dual) ";
					}
				}
				
				//审核状态为空 则默认查询待审核
				String[] status = query.getStatus();
				if (BlankUtils.isBlank(status)||status.length==0) {
					//则默认查全部
					statusSql = " AND ((STATUS = '" + WorkNodeStatusEnum.WAIT_AUDITING.getKey() + "' AND E_STATUS = '" 
									+ WorkNodeEStatusEnum.WAIT_EXECUTE.getKey() + "') or (STATUS != '" 
									+ WorkNodeStatusEnum.WAIT_AUDITING.getKey() + 
									"' AND E_STATUS = '" + WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey() + "')) ";
				}else{
					statusSql += " and ( ";
					for(int i=0;i<status.length;i++){
						if(status[i].equals(WorkNodeStatusEnum.WAIT_AUDITING.getKey())){
							//待审核
							statusSql += " (STATUS = '" + status[i] + "' AND E_STATUS = '" 
											+ WorkNodeEStatusEnum.WAIT_EXECUTE.getKey() + "' ) ";
						}else{
							statusSql += " (STATUS = '" + status[i] + 
									"' AND E_STATUS = '" + WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey() + "' ) ";
						}
						if(i != status.length-1){
							statusSql += " or ";
						}
					}
					statusSql += " ) ";					
				}
				
				sqlContent = sqlContent.concat(bCodeSql).concat(bTypeSql).concat(roleIdSql).concat(statusSql);
				result.setSqlContent(sqlContent);
			//新版本走更好的路
			}else if(query != null && query.getVersion() == 2){
				//审核状态
				//'WAIT_EXECUTE' 待处理=待审核
				//'ALREADY_EXECUTE' 已处理={通过，不通过，退回}
				String[] status = query.getStatus();
				
				if(BlankUtils.isBlank(status) || status.length == 0){
					throw new WorkFlowException("异常：查询审核状态参数为空！");
				}
				
				String roleIdSql = "", statusSql = "", bCodeSql = "", bTypeSql = "";
				if (StringUtils.isEmpty(query.getBusinessCode()) && StringUtils.isEmpty(query.getBusinessType())) {
					throw new WorkFlowException("异常：业务类型和业务ID必须有一个不能为空！");
				} else {
					if (StringUtils.isNotEmpty(query.getBusinessCode()) && StringUtils.isEmpty(query.getBusinessType())) {
						bCodeSql = " AND a.b_code IN ('" + query.getBusinessCode() + "')) ";
					}
					if (StringUtils.isNotEmpty(query.getBusinessType()) && StringUtils.isEmpty(query.getBusinessCode())) {
						bTypeSql = " AND a.b_type = '" + query.getBusinessType() + "') ";
					}
					if (StringUtils.isNotEmpty(query.getBusinessType())
							&& StringUtils.isNotEmpty(query.getBusinessCode())) {
						bCodeSql = " AND a.b_code IN ('" + query.getBusinessCode() + "') ";
						bTypeSql = " AND a.b_type = '" + query.getBusinessType() + "') ";
					}
				}
				if (query.getRoleIdArray() == null && query.getUserIdArray() == null) {
					throw new WorkFlowException("异常：角色ID和用户ID不能都为空！");
				} else {
					if((query.getRoleIdArray() != null)&&(query.getUserIdArray() != null)){
						String roleScopeSql = "x.ROLE_ID IN (" + this.handleString(query.getRoleIdArray()) + ") ";
						String userScopeSql = "1 = (select array_compare(x.USER_ID,'" + this.handleMarkString(query.getUserIdArray()) + "') from dual) ";
						roleIdSql = "and ((" + roleScopeSql + ") or (" + userScopeSql + "))";
					}else if((query.getRoleIdArray() != null)){
						roleIdSql = " AND x.ROLE_ID IN (" + this.handleString(query.getRoleIdArray()) + ") ";
						//如果改成和user_id一致，则需要执行1个脚本，生成1个type，2个function
					}else if((query.getUserIdArray() != null)){
						roleIdSql = " AND 1 = (select array_compare(x.USER_ID,'" + this.handleMarkString(query.getUserIdArray()) + "') from dual) ";
					}
				}
				
				StringBuffer sqlContent = new StringBuffer();
				//查询待处理数据
				if(StringUtils.equals(status[0], WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())){
					sqlContent.append(NodeListSqlResult.getSqlStartPartWithAlisa());
					statusSql += " and ( ";
					statusSql += " (STATUS = '" + WorkNodeStatusEnum.WAIT_AUDITING.getKey() + "' AND E_STATUS = '" 
									+ WorkNodeEStatusEnum.WAIT_EXECUTE.getKey() + "' ) ";
					statusSql += " ) ";
					sqlContent.append(bCodeSql).append(bTypeSql).append(roleIdSql).append(statusSql);
				//查询已处理数据
				}else if(StringUtils.equals(status[0], WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey())){
					String notExistInSpWorkNode = " AND NOT EXISTS (SELECT 1 FROM SP_WORK_NODE n WHERE n.W_ID = x.W_ID AND n.P_ID = x.P_ID AND n.NODE_ID = x.NODE_ID AND n.E_STATUS = '" + WorkNodeEStatusEnum.WAIT_EXECUTE + "' AND n.STATUS = '" + WorkNodeStatusEnum.WAIT_AUDITING + "') ";
					sqlContent.append(NodeListSqlResult.getSqlStartPartReturnAuditing());
					sqlContent.append(bCodeSql).append(bTypeSql).append(roleIdSql).append(notExistInSpWorkNode);
				}
				result.setSqlContent(sqlContent.toString());
			//没有版本走投无路
			} else {
				throw new WorkFlowException("异常：查询对象为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#queryWorkFlowByWorkId(
	 * java.lang.String)
	 */

	@Override
	public SpWorkProcedure queryWorkFlowByWorkId(String workId) throws WorkFlowException {
		try {
			if (StringUtils.isNotEmpty(workId)) {
				return spWorkProcedureService.findWorkProcedureByWId(workId);
			} else {
				throw new WorkFlowException("异常：工作ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpWorkProcedure queryWorkFlowByWorkIdAndRoleId(String workId, String[] roleIdArray, String[] userIdArray) throws WorkFlowException {
		SpWorkProcedure spWorkProcedure = null;
		try {
			if ((roleIdArray == null || roleIdArray.length == 0)
					&& (userIdArray == null || userIdArray.length == 0)) {
				throw new WorkFlowException("异常：角色和用户数组都为空！");
			}
			if (StringUtils.isEmpty(workId)) {
				throw new WorkFlowException("异常：工作ID为空！");
			}
			List<SpWorkNode> nodeList = new ArrayList<SpWorkNode>();
			spWorkProcedure = spWorkProcedureService.findWorkProcedureByWId(workId);
			// 通过角色设置节点是否可以编辑
			if ((roleIdArray != null || userIdArray != null) && spWorkProcedure != null 
					&& spWorkProcedure.getSpWorkNodeList() != null
					&& spWorkProcedure.getSpWorkNodeList().size() > 0) {
				for (SpWorkNode spWorkNode : spWorkProcedure.getSpWorkNodeList()) {
					if (spWorkNode.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())
							&& (isExists(roleIdArray,spWorkNode.getRoleId()) || isExists(userIdArray,spWorkNode.getUserId()))) {
						spWorkNode.setEdit(true);
					} else {
						spWorkNode.setEdit(false);
					}
					nodeList.add(spWorkNode);
				}
				spWorkProcedure.setSpWorkNodeList(nodeList);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return spWorkProcedure;
	}

	private boolean isExists(String[] roles, String role){
		boolean bol = true;
		if(roles != null && roles.length > 0 && StringUtils.isNotEmpty(role)){
			for(int i = 0; i < roles.length; i++){
				if(role.equals(roles[i])){
					bol = true;
					break;
				}
				bol = false;
			}
		}else{
			bol = false;
		}		
		return bol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#doAuditingRsult(com.woshidaniu
	 * .workflow.model.SpWorkNode)
	 */

	@Override
	public BaseResult doAuditingRsult(SpWorkNode spWorkNode, String[] roleIdArray, String[] userIdArray, String returnNodeId)
			throws WorkFlowException {
		BaseResult result = new BaseResult();
		try {
			if (spWorkNode != null) {
				if (StringUtils.isEmpty(spWorkNode.getNodeId())) {
					throw new WorkFlowException("异常：节点ID为空！");
				}
				if (StringUtils.isEmpty(spWorkNode.getWid())) {
					throw new WorkFlowException("异常：工作ID为空！");
				}
				if (StringUtils.isEmpty(spWorkNode.getStatus())) {
					throw new WorkFlowException("异常：操作类型为空！");
				}
				/*审核通过允许为空
				if (StringUtils.isEmpty(spWorkNode.getSuggestion())) {
					throw new WorkFlowException("异常：审核意见为空！");
				}
				*/
				if (StringUtils.isEmpty(spWorkNode.getAuditorId())) {
					throw new WorkFlowException("异常：操作人为空！");
				}
				if (StringUtils.isEmpty(spWorkNode.getRoleId()) 
						|| StringUtils.isEmpty(spWorkNode.getUserId())) {
					SpWorkNode node = new SpWorkNode();
					node.setWid(spWorkNode.getWid());
					node.setNodeId(spWorkNode.getNodeId());
					SpWorkNode newNode = spWorkNodeService.findWorkNodeByWidAndNodeId(node);
					if(newNode != null){
						if(StringUtils.isEmpty(newNode.getRoleId()) && StringUtils.isEmpty(newNode.getUserId())){
							throw new WorkFlowException("异常：节点操作角色或用户都为空！");
						}else{
							spWorkNode.setRoleId(newNode.getRoleId());
							spWorkNode.setUserId(newNode.getUserId());
						}
						
					}else{
						throw new WorkFlowException("异常：找不到节点对象！");
					}					
				} else {
					if (!(isExists(roleIdArray,spWorkNode.getRoleId()) 
							|| isExists(userIdArray,spWorkNode.getUserId()))) {
						throw new WorkFlowException("异常：不具备此节点的角色或用户操作权限！");
					}
				}
				
				//保证当前节点执行状态为“WAIT_EXECUTE”，避免出现上级节点撤消脏数据的情况
				SpWorkNode node = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
				
				if (!WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(node.getEstatus())){
					throw new WorkFlowException("异常：流程数据已被其它用户处理，不能进行此操作！");
				}

				if (spWorkNode.getStatus().equals(// 保存
						WorkNodeStatusEnum.IN_AUDITING.getKey())) {
					// 保存审核信息及任务信息，纯洁的保持，不记录操作日志
					this.saveSpWorkNode(spWorkNode, false, false);
					this.saveSpWorkTask(spWorkNode, false);

				} else if (spWorkNode.getStatus().equals(// 审核通过
						WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
					
					// 保存审核信息及任务信息，并记录操作日志；同时把任务执行状态置为“已执行”
					spWorkNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					this.saveSpWorkNode(spWorkNode, true, true);

					// 执行节点中的任务
					this.saveSpWorkTask(spWorkNode, true);
					
					/*
					 * 判断节点所有任务执行状态是否为“已执行”， 如是则改当前节点执行状态为“已执行”，下一结点执行状态为“待执行”
					 * 只需判断本次操作节点提交的工作任务以外的任务状态是否都为“已执行”，
					 * 若是，节点执行状态为“已执行”与审核信息同时作更新，并同时更新下一节点的执行状态为“待执行”
					 */
					SpWorkTask wt = new SpWorkTask();
					wt.setIsMust(TaskIsMustEnum.NO.getKey());
					wt.setWid(spWorkNode.getWid());
					wt.setNodeId(spWorkNode.getNodeId());
					wt.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					int cou = spWorkTaskService.countWorkTaskForNonExecute(wt);
					if (cou < 1) {						
						// 当前非结束节点，更新下一节点执行状态
						SpWorkNode spWorkNode2 = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
						changeNextNodeStatus(spWorkNode2);
						/*
						 * if (!NodeTypeEnum.END_NODE.getKey().equalsIgnoreCase(
						 * spWorkNode.getNodeType())) { SpWorkLine spWorkLine =
						 * new SpWorkLine();
						 * spWorkLine.setWid(spWorkNode.getWid());
						 * spWorkLine.setUnodeId(spWorkNode.getNodeId());
						 * List<SpWorkLine> spWorkLineList = spWorkLineService
						 * .findWorkLineList(spWorkLine); if (null !=
						 * spWorkLineList && spWorkLineList.size() > 0) { //
						 * 根据连线，全部下一节点置为“待执行” for (SpWorkLine tempSpWorkLine :
						 * spWorkLineList) { // spWorkLine =
						 * spWorkLineList.get(0); SpWorkNode nextNode = new
						 * SpWorkNode();
						 * nextNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE
						 * .getKey()); nextNode.setWid(spWorkNode.getWid());
						 * nextNode.setNodeId(tempSpWorkLine .getDnodeId());
						 * spWorkNodeService.editSpWorkNode(nextNode); } } }
						 */
					}

				} else if (spWorkNode.getStatus().equals(// 审核不通过
						WorkNodeStatusEnum.NO_PASS_AUDITING.getKey())) {
					/*
					 * 保存审核信息及任务信息（含审核状态为不通过等），并记录操作日志； 记录操作日志；
					 * 无论相关必须执行的任务是否都已经执行，节点执行状态置为“已执行”， 但下一节点执行状态不改变，流程停止流转
					 */
					spWorkNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					this.saveSpWorkNode(spWorkNode, true, true);
					this.saveSpWorkTask(spWorkNode, true);

				} else if (spWorkNode.getStatus().equals(// 如果是退回操作，必须判断退回节点ID是否为空
						WorkNodeStatusEnum.RETURN_AUDITING.getKey())) {
					
					spWorkNode.setReturnNodeId(returnNodeId);//为了记录日志
					
					if (StringUtils.isEmpty(returnNodeId)) {
						throw new WorkFlowException("异常：退回节点ID为空！");
					}
					// 如果是退回申报人则删除整个工作审批实例
					else if("-1".equals(returnNodeId)){
						
						if (StringUtils.isNotEmpty(spWorkNode.getWid())) {
							// 记录操作日志
							spWorkNodeService.insertNodeLog(spWorkNode);
							
							//删除审批流工作实例
							spWorkProcedureService.removeSpWorkProcedureByWid(spWorkNode.getWid());
							// 删除代办事宜
							pendingAffairService.removePendingAffairInfoByBid(spWorkNode.getWid());
							
							
						}else{
							throw new WorkFlowException("异常：工作ID为空！");
						}
						
						return result;
					}
//					if (NodeTypeEnum.START_NODE.getKey().equalsIgnoreCase(spWorkNode.getNodeType())) {
//						throw new WorkFlowException("异常：已是开始节点！");
//					}

					//如果是退回本节点，则认为是撤销审核操作，日志中操作状态记录撤销操作类型
//					if(returnNodeId.equals(spWorkNode.getNodeId())){
//						spWorkNode.setStatus(WorkNodeStatusEnum.CANCEL_AUDITING.getKey());
//					}
					spWorkNodeService.insertNodeLog(spWorkNode);// 记录操作日志
					// 清空 给定节点及其后所有节点 的 审核信息及任务审核信息，还原节点及任务为初始状态
					spWorkNode.setAuditorId(null);
					spWorkNode.setAuditTime(null);
					spWorkNode.setAuditResult(null);
					spWorkNode.setEstatus(WorkNodeEStatusEnum.CLOSE.getKey());

					// 获取工作的所有节点连线
					SpWorkLine spWorkLine = new SpWorkLine();
					spWorkLine.setWid(spWorkNode.getWid());
					List<SpWorkLine> spWorkLineList = spWorkLineService.findWorkLineList(spWorkLine);
					java.util.Map<String, SpWorkLine> spWorkLineMap = new java.util.HashMap<String, SpWorkLine>();
					for (SpWorkLine workLine : spWorkLineList) {
						spWorkLineMap.put(workLine.getDnodeId(), workLine);// key是下一节点的ID
					}

					String uNodeId = spWorkNode.getNodeId();
					boolean flag = true;
					do {						
						spWorkNode.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
						spWorkNode.setSuggestion(" ");
						if (returnNodeId.equalsIgnoreCase(uNodeId)) {
							flag = false;
							spWorkNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
						}

						if (StringUtils.isEmpty(uNodeId))
							continue;
						spWorkNode.setNodeId(uNodeId);
						this.saveSpWorkNode(spWorkNode, false, true);

						// 获得节点关联的任务
						SpWorkTask wt = new SpWorkTask();
						wt.setWid(spWorkNode.getWid());
						wt.setNodeId(spWorkNode.getNodeId());
						spWorkNode.setSpWorkTaskList(spWorkTaskService.findWorkTaskList(wt));
						if (spWorkNode.getSpWorkTaskList() != null && spWorkNode.getSpWorkTaskList().size() > 0) {
							for (SpWorkTask spWorkTask : spWorkNode.getSpWorkTaskList()) {
								// spWorkTaskService.insertTaskLog(spWorkTask);//
								// 记录操作日志
								spWorkTask.setOperator(null);
								spWorkTask.setOpreateTime(null);
								spWorkTask.setResult(" ");
								spWorkTask.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
								spWorkTaskService.editSpWorkTask(spWorkTask);
							}
						}

						SpWorkLine workLine = spWorkLineMap.get(uNodeId);// 得到上一节点的连线实体
						if (null != workLine) {
							uNodeId = workLine.getUnodeId();
						} else {
							flag = false;
						}
					} while (flag);// 根据当前操作节点一节节向上退回，还原的节点包含退回指定的节点

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 
	 * 方法描述：执行节点中的任务
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-3 上午08:44:54
	 */
	private void saveSpWorkTask(SpWorkNode spWorkNode, boolean logFlag) {
		if (spWorkNode.getSpWorkTaskList() != null && spWorkNode.getSpWorkTaskList().size() > 0) {
			for (SpWorkTask spWorkTask : spWorkNode.getSpWorkTaskList()) {
				if (spWorkNode.getStatus().equals(// 审核通过
						WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
					spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());// 执行状态
				}
				spWorkTaskService.editSpWorkTask(spWorkTask);
				if (logFlag)
					spWorkTaskService.insertTaskLog(spWorkTask, null);// 记录操作日志
			}
		}
	}

	/**
	 * 
	 * 方法描述：修改节点状态并记录日志
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-3 上午08:44:24
	 */
	private void saveSpWorkNode(SpWorkNode spWorkNode, boolean logFlag, boolean updateDbsy) {
		// 保存审核信息及任务信息，并记录操作日志
		spWorkNodeService.editSpWorkNode(spWorkNode);
		// 如果需要修改或添加代办事宜
		if(updateDbsy){
			if(spWorkNode.getEstatus().equals(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey())){
				// 修改代办事宜
				this.updatePendingAffairInfo(spWorkNode);
			}else if(spWorkNode.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())){
				// 添加代办事宜
				this.addPendingAffairInfo(spWorkNode);
			}			
		}
		if (logFlag){
			spWorkNodeService.insertNodeLog(spWorkNode);// 记录操作日志
		}			
	}

	
	
	
	@Override
	public boolean isAllowCancelAuditing(SpWorkNode spWorkNode) {
		
		//要执行撤消的节点
		SpWorkNode currNode = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
		// 如果该节点执行状态为CLOSE,说明记录已被其它用户操作过、不能再撤消
		if(WorkNodeEStatusEnum.CLOSE.getKey().equals(currNode.getEstatus())){
			return false;
		}
		// 如果该节点为待审核，不能执行撤销操作
		if(WorkNodeStatusEnum.WAIT_AUDITING.getKey().equals(currNode.getStatus())){
			return false;
		}
		
		// 撤销操作就等同于从下一个节点退回
		SpWorkLine spWorkLine = new SpWorkLine();
		spWorkLine.setWid(spWorkNode.getWid());
		spWorkLine.setUnodeId(spWorkNode.getNodeId());
		List<SpWorkLine> spWorkLineList = spWorkLineService.findWorkLineList(spWorkLine);
		
		if(spWorkLineList != null && spWorkLineList.size() > 0){
			// 通过连线获取下一个节点
			SpWorkNode nodeInfo = new SpWorkNode();
			
			spWorkLine = spWorkLineList.get(0);
			nodeInfo.setWid(spWorkNode.getWid());
			nodeInfo.setNodeId(spWorkLine.getDnodeId());
			SpWorkNode downSpWrokNode = spWorkNodeService.findWorkNodeByWidAndNodeId(nodeInfo);
			
			// 如果下一个节点不是待审核状态，不能执行撤销操作
			if(!WorkNodeStatusEnum.WAIT_AUDITING.getKey().equals(downSpWrokNode.getStatus())){
				return false;
			}
		}
		return true;
	}
	
	
	 public boolean doCancelAuditingRsult(SpWorkNode spWorkNode,User user)  throws WorkFlowException{
		 try{
			 spWorkNode.setAuditorId(user.getYhm());
			 spWorkNode.setAuditorName(user.getXm());
			 doCancelAuditingRsult(spWorkNode, null, null);
			 return true;
		 }catch (WorkFlowException e) {
			 throw e;
		 }
	 }
	
	
	
	@Override
	public BaseResult doCancelAuditingRsult(SpWorkNode spWorkNode, String[] roleIdArray, String[] userIdArray)
			throws WorkFlowException {
		try {
			//要执行撤消的节点
			SpWorkNode currNode = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
			// 如果该节点执行状态为CLOSE,说明记录已被其它用户操作过、不能再撤消
			if(WorkNodeEStatusEnum.CLOSE.getKey().equals(currNode.getEstatus())){
				throw new WorkFlowException("异常：流程已经被其它用户处理，不能执行撤销操作！");
			}
			// 如果该节点为待审核，不能执行撤销操作
			if(WorkNodeStatusEnum.WAIT_AUDITING.getKey().equals(currNode.getStatus())){
				throw new WorkFlowException("异常：当前审核环节未执行审核操作，不能执行撤销操作！");
			}
			
			// 撤销操作
			SpWorkLine spWorkLine = new SpWorkLine();
			spWorkLine.setWid(spWorkNode.getWid());
			spWorkLine.setUnodeId(spWorkNode.getNodeId());
			List<SpWorkLine> spWorkLineList = spWorkLineService.findWorkLineList(spWorkLine);
			
			if(spWorkLineList != null && spWorkLineList.size() > 0){
				spWorkLine = spWorkLineList.get(0);
				// 通过连线获取下一个节点
				SpWorkNode nodeInfo = new SpWorkNode();
				nodeInfo.setWid(spWorkNode.getWid());
				nodeInfo.setNodeId(spWorkLine.getDnodeId());
				//查询出下级节点
				SpWorkNode downSpWrokNode = spWorkNodeService.findWorkNodeByWidAndNodeId(nodeInfo);
				// 如果下一个节点不是待审核状态，不能执行撤销操作
				if(!WorkNodeStatusEnum.WAIT_AUDITING.getKey().equals(downSpWrokNode.getStatus())){
					throw new WorkFlowException("异常：下一个审核环节已经执行审核操作，不能执行撤销操作！");
				}
				
				currNode.setStatus(WorkNodeStatusEnum.CANCEL_AUDITING.getKey());// 执行撤消操作
				currNode.setSuggestion(WorkNodeStatusEnum.CANCEL_AUDITING.getText());
				currNode.setAuditorId(spWorkNode.getAuditorId());
				currNode.setAuditorName(spWorkNode.getAuditorName());
				spWorkNodeService.insertNodeLog(currNode);// 记录操作日志
				
				//将当前节点状态还原
				spWorkNode.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
				spWorkNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
				spWorkNode.setPid(downSpWrokNode.getPid());
				this.saveSpWorkNode(spWorkNode, false, true);
				
				//将下级节点状态还原
				downSpWrokNode.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
				downSpWrokNode.setEstatus(WorkNodeEStatusEnum.CLOSE.getKey());
				this.saveSpWorkNode(downSpWrokNode, false, true);
				
			}else{
				// 如果是最后一个节点
				SpWorkNode spWorkNode2 = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
				if(spWorkNode2 != null){
					spWorkNode2.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
					spWorkNode2.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
					spWorkNode2.setAuditorId(spWorkNode.getAuditorId());
					spWorkNode2.setAuditorName(spWorkNode.getAuditorName());
					spWorkNode2.setBcode(spWorkNode.getBcode());
					spWorkNode2.setBtype(spWorkNode.getBtype());
					spWorkNode2.setAuditResult(spWorkNode.getAuditResult());
					spWorkNodeService.editSpWorkNode(spWorkNode2);
					spWorkNode2.setStatus(WorkNodeStatusEnum.CANCEL_AUDITING.getKey());// 执行撤消操作
					spWorkNode2.setSuggestion(WorkNodeStatusEnum.CANCEL_AUDITING.getText());
					spWorkNodeService.insertNodeLog(spWorkNode2);// 记录操作日志
				}
			}	
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#doTaskRsult(java.util.
	 * List)
	 */
	@Override
	public List<SpWorkTask> doTaskRsult(List<SpWorkTask> spWorkTaskList) throws WorkFlowException {
		List<SpWorkTask> resultList = new ArrayList<SpWorkTask>();
		try {
			if (spWorkTaskList != null && spWorkTaskList.size() > 0) {
				for (SpWorkTask spWorkTask : spWorkTaskList) {
					if (spWorkTask != null && StringUtils.isNotEmpty(spWorkTask.getWid())) {
						List<SpWorkTask> taskList = null;
						/*
						 * TaskCode不为空，则优先根据TaskCode和Wid取
						 */
						if (StringUtils.isNotEmpty(spWorkTask.getTaskCode())) {
							// task =
							// spWorkTaskService.findWorkNodeByTaskCodeAndTaskId(spWorkTask);
							taskList = spWorkTaskService.findWorkTaskList(spWorkTask);
						} else if (StringUtils.isNotEmpty(spWorkTask.getTaskId())) {
							SpWorkTask t = spWorkTaskService.findWorkNodeByWidAndTaskId(spWorkTask);
							if (null != t) {
								taskList = new ArrayList<SpWorkTask>();
								taskList.add(t);
							}
						}
						if (null != taskList && taskList.size() > 0) {
							// String warnInfo = "";
							for (SpWorkTask task : taskList) {
								if (task != null && task.getEstatus().equals(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey())) {
									this.doTask(resultList, spWorkTask, task);
									/*
									 * // 判断是否满足执行条件 if
									 * (StringUtils.isEmpty(task.
									 * getExeCondition())) {
									 * this.doTask(resultList, spWorkTask,
									 * task); } else { if
									 * (this.isContentExeCondition(task)) {
									 * this.doTask(resultList, spWorkTask,
									 * task); } else { warnInfo =
									 * "不满足任务执行条件，执行任务操作失败";
									 * spWorkTaskService.editSpWorkTask
									 * (spWorkTask);
									 * spWorkTaskService.insertTaskLog
									 * (spWorkTask, warnInfo);// 记录操作日志 }
									 * 
									 * }
									 */
								}
							}
						}
					}
				}

			} else {
				throw new WorkFlowException("异常：执行的任务集合为空！");
			}
		} catch (Exception e) {
			throw new WorkFlowException(e.getMessage(), e);
		}
		return resultList;
	}

	/**
	 * 
	 * 私有方法描述：执行任务
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-6 上午11:14:19
	 */
	private void doTask(List<SpWorkTask> resultList, SpWorkTask spWorkTask, SpWorkTask task) {
		spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
		spWorkTaskService.editSpWorkTask(spWorkTask);
		spWorkTaskService.insertTaskLog(spWorkTask, null);// 记录操作日志
		editNodeEStatus(task);// 修改节点的执行状态
		resultList.add(spWorkTask);
	}

	/**
	 * 
	 * 私有方法描述：判断是否满足执行条件
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-3 下午05:42:10
	 */
	// private boolean isContentExeCondition(SpWorkTask task) {
	// boolean result = true;
	// SpWorkTask spWorkTask =
	// spWorkTaskService.findWorkNodeByWidAndTaskId(task);
	// if (spWorkTask != null) {
	// // 条件表达式格式：|name1|%|name2|>=|2|,|name3|>=|60|
	// Map<String, String> valueMap = spBillExportService.getValueMap(
	// spBillConfigService.getSpBillConfigById(spWorkTask.getBillId()),
	// spBillInstanceService.getNewSpBillInstance(spWorkTask.getInstanceId()));
	// if (valueMap != null && valueMap.size() > 0) {
	// // 取出所有条件表达式（逗号隔开样式）放到数组中
	// String[] allConditionarray = spWorkTask.getExeCondition().split(",");
	// if (allConditionarray != null && allConditionarray.length > 0) {
	// String expression = "";
	// for (String condition : allConditionarray) {
	// String[] conditionArray = condition.split("|");
	// for (String cond : conditionArray) {
	// condition.replace(cond, valueMap.get(cond));
	// }
	// expression = condition.replace("|", "");
	// if (!ScriptUtil.ExecuteStringScript(expression)) {
	// result = false;
	// }
	// }
	// } else {
	// result = false;
	// }
	// } else {
	// result = false;
	// }
	// } else {
	// result = false;
	// }
	// return result;
	// }

	/**
	 * 
	 * 私有方法描述：修改节点的执行状态
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-3-30 下午12:41:19
	 */
	private void editNodeEStatus(SpWorkTask wt) {
		/*
		 * 节点是否需要往下走
		 */
		if (null != wt && StringUtils.isNotEmpty(wt.getWid()) && StringUtils.isNotEmpty(wt.getNodeId())) {
			wt.setIsMust(TaskIsMustEnum.NO.getKey());
			wt.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
			int cou = spWorkTaskService.countWorkTaskForNonExecute(wt);
			SpWorkNode spWorkNode = null;
			if (cou < 1) {
				// 所有必须执行的任务已执行，且节点已经审核通过，则节点往下走
				spWorkNode = new SpWorkNode();
				spWorkNode.setNodeId(wt.getNodeId());
				spWorkNode.setWid(wt.getWid());
				spWorkNode = spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
				if (WorkNodeStatusEnum.PASS_AUDITING.getKey().equalsIgnoreCase(spWorkNode.getStatus())) {
					spWorkNode.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					spWorkNodeService.editSpWorkNode(spWorkNode);
					changeNextNodeStatus(spWorkNode);
				}
			}
		}
	}

	/**
	 * 
	 * 私有方法描述：修改下一个节点的执行状态
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-4-7 上午09:39:00
	 */
	private void changeNextNodeStatus(SpWorkNode spWorkNode) {
		// 当前非结束节点，更新下一节点执行状态
		if (null != spWorkNode && null != spWorkNode.getNodeType()
				&& !NodeTypeEnum.END_NODE.getKey().equalsIgnoreCase(spWorkNode.getNodeType())) {
			SpWorkLine spWorkLine = new SpWorkLine();
			spWorkLine.setWid(spWorkNode.getWid());
			spWorkLine.setUnodeId(spWorkNode.getNodeId());
			List<SpWorkLine> spWorkLineList = spWorkLineService.findWorkLineList(spWorkLine);
			if (null != spWorkLineList && spWorkLineList.size() > 0) {
				// 根据连线，全部下一节点置为“待执行”
				for (SpWorkLine tempSpWorkLine : spWorkLineList) {
					// spWorkLine = spWorkLineList.get(0);
					SpWorkNode nextNode = new SpWorkNode();
					nextNode.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
					nextNode.setWid(spWorkNode.getWid());
					nextNode.setNodeId(tempSpWorkLine.getDnodeId());
					spWorkNodeService.editSpWorkNextNode(nextNode);
					// 添加代办事宜
					nextNode = spWorkNodeService.findWorkNodeByWidAndNodeId(nextNode);
					this.addPendingAffairInfo(nextNode);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#queryWorkTaskListSql(com
	 * .woshidaniu.workflow.model.query.WorkTaskQuery)
	 */

	@Override
	public TaskListSqlResult queryWorkTaskListSql(WorkTaskQuery query) throws WorkFlowException {
		TaskListSqlResult result = new TaskListSqlResult();
		try {
			String sqlContent = "";
			String sqlStatus = "";
			String sqlTaskType = "";
			if (query != null) {
				if (StringUtils.isEmpty(query.getTaskType())) {
					throw new WorkFlowException("异常：任务类别为空！");
				} else {
					sqlTaskType = " AND wt.TASK_CODE = '" + query.getTaskType() + "' ";
				}
				sqlStatus = " AND wt.E_STATUS = '" + query.getStatus() + "' ";

				sqlContent = TaskListSqlResult.getSqlStartPart().concat(sqlStatus).concat(sqlTaskType);
				result.setSqlContent(sqlContent);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}

	/*
	 * 
	 * 方法描述：字符串数组转换为SQL字符串
	 * 
	 * @param:
	 * 
	 * @return:
	 * 
	 * @version: 1.0
	 * 
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * 
	 * @since: 2013-3-23 上午11:18:21
	 */
	private String handleString(String[] array) {
		String arrayStr = "";
		for (String str : array) {
			arrayStr += "'";
			arrayStr += str;
			arrayStr += "'";
			arrayStr += ",";
		}
		return arrayStr.substring(0, arrayStr.length() - 1);
	}
	
	private String handleMarkString(String[] array) {
	    String arrayStr = "";
	    for (String str : array) {
            arrayStr += str;
            arrayStr += ",";
        }
	    return arrayStr.substring(0, arrayStr.length()-1);
	}

	@Override
	public List<SpAuditingLog> querySpAuditingLog(String wid, String roleId) throws WorkFlowException {
		if (StringUtils.isEmpty(wid))
			return null;
		SpAuditingLog spAuditingLog = new SpAuditingLog();
		spAuditingLog.setWid(wid);
		spAuditingLog.setOrole(roleId);
		return spAuditingLogService.findAuditingLog(spAuditingLog);
	}

	@Override
	public List<SpWorkTask> judgeTaskEstatus(String taskType, List<SpWorkTask> spWorkTaskList) throws WorkFlowException {
		List<SpWorkTask> resultList = new ArrayList<SpWorkTask>();
		try {
			if (StringUtils.isEmpty(taskType)) {
				throw new WorkFlowException("异常：任务类型为空！");
			}
			if (spWorkTaskList != null && spWorkTaskList.size() > 0) {
				SpWorkTask spWorkTask = new SpWorkTask();
				for (SpWorkTask task : spWorkTaskList) {
					spWorkTask.setWid(task.getWid());
					// 发送面试通知
					if (taskType.equals(TaskNameEnum.NOTIFY_ORAL_EXAM.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_ORAL_EXAM.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送笔试通知
					if (taskType.equals(TaskNameEnum.NOTIFY_WRIT_EXAM.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_WRIT_EXAM.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送岗位实践通知
					if (taskType.equals(TaskNameEnum.NOTIFY_POST_PRACTICE.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_POST_PRACTICE.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送拟录用通知
					if (taskType.equals(TaskNameEnum.NOTIFY_WANT_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_WANT_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 生成拟录用公示
					if (taskType.equals(TaskNameEnum.EDICT_WANT_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_WANT_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 发送录用通知
					if (taskType.equals(TaskNameEnum.NOTIFY_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 生成录用公示
					if (taskType.equals(TaskNameEnum.EDICT_EMPLOY.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					// 人才入库
					if (taskType.equals(TaskNameEnum.PERSON_ENTER_FILE.getKey())) {
						spWorkTask.setTaskCode(TaskNameEnum.LIST_EMPLOY.getKey());
						spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());
					}
					List<SpWorkTask> list = spWorkTaskService.findWorkTaskList(spWorkTask);
					if (list.size() > 0) {
						resultList.add(task);
					}
				}
			} else {
				throw new WorkFlowException("异常：任务对象集合为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.workflow.service.ISpWorkFlowService#queryWorkNodeByWidAndNodeId(com.woshidaniu.workflow.model.SpWorkNode)
	 */
	
	@Override
	public SpWorkNode queryWorkNodeByWidAndNodeId(String workId, String nodeId) throws DataAccessException {
		try {
			if (StringUtils.isEmpty(workId)) {
				throw new WorkFlowException("异常：工作ID为空！");
			}
			if (StringUtils.isEmpty(nodeId)) {
				throw new WorkFlowException("异常：节点ID为空！");
			}
			SpWorkNode spWorkNode = new SpWorkNode();
			spWorkNode.setWid(workId);
			spWorkNode.setNodeId(nodeId);
			return spWorkNodeService.findWorkNodeByWidAndNodeId(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.woshidaniu.workflow.service.ISpWorkFlowService#queryWorkFlowHtmlByWorkId(java.lang.String)
	 */
	
	@Override
	public SpWorkNode queryNextWorkNodeByWidAndNodeId(String workId,
			String nodeId) throws DataAccessException {
		try {
			if (StringUtils.isEmpty(workId)) {
				throw new WorkFlowException("异常：工作ID为空！");
			}
			if (StringUtils.isEmpty(nodeId)) {
				throw new WorkFlowException("异常：节点ID为空！");
			}
			SpWorkNode spWorkNode = new SpWorkNode();
			spWorkNode.setWid(workId);
			spWorkNode.setNodeId(nodeId);
			return spWorkNodeService.findNextWorkNodeByWidAndNodeId(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public String viewWorkFlowHtmlByWorkId(String workId) throws DataAccessException {
		try{
			SpWorkProcedure procedure = this.queryWorkFlowByWorkId(workId);
			if(procedure == null){
				throw new WorkFlowException("流程信息未找到或者流程还未开始");
			}else{
				WorkFlowViewHtmlCreator creator = new WorkFlowViewHtmlCreator(procedure);
				return creator.html();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#addPendingAffairInfo(com
	 * .woshidaniu.workflow.model.SpWorkNode)
	 */

	@Override
	public String viewWorkFlowLogHtmlByWorkId(String workId)
			throws DataAccessException {
		try{
			SpWorkProcedure procedure = this.queryWorkFlowByWorkId(workId);
			if(procedure == null){
				throw new WorkFlowException("异常：流程信息未找到！");
			}else{
				WorkFlowLogViewHtmlCreator creator = new WorkFlowLogViewHtmlCreator(procedure);
				return creator.html();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public boolean addPendingAffairInfo(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			if (spWorkNode != null) {
				PendingAffairInfo pendingAffairInfo = new PendingAffairInfo();
				SpWorkProcedure spWorkProcedure = new SpWorkProcedure();
				spWorkProcedure.setWid(spWorkNode.getWid());
				spWorkProcedure.setPid(spWorkNode.getPid());
				List<SpWorkProcedure> list = spWorkProcedureService.findWorkProcedureList(spWorkProcedure);
				if (list != null && list.size() > 0) {
					spWorkProcedure = list.get(0);
				}
				//根据pid获取跳转路径
				String menu = spProcedureService.findTzljByPid(spWorkNode.getPid());
				pendingAffairInfo.setMenu(menu);
				pendingAffairInfo.setAffairName(spWorkNode.getNodeName());
				if(spWorkProcedure.getBcode().contains(BusinessEnum.SH_GRXX.getKey())){
					spWorkProcedure.setBcode(BusinessEnum.SH_GRXX.getKey());
				}
				pendingAffairInfo.setAffairType(spWorkProcedure.getBcode());
				pendingAffairInfo.setStatus(0);
				pendingAffairInfo.setRoleId(spWorkNode.getRoleId());
				pendingAffairInfo.setUserId(spWorkNode.getUserId());
				pendingAffairInfo.setBusinessId(spWorkNode.getWid() + "-" + spWorkNode.getNodeId());

				pendingAffairService.addPendingAffairInfo(pendingAffairInfo);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkFlowService#updatePendingAffairInfo
	 * (com.woshidaniu.workflow.model.SpWorkNode)
	 */

	@Override
	public boolean updatePendingAffairInfo(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			if (spWorkNode != null) {
				PendingAffairInfo query = new PendingAffairInfo();
				String businessId = spWorkNode.getWid() + "-" + spWorkNode.getNodeId();
				query.setBusinessId(businessId);
				query.setStatus(1);
				pendingAffairService.modifyByYwId(query);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return false;
	}
	
	public void setSpAuditingLogService(ISpAuditingLogService spAuditingLogService) {
		this.spAuditingLogService = spAuditingLogService;
	}

	public void setSpProcedureService(ISpProcedureService spProcedureService) {
		this.spProcedureService = spProcedureService;
	}

	public void setSpWorkProcedureService(ISpWorkProcedureService spWorkProcedureService) {
		this.spWorkProcedureService = spWorkProcedureService;
	}

	public void setSpWorkNodeService(ISpWorkNodeService spWorkNodeService) {
		this.spWorkNodeService = spWorkNodeService;
	}

	public void setSpWorkTaskService(ISpWorkTaskService spWorkTaskService) {
		this.spWorkTaskService = spWorkTaskService;
	}

	public void setSpWorkLineService(ISpWorkLineService spWorkLineService) {
		this.spWorkLineService = spWorkLineService;
	}

	public void setPendingAffairService(IPendingAffairService pendingAffairService) {
		this.pendingAffairService = pendingAffairService;
	}
	
	/**
	 *@描述：查询是否符合分支业务
	 *@创建人:"huangrz"
	 *@创建时间:2015-3-18上午09:19:56
	 *@param ywdm
	 *@param userId
	 *@return
	 */
	public String getSpFzyw(String ywdm, String userId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("ywdm", ywdm);
		map.put("userId", userId);
		spProcedureService.getSpFzyw(map);
		String out_ywdm = map.get("out_ywdm");
		return out_ywdm;
	}
}
