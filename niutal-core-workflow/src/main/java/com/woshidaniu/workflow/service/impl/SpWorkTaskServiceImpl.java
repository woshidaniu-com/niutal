package com.woshidaniu.workflow.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.workflow.dao.ISpWorkTaskDao;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpWorkTask;
import com.woshidaniu.workflow.service.ISpWorkTaskService;

/**
 * 工作审核任务管理接口实现类
 * 
 * @version 3.2.0
 */
public class SpWorkTaskServiceImpl extends SpAuditingLogServiceImpl implements
		ISpWorkTaskService {
	/* @model: 注入SpTask */
	public ISpWorkTaskDao spWorkTaskDao;

	public void setSpWorkTaskDao(ISpWorkTaskDao spWorkTaskDao) {
		this.spWorkTaskDao = spWorkTaskDao;
	}

	@Override
	public void addSpWorkTask(SpWorkTask spWorkTask) throws DataAccessException {
		try {
			spWorkTaskDao.addSpWorkTask(spWorkTask);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpWorkTask> findWorkTaskList(SpWorkTask spWorkTask)
			throws DataAccessException {
		try {
			if (spWorkTask != null) {
				return spWorkTaskDao.findWorkTaskList(spWorkTask);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void editSpWorkTask(SpWorkTask spWorkTask) throws WorkFlowException {
		try {
			if(StringUtils.isEmpty(spWorkTask.getTaskType()) 
					&& StringUtils.isEmpty(spWorkTask.getWid())){
				throw new WorkFlowException("异常：任务类型和工作ID不能都为空！");
			}
			if(StringUtils.isEmpty(spWorkTask.getTaskId()) 
					&& StringUtils.isEmpty(spWorkTask.getWid())){
				throw new WorkFlowException("异常：任务ID和工作ID不能都为空！");
			}
			spWorkTaskDao.editSpWorkTask(spWorkTask);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public int countWorkTaskForNonExecute(SpWorkTask wt)
			throws WorkFlowException {
		try {
			return spWorkTaskDao.countWorkTaskForNonExecute(wt);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpWorkTask findWorkNodeByWidAndTaskId(SpWorkTask spWorkTask)
			throws WorkFlowException {
		try {
			return spWorkTaskDao.findWorkNodeByWidAndTaskId(spWorkTask);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpWorkTask findWorkNodeByTaskCodeAndTaskId(SpWorkTask spWorkTask)
			throws WorkFlowException {
		SpWorkTask result = null;
		try {
			List<SpWorkTask> list = spWorkTaskDao.findWorkTaskList(spWorkTask);
			if(list != null && list.size() > 0){
				result = (SpWorkTask)list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return result;
	}

}
