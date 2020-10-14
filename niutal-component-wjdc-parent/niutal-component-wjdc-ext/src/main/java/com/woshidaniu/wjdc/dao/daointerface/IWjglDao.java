/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.wjdc.dao.entites.WjffmxModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;

import net.sf.json.JSONArray;

/**
 * @author Penghui.Qu(445)
 * 功能实现,问卷管理DAO
 * 
 * @author ：康康（1571）
 * 整理，优化
 * 
 * */
@Repository("wjglDao")
public interface IWjglDao  extends BaseDao<WjglModel>{
	/**
	 * 根据问卷id集合查询问卷model
	 * @param wjids
	 * @return
	 */
	List<WjglModel> getModelsByWjids(@Param("wjids")List<String> wjids);
	/**
	 * 删除问卷信息
	 * @param wjid 问卷ID
	 * @return int
	 */
	int delete(@Param("wjid")String wjid) ;
	/**
	 * 保存用户答卷其中的一条记录
	 * @param paramsList
	 * @return
	 */
	int saveYhdjOneRecord(Map<String, String> param);
	/**
	 * <p>方法说明：保存用户答卷<p>
	 * @param paramsList 答题信息
	 * @return boolean
	 */
	int saveYhdj(@Param(value = "paramsList") List<Map<String,String>> paramsList);
	/**
	 * @description	： 删除用户答卷信息
	 * @param wjid
	 */
	void deleteYhdj(@Param("wjid")String wjid);
	/**
	 * <p>方法说明：查询已分发问卷<p>
	 * @return list
	 */
	List<WjglModel> getFfwjList(@Param("djrid")String djrid);
	/**
	 * <p>方法说明：查询问卷发放<p>
	 * @param wjid 问卷ID
	 * @return list
	 */
	List<Map<String,String>> getWjffList(@Param("wjid")String wjid);
	/**
	 * <p>方法说明：查询问卷功能<p>
	 * @param wjid 问卷ID
	 * @return list
	 */
	List<String> getWjgnList(@Param("wjid")String wjid);
	/**
	 * <p>方法说明：删除问卷分发<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	int deleteWjff(@Param("wjid")String wjid);
	/**
	 * <p>方法说明：保存问卷分发明细<p>
	 * @param wjid 问卷ID
	 * @param ffdx 发放对象
	 * @return int 
	 */
	int saveWjff(@Param(value = "wjid")String wjid,@Param(value = "list") JSONArray ffdx);
	/**
	 * <p>方法说明：删除业务绑定<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	int deleteYwbd(@Param("wjid")String wjid);
	/**
	 * <p>方法说明：保存业务绑定<p>
	 * @param wjid 问卷ID
	 * @param gnid 业务功能
	 * @return int
	 */
	int saveYwbd(@Param(value = "wjid")String wjid,@Param(value = "clsx")String clsx,@Param(value = "gnids") String[] gnid);
	
	/**
	 * <p>方法说明：更新业务绑定的问卷处理顺序<p>
	 * @param wjid 问卷ID
	 * @param clsx 处理顺序
	 * @return int
	 */
	int updateYwbdWjClsx(@Param(value = "wjid")String wjid,@Param(value = "clsx")String clsx);
	
	/**
	 * <p>方法说明：查询发放人<p>
	 * @param sql
	 * @param param
	 * @return
	 */
	List<String> getFfrs(@Param(value = "sql")String sql);
	/**
	 * @description	： 查询发放人数量
	 * @param sql
	 * @return
	 */
	int getFfrsCount(@Param(value = "sql")String sql);
	/**
	 * <p>方法说明：查询答题详情<p>
	 * @param wjid
	 * @return
	 */
	List<Map<String,String>> getDtxqList(@Param("wjid")String wjid);
	/**
	 * <p>方法说明：问卷统计<p>
	 * @param wjid
	 * @return
	 */
	List<Map<String,Object>> getWjtjList(@Param("wjid")String wjid);
	/**
	 * @description	： 批量删除指定的问卷人id的问卷的回答的试题
	 * @param djrid
	 * @param wjid
	 * @param stids
	 * @return
	 */
	int deleteBatchYhdjStxx(@Param(value = "djrid")String djrid, @Param(value = "wjid")String wjid, @Param(value = "stids")Set<String> stids);
	/**
	 * 
	 * @description	： 更新多选试题回答的排序
	 * @param sortParam
	 * @return
	 */
	boolean updateDxstSort(@Param(value = "sortParam")Map<String, String> sortParam);
	
	/**
	 * @description	： 获得问卷分发明细列表
	 * @param wjid
	 * @return
	 */
	List<WjffmxModel> getWjffmx(@Param("wjid")String wjid);
	
	List<String> getStringList(@Param(value = "sql")String sql);
	
}
