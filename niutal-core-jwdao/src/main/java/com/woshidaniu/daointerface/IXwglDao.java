package com.woshidaniu.daointerface;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.XwglModel;

/**
 * 
* 
* 类名称：XwglDao 
* 类描述： 新闻管理
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public interface IXwglDao extends BaseDao<XwglModel>{
	
	/**
	 *@描述：判断通知是否可以删除
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-22上午10:17:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param ids
	 *@return
	 */
	public int canDelete(@Param(value="ids")String ids);
	
	public void getList();
	
	/**
	 * 
	 * @description: 记录查阅新闻（通知）信息
	 * @author : kangzhidong
	 * @date : 2014-3-25
	 * @time : 上午11:42:54 
	 * @param model
	 * @return
	 */
	public int insertNewsLog(XwglModel model);
	/**
	 * 
	 * @description: 插入面向学生对象
	 * @author : m
	 * @date : 2014-10-22
	 * @time : 上午11:42:54 
	 * @param model
	 * @return
	 */
	public int insertXsdx(XwglModel model);
	/**
	 * 
	 * @description: 插入面向教师对象
	 * @author : m
	 * @date : 2014-10-22
	 * @time : 上午11:42:54 
	 * @param model
	 * @return
	 */
	public int insertJsdx(XwglModel model);
	/**
	 * 
	 * @description: 插入面向岗位对象
	 * @author : m
	 * @date : 2014-10-22
	 * @time : 上午11:42:54 
	 * @param model
	 * @return
	 */
	public int insertGwdx(XwglModel model);
	/**
	 * 
	 * @description: 删除面向对象
	 * @author : m
	 * @date : 2014-10-22
	 * @time : 上午11:42:54 
	 * @param model
	 * @return
	 */
	public int deleteMxdx(@Param(value="xwbh")String xwbh);
	/**
	 * 
	 * @description: 查询个人可以接收的通知信息
	 * @author : kangzhidong
	 * @date : 2014-3-24
	 * @time : 下午02:04:31 
	 * @param model
	 * @return
	 */
	public List<XwglModel> getGrtzList(XwglModel model);
	/**
	 * 查询学生信息
	 * @param model
	 * @return
	 */
	public List<BaseMap> getPagedByScopeXsxx(XwglModel model);
	  /**
	 * 
	 *@描述：查询符合条件的学年代码集合
	 *@创建人:m
	 *@创建时间:2014-10-22 14.6
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param  
	 *@return
	 */
	public List<BaseMap> queryXnList();
	/**
	 * 查询教师信息
	 * @param model
	 * @return
	 */
	public List<BaseMap> getPagedByScopeJsxx(XwglModel model);
	/**
	 * 查询角色信息
	 * @param model
	 * @return
	 */
	public List<BaseMap> queryJsList();
	/**
	 * 查询岗位信息
	 * @param model
	 * @return
	 */
	public List<BaseMap> getPagedByScopeGwxx(XwglModel model);
	
	/**
	 * 分页查询新闻公告
	 * @param model 
	 */
	public List<XwglModel> getPagedByScopeXwList(XwglModel model);
}
