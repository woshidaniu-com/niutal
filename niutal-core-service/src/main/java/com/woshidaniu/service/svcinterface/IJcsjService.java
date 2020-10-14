package com.woshidaniu.service.svcinterface;

import java.util.HashMap;
import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.JcsjModel;

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

	public static final String XTJB_XT = "xt";
	public static final String XTJB_YW = "yw";
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
	public int[] scJcsj(JcsjModel model);
	
	
	/**
	 * 
	* 方法描述: 根据类型代码查询基础数据
	* 参数 @return 参数说明
	* 返回类型  List<JcsjModel>  返回类型
	*/
	public List<JcsjModel> getJcsjList(String lxdm); 
	
	/**
	 * 
	* 方法描述: 根据类型代码查询基础数据
	* 参数 @return 参数说明
	* 返回类型  HashMap<String, String>  返回类型
	*/
	public List<HashMap<String, String>> getJcsjHashMap(String lxdm); 
	
	/**
	 * 方法描述：获取单个基础数据
	 * @param lx	类型
	 * @param dm	代码
	 * @return
	 */
	public JcsjModel getJcsjModel(String lx,String dm);
	
	/**
	 * 
	 * <p>方法说明：判断是否可删除<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年12月26日下午8:14:57<p>
	 */
	public boolean canDelete(JcsjModel model);
	
}
