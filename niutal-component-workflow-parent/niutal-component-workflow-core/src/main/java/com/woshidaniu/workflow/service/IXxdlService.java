package com.woshidaniu.workflow.service;

import java.util.List;

import com.woshidaniu.workflow.model.XxdlModel;

/**
 * 
 * 类描述：消息队列接口类
 *
 * @version: 1.0
 * @author: jyy
 * @since: 2017-8-23 10:42:47
 */
public interface IXxdlService {
	
	/**
	 * 新增消息队列
	 * @param addXxdlInfo
	 */
	public void addXxdl(XxdlModel xxdl);
	
	
	/**
	 * 查询消息队列信息
	 * @param selectXxdlxxByxxzH
	 */
	public List<XxdlModel> selectXxdlxxByxxzH(XxdlModel xxdlModel);
	
	
	/**
	 * 删除消息队列信息(主要针对发给审核人的信息:中间节点的撤销，撤销发送到下一环节的消息)
	 * @param deleteXxdl
	 */
	public void deleteXxdl(XxdlModel xxdlModel);
	
	
	/**
	 * 逻辑删除消息队列信息(主要针对发送给申请人的消息:末端节点的撤销，撤销已经发送到申请人的消息)
	 * @param deleteSqrXxdl
	 */
	public void deleteSqrXxdl(XxdlModel xxdlModel);
	
	/**
	 * 逻辑删除消息队列信息(主要针对撤销消息队列信息:申请人发起的撤销)
	 * @param deleteSqrXxdl
	 */
	public void deleteXxdlBySqr(XxdlModel xxdlModel);
	
	
	/**
	 * 更新教务中队列的信息(如果发送给了一批人的消息，其中一个人已经审核通过，则把其它人收到的消息状态更新为：已处理)
	 * @param updateXxdlclzt
	 */
	public void updateXxdlclzt(XxdlModel xxdlModel);
	
}
