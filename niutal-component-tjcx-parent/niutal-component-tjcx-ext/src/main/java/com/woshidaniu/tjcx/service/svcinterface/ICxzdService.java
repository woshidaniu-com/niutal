package com.woshidaniu.tjcx.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.YsfModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 查询字段service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public interface ICxzdService extends BaseService<CxzdModel> {
	
	/**
	 * 
	 * @描述:取所有运算符
	 * @作者：ligl
	 * @日期：2013-8-28 上午10:24:41
	 * @修改记录: 
	 * @return
	 * List<YsfModel> 返回类型 
	 * @throws
	 */
	public List<YsfModel> getAllYsfList() throws Exception ;
	
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
