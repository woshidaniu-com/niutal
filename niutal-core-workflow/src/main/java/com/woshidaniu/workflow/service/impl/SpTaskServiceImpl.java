package com.woshidaniu.workflow.service.impl; 
  
 import java.util.List;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.workflow.dao.ISpNodeTaskDao;
import com.woshidaniu.workflow.dao.ISpTaskDao;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpNodeTask;
import com.woshidaniu.workflow.model.SpTask;
import com.woshidaniu.workflow.service.ISpTaskService;
  
 /**  
  * 代码自动生成(bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class SpTaskServiceImpl extends BaseInterfaceServiceImpl implements 
 		ISpTaskService { 
 	/* @model: 注入SpTask */ 
 	public ISpTaskDao spTaskDao; 
	public ISpNodeTaskDao spNodeTaskDao; 
  
 	public void setSpTaskDao(ISpTaskDao spTaskDao) { 
 		this.spTaskDao = spTaskDao; 
 	}

	public void setSpNodeTaskDao(ISpNodeTaskDao spNodeTaskDao) {
		this.spNodeTaskDao = spNodeTaskDao;
	}

	@Override
	public void insert(SpTask spTask) throws WorkFlowException {
		try{
			int result = spTaskDao.getCountByTaskNameAndTaskType(spTask);
			if(result > 0){
				throw new WorkFlowException("存在相同类型和名称的任务，不能执行新增操作！");
			}else{
				spTaskDao.insert(spTask);
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpTask spTask) throws WorkFlowException {
		try{
			int result = spTaskDao.getCountByTaskNameAndTaskType(spTask);
			if(result > 0){
				throw new WorkFlowException("存在相同类型和名称的任务，不能执行编辑操作！");
			}else{
				spTaskDao.update(spTask);
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String taskId) throws WorkFlowException {
		try{
			spTaskDao.delete(taskId);//删除节点
			spTaskDao.deleteByTaskId(taskId);//删除节点下所有任务关联
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpTask> findTaskList(SpTask spTask) throws WorkFlowException {
		try{
			if(spTask != null){
				return spTaskDao.findTaskList(spTask);
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	} 
	
	public List<SpTask> findTaskListByNodeId(String nodeId) throws WorkFlowException {
		try{
			if(StringUtils.isNotEmpty(nodeId)){
				return spTaskDao.findTaskListByNodeId(nodeId);
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<SpNodeTask> findNodeTaskListByNodeId(String nodeId)
			throws WorkFlowException {
		try{
		return spNodeTaskDao.findNodeTaskListByNodeId(nodeId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
  
	
 } 
 