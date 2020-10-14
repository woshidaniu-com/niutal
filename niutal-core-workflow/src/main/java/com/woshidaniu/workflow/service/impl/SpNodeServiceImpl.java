package com.woshidaniu.workflow.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.workflow.dao.ISpNodeDao;
import com.woshidaniu.workflow.dao.ISpNodeTaskDao;
import com.woshidaniu.workflow.dao.ISpProcedureDao;
import com.woshidaniu.workflow.exception.DaoException;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpNode;
import com.woshidaniu.workflow.model.SpNodeTask;
import com.woshidaniu.workflow.model.SpTask;
import com.woshidaniu.workflow.service.ISpNodeService;

/**
 * 节点管理接口实现类
 * 
 * @version 3.2.0
 */
public class SpNodeServiceImpl extends SpAuditingLogServiceImpl implements ISpNodeService {
	/* @model: 注入SpNode */
	public ISpNodeDao spNodeDao;
	public ISpNodeTaskDao spNodeTaskDao;
	public ISpProcedureDao spProcedureDao;

	@Override
	public void insert(SpNode spNode, Map<String, String[]> map) {
		if (map != null && map.size() > 0) {
			String[] taskIds = map.get("taskIds");
			String[] musts = map.get("musts");
			String[] autos = map.get("autos");
			spNodeDao.insert(spNode);
			this.addTask(spNode, taskIds, musts, autos);
		}
	}

	@Override
	public void insert(SpNode spNode) throws WorkFlowException {
		try {
			int result = spNodeDao.getCountByNodeNameAndPid(spNode);
			if (result > 0) {
				throw new WorkFlowException("同一流程相同节点名称的记录已经存在，不能执行新增操作！");
			} else {
				spNodeDao.insert(spNode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpNode spNode, Map<String, String[]> map) {
		try {
			if (map != null && map.size() > 0) {
				String[] taskIds = map.get("taskIds");
				String[] musts = map.get("taskIsMusts");
				String[] autos = map.get("taskIsAutos");
				spNodeDao.update(spNode);
				this.addTask(spNode, taskIds, musts, autos);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpNode spNode) throws WorkFlowException {
		try {
			spNodeDao.update(spNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String nodeId) throws WorkFlowException {
		try {
			spNodeDao.delete(nodeId);// 删除节点
			spNodeDao.deleteTaskByNodeId(nodeId);// 删除节点下所有任务关联
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpNode> findNodeList(SpNode spNode) throws WorkFlowException {
		try {
			if (spNode != null) {
				return spNodeDao.findNodeList(spNode);
			} else {
				throw new WorkFlowException("异常：节点对象为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage());
		}
	}

	@Override
	public List<SpNode> findNodeListByDnodeId(String dNodeId) throws DataAccessException {
		try {
			if (StringUtils.isNotEmpty(dNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(dNodeId);
				return spNodeDao.findNodeList(spNode);
			} else {
				throw new WorkFlowException("异常：下节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage());
		}
	}

	@Override
	public SpNode findNodeByDnodeId(String dNodeId) throws DataAccessException {
		try {
			if (StringUtils.isNotEmpty(dNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(dNodeId);
				List<SpNode> nodeList = spNodeDao.findNodeList(spNode);
				if (nodeList != null) {
					return nodeList.get(0);
				}
			} else {
				throw new WorkFlowException("异常：下节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage());
		}
		return null;
	}

	@Override
	public List<SpNode> findNodeListByUnodeId(String uNodeId) throws DataAccessException {
		try {
			if (StringUtils.isNotEmpty(uNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(uNodeId);
				return spNodeDao.findNodeList(spNode);
			} else {
				throw new WorkFlowException("异常：上节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpNode findNodeByUnodeId(String uNodeId) throws DataAccessException {
		try {
			if (StringUtils.isNotEmpty(uNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(uNodeId);
				List<SpNode> nodeList = spNodeDao.findNodeList(spNode);
				if (nodeList != null) {
					return nodeList.get(0);
				}
			} else {
				throw new WorkFlowException("异常：上节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void deleteByPid(String pId) throws DaoException {
		try {
			if (StringUtils.isNotEmpty(pId)) {
				spNodeDao.deleteByPid(pId);
			} else {
				throw new WorkFlowException("异常：流程ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	private void addTask(SpNode spNode, String[] taskIds, String[] musts, String[] autos) {
		spNodeTaskDao.deleteByNodeId(spNode.getNodeId());
		SpNodeTask spNodeTask;
		// 添加任务
		if (taskIds != null && taskIds.length > 0) {
			for (int i = 0; i < taskIds.length; i++) {
				spNodeTask = new SpNodeTask();
				spNodeTask.setNeed(musts[i]);
				spNodeTask.setAuto(autos[i]);
				spNodeTask.setSpNode(new SpNode());
				spNodeTask.getSpNode().setNodeId(spNode.getNodeId());
				spNodeTask.setSpTask(new SpTask());
				spNodeTask.getSpTask().setTaskId(taskIds[i]);
				spNodeTaskDao.insert(spNodeTask);
			}
		}
	}

	@Override
	public SpNode findNodeById(String nodeId) {
		return spNodeDao.findNodeById(nodeId);
	}

	public void setSpNodeTaskDao(ISpNodeTaskDao spNodeTaskDao) {
		this.spNodeTaskDao = spNodeTaskDao;
	}

	public void setSpNodeDao(ISpNodeDao spNodeDao) {
		this.spNodeDao = spNodeDao;
	}
	
	public void setSpProcedureDao(ISpProcedureDao spProcedureDao) {
		this.spProcedureDao = spProcedureDao;
	}
}
