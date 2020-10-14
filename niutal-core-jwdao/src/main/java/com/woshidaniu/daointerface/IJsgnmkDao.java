package com.woshidaniu.daointerface;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.JsgnmkModel;

/**
 * 
 *@类名称:IJsgnmkDao.java
 *@类描述：角色功能模块dao接口：查询用户角色功能数据
 *@创建人：kangzhidong
 *@创建时间：2014-10-20 上午08:49:54
 *@版本号:v1.0
 */
public interface IJsgnmkDao extends BaseDao<JsgnmkModel> {

	/**
	 * 
	 * @描述：根据角色代码查询角色的所以父级才是是否任意一个具有二级授权功能
	 * @创建人:kangzhidong
	 * @创建时间：2015-6-29 上午11:20:44
	 * @param jsdm
	 * @return
	 */
	public Integer getParentSfejsq(JsgnmkModel model);
	
	/**
	 * 
	 *@描述：根据用户名，角色代码 查询该 用户此角色 所有 指定级别的功能模块列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午10:07:58
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsgnmkModel> getGnmkdmList(JsgnmkModel model);
	
	/**
	 * 
	 *@描述		：获取指定功能代码对应的功能菜单信息 
	 *@创建人		: kangzhidong
	 *@创建时间	: Mar 7, 20171:05:33 PM
	 *@param model
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public JsgnmkModel getGnmkdmModel(JsgnmkModel model);
	
	/**
	 * 
	 *@描述：获取指定角色 所有功能模块集合【菜单功能】
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-18下午01:57:52
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param jsdm
	 *@return
	 */
	public List<String> getJsGnmkdmList(@Param(value="jsdm")String jsdm);
	
	/**
	 * 
	 *@描述：根据用户所属角色 查询功能模块列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午08:54:02
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsgnmkModel> getGnmkczList(JsgnmkModel model);

	
	/**
	 * 
	 *@描述：获取系统 指定级别的功能模块列表 :针对于admin
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午10:07:58
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsgnmkModel> getXtGnmkdmList(JsgnmkModel model);
	
	/**
	 * 
	 *@描述：获得一级功能模块代码集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-6下午05:56:36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<String> getYjXtGnmkdmList();
	
	/**
	 * 
	 *@描述：获取系统 所有功能模块按钮操作权限:针对于admin
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-21上午09:51:45
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsgnmkModel> getXtGnmkczList(JsgnmkModel model);
	
	/**
	 * deleteJsgnsqxx
	 *@描述：更新角色功能授权信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20下午02:22:45
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param param
	 */
	public void updateJsgnsqxx(JsgnmkModel model);
	
	public void deleteJsgnsqxx(JsgnmkModel model);
	/**
	 * getJsgnList
	 *@描述：查询学生和教师功能
	 *@创建人:majun
	 *@创建时间:2015-04-23 10：36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param jsdm 角色代码
	 */
	public List<HashMap<String, String>> getJsgnList(@Param(value="jsdm")String jsdm,@Param(value="type")String type,@Param(value="localeKey")String localeKey);
	
}
