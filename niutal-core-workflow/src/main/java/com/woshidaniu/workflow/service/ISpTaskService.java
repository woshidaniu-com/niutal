package com.woshidaniu.workflow.service;

import java.util.List;

import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpNodeTask;
import com.woshidaniu.workflow.model.SpTask;

/**
 * 任务管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpTaskService extends BaseInterface {
	/* @interface model: 添加一条SpTask记录 */
	void insert(SpTask spTask) throws WorkFlowException;

	/* @interface model: 更新一条SpTask记录 */
	void update(SpTask spTask) throws WorkFlowException;

	/* @interface model: 删除一条SpTask记录 */
	void delete(String taskId) throws WorkFlowException;

	/* @interface model: 根据条件查询SpTask结果集,返回SpTask对象的集合 */
	List<SpTask> findTaskList(SpTask spTask) throws WorkFlowException;
	
	List<SpTask> findTaskListByNodeId(String nodeId) throws WorkFlowException;
	
	List<SpNodeTask> findNodeTaskListByNodeId(String nodeId)throws WorkFlowException;
}
