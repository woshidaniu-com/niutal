package com.woshidaniu.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.util.base.StringUtil;
import com.woshidaniu.workflow.dao.ISpAuditingLogDao;
import com.woshidaniu.workflow.dao.ISpWorkLineDao;
import com.woshidaniu.workflow.dao.ISpWorkNodeDao;
import com.woshidaniu.workflow.dao.ISpWorkProcedureDao;
import com.woshidaniu.workflow.dao.ISpWorkTaskDao;
import com.woshidaniu.workflow.enumobject.NodeTypeEnum;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpWorkLine;
import com.woshidaniu.workflow.model.SpWorkNode;
import com.woshidaniu.workflow.model.SpWorkProcedure;
import com.woshidaniu.workflow.service.ISpWorkProcedureService;

/**
 * 工作审核节点连线管理接口实现类
 * 
 * @version 3.2.0
 */
public class SpWorkProcedureServiceImpl extends BaseInterfaceServiceImpl implements ISpWorkProcedureService {
	/* @model: 注入SpLine */
	public ISpWorkProcedureDao spWorkProcedureDao;
	public ISpWorkLineDao spWorkLineDao;
	public ISpWorkNodeDao spWorkNodeDao;
	public ISpWorkTaskDao spWorkTaskDao;
	public ISpAuditingLogDao spAuditingLogDao;
	
	public void setSpAuditingLogDao(ISpAuditingLogDao spAuditingLogDao) {
		this.spAuditingLogDao = spAuditingLogDao;
	}

	public void setSpWorkProcedureDao(ISpWorkProcedureDao spWorkProcedureDao) {
		this.spWorkProcedureDao = spWorkProcedureDao;
	}

	/**
	 * @param spWorkLineDao
	 *            : set the property spWorkLineDao.
	 */

	public void setSpWorkLineDao(ISpWorkLineDao spWorkLineDao) {
		this.spWorkLineDao = spWorkLineDao;
	}

	/**
	 * @param spWorkNodeDao
	 *            : set the property spWorkNodeDao.
	 */

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

	@Override
	public void addSpWorkProcedure(SpWorkProcedure spWorkProcedure) throws WorkFlowException {
		try {
			spWorkProcedureDao.removeSpWorkProcedureByWid(spWorkProcedure.getWid());// 清除垃圾数据
			spWorkProcedureDao.addSpWorkProcedure(spWorkProcedure);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.woshidaniu.workflow.service.ISpWorkProcedureService#
	 * removeSpWorkProcedureByWid(java.lang.String)
	 */

	@Override
	public boolean removeSpWorkProcedureByWid(String wId) throws DataAccessException {
		try {
			if (StringUtil.isNotEmpty(wId)) {
				spWorkProcedureDao.removeSpWorkProcedureByWid(wId);
				spWorkLineDao.removeSpWorkLineByWId(wId);
				spWorkNodeDao.removeSpWorkNodeByWid(wId);
				spWorkTaskDao.removeSpWorkTaskByWid(wId);
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public List<SpWorkProcedure> findWorkProcedureList(SpWorkProcedure spWorkProcedure) throws WorkFlowException {
		try {
			if (spWorkProcedure != null) {
				return spWorkProcedureDao.findWorkProcedureList(spWorkProcedure);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public SpWorkProcedure findWorkProcedureByWId(String wid) throws WorkFlowException {
		SpWorkProcedure spWorkProcedure = null;
		try {
			if (StringUtil.isEmpty(wid)) {
				throw new WorkFlowException("异常：工作ID为空！");
			}
			SpWorkProcedure workProcedure = new SpWorkProcedure();
			workProcedure.setWid(wid);
			List<SpWorkProcedure> list = spWorkProcedureDao.findWorkProcedureList(workProcedure);
			
			if (null != list && list.size() > 0) {
				spWorkProcedure = (SpWorkProcedure) list.get(0);
				spWorkProcedure = this.nodeSequence(spWorkProcedure, wid);
			}else{
				//如果找不到流程信息，则只查询审批日志
				spWorkProcedure = new SpWorkProcedure();
				spWorkProcedure.setWid(wid);
				spWorkProcedure.setSpAuditingLogList(spAuditingLogDao.findAuditingLogByWid(wid));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return spWorkProcedure;
	}

	/**
	 * 
	 * 私有方法描述：工作流程节点排序
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-2 下午12:22:47
	 */
	private SpWorkProcedure nodeSequence(SpWorkProcedure spWorkProcedure, String wid) throws WorkFlowException {
		if(spWorkProcedure != null){
			SpWorkNode spwn = new SpWorkNode();
			spwn.setPid(spWorkProcedure.getPid());
			spwn.setWid(wid);
			spwn.setNodeType(NodeTypeEnum.START_NODE.getKey());
			List<SpWorkNode> workNodeList = spWorkNodeDao.findWorkNodeList(spwn);
			if (workNodeList != null && workNodeList.size() > 0) {
				SpWorkNode spWorkNode = workNodeList.get(0);// 获取开始节点对象
				if (spWorkNode != null) {
					// 流程中的节点集合
					List<SpWorkNode> spWorkNodeList = new ArrayList<SpWorkNode>();
					spWorkNodeList.add(spWorkNode);// 装在开始节点对象

					// 根据开始节点查询连线
					SpWorkNode nextWrokNode = spWorkNode;
					boolean condition = true;
					SpWorkLine spwl = new SpWorkLine();
					do {
						spwl.setUnodeId(nextWrokNode.getNodeId());
						spwl.setWid(wid);
						List<SpWorkLine> workLineList = spWorkLineDao.findWorkLineList(spwl);
						if (workLineList != null && workLineList.size() > 0) {
							// 一条线的流程
							SpWorkLine spWorkLine = workLineList.get(0);
							spwn.setNodeId(spWorkLine.getDnodeId());
							spwn.setWid(wid);
							nextWrokNode = spWorkNodeDao.findWorkNodeByWidAndNodeId(spwn);
							spWorkNodeList.add(nextWrokNode);
							if (NodeTypeEnum.END_NODE.getKey().equalsIgnoreCase(nextWrokNode.getNodeType())) {
								// 结束节点
								condition = false;
							}
						} else {
							// 只有一个节点的流程
							if (nextWrokNode.getNodeType().equals(NodeTypeEnum.START_NODE.getKey())) {
								condition = false;
							} else {
								throw new WorkFlowException("异常：不完整的工作流程定义，其中一个可能的原因是节点无法正常联通或没有结束节点！" + "\n流程编号："
										+ spWorkProcedure.getPid() + "，流程名称：" + spWorkProcedure.getPname() + "中断节点编号："
										+ nextWrokNode.getNodeId() + "，节点名称：" + nextWrokNode.getNodeName());
							}
						}
					} while (condition);
					spWorkProcedure.setSpWorkNodeList(spWorkNodeList);
				}
			}
		}
		return spWorkProcedure;
	}
}
