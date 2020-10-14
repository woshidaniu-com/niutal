package com.woshidaniu.tjcx.dao.daointerface;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.tjcx.dao.entites.KzszModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 快照设置Dao
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:27:26
 * @版本： V1.0
 * @修改记录:
 */
public interface IKzszDao extends BaseDao<KzszModel> {
	/**
	 * 
	 * @描述:删除查询快照
	 * @作者：ligl
	 * @日期：2013-8-23 上午09:53:23
	 * @修改记录: 
	 * @param model
	 * @return
	 * @throws Exception
	 * int 返回类型 
	 * @throws
	 */
	public int deleteCxkz(KzszModel model) throws Exception;

}
