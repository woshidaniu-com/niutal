package com.woshidaniu.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.woshidaniu.workflow.dao.ISpBusinessDao;
import com.woshidaniu.workflow.dao.ISpProcedureDao;
import com.woshidaniu.workflow.enumobject.NodeTypeEnum;
import com.woshidaniu.workflow.enumobject.StatusEnum;
import com.woshidaniu.workflow.exception.DaoException;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpBusiness;
import com.woshidaniu.workflow.model.SpLine;
import com.woshidaniu.workflow.model.SpNode;
import com.woshidaniu.workflow.model.SpProcedure;
import com.woshidaniu.workflow.service.ISpLineService;
import com.woshidaniu.workflow.service.ISpNodeService;
import com.woshidaniu.workflow.service.ISpProcedureService;

/**
 * 流程管理service接口实现类
 * 
 * @version 3.2.0
 */
public class SpProcedureServiceImpl extends BaseInterfaceServiceImpl implements ISpProcedureService {
	/* @model: 注入SpProcedure */
	public ISpProcedureDao spProcedureDao;
	public ISpNodeService spNodeService;
	public ISpLineService spLineService;
	public ISpBusinessDao spBusinessDao;

	/**
	 * @param spNodeService
	 *            : set the property spNodeService.
	 */

	public void setSpNodeService(ISpNodeService spNodeService) {
		this.spNodeService = spNodeService;
	}

	/**
	 * @param spLineService
	 *            : set the property spLineService.
	 */

	public void setSpLineService(ISpLineService spLineService) {
		this.spLineService = spLineService;
	}

	public void setSpProcedureDao(ISpProcedureDao spProcedureDao) {
		this.spProcedureDao = spProcedureDao;
	}
	
	public ISpBusinessDao getSpBusinessDao() {
        return spBusinessDao;
    }

    public void setSpBusinessDao(ISpBusinessDao spBusinessDao) {
        this.spBusinessDao = spBusinessDao;
    }

    @Override
	public void insert(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds)
			throws WorkFlowException {
		try {
			int result = spProcedureDao.getCountByProcedureNameAndPtype(spProcedure);
			if (result > 0) {
				throw new WorkFlowException("异常：相同的流程名称和业务类型记录已经存在，不能执行新增操作！");
			} else {
				// 新增流程
				spProcedureDao.insert(spProcedure);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds)
			throws WorkFlowException {
		try {
			// 修改流程
			spProcedureDao.update(spProcedure);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.woshidaniu.workflow.service.ISpProcedureService#update(com.woshidaniu.workflow
	 * .model.SpProcedure)
	 */

	@Override
	public void update(SpProcedure spProcedure) throws WorkFlowException {
		try {
			// 修改流程
			spProcedureDao.update(spProcedure);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String pId) throws WorkFlowException {
		try {
			spProcedureDao.deleteTaskByPid(pId);// 删除流程节点下所有任务关联
			spNodeService.deleteByPid(pId);// 删除流程所有节点
			spProcedureDao.delete(pId);// 删除流程
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpProcedure> findSpProcedureList(SpProcedure spProcedure) throws WorkFlowException {
		try {
			List<SpProcedure> pList = spProcedureDao.findSpProcedureList(spProcedure);
			return pList;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpProcedure findSpProcedureByBCode(String bCode) throws DaoException {
		SpProcedure spProcedure = null;
		try {
			spProcedure = this.putNodeToProcedure(spProcedureDao.findSpProcedureByBCode(bCode),false);// 获取流程对象
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return spProcedure;
	}

	@Override
	public List<SpProcedure> findSpProcedureListByBCode(String bCode)
			throws WorkFlowException {
		List<SpProcedure> spProcedureList = null;
		try {
			spProcedureList = spProcedureDao.findSpProcedureListByBCode(bCode);// 获取流程对象集合
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return spProcedureList;
	}

	@Override
	public SpProcedure findSpProcedureByPid(String pid, boolean sequence) throws DaoException {
		SpProcedure sp = spProcedureDao.findSpProcedureById(pid);
		SpProcedure spProcedure = this.putNodeToProcedure(sp,sequence);
		return spProcedure;
	}

	/*
	 * 
	 * 方法描述：按顺序封装流程节点和连线的私有类
	 * 
	 * @param:
	 * 
	 * @return:
	 * 
	 * @version: 1.0
	 * 
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * 
	 * @since: 2013-3-17 下午03:08:01
	 */
	private SpProcedure putNodeToProcedure(SpProcedure spProcedure, boolean sequence) throws WorkFlowException {
		SpNode node = new SpNode();
		if(spProcedure != null){
			node.setPid(spProcedure.getPid());
			node.setNodeType(NodeTypeEnum.START_NODE.getKey());
			List<SpNode> nodeList = spNodeService.findNodeList(node);
			// 获取节点有排序的流程信息
			if(sequence){
				if (nodeList != null && nodeList.size() > 0) {
					SpNode spNode = nodeList.get(0);// 获取开始节点对象
					if (spNode != null) {
						// 流程中的节点集合
						List<SpNode> spNodeList = new ArrayList<SpNode>();
						spNodeList.add(spNode);// 装在开始节点对象

						// 根据开始节点查询连线
						SpNode nextNode = spNode;
						boolean condition = true;
						do {
							List<SpLine> lineList = spLineService.findLineListByUnodeId(nextNode.getNodeId());
							if (lineList != null && lineList.size() > 0) {
								SpLine spLine = lineList.get(0);
								nextNode = spNodeService.findNodeByDnodeId(spLine.getDnodeId());
								spNodeList.add(nextNode);
								if (NodeTypeEnum.END_NODE.getKey().equalsIgnoreCase(nextNode.getNodeType())) {
									// 结束节点
									condition = false;
								}
							} else {
								// 只有一个节点的流程
								if (nextNode.getNodeType().equals(NodeTypeEnum.START_NODE.getKey())) {
									condition = false;
								} else {
									throw new WorkFlowException("异常：不完整的流程定义，其中一个可能的原因是节点无法正常联通或没有结束节点！" + "\n流程编号："
											+ spProcedure.getPid() + "，流程名称：" + spProcedure.getPname() + "中断节点编号："
											+ nextNode.getNodeId() + "，节点名称：" + nextNode.getNodeName());
								}
							}
						} while (condition);
						spProcedure.setSpNodeList(spNodeList);
					}
				}
			}			
		}		
		return spProcedure;
	}
	
	public List<SpProcedure> getPagedAllSpList(SpProcedure spProcedure) throws WorkFlowException {
        return spProcedureDao.getPagedAllSpList(spProcedure);
    }
	
	public boolean saveSpSetting(SpProcedure spProcedure,String[] bzName,String[] node_bj,String[] bzlx,String[] persons,String[] personIds
			,String[] sjfwztj,String[] sjfwztj_qz,String[] send_mes,String[] send_mail,String[] send_sms,String[] content_mes,String[] content_mail,String[] content_sms,String[] content_mes_end,String[] content_mail_end,String[] content_sms_end) {
	    try {
	        String bCode = spProcedure.getBusinessType();
	        if(spProcedure.getPid()==null||spProcedure.getPid().equals("")) {
	            int result = spProcedureDao.getCountByProcedureNameAndPtype(spProcedure);
	            if (result > 0) {
	                throw new WorkFlowException("异常：相同的业务类型记录已经存在，不能执行新增操作！");
	            }
	            spProcedureDao.insert(spProcedure);
	        }else {
	            
	            spProcedureDao.update(spProcedure);
	        }
            String pid = spProcedure.getPid();
            
            //绑定业务表
            spBusinessDao.deleteByPid(pid);
            SpBusiness busi = new SpBusiness();
            busi.setBstatus("1");
            busi.setPid(pid);
            busi.setBcode(bCode);
//            busi.setBname(bCode);
//            busi.setBtype(bCode);
            spBusinessDao.insert(busi);
            
            //新增节点
            spNodeService.deleteByPid(pid);
            List<String> nodeIdList = new ArrayList<String>();
            for(int i=0;i<bzName.length;i++) {
                SpNode spNode = new SpNode();
                spNode.setPid(pid);
                spNode.setOutType("0");
                spNode.setInType("0");
                spNode.setIsAuto("0");
                spNode.setNodeStatus("1");
                spNode.setNodeName(bzName[i]);
                spNode.setNodeDesc(bzName[i]);
                if(node_bj!=null&&node_bj.length>0){
                	//有环节标记就设置，没有就为空
                	spNode.setNode_bj(node_bj[i]);
                }                
                if(bzlx[i].equals("jbry")) {
                    spNode.setUserId(personIds[i]);
                    spNode.setUserName(persons[i]);
                }else if(bzlx[i].equals("jbjs")) {
                    spNode.setRoleId(personIds[i]);
                    spNode.setRoleName(persons[i]);
                }
                if(i==0) {
                    spNode.setNodeType(NodeTypeEnum.START_NODE.getKey());
                }else if(bzName.length!=1&&i==bzName.length-1) {
                    spNode.setNodeType(NodeTypeEnum.END_NODE.getKey());
                }else {
                    spNode.setNodeType(NodeTypeEnum.NORMAL_NODE.getKey());
                }
                
                spNode.setSjfwztj(sjfwztj[i]);
                spNode.setSjfwztj_qz(sjfwztj_qz[i]);
                spNode.setSend_mes(StatusEnum.VALID_STATUS.getKey());
                spNode.setSend_mail(send_mail[i]);
                spNode.setSend_sms(send_sms[i]);
                spNode.setContent_mes(content_mes[i]);
                spNode.setContent_mail(content_mail[i]);
                spNode.setContent_sms(content_sms[i]);
                spNode.setContent_mes_end(content_mes_end[i]);
                spNode.setContent_mail_end(content_mail_end[i]);
                spNode.setContent_sms_end(content_sms_end[i]);
                
                spNodeService.insert(spNode);
                nodeIdList.add(spNode.getNodeId());
            }
            
            //新增连线
            spLineService.deleteLineByPId(pid);
            if(nodeIdList.size()>1) {
                String pnode="";
                for (int j=0;j<nodeIdList.size();j++) {
                    if(j>0) {
                        SpLine spLine = new SpLine();
                        spLine.setPid(pid);
                        spLine.setUnodeId(pnode);
                        spLine.setDnodeId(nodeIdList.get(j));
                        spLine.setIsMustPass("1");
                        spLine.setExpression("");
                        spLineService.insert(spLine);
                    }
                    pnode= (String) nodeIdList.get(j);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new WorkFlowException(e.getMessage(), e);
        }
	    return true;
	}
	
	public void deleteSp(SpProcedure m) {
	    String pks = m.getPkValue();
	    String pkValues[] = pks.split(",");
	    for (int i = 0; i < pkValues.length; i++) {
	        spProcedureDao.delete(pkValues[i]);
	        spNodeService.deleteByPid(pkValues[i]);
	        spLineService.deleteLineByPId(pkValues[i]);
	        spBusinessDao.deleteByPid(pkValues[i]);
        }
	}
	
	/**
	 *@描述：根据pid获取跳转路径
	 *@创建人:"huangrz"
	 *@创建时间:2014-9-16上午09:23:40
	 *@param pid
	 *@return
	 */
	public String findTzljByPid(String pid){
		return spProcedureDao.findTzljByPid(pid);
	}
	
	/**
	 *@描述：根据pid获取申请路径
	 *@创建人:jiangyy
	 *@创建时间:2018-2-25 14:23:40
	 *@param pid
	 *@return
	 */
	public String findSqljByPid(String pid){
		return spProcedureDao.findSqljByPid(pid);
	}
	
	/**
	 *@描述：查询审批环节标记
	 *@创建人:"huangrz"
	 *@创建时间:2014-10-18上午10:45:35
	 *@return
	 */
	public List<Map<String,String>> findSpNodeBjList(String ywdm){
		return spProcedureDao.findSpNodeBjList(ywdm);
	}
	
	/**
	 *@描述：查询是否符合分支业务
	 *@创建人:"huangrz"
	 *@创建时间:2015-3-18上午09:33:27
	 *@param map
	 */
	@Override
	public Map<String,String> getSpFzyw(Map<String, String> map) {
		return spProcedureDao.getSpFzyw(map);
	}
}
