package com.woshidaniu.service.svcinterface;


import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.JsgnmkModel;

/**
 * 
 *@类名称:IJsgnmkService.java
 *@类描述：角色功能模块service接口
 *@创建人：kangzhidong
 *@创建时间：2014-10-20 上午09:02:21
 *@版本号:v1.0
 */
public interface IJsgnmkService extends BaseService<JsgnmkModel>{

	  /**
	 * 
	 * @描述：根据角色代码查询角色的所以父级才是是否任意一个具有二级授权功能
	 * @创建人:kangzhidong
	 * @创建时间：2015-6-29 上午11:20:44
	 * @param jsdm
	 * @return
	 */
	public boolean getParentSfejsq(JsgnmkModel model);
	
	/**
	 * 
	 *@描述：查询一级功能模块代码列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午09:18:28
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsgnmkModel> cxYjGnmkdmList(JsgnmkModel model);
	
	/**
	 * 
	 *@描述：根据用户所属角色已经功能模块代码 查询功能模块下的功能列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午09:11:29
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsgnmkModel> cxGnmkdmList(JsgnmkModel model);
	
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
	public List<String> cxJsGnmkdmList(String jsdm);
	
	/**
	 * 
	 *@描述：根据用户所属角色 查询功能模块操作代码列表
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20上午09:16:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsgnmkModel> cxGnmkczList(JsgnmkModel model) ;
	
	
	/**
	 * 
	 *@描述：更新角色功能授权信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-20下午05:04:27
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void updateJsgnsqxx(JsgnmkModel model) ;
	/**
	 *@描述：查询学生和教师功能
	 *@创建人:majun
	 *@创建时间:2015-04-23 10：36
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param jsdm 角色代码
	 */
	public List<List<Map<String,String>>> getJsgnList(String jsdm,String type);

}
