/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.XxglModel;

/**
 * @author 		：康康（1571）
 * @description	： 试题管理服务
 */
public interface IStglService  extends BaseService<StglModel>{
	/**
	 * <p>方法说明：插入试题信息<p>
	 * @param list 试题信息
	 * @return int
	 */
	int insertStxx(List<?> list) ;
	/**
	 * <p>方法说明：插入试题选项信息<p>
	 * @param list 试题选项
	 * @return int
	 */
	int insertXxxx(List<?> list);
	/**
	 * 获取试题和试题大类排序后的列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	List<StglModel> getStxxAndStdlXxList(WjglModel model);
	/**
	 * 获取用户答卷的试题和试题大类排序后的列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	List<StglModel> getYhdjStxxAndStdlXxList(WjglModel model);
	/**
	 * 获取试题选项信息列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	List<XxglModel> getStXxxxList(WjglModel model);
	/**
	 * @description	： 获取用户答卷试题的选项列表
	 * @author 		： 康康（1571）
	 * @date 		：2018年8月3日 上午9:53:10
	 * @param wjModel
	 * @return
	 */
	List<XxglModel> getYhdjStXxxxList(WjglModel wjModel);
	/**
	 * @description	： 获取单选组合，多选组合最后一个选项的input标签的内容
	 * @author 		： 康康（1571）
	 * @date 		：2018年8月3日 上午11:26:24
	 * @param wjid 问卷id
	 * @param djrid 答卷人id
	 * @return
	 */
	Map<String,Map<String,String>> getStLastOptionValues(String wjid,String djrid);
	
	/**
	 * <p>方法说明：删除问卷试题分类<p>
	 * @param wjid  问卷ID
	 * @return int
	 */
	int deleteStfl(String wjid);
	
	/**
	 * <p>方法说明：删除问卷试题<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	int deleteWjSt(String wjid);
	
	/**
	 * <p>方法说明：删除问卷试题选项<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	int deleteWjStXx(String wjid);
	
	/**
	 * <p>方法说明：删除问卷回答<p>
	 * @param wjid 问卷ID
	 * @return int
	 */
	int deleteWjhd(String wjid);
	
	/**
	 * <p>方法说明：获取试题信息列表<p>
	 * @param model
	 * @return
	 */
	List<StglModel> getStxxList(String wjid);
}
