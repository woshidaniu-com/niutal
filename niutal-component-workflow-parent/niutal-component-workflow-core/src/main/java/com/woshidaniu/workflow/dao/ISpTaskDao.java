package com.woshidaniu.workflow.dao; 
  
 import java.util.List;

import com.woshidaniu.workflow.exception.DaoException;
import com.woshidaniu.workflow.model.SpTask;
  
 /**  
  *  
  * 类描述：任务管理接口类
  *
  * @version: 1.0
  * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
  * @since: 2013-7-12 下午12:35:41
  */
 public interface ISpTaskDao { 
 	/* @interface model: 添加一条SpTask记录 */ 
 	void insert(SpTask spTask) throws DaoException; 
  
 	/* @interface model: 更新一条SpTask记录 */ 
 	void update(SpTask spTask) throws DaoException; 
  
 	/* @interface model: 删除一条SpTask记录 */ 
 	void delete(String taskId) throws DaoException; 
 	
 	/* @interface model: 删除流程下所有SpTask记录 */ 
 	void deleteByTaskId(String taskId) throws DaoException; 
 	
 	/* @interface model: 根据条件查询是否有SpTask记录 */
	int getCountByTaskNameAndTaskType(SpTask spTask) throws DaoException;
	
	SpTask findTaskListById(String id) throws DaoException;
  
 	/* @interface model: 根据节点ID查询SpTask结果集,返回SpTask对象的集合 */ 
 	List <SpTask> findTaskListByNodeId(String nodeId) throws DaoException;
 	
 	/* @interface model: 根据条件查询SpTask结果集,返回SpTask对象的集合 */ 
 	List <SpTask> findTaskList(SpTask spTask) throws DaoException;
 	
 } 
 