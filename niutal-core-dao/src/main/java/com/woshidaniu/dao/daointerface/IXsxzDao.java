package com.woshidaniu.dao.daointerface;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.XsxzModel;

/**
 * 学生选择信息
 * @author Jiangdong.Yi
 *
 */
@Repository("xsxzDao")
public interface IXsxzDao extends BaseDao<XsxzModel> {
	/**
	 * 查询学生信息列表
	 * @param hashMap
	 * @return
	 */
	public List<XsxzModel> getModelList(HashMap<String, Object> hashMap);
}
