package com.woshidaniu.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;

/**
 * 
* 
* 类名称：YhglDao 
* 类描述： 用户管理DAO
* 创建人：Administrator 
* 创建时间：2012-4-10 下午06:45:13 
* 修改人：Administrator 
* 修改时间：2012-4-10 下午06:45:13 
* 修改备注： 
* @version 
*
 */
public interface IYhglDao extends BaseDao<YhglModel>{
	
	/**
	 * 
	 *@描述：根据用户名查询用户角色列表
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:15:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsglModel> cxJsdmListByYhm(YhglModel model);
	
	/**
	 * 
	 *@描述：查询属于指定用户的角色列表:用于页面treeGrid
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:13:37
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsglModel> getTreeGridJsdmList(YhglModel model);
	
	/**
	 * 
	 *@描述：增加用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:13:22
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void zjYhxx(YhglModel model);
	
	/**
	 * 
	 *@描述：修改用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:13:12
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void xgYhxx(YhglModel model);
	
	/**
	 * 
	 *@描述：删除用户信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:13:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void scYhxx(YhglModel model);
	
	/**
	 * 
	 *@描述：设置用户所属角色
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:12:52
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void szSsjs(YhglModel model);
	
	/**
	 * 
	* 方法描述: 密码初始化
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int mmCsh(YhglModel model);
	
	/**
	 * 
	* 方法描述: 修改密码
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int xgMm(YhglModel model);
	
	/**
	 * 
	* 方法描述: 根据角色代码查询用户列表
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 JsglModel 返回类型
	* @throws
	 */
	public List<YhglModel> cxYhByJsdm(YhglModel model);
	
	/**
	 * 根据机构代码查询用户
	 * @param model
	 * @return List<YhglModel>
	 */
	public List<YhglModel> cxYhByJgdm(YhglModel model);
	
	/**
	 * 
	* 方法描述: 根据用户名及功能模块代码查询是否有权限
	* 参数 @param array 参数说明
	* 返回类型 int 返回类型
	* @throws
	 */
	public int checkPrivilege(YhglModel model);

	
}
