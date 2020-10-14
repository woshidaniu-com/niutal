package com.woshidaniu.workflow.dao;

import java.util.List;

import com.woshidaniu.annotation.BaseAnDao;
import com.woshidaniu.workflow.model.PendingAffairInfo;

/**
 * 
 * 类描述：待办事宜DAO类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-19 上午09:44:27
 */
public interface IPendingAffairInfoDao extends BaseAnDao<PendingAffairInfo,PendingAffairInfo>{
	
	/**
	 * 方法描述：通过业务ID修改代表事宜
	 * @param pendingAffairInfo
	 */
	public void modifyByYwId(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 
	 * 方法描述：通过角色ID获取代办事宜
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-13 上午11:46:46
	 */
	public List<PendingAffairInfo> findByRoleIds(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 
	 * 方法描述：通过人员ID获取代办事宜
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-13 上午11:46:38
	 */
	public List<PendingAffairInfo> findByUserId(PendingAffairInfo pendingAffairInfo);
	
}
