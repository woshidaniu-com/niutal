package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;

/**
 * 
* 
* 类名称：JsglService 
* 类描述： 角色管理业务接口
* 创建人：Administrator 
* 创建时间：2012-4-1 下午03:52:26 
* 修改人：Administrator 
* 修改时间：2012-4-1 下午03:52:26 
* 修改备注： 
* @version 
*
 */
public interface IJsglService extends BaseService<JsglModel>{

	/**
	 * 
	 *@描述：增加角色信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午06:24:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void zjJsxx(JsglModel model);
	
	/**
	 * 
	 *@描述：增加子角色信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午06:24:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void zjSubJsxx(JsglModel model);
	
	/**
	 * 
	 *@描述：修改角色信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午06:24:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void xgJsxx(JsglModel model);
	
	/**
	 * 
	 *@描述：删除角色信息
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午06:24:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void scJsxx(JsglModel model);
	
	
	/**
	 * 
	 *@描述：保存角色分配用户
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午08:24:58
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public void zjJsyhfpxx(JsglModel model);
	
	/**
	 * 
	 *@描述：删除角色已分配用户
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-15下午08:24:58
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public void scJsyhfpxx(JsglModel model);
	
	
	/**
	 * 
	 *@描述：根据角色代码查询角色名称
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-9下午06:13:28
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public  JsglModel cxJsmcByJsdm(JsglModel model);
	
	/**
	 * 
	 *@描述：根据用户代码查询角色信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-9下午06:28:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param yhm
	 *@return
	 */
	public List<JsglModel> cxJsxxListByYhm(String yhm) ;	
	
	/**
	 * 
	 *@描述：根据操作人ID,用户代码查询获得当前操作人的角色集合与被操作人的角色集合的交集
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-9下午06:30:35
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsglModel> cxKczjsxxList(YhglModel model);
	
	/**
	 * 
	 *@描述：根据角色代码分页查询该角色未分配用户信息；并受到数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-15上午09:27:33
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<YhglModel> getPagedListWfpYh(JsglModel model) ;
	
	/**
	 * 
	 *@描述：根据角色代码分页查询该角色已分配用户信息；并受到数据范围控制
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-15上午09:26:43
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
    public List<YhglModel> getPagedListYfpYh(JsglModel model) ;
    
    /**
     * 
     *@描述：查询所有一级功能模块列表
     *@创建人:kangzhidong
     *@创建时间:2015-1-15上午09:25:48
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@param model
     *@return
     */
    public List<JsglModel> getGnmkYj(JsglModel model);
    
    /**
     * 
     *@描述：根据角色代码查询角色是否具有二级授权功能；
     *@创建人:kangzhidong
     *@创建时间:2015-1-15上午09:25:57
     *@修改人:
     *@修改时间:
     *@修改描述:
     *@param yhm
     *@return
     */
    public boolean getYhEjsq(String jsdm);

	/**
	 *@描述：查询管理员的角色树形显示结果结果数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:25:57
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsglModel> getAdminTreeGridModelList(JsglModel model);
	
	/**
	 *@描述：查询角色树形显示结果结果数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-20下午07:25:57
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<JsglModel> getTreeGridModelList(JsglModel model);

}
