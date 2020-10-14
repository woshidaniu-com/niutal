package com.woshidaniu.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.woshidaniu.workflow.model.SpWorkNode;

/**
 * 
 * 类描述：工作节点实例接口类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:35:55
 */
public interface ISpWorkNodeDao  {
	/* @interface model: 添加一条SpWorkNode记录 */
	void addSpWorkNode(SpWorkNode spWorkNode) throws DataAccessException;

	/* @interface model: 更新一条SpWorkNode记录 */
	void editSpWorkNode(SpWorkNode spWorkNode) throws DataAccessException;
	
	/* @interface model: 更新消息队列 */
	//void editXxdl(SpWorkNode spWorkNode) throws DataAccessException;

	/* @interface model: 根据条件更新节点执行状态 */
	void editEStatus(SpWorkNode spWorkNode) throws DataAccessException;

	/* @interface model: 删除一条SpWorkNode记录 */
	void removeSpWorkNodeById(String id) throws DataAccessException;

	/* @interface model: 根据工作ID和节点ID删除SpWorkNode记录 */
	void removeSpWorkNodeByWidAndNodeId(SpWorkNode spWorkNode)
			throws DataAccessException;

	/* @interface model: 根据工作ID删除全部SpWorkNode记录 */
	void removeSpWorkNodeByWid(String wid) throws DataAccessException;
	
	/* @interface model: 根据工作ID和节点查询SpWorkNode结果,返回SpWorkNode对象 */
	SpWorkNode findWorkNodeByWidAndNodeId(SpWorkNode spWorkNode) throws DataAccessException;
	
	/* @interface model: 根据工作ID和节点查询SpWorkNode结果,返回SpWorkNode对象 */
	SpWorkNode findNextWorkNodeByWidAndNodeId(SpWorkNode spWorkNode) throws DataAccessException;
	
	/* @interface model: 根据工作ID查询SpWorkNode结果集,返回SpWorkNode对象的集合 */
	List<SpWorkNode> findWorkNodeListByWid(String wid) throws DataAccessException;

	/* @interface model: 查询所有SpWorkNode结果集,返回SpWorkNode对象的集合 */
	List<SpWorkNode> findWorkNodeList(SpWorkNode spWorkNode)
			throws DataAccessException;

	/* @interface model: 根据条件查询SpWorkNode结果,返回SpWorkNode对象集合 */
	List<SpWorkNode> findWorkNodeListByCondition(SpWorkNode spWorkNode)
			throws DataAccessException;
	
	/* @interface model: 更新下一条SpWorkNode记录 */
	void editSpWorkNextNode(SpWorkNode spWorkNode) throws DataAccessException;
	
	/**
	 * @description: 获取起始节点
	 * @author : huangrz
	 * @date 上午11:43:38 2015-4-22
	 */
	SpWorkNode findFirstWorkNode(String wId);	
	
	/**
	 * @description: 获取下一结点
	 * @author : huangrz
	 * @date 上午11:43:48 2015-4-22
	 */
	SpWorkNode findNextWorkNode(SpWorkNode node);
	
	/**
	 * 
	 *@描述：撤销审核时，获取当前节点的位置
	 *@创建人:zfankai
	 *@创建时间:2016-3-24上午11:19:26
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param node
	 *@return
	 */
	int findNodeLocation (SpWorkNode node);
}
