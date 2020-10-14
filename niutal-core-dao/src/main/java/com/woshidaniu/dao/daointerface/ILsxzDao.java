package com.woshidaniu.dao.daointerface;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.LsxzModel;

/**
 * 老师选择信息
 * @author Jiangdong.Yi
 *
 */
@Repository("lsxzDao")
public interface ILsxzDao extends BaseDao<LsxzModel> {
	/**
	 * 老师选择信息
	 * @param hashMap
	 * @return
	 */
	public List<LsxzModel> getModelList(HashMap<String, Object> hashMap);
	
	/**
	 * 获取老师信息列表
	 * @param model
	 * @return
	 */
	public List<LsxzModel> getLsxxList(LsxzModel model);
	
	/**
	 * 获取老师信息列表【2015.10.16模糊查询】
	 * @param model
	 * @return
	 */
	public List<Map<String, String>> likeQueryLsxxList(HashMap<String, Object> map);
}
