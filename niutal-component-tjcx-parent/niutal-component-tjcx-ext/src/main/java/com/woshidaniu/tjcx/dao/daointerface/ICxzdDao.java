package com.woshidaniu.tjcx.dao.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.YsfModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 查询字段Dao
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:27:26
 * @版本： V1.0
 * @修改记录:
 */
public interface ICxzdDao extends BaseDao<CxzdModel> {


	/**
	 * 
	 * @描述:取运算符
	 * @作者：ligl
	 * @日期：2013-7-24 上午08:56:23
	 * @修改记录:
	 * @param map
	 * @return List<DmmcModel>
	 * @throws
	 */
	public List<YsfModel> getYsf(CxzdModel model);

	public List<YsfModel> getAllYsf();

	/**
	 * 
	 * @描述:取所有子类
	 * @作者：ligl
	 * @日期：2013-9-24 下午02:43:33
	 * @修改记录: 
	 * @param model
	 * @return
	 * List<CxzdModel> 返回类型 
	 * @throws
	 */
	public List<CxzdModel> getChildrenList(CxzdModel model);

}
