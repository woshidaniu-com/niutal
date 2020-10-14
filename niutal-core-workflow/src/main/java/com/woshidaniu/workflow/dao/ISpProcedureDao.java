package com.woshidaniu.workflow.dao;

import java.util.List;
import java.util.Map;

import com.woshidaniu.workflow.exception.DaoException;
import com.woshidaniu.workflow.model.SpProcedure;

/**
 * 
 * 类描述：流程管理dao接口类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:35:33
 */
public interface ISpProcedureDao {
	/* @interface model: 添加一条SpProcedure记录 */
	void insert(SpProcedure spProcedure) throws DaoException;

	/* @interface model: 更新一条SpProcedure记录 */
	void update(SpProcedure spProcedure) throws DaoException;

	/* @interface model: 删除一条SpProcedure记录 */
	void delete(String pId) throws DaoException;

	/* @interface model: 根据流程ID删除关联的节点和任务记录 */
	void deleteTaskByPid(String pId) throws DaoException;

	/* @interface model: 根据条件查询是否存在相同名称和业务类型的流程记录 */
	int getCountByProcedureNameAndPtype(SpProcedure spProcedure) throws DaoException;
	
	/* @interface model: 根据业务编码查询流程对象（一个业务对应一个流程） */
	SpProcedure findSpProcedureByBCode(String bCode) throws DaoException;
	
	/* @interface model: 根据业务编码查询流程对象（一个业务对应多个流程） */
	List<SpProcedure> findSpProcedureListByBCode(String bCode) throws DaoException;
	
	/* @interface model: 根据业务编码查询流程对象（一个业务对应一个流程） */
	SpProcedure spProcedure(String pid) throws DaoException;
	
	/* @interface model: 查询所有SpProcedure结果集,返回SpProcedure对象的集合 */
	List<SpProcedure> findSpProcedureList(SpProcedure spProcedure) throws DaoException;

	SpProcedure findSpProcedureById(String pid);
	
	/**
	 * new
	 */
	List<SpProcedure> getPagedAllSpList(SpProcedure spProcedure) throws DaoException;
	
	/**
	 *@描述：根据pid获取跳转路径
	 *@创建人:"huangrz"
	 *@创建时间:2014-9-16上午09:23:40
	 *@param pid
	 *@return
	 */
	public String findTzljByPid(String pid);
	
	/**
	 *@描述：查询审批环节标记
	 *@创建人:"huangrz"
	 *@创建时间:2014-10-18上午10:45:35
	 *@return
	 */
	public List<Map<String,String>> findSpNodeBjList(String ywdm);
	
	/**
	 *@描述：查询是否符合分支业务
	 *@创建人:"huangrz"
	 *@创建时间:2015-3-18上午09:43:48
	 *@param map
	 *@return
	 */
	public Map<String,String> getSpFzyw(Map<String, String> map);
}
