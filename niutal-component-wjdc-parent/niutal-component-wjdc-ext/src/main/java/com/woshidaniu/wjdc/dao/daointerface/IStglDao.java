/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.XxglModel;

/**
 * @author Penghui.Qu(445)
 * 试题管理DAO
 * 
  * @author ：康康（1571）
 * 整理，优化
 * */
@Repository("stglDao")
public interface IStglDao  extends BaseDao<StglModel>{
	/**
	 * <p>方法说明：插入试题信息<p>
	 * @param list 试题信息
	 * @return int
	 */
	public int insertStxx(List<?> list) ;
	/**
	 * <p>方法说明：插入试题选项信息<p>
	 * @param list 试题选项
	 * @return int
	 */
	public int insertXxxx(List<?> list);
	/**
	 * <p>方法说明：删除问卷试题<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	public int deleteWjSt(String wjid) ;
	/**
	 * <p>方法说明：删除问卷试题选项<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	public int deleteWjStXx(String wjid);
	/**
	 * <p>方法说明：删除问卷回答<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	public int deleteWjhd(String wjid) ;
	/**
	 * <p>方法说明：删除问卷试题分类<p>
	 * @param wjid  问卷ID
	 * @return int
	 */
	public int deleteStfl(String wjid) ;
	/**
	 * <p>方法说明：获取试题和试题大类排序后的列表<p>
	 * @param model
	 * @return
	 */
	public List<StglModel> getStxxAndStdlXxList(WjglModel model);
	/**
	 * @description	： 获得用户答卷试题大类排序后的列表
	 * @param model
	 * @return
	 */
	public List<StglModel> getYhdjStxxAndStdlXxList(WjglModel model);
	/**
	 * <p>方法说明：获取试题选项信息列表<p>
	 * @param model
	 * @return
	 */
	public List<XxglModel> getStXxxxList(WjglModel model);
	/**
	 * @description	： 获得用户答卷选项列表
	 * @param model
	 * @return
	 */
	public List<XxglModel> getYhdjStXxxxList(WjglModel model);
	/**
	 * @description	： 获取单选组合，多选组合最后一个选项的input标签的内容
	 * @param wjid
	 * @return
	 */
	@MapKey("stid")
	public Map<String,Map<String,String>> getStLastOptionValues(@Param("wjid")String wjid,@Param("djrid")String djrid);
	/**
	 * <p>方法说明：获取试题信息列表<p>
	 * @param model
	 * @return
	 */
	public List<StglModel> getStxxList(String wjid);
}
