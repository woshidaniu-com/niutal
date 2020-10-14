/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.wjdc.dao.entites.DjrffModel;

/**
 * @author 		：康康（1571）
 * @description	：  答卷人分发Dao
 */
@Repository("djrffDao")
public interface IDjrffDao {
	/**
	 * @description	：批量 插入新记录
	 * @param model
	 * @return
	 */
	public int insertBatch(@Param(value = "models")List<DjrffModel> models);
	/**
	 * @description	： 安全地插入新记录
	 * @param model
	 * @return
	 */
	public int insertSafe(DjrffModel model);
	/**
	 * @description	： 插入新记录
	 * @param model
	 * @return
	 */
	public int insert(DjrffModel model);
	/**
	 * @description	： 更新记录
	 * @param model
	 * @return
	 */
	public int update(DjrffModel model);
	/**
	 * 查询主键值
	 * @param model
	 * @return
	 */
	public List<String> queryZjzList(DjrffModel model);
	/**
	 * @description	： 查询记录
	 * @param model
	 * @return
	 */
	public List<DjrffModel> query(DjrffModel model);
	/**
	 * @description	：删除所有明细信息
	 * @param wjid
	 */
	public void deleteAll(@Param("wjid")String wjid);
	/**
	 * @description	：查询未完成问卷id列表
	 * @param wjid
	 * @return
	 */
	public List<String> queryWjidList(@Param("userid")String userid);
	/**
	 * @description	：查询未完成问卷id列表
	 * @param wjid
	 * @return
	 */
	public List<String> queryUnfinishWjidList(@Param("userid")String userid);
	/**
	 * @description	： 查询已经完成的问卷id列表
	 * @param userid
	 * @return
	 */
	public List<String> queryFinisedhWjidList(@Param("userid")String userid);
	
	/**
	 * 更新答卷人分发信息为答卷完成
	 * @return
	 */
	public int updateFinishZt(@Param("zjz")String zjz,@Param("wjid")String wjid,@Param("lxid")String lxid);
	
	/**
	 * 更新答卷人分发信息为正在答卷
	 * @return
	 */
	public int updateDoingZt(@Param("zjz")String zjz,@Param("wjid")String wjid,@Param("lxid")String lxid);
}