package com.woshidaniu.tjcx.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjbbModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public interface ITjbbService extends BaseService<TjxmModel> {


	/**
	 * 
	 * @描述:根据设置内容返回报表数据
	 * @作者：ligl
	 * @日期：2013-7-23 下午02:22:57
	 * @修改记录: 
	 * @param kzszModel
	 * @return
	 * TjbbModel 返回类型 
	 * @throws
	 */
	public TjbbModel getTjbb(KzszModel kzszModel) throws Exception;
	/**
	 * 
	 * @描述:根据项目代码查询相关数据
	 * @作者：ligl
	 * @日期：2013-7-23 下午02:22:57
	 * @修改记录: 
	 * @param tjxmModel
	 * @return
	 * KzszModel 返回类型 
	 * @throws
	 */
	public TjbbModel getSjByXmdm(KzszModel kzszModel) throws Exception;
}
