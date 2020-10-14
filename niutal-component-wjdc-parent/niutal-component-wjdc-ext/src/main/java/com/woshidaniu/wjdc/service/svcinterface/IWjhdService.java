package com.woshidaniu.wjdc.service.svcinterface;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.wjdc.dao.entites.WjhdModel;

/**
 * 问卷回答服务
 * @author 1571
 * 
 * 这里我们encache缓存用户的所有操作，包括更新操作，删除操作，插入操作
 * 
 * 当调用updateFinishZt方法的时候，就是释放这些缓存的操作的时机
 * 
 * 这里要把所有的问卷回答放入内存，那么我们考虑一下容量，假设一份问卷调查的上限的200题，且一个学校有10000人答题，那么就要200万个对象条目！！！
 * 也就是200万个WjhdModel,所以说，需要nginx负载均衡一下,设置成1个nginx和4个tomcat
 *
 */
//TODO
public interface IWjhdService {
	
	/**
	 * 保存用户答卷
	 * @param paramsList
	 * @return
	 */
	int saveYhdj(String wjid,String djrid,List<Map<String, String>> paramsList);
	
	/**
	 * 更新答题顺序
	 * @param sortParam
	 * @return
	 */
	boolean updateDxstSort(@Param(value = "sortParam")Map<String, String> sortParam);
	
	
	int deleteBatchYhdjStxx(@Param(value = "wjid")String wjid,@Param(value = "djrid")String djrid,@Param(value = "stids")Set<String> stids);
	
	/**
	 * 更新答卷人分发表的完成状态，此时释放我们的所有缓存的操作
	 * @param zjz
	 * @param wjid
	 * @param lxid
	 * @return
	 */
	int updateFinishZt(@Param("wjid")String wjid,@Param("djrid")String djrid,@Param("lxid")String lxid);

	/**
	 * 查询问卷回答
	 * @param wjid
	 * @param djrid
	 * @return
	 */
	List<WjhdModel> queryWjhd(@Param("wjid")String wjid,@Param("djrid")String djrid);
	
	/**
	 * 查询问卷试题回答
	 * @param wjid
	 * @param stid
	 * @param djrid
	 * @return
	 */
	List<WjhdModel> queryWjSthd(@Param("wjid")String wjid,@Param("djrid")String djrid,@Param("stid")String stid);
}
