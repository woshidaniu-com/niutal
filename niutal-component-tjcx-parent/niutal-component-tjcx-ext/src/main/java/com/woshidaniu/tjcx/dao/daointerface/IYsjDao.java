package com.woshidaniu.tjcx.dao.daointerface;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.YsjModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 源数据Dao
 * @类功能描述: 
 * @作者： ligl 
 * @时间： 2013-7-22 下午04:27:26 
 * @版本： V1.0
 * @修改记录:
 */
public interface IYsjDao extends BaseDao<YsjModel> {
	/**
	 * 
	 * @描述:根据项目代码查询所有记录
	 * @作者：ligl
	 * @日期：2013-7-22 下午04:56:08
	 * @修改记录: 
	 * @param gnbh
	 * @return
	 * List<TjxmModel> 返回类型 
	 * @throws
	 */
	public YsjModel getModel(KzszModel kzszModel);
}
