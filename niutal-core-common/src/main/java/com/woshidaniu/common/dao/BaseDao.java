package com.woshidaniu.common.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 类名称： 通用DAO接口
 * 创建人：Penghui.Qu
 * 创建时间：2012-5-3
 * 修改人：Zhenfei.Cao
 * 修改时间：2012-8-2
 */
public interface BaseDao <T> {

	/**
	 * 增加记录
	 * @param t
	 * @return
	 */
	public int insert(T t);
	
	/**
	 * 修改记录
	 * @param t
	 * @return
	 */
	public int update(T t);
	
	/**
	 * 删除记录
	 * @param id
	 * @return
	 */
	public int delete(String id);
	/**
	 * 删除记录
	 * @param t
	 * @return
	 */
	public int delete(T t);
	
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	public T getModel(String id);
	
	/**
	 * 查询单条数据
	 * @param t
	 * @return
	 */
	public T getModel(T t) ;
	
	/**
	 * 批量删除
	 * @param map
	 * @return
	 */
	public int batchDelete(Map<String,Object> map);
	
	
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public int batchDelete(List<?> list);
	
	/**
	 * 批量修改
	 * @param map
	 * @return
	 */
	public int batchUpdate(Map<String,Object> map);
	
	public int batchUpdate(List<T> list);
	
	/**
	 * 
	 *@描述：有数据范围的批量修改：多用于批量修改
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-15上午09:03:00
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int batchUpdateWrapByScope(T model);
	
	
	/**
	 * 分页查询
	 * @param t
	 * @return
	 */
	public List<T> getPagedList(T t);
	
	
	/**
	 * 无分页查询
	 * @param t
	 * @return
	 */
	public List<T> getModelList(T t);
	
	
	/**
	 * 无分页查询
	 * @param str
	 * @return
	 */
	public List<T> getModelList(String... str);
	
	/**
	 * 统计记录数
	 * @param t
	 * @return
	 */
	public int getCount(T t);
	
	/**
	 * 按数据范围分页查询
	 * @param t
	 * @return
	 */
	public List<T> getPagedByScope(T t);
	
	/**
	 * 按数据范围无分页查询
	 * @param t
	 * @return
	 */
	public List<T> getModelListByScope(T t);
	
	/**
	 * 通过指定key查询对应的唯一值
	 * @param key
	 * @return
	 */
	public String getValue(@Param("key") String key);
	
	/**
	 * 通过指定key查询多个值
	 * @param key
	 * @return
	 */
	public Map<String, String> getValues(@Param("key") String key);
	
}
