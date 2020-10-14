package com.woshidaniu.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpAuditingLog;

/**
 * 
 * 类描述：流程日志DAO
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:34:24
 */
public interface ISpAuditingLogDao {
	/**
	 * 按照艺术类成果id查询艺术类成果信息
	 * 
	 * @param id
	 *            艺术类成果id
	 * @return
	 * @throws DataAccessException
	 *             异常对象
	 */
	void insert(SpAuditingLog spAuditingLog) throws DataAccessException;

	/**
	 * 按照工作id查询工作审核日志信息
	 * 
	 * @param wId
	 *            工作id
	 * @return List 关于这个工作的所有审核日志记录（按日志时间倒序排序）
	 * @throws DataAccessException
	 *             异常对象
	 */
	List<SpAuditingLog> findAuditingLogByWid(String wId) throws DataAccessException;

	/**
	 * 查询某业务历史操作日志
	 * @param spAuditingLog 其中业务编号不可为空
	 * @return
	 * @throws WorkFlowException
	 */
	List<SpAuditingLog> findAuditingLog(SpAuditingLog spAuditingLog) throws DataAccessException;
}
