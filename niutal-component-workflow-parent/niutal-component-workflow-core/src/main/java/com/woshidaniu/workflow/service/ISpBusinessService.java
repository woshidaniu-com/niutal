package com.woshidaniu.workflow.service;

import java.util.List;
import java.util.Map;

import com.woshidaniu.workflow.exception.WorkFlowException;
import com.woshidaniu.workflow.model.SpBusiness;
import com.woshidaniu.workflow.model.SpProcedure;

/**
 * 类说明：业务管理SERVICE接口类
 * @author yingjie.fan
 * @version 1.0
 */
public interface ISpBusinessService extends BaseInterface {
	/* @interface model: 添加一条SpBusiness记录 */
	void insert(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException;
	
	/* @interface model: 添加一条SpBusiness记录 */
	void insert(SpBusiness spBusiness) throws WorkFlowException;

	/* @interface model: 更新一条SpBusiness记录 */
	void update(SpBusiness spBusiness, String[] calssIds, String[] calssPrivilege) throws WorkFlowException;

	/* @interface model: 删除一条SpBusiness记录 */
	void delete(String bId) throws WorkFlowException;
	
	/* @interface model: 删除一条SpBusiness记录 */
	void deleteByRelDetail(String relDetail) throws WorkFlowException;

	/* @interface model: 查询SpBusiness结果集,返回SpBusiness对象的集合 */
	List<SpBusiness> findSpBusiness(SpBusiness spBusiness) throws WorkFlowException;
	
	SpBusiness findSpBusinessById(String bid);
	
	SpBusiness findSpBusinessByIdAndBType(String bid, String bType);
	
	SpBusiness findSpBusinessByBcode(String bcode);
	
	SpBusiness findSpBusinessByRelDetail(String relDetail);
	
	List<SpBusiness> getBusinessType(SpBusiness spBusiness);

	List<Map<String,String>> getPagedBusinessType(SpProcedure model);

	List<Map<String,String>> getYwdl();
	
	String getTxyw();
}
