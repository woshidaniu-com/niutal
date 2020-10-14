/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.svcinterface;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.wjdc.dao.entites.FfdxModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.WjhdModel;

/**
 * @author Penghui.Qu(445)
 *   说明：问卷管理-Service
 * @author zhidong(1571)
 *   补充，整理，优化
 */
public interface IWjglService  extends BaseService<WjglModel>{
	/**
	 * @description	： 获得初始化后的分发配置模型对象列表
	 * @return
	 */
	public List<FfdxModel> getInitedFfdxModels();
	/**
	 * @description	：获得下一个问卷id
	 * @return
	 */
	String getNextWjid();
	/**
	 * @description	：删除问卷信息
	 * @param wjid 问卷ID
	 * @return boolean
	 */
	boolean scWjxx(String wjid) ;
	/**
	 * @description	： 保存问卷试题
	 * @param wjid
	 * @param stflList
	 * @param wjstList
	 * @param stxxList
	 * @return boolean
	 */
	boolean saveWjst(String wjid,List<Map<String,String>> stflList,List<Map<String,String>> wjstList,List<Map<String,String>> stxxList) ;
	/**
	 * @description	： 保存问卷分发
	 * @param wjid 问卷ID
	 * @param ffdx 发放对象
	 * @param gnid 业务功能
	 * @return boolean
	 */
	boolean saveWjff(String wjid ,String ffdx, String gnid);
	/**
	 * @description	： 获取发放人数
	 * @param ffdx 发放对象
	 * @return int
	 */
	int getFfrs(String ffdx);
	/**
	 * @description	： 用户已经完成的问卷id列表
	 * @param userid
	 * @return
	 */
	List<String> getFinishedWjidList(String userid);
	/**
	 * @description	： 用户未完成的问卷id列表
	 * @param userid
	 * @return
	 */
	List<String> getUnfinshWjidList(String userid);
	/**
	 * @description	： 用户未完成的问卷列表
	 * @param userid 用户id
	 * @return
	 */
	List<WjglModel> getUnfinshedWjList(String userid);
	/**
	 * @description	： 查询我的问卷
	 * @param userid
	 * @return list
	 */
	List<WjglModel> getWdwjList(String userid);
	/**
	 * @description	： 查询问卷发放
	 * @param wjid 问卷ID
	 * @return list
	 */
	List<Map<String,String>> getWjffList(String wjid);
	/**
	 * @description	： 查询问卷功能
	 * @param wjid 问卷ID
	 * @return list
	 */
	List<String> getWjgnList(String wjid);
	/**
	 * @description	： 获得问卷统计列表
	 * @param wjid
	 * @return
	 */
	List<Map<String,Object>> getWjtjList(String wjid);
	/**
	 * @description	： 批量删除一个答卷人的指定文件的回答的试题
	 * @param djrid
	 * @param wjid
	 * @param needDeleteStIds
	 * @return
	 */
	void deleteBatchYhdjStxx(String djrid, String wjid, Set<String> stids);
	
	/**
	 * @description	： 保存用户提交用户答卷
	 * @param user
	 * @param wjModel
	 * @param request
	 * @param ajax 是否是ajax方式调用本方法
	 * @return
	 */
	boolean saveSubmitYhdj(User user, WjglModel wjModel,HttpServletRequest request,boolean ajax);
	
	/**
	 * 更新提交完成状态
	 * @param user
	 * @param wjid
	 * @return
	 */
	boolean updateSubmitFinish(User user,String wjid);

	/**
	 * @description	： 保存用户回答的多选题的排序
	 * @param user
	 * @param wjModel
	 * @param request
	 * @return
	 */
	boolean saveAjaxSubmitSort(User user, String wjid, String stid, String xxids);
	/**
	 * @description	： 查询多选题排序
	 * @param user
	 * @param wjid
	 * @param stid
	 * @return
	 */
	List<WjhdModel> queryDxSort(User user, String wjid, String stid);
	
	/**
	 * @description	： 插入拷贝问卷
	 * @param wjid
	 * @param newWjmc
	 * @param user
	 * @return
	 */
	boolean insertCopyWj(WjglModel model);
	
}
