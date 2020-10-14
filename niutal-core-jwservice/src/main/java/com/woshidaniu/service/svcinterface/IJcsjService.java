package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.JcsjModel;

/**
 * 
* 
* 类名称：JcsjService 
* 类描述：基础数据业务处理接口
* 创建人：xucy 
* 创建时间：2012-4-13 下午01:44:18 
* 修改人：xucy 
* 修改时间：2012-4-13 下午01:44:18 
* 修改备注： 
* @version 
*
 */
public interface IJcsjService extends BaseService<JcsjModel>{

	
	/**
	 * 
	* 方法描述: 查询基础数据列表(不分页)
	* 参数 @return 参数说明
	* 返回类型  List<JcsjModel>  返回类型
	*/
	public List<JcsjModel> cxJcsjList(JcsjModel model); 
	
	/**
	 * 
	* 方法描述: 删除用户信息
	* 参数 @return 参数说明
	* 返回类型  boolean  返回类型
	*/
	public boolean scJcsj(JcsjModel model);
	
	
	/**
	 * 
	* 方法描述: 根据类型代码查询基础数据
	* 参数 @return 参数说明
	* 返回类型  List<JcsjModel>  返回类型
	*/
	public List<JcsjModel> getJcsjList(String lx); 
	
	/**
	 * 
	 *@描述：查询学期list
	 *@创建人:wjy
	 *@创建时间:2014-12-27下午05:05:09
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param syxq
	 *@return
	 */
	public List<JcsjModel> getXqList(String syxq);
}
