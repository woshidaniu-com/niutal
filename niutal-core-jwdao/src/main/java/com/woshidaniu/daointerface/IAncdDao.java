package com.woshidaniu.daointerface;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.AncdModel;

/**
 * 
 *@类名称:IAncdDao.java
 *@类描述：按钮菜单DAO
 *@创建人：yijd
 *@创建时间：2012-4-25 上午10:22:13
 *@修改时间：2014-11-17 下午16:25:13 
 *@修改人：kangzhidong
 *@修改备注：添加按钮查询功能
 *@版本号:v1.0
 */
public interface IAncdDao  extends BaseDao<AncdModel>{
	
	/**
	 * 
	 *@描述：根据用户类型查询不同角色功能按钮
	 *@创建人:majun
	 *@创建时间:2014-7-9下午06:51:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<AncdModel> cxButtonsList(AncdModel model);
	
	
	
	/**
	 * 
	 *@描述：根据用户角色查询对应功能按钮
	 *@创建人:jiangyy
	 *@创建时间:2017-11-22下午06:51:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<AncdModel> cxUserRoleButtonsList(AncdModel model);
	
	/**
	 *@描述：查询指定功能代码的菜单信息
	 *@创建人:kangzhidong
	 *@创建时间:Feb 23, 20169:13:55 AM
	 *@param gnmkdms
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<AncdModel> cxLinkList(@Param(value="gnmkdms")String[] gnmkdms,@Param(value="localeKey")String localeKey);
	
	/**
	 * 
	 *@描述：仅仅查询按钮部分html,方便页面扩展
	 *@创建人:kangzhidong
	 *@创建时间:2014-11-11下午04:56:31
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<AncdModel> cxButtonGroupList(AncdModel model);
	
	/**
	 * 
	 *@描述		：查询角色菜单：通过指定不同的起始功能模块代码可得到不同的树形结构数据
	 *@创建人		: kangzhidong
	 *@创建时间	: Aug 8, 201611:28:16 AM
	 *@param gnmkdm	：功能模块代码
	 *@param xtgnmkdm ：系统功能模块代码
	 *@param localeKey ：国际化标签
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getNavMenuTreeList(
			@Param(value="gnmkdm")String gnmkdm,
			@Param(value="xtgnmkdm")String xtgnmkdm,
			@Param(value="localeKey")String localeKey);
	
	
	public List<String> getTopNavMenuList(@Param(value="xtgnmkdm")String xtgnmkdm);
	
	/**
	 * 
	 *@描述		：查询用户角色菜单：通过指定不同的起始功能模块代码可得到不同的树形结构数据:注意用户菜单数结构由最深层向根菜单查找
	 *@创建人		: kangzhidong
	 *@创建时间	: Aug 8, 201611:27:59 AM
	 *@param yhm	：用户ID
	 *@param jsdm	：角色代码
	 *@param xtgnmkdm ：系统功能模块代码
	 *@param localeKey ：国际化标签
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getUserNavMenuTreeList(@Param(value="yhm")String yhm,
			@Param(value="jsdm")String jsdm,
			@Param(value="localeKey")String localeKey);
	
	/**
	 * 
	 *@描述		：查询用户角色个人的菜单：通过指定不同的起始功能模块代码可得到不同的树形结构数据:注意用户菜单数结构由最深层向根菜单查找
	 *@创建人		: jiangyy
	 *@创建时间	: Aug 8, 201611:27:59 AM
	 *@param yhm	：用户ID
	 *@param jsdm	：角色代码
	 *@param xtgnmkdm ：系统功能模块代码
	 *@param localeKey ：国际化标签
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getUserRoleNavMenuTreeList(@Param(value="yhm")String yhm,
			@Param(value="jsdm")String jsdm,
			@Param(value="localeKey")String localeKey);
	
	
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
	
	/**
	 * 方法描述: 查询按钮菜单 老师
	 * 参数 @param ancdMode 菜单模型
	 * 返回类型 List<AncdModel> 返回类型
	 * @throws Exception
	 */
	public List<AncdModel> cxAncdLs(AncdModel ancdModel);
	
	/**
	 * 方法描述: 查询按钮菜单  学生
	 * 参数 @param ancdMode 菜单模型
	 * 返回类型 List<AncdModel> 返回类型
	 * @throws Exception
	 */
	public List<AncdModel> cxAncdXs(AncdModel ancdModel);
	
}
