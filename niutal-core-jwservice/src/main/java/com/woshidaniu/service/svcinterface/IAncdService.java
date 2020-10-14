package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.AncdModel;
/**
 * 
 *@类名称:IAncdService.java
 *@类描述：按钮菜单Service
 *@创建人：kangzhidong
 *@创建时间：2014-11-17 下午04:16:14
 *@版本号:v1.0
 */
public interface IAncdService extends BaseService<AncdModel> {
	
	/***
	 * 
	 *@描述：返回用户功能页面按钮
	 *@创建人:majun
	 *@创建时间:2014-7-9下午06:54:13
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param user
	 *@param gnmkdm 功能模块代码
	 *@return
	 */
	public List<AncdModel> cxButtonsList(User user,String gnmkdm);
	
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
	public List<AncdModel> cxButtonGroupList(User user, String gnmkdm) ;
	
	/***
	 * 
	 *@描述：查询菜单
	 *@创建人:majun
	 *@创建时间:2014-7-11下午04:17:33
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param yhm
	 *@param jsdm
	 *@return
	 */
	public String getMenu(String yhm,String jsdm);

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
	public List<AncdModel> cxLinkList(String[] gnmkdms);
	
}
