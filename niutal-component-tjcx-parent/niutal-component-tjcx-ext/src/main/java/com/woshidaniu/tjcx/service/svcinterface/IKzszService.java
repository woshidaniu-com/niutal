package com.woshidaniu.tjcx.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.tjcx.dao.entites.KzszModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 快照设置service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public interface IKzszService extends BaseService<KzszModel> {

	/**
	 * 
	 * @描述:删除查询快照
	 * @作者：ligl
	 * @日期：2013-8-23 上午09:53:23
	 * @修改记录: 
	 * @param model
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public boolean deleteCxkz(KzszModel model) throws Exception;
}
