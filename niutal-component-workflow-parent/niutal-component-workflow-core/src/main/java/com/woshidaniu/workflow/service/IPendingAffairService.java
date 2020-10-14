package com.woshidaniu.workflow.service;

import java.util.List;

import com.woshidaniu.common.log.User;
import com.woshidaniu.workflow.model.PendingAffairInfo;

/**
 * 
 * 类描述：待办事宜接口类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-19 上午09:42:02
 */
public interface IPendingAffairService {
	
	/**
	 * 获得待办事宜列表
	 * @param user
	 * @return
	 */
	public List<PendingAffairInfo> getListByUser(User user);
	/**
	 * 通过角色获取
	 * @param roleIds
	 * @return
	 */
	public List<PendingAffairInfo> getListByRoles(List<String> roleIds);
	
	/**
	 * 获得待办事宜列表
	 * @param query
	 * @return
	 */
	public List<PendingAffairInfo> getListByQuery(PendingAffairInfo query);
	
	/**
	 * 获得待办事宜列表
	 * @param id
	 * @return
	 */
	public PendingAffairInfo getById(String id);
	
	/**
	 * 新增代表事宜
	 * @param pendingAffairInfo
	 */
	public void addPendingAffairInfo(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 修改代表事宜
	 * @param pendingAffairInfo
	 */
	public void modifyPendingAffairInfo(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 通过业务ID修改代表事宜
	 * @param pendingAffairInfo
	 */
	public void modifyByYwId(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 删除代表事宜
	 * @param pendingAffairInfo
	 */
	public void removePendingAffairInfo(String id);
	
	/**
	 * 	通过业务ID删除代表事宜
	 * @param pendingAffairInfo
	 */
	public void removePendingAffairInfoByBid(String bid);
}
