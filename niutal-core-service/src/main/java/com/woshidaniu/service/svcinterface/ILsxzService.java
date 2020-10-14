package com.woshidaniu.service.svcinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.LsxzModel;

/**
 * 选择老师信息
 * @author Jiangdong.Yi
 *
 */
public interface ILsxzService extends BaseService<LsxzModel> {
	/**
	 * 查询老师信息列表
	 * @param String
	 * @return
	 */
	public List<LsxzModel> getModelList(String ids);
	
	/**
	 * 获取老师信息列表
	 * @param model
	 * @return
	 */
	public List<LsxzModel> getLsxxList(LsxzModel model);
	
	/**
	 * 获取老师选择根据职工号
	 * @param zgh
	 * @return
	 */
	public LsxzModel getLsxzByZgh(String zgh);
	
	/**
	 * 获取老师信息列表【2015.10.16模糊查询】
	 * @param model
	 * @return
	 */
	public List<Map<String, String>> likeQueryLsxxList(HashMap<String, Object> map);
}
