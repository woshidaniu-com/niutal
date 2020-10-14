package com.woshidaniu.tjcx.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import com.woshidaniu.common.dao.BaseDao;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 基础Dao
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:27:26
 * @版本： V1.0
 * @修改记录:
 */
public interface ITjcxBaseDao extends BaseDao<Object> {
	
	/**
	 * 
	 * <p>方法说明：获取数据条数<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年8月18日下午8:39:05<p>
	 */
	public int getDataCount(HashMap<String, String> map);
	
	/**
	 * 
	 * @描述:通过sql语句返回数据
	 * @作者：ligl
	 * @日期：2013-7-22 下午04:56:08
	 * @修改记录:
	 * @param kzszModel
	 * @return ist<HashMap<String,String>> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> getListBySql(HashMap<String, String> map);
	/**
	 * 
	 * @描述:通过sql语句返回数据，自动分页
	 * @作者：ligl
	 * @日期：2013-7-22 下午04:56:08
	 * @修改记录:
	 * @param kzszModel
	 * @return ist<HashMap<String,String>> 返回类型
	 * @throws
	 */
	//@SuppressWarnings("rawtypes")
	//public List<HashMap> getPagedListBySql(HashMap<String, String> map);
	
	/**
	 * 
	 * @描述:通过sql语句返回数据,过滤数据范围
	 * @作者：ligl
	 * @日期：2013-7-22 下午04:56:08
	 * @修改记录:
	 * @param kzszModel
	 * @return ist<HashMap<String,String>> 返回类型
	 * @throws
	 */
	//@SuppressWarnings("rawtypes")
	//public List<HashMap> getListBySqlByScope(HashMap<String, String> map);
	
	/**
	 * 
	 * @描述:通过sql语句返回数据
	 * @作者：ligl
	 * @日期：2013-7-22 下午04:56:08
	 * @修改记录:
	 * @param kzszModel
	 * @return ist<HashMap<String,String>> 返回类型
	 * @throws
	 */
	public HashMap<String, String> getMapBySql(HashMap<String, String> map);
	
	/**
	 * 
	 * @描述:通过sql语句返回数据,过滤数据范围
	 * @作者：ligl
	 * @日期：2013-7-22 下午04:56:08
	 * @修改记录:
	 * @param kzszModel
	 * @return ist<HashMap<String,String>> 返回类型
	 * @throws
	 */
	//public HashMap<String, String> getMapBySqlByScope(HashMap<String, String> map);
	
	/**
	 * 
	 * @描述:数据库取值类型,获取代码名称
	 * @作者：ligl
	 * @日期：2013-7-24 上午08:56:23
	 * @修改记录:
	 * @param map
	 * @return List<DmmcModel>
	 * @throws
	 */
	//@SuppressWarnings("rawtypes")
	//public List<HashMap> getDmMc(HashMap<String, String> map);
}
