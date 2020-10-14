package com.woshidaniu.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.workflow.model.SpWorkLine;

/**
 * 
 * 类描述：工作审批连线接口类 
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:35:50
 */
public interface ISpWorkLineDao {
	/* @interface model: 添加一条SpWorkLine记录 */
	void addSpWorkLine(SpWorkLine spWorkLine) throws DataAccessException;

	/* @interface model: 删除一条SpWorkLine记录 */
	void removeSpWorkLineById(String id) throws DataAccessException;
	
	/* @interface model: 删除SpWorkLine记录 */
	void removeSpWorkLine(SpWorkLine spWorkLine) throws DataAccessException;
	
	/* @interface model: 根据工作ID删除所有SpWorkLine记录 */
	void removeSpWorkLineByWId(String wid) throws DataAccessException;

	/* @interface model: 查询SpWorkLine结果集,返回SpWorkLine对象的集合 */
	List<SpWorkLine> findWorkLineList(SpWorkLine spWorkLine) throws DataAccessException;
	
	/* @interface model: 查询SpWorkLine结果集,返回SpWorkLine对象的集合 */
	List<SpWorkLine> findWorkLineListByWid(String wid) throws DataAccessException;
	
}
