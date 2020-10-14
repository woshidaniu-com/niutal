package com.woshidaniu.zdybd.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.zdybd.dao.entities.FlszModel;
import com.woshidaniu.zdybd.dao.entities.GnszModel;

/**
 * 
 * @系统名称: 新框架
 * @模块名称:自定义表单
 * @类功能描述: 分类设置
 * @作者： ligl
 * @时间： 2014-2-18 上午09:33:58
 * @版本： V1.0
 * @修改记录:
 */
public interface IFlszService extends BaseService<FlszModel> {

	/**
	 * 
	 * @描述:根据功能代码获取分类列表数据
	 * @作者：ligl
	 * @日期：2014-2-18 上午09:34:23
	 * @修改记录:
	 * @param gnszModel
	 * @return
	 * @throws Exception
	 *             List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<FlszModel> getListByGndm(GnszModel gnszModel)
			throws Exception;

	/**
	 * 
	 * @描述:根据功能代码获取分类列表数据,src
	 * @作者：ligl
	 * @日期：2014-2-18 上午09:35:51
	 * @修改记录:
	 * @param gnszModel
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<String> getSrcListByGndm(GnszModel gnszModel) throws Exception;

}
