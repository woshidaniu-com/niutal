package com.woshidaniu.zdybd.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zdybd.dao.entities.FlszModel;
import com.woshidaniu.zdybd.dao.entities.GnszModel;

/**
 * 
 * @系统名称: 新框架
 * @模块名称:自定义表单
 * @类功能描述: 分类设置
 * @作者： ligl
 * @时间： 2014-2-18 上午11:26:39
 * @版本： V1.0
 * @修改记录:
 */
@Repository(value="flszDao")
public interface IFlszDao extends BaseDao<FlszModel> {

	/**
	 * 
	 * @描述:根据功能代码获取分类列表数据
	 * @作者：ligl
	 * @日期：2014-2-18 上午11:27:22
	 * @修改记录:
	 * @param gnszModel
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<FlszModel> getListByGndm(GnszModel gnszModel);
}
