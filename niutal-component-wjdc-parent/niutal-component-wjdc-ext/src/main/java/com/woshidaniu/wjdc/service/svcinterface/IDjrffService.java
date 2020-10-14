package com.woshidaniu.wjdc.service.svcinterface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.wjdc.dao.entites.DjrffModel;

/**
 * 答卷人分发服务
 * @author 1571
 *
 */
public interface IDjrffService {

	/**
	 * @description	：批量 插入新记录
	 * @param model
	 * @return
	 */
	int insertBatch(@Param(value = "models")List<DjrffModel> models);
	/**
	 * @description	： 插入新记录
	 * @param model
	 * @return
	 */
	int insertSafe(DjrffModel model);
	/**
	 * @description	： 插入新记录
	 * @param model
	 * @return
	 */
	int insert(DjrffModel model);
	/**
	 * @description	： 更新记录
	 * @param model
	 * @return
	 */
	int update(DjrffModel model);
	/**
	 * 查询主键值
	 * @param model
	 * @return
	 */
	List<String> queryZjzList(DjrffModel model);
	/**
	 * @description	： 查询记录
	 * @param model
	 * @return
	 */
	List<DjrffModel> query(DjrffModel model);
	/**
	 * @description	：删除所有明细信息
	 * @param wjid
	 */
	void deleteAll(@Param("wjid")String wjid);
	/**
	 * @description	：查询未完成问卷id列表
	 * @param wjid
	 * @return
	 */
	List<String> queryWjidList(@Param("userid")String userid);
	/**
	 * @description	：查询未完成问卷id列表
	 * @param wjid
	 * @return
	 */
	List<String> queryUnfinishWjidList(@Param("userid")String userid);
	/**
	 * @description	： 查询已经完成的问卷id列表
	 * @param userid
	 * @return
	 */
	List<String> queryFinisedhWjidList(@Param("userid")String userid);
	
	/**
	 * 更新答卷人分发信息为答卷完成
	 * @return
	 */
	int updateFinishZt(@Param("zjz")String zjz,@Param("wjid")String wjid,@Param("lxid")String lxid);
	
	/**
	 * 更新答卷人分发信息为正在答卷
	 * @return
	 */
	int updateDoingZt(@Param("zjz")String zjz,@Param("wjid")String wjid,@Param("lxid")String lxid);
	/**
	 * 根据主键值查询
	 * @param queryModel
	 * @return
	 */
	List<DjrffModel> queryByZjz(String zjz);
	/**
	 * 根据问卷id和主键值查询
	 * @param model
	 * @return
	 */
	List<DjrffModel> queryByWjidAndZjz(String wjid,String zjz);
}
