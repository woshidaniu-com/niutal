package com.woshidaniu.tjcx.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.tjcx.dao.entites.TsxdyModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 特殊项定义Dao
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:27:26
 * @版本： V1.0
 * @修改记录:
 */
public interface ITsxdyDao extends BaseDao<TsxdyModel> {

	/**
	 * 
	 * @描述:获取已选择的项 
	 * @作者：ligl
	 * @日期：2013-7-24 下午06:58:23
	 * @修改记录: 
	 * @param map
	 * @return
	 * List<TsxdyModel> 返回类型 
	 * @throws
	 */
	public List<TsxdyModel> getListByTj(HashMap<String, String> map);
}
