package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;

/**
 * 
 * 
 * 类名称：YhglService 类描述： 用户信息业务处理接口 创建人：Administrator 创建时间：2012-4-10 下午06:44:18
 * 修改人：Administrator 修改时间：2012-4-10 下午06:44:18 修改备注：
 * 
 * @version
 * 
 */
public interface IYhglService extends BaseService<YhglModel> {

	/**
	 * 
	 *@描述：保存用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:19:52
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void zjYhxx(YhglModel model) ;

	/**
	 * 
	 *@描述：修改用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:19:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void xgYhxx(YhglModel model) ;

	/**
	 * 
	 *@描述：查询用户角色列表
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:19:59
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsglModel> cxJsdmList(YhglModel model) ;
	
	/**
	 * 
	 *@描述：查询属于指定用户的角色列表:用于页面treeGrid
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:17:49
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsglModel> getTreeGridJsdmList(YhglModel model) ;
	
	/**
	 * 
	 *@描述：删除用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:18:01
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void scYhxx(YhglModel model) ;

	/**
	 * 
	 *@描述：设置所属角色 
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:18:07
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void szSsjs(YhglModel model) ;

	/**
	 * 
	 *@描述：密码初始化 
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:18:14
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void mmCsh(YhglModel model) ;

	/**
	 * 
	 *@描述： 修改密码 
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:18:20
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void xgMm(YhglModel model) ;

	/**
	 * 
	 *@描述：根据角色代码查询所属用户
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:18:26
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<YhglModel> cxYhByJsdm(YhglModel model) ;
	
	/**
	 * 
	 *@描述：根据机构代码查询用户
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:20:08
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<YhglModel> cxYhByJgdm(YhglModel model) ;


	
	/**
	 * 
	 *@描述：验证用户名称
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:20:21
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param zgh
	 *@param mm
	 *@return
	 */
	public boolean checkYhMm(String zgh,String mm);
	
	/**
	 * 
	 *@描述：根据用户名及功能模块代码查询是否有权限
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:20:21
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param zgh
	 *@param mm
	 *@return
	 */
	public boolean checkPrivilege(String yhm,String gnmkdm);
 
}
