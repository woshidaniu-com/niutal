package com.woshidaniu.zdybd.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zdybd.dao.entities.DmmcModel;

/**
 * 
 * @系统名称: 新框架
 * @模块名称:自定义表单
 * @类功能描述: 通用类
 * @作者： ligl
 * @时间： 2014-2-18 上午09:55:32
 * @版本： V1.0
 * @修改记录:
 */
@Repository(value="zddyBaseDao")
public interface IZdybdBaseDao extends BaseDao<DmmcModel> {

	/**
	 * 
	 * @描述:通过sql获取列表
	 * @作者：ligl
	 * @日期：2014-2-18 上午10:00:40
	 * @修改记录:
	 * @param map
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<HashMap<String, String>> getListBySql(
			HashMap<String, String> map);
	
	

	/**
	 * 
	 * @描述:通过sql获取列表
	 * @作者：ligl
	 * @日期：2014-2-18 上午10:00:40
	 * @修改记录:
	 * @param map
	 * @return List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<DmmcModel> getDmmcListBySql(
			HashMap<String, String> map);

	/**
	 * 
	 * @描述:获得省份列表
	 * @作者：ligl
	 * @日期：2014-2-18 上午10:00:40
	 * @修改记录:
	 * @return
	 * @throws Exception
	 *             List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<HashMap<String, String>> getShList(@Param(value = "sql") String sql);

	/**
	 * 
	 * @描述:获得区县列表
	 * @作者：ligl
	 * @日期：2014-2-18 上午10:00:40
	 * @修改记录:
	 * @return
	 * @throws Exception
	 *             List<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public List<HashMap<String, String>> getQxList(@Param(value = "sql") String sql);
}
