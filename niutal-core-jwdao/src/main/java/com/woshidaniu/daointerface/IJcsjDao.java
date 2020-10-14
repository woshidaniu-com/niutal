package com.woshidaniu.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.JcsjModel;

/**
 * 
* 
* 类名称：JcsjDao 
* 类描述：基础数据DAO
* 创建人：xucy 
* 创建时间：2012-4-13 下午01:45:13 
* 修改人：xucy 
* 修改时间：2012-4-13 下午01:45:13 
* 修改备注： 
* @version 
*
 */
public interface IJcsjDao extends BaseDao<JcsjModel>{

	/**
	 * 
	* 方法描述: 查询基础数据列表(不分页)
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<JcsjModel> 返回类型
	* @throws
	 */
	public List<JcsjModel> cxJcsjList(JcsjModel model);
	
	
	/**
	 * 
	* 方法描述:基础数据列表
	* 参数 @param array 参数说明
	* 返回类型  List<JcsjModel> 返回类型
	* @throws
	 */
	public  List<JcsjModel> getJcsjList(@Param(value="localeKey")String localeKey,@Param(value="lxdm") String lx);
	/**
	 * 
	 *@描述：查询学期List
	 *@创建人:majun
	 *@创建时间:2014-7-18上午09:28:02
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param syxq  使用学期（1:大学期；0:小学期）
	 *@return
	 */
	public  List<JcsjModel> getXqList(@Param(value="syxq") String syxq);
	
}
