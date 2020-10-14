package com.woshidaniu.workflow.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.workflow.dao.ISpWorkNodeDao;
import com.woshidaniu.workflow.dao.ISpWorkTaskDao;
import com.woshidaniu.workflow.enumobject.WorkNodeEStatusEnum;
import com.woshidaniu.workflow.enumobject.WorkNodeStatusEnum;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpWorkNode;
import com.woshidaniu.workflow.model.SpWorkTask;
import com.woshidaniu.workflow.service.ISpWorkNodeService;

/**
 * 工作审核节点管理接口实现类
 * 
 * @version 3.2.0
 */
public class SpWorkNodeServiceImpl extends SpAuditingLogServiceImpl implements ISpWorkNodeService {
	/* @model: 注入SpNode */
	public ISpWorkNodeDao spWorkNodeDao;
	public ISpWorkTaskDao spWorkTaskDao;

	public void setSpWorkNodeDao(ISpWorkNodeDao spWorkNodeDao) {
		this.spWorkNodeDao = spWorkNodeDao;
	}

	/**
	 * @param spWorkTaskDao
	 *            : set the property spWorkTaskDao.
	 */

	public void setSpWorkTaskDao(ISpWorkTaskDao spWorkTaskDao) {
		this.spWorkTaskDao = spWorkTaskDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkNodeService#editSpWorkNode(com.woshidaniu
	 * .workflow.model.SpWorkNode)
	 */
	@Override
	public void editSpWorkNode(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			if (spWorkNode == null) {
				throw new WorkFlowException("异常：工作审核节点对象为空！");
			}
			spWorkNodeDao.editSpWorkNode(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpWorkNodeService#editSpWorkNodeAndTask(
	 * com.woshidaniu.workflow.model.SpWorkNode)
	 */

	@Override
	public void editSpWorkNodeAndTask(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			// 更新节点对象
			this.editSpWorkNode(spWorkNode);
			// 修改任务信息
			List<SpWorkTask> spWorkTaskList = spWorkNode.getSpWorkTaskList();
			SpWorkTask spWorkTask = null;
			if (spWorkTaskList != null && spWorkTaskList.size() > 0) {
				for (Iterator<SpWorkTask> its = spWorkTaskList.iterator(); its.hasNext();) {
					spWorkTask = (SpWorkTask) its.next();
					spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());// 执行状态
					spWorkTaskDao.editSpWorkTask(spWorkTask);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void addSpWorkNode(SpWorkNode spWorkNode) throws DataAccessException {
		try {
			if (spWorkNode == null) {
				throw new WorkFlowException("异常：工作审核节点对象为空！");
			}
			spWorkNodeDao.addSpWorkNode(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpWorkNode> findWorkNodeList(SpWorkNode spWorkNode) throws DataAccessException {
		try {
			if (spWorkNode != null) {
				return spWorkNodeDao.findWorkNodeList(spWorkNode);
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
	 * com.woshidaniu.workflow.service.ISpWorkNodeService#findWorkNodeListByCondition
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	@Override
	public List<SpWorkNode> findWorkNodeListByCondition(String bType, String bCode, String status, boolean isDispose,
			String[] roleIdArray, String[] userIdArray, String auditor) throws WorkFlowException {
		String eStatus = "";String[] statusArray = null;
		try {
			if ((roleIdArray == null || roleIdArray.length == 0) 
					&& (userIdArray == null || userIdArray.length == 0)) {
				throw new WorkFlowException("异常：角色ID和用户ID不能都为空！");
			}
			if (StringUtils.isEmpty(bType)) {
				throw new WorkFlowException("异常：业务类型不能为空！");
			}
			if (StringUtils.isEmpty(auditor)) {

			}
			
			if (StringUtils.isNotEmpty(status)) {
				if (status.equals(WorkNodeStatusEnum.WAIT_AUDITING.getKey())) {
					eStatus = WorkNodeEStatusEnum.WAIT_EXECUTE.getKey();
					auditor = "";
				} else if (status.equals(WorkNodeStatusEnum.IN_AUDITING.getKey())) {
					eStatus = WorkNodeEStatusEnum.WAIT_EXECUTE.getKey();
				} else if (status.equals(WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
					eStatus = "";
				} else {
					eStatus = WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey();
				}
			}
			// 如果查询已处理（状态为：审核通过、审核不通过）的记录
			else if(isDispose){
				statusArray = new String[2];
				statusArray[0] = WorkNodeStatusEnum.PASS_AUDITING.getKey();
				statusArray[1] = WorkNodeStatusEnum.NO_PASS_AUDITING.getKey();
				eStatus = WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey();
			}
			// 如果查询未处理（状态为：待审核、审核中）的记录
			else if(!isDispose){
				statusArray = new String[2];
				statusArray[0] = WorkNodeStatusEnum.WAIT_AUDITING.getKey();
				statusArray[1] = WorkNodeStatusEnum.IN_AUDITING.getKey();
				eStatus = WorkNodeEStatusEnum.WAIT_EXECUTE.getKey();
			}
			
			SpWorkNode spWorkNode = new SpWorkNode();
			spWorkNode.setBtype(bType);
			spWorkNode.setBcode(bCode);
			spWorkNode.setStatus(status);
			spWorkNode.setStatusArray(statusArray);
			spWorkNode.setEstatus(eStatus);
			spWorkNode.setRoleIdArray(roleIdArray);
			spWorkNode.setUserIdArray(userIdArray);
			spWorkNode.setAuditorId(auditor);// 操作人
			return spWorkNodeDao.findWorkNodeListByCondition(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpWorkNode findWorkNodeByWidAndNodeId(SpWorkNode spWorkNode) throws DataAccessException {
		try {
			if (spWorkNode != null) {
				return spWorkNodeDao.findWorkNodeByWidAndNodeId(spWorkNode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public SpWorkNode findNextWorkNodeByWidAndNodeId(SpWorkNode spWorkNode) throws DataAccessException {
		try {
			if (spWorkNode != null) {
				return spWorkNodeDao.findNextWorkNodeByWidAndNodeId(spWorkNode);
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
	 * com.woshidaniu.workflow.service.ISpWorkNodeService#editSpWorkNextNode(com.woshidaniu
	 * .workflow.model.SpWorkNode)
	 */
	@Override
	public void editSpWorkNextNode(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			if (spWorkNode == null) {
				throw new WorkFlowException("异常：工作审核节点对象为空！");
			}
			spWorkNodeDao.editSpWorkNextNode(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

}
