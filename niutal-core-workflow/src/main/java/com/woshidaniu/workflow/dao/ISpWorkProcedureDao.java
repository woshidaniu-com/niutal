package com.woshidaniu.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.workflow.model.SpWorkProcedure;

/**
 * 
 * 类描述：工作审核流程接口类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:36:15
 */
public interface ISpWorkProcedureDao {
	/* @interface model: 添加一条SpWorkProcedure记录 */
	void addSpWorkProcedure(SpWorkProcedure spWorkProcedure)
			throws DataAccessException;

	/* @interface model: 删除一条SpWorkProcedure记录 */
	void removeSpWorkProcedureById(String id) throws DataAccessException;

	/* @interface model: 删除SpWorkProcedure记录 */
	void removeSpWorkProcedureByWid(String wid) throws DataAccessException;

	/* @interface model: 查询SpWorkProcedure结果集,返回SpWorkProcedure对象的集合 */
	List<SpWorkProcedure> findWorkProcedureList(SpWorkProcedure spWorkProcedure)
			throws DataAccessException;
}
