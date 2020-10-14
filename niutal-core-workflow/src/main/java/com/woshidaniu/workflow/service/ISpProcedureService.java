package com.woshidaniu.workflow.service;

import java.util.List;
import java.util.Map;
import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpProcedure;

/**
 * 流程管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpProcedureService extends BaseInterface {
	/* @interface model: 添加一条SpProcedure记录 */
	void insert(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds) throws WorkFlowException;

	/* @interface model: 更新一条SpProcedure记录 */
	void update(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds) throws WorkFlowException;
	
	/* @interface model: 更新一条SpProcedure记录 */
	void update(SpProcedure spProcedure) throws WorkFlowException;

	/* @interface model: 删除一条SpProcedure记录 */
	void delete(String pId) throws WorkFlowException;

	/* @interface model: 根据业务编码查询流程对象（一个业务对应一个流程） */
	SpProcedure findSpProcedureByBCode(String bCode) throws WorkFlowException;
	
	/* @interface model: 根据业务编码查询流程对象（一个业务对应一个或多个流程） */
	List<SpProcedure> findSpProcedureListByBCode(String bCode) throws WorkFlowException;

	/* @interface model: 根据流程编码查询流程对象（一个业务对应一个流程） */
	SpProcedure findSpProcedureByPid(String pid, boolean sequence) throws WorkFlowException;

	/* @interface model: 查询所有SpProcedure结果集,返回SpProcedure对象的集合 */
	List<SpProcedure> findSpProcedureList(SpProcedure spProcedure) throws WorkFlowException;
	
	/**
	 * 获取流程配置列表
	 * @param spProcedure
	 * @return
	 * @throws WorkFlowException List<SpProcedure>
	 */
	List<SpProcedure> getPagedAllSpList(SpProcedure spProcedure) throws WorkFlowException;
	
	/**
	 * 保存流程配置信息
	 * @param spProcedure
	 * @param bzName
	 * @param persons
	 * @return boolean
	 */
	public boolean saveSpSetting(SpProcedure spProcedure,String[] bzName,String[] node_bj,String bzlx,String[] persons,String[] personIds);
	
	public void deleteSp(SpProcedure spProcedure);
	
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
	 *@创建时间:2015-3-18上午09:31:57
	 *@param map
	 */
	public Map<String,String> getSpFzyw(Map<String, String> map);
}
