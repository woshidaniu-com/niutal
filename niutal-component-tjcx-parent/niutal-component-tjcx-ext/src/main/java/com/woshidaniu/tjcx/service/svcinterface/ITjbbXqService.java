package com.woshidaniu.tjcx.service.svcinterface;

import java.util.HashMap;
import java.util.List;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表信息详情service
 * @类功能描述:
 * @作者： ligl
 * @时间： 2014-05-20 下午06:38:37
 * @版本： V1.0
 * @修改记录:
 */
public interface ITjbbXqService extends BaseService<TjxmModel> {

	/**
	 * 获得统计报表信息详情
	 * 
	 * @param kzszModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> getTjbbXq(QueryModel queryModel, KzszModel kzszModel)
			throws Exception;

	/**
	 * 得到配置的查询字段
	 */
	public List<CxzdModel> getCxzdBt(KzszModel kzszModel);
}
