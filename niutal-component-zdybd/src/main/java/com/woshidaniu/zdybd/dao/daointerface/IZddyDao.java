package com.woshidaniu.zdybd.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zdybd.dao.entities.GnszModel;
import com.woshidaniu.zdybd.dao.entities.ZddyModel;

/**
 * 
 * @系统名称: 新框架
 * @模块名称:自定义表单
 * @类功能描述: 字段定义
 * @作者： ligl
 * @时间： 2014-2-18 上午09:36:54
 * @版本： V1.0
 * @修改记录:
 */
@Repository(value="zddyDao")
public interface IZddyDao extends BaseDao<ZddyModel> {

	/**
	 * 
	 * @描述:根据功能代码获取所有分类下的字段列表
	 * @作者：ligl
	 * @日期：2014-2-18 上午09:37:13
	 * @修改记录:
	 * @param gnszModel
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<ZddyModel> getListByGndm(GnszModel gnszModel);

	/**
	 * 
	 * @描述:根据功能代码获取所有分类下的字段列表，全改为仅显示状态
	 * @作者：ligl
	 * @日期：2014-2-18 上午09:38:46
	 * @修改记录:
	 * @param gnszModel
	 * @return
	 * @throws Exception
	 *             List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<ZddyModel> getListByGndmOfXs(GnszModel gnszModel)
			throws Exception;
}
