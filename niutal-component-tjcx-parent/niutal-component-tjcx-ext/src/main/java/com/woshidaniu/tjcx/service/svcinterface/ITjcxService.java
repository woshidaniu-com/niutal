package com.woshidaniu.tjcx.service.svcinterface;

import java.util.HashMap;
import java.util.List;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjcxModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计查询service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public interface ITjcxService extends BaseService<TjcxModel> {

	/**
	 * 
	 * @描述:得到某一项目的统计查询列表
	 * @作者：ligl
	 * @日期：2013-8-26 下午02:07:54
	 * @修改记录:
	 * @param kzszModel
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> getTjcxsj(QueryModel queryModel,KzszModel kzszModel)  throws Exception;
	
	/**
	 * 
	 * @描述:得到某一项目的统计查询列表，不分页
	 * @作者：ligl
	 * @日期：2013-8-26 下午02:07:54
	 * @修改记录:
	 * @param kzszModel
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> getTjcxsjNoPage(KzszModel kzszModel) throws Exception;
	
	
	/**
	 * 得到某一项目的统计查询记录数
	 * @param kzszModel
	 * @return
	 * @throws Exception
	 */
	public int getTjcxsjCountNoPage(KzszModel kzszModel)throws Exception;

}
