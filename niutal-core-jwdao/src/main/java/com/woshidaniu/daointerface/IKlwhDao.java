package com.woshidaniu.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.XsmmModel;

/**
 * 
* 
* 类名称：KlwhDao 
* 类描述： 用户管理DAO
* 创建人：xucy 
* 创建时间：2012-4-17 下午01:45:13 
* @version 
*
 */
public interface IKlwhDao extends BaseDao<XsmmModel>{

	/**
	 * 
	* 方法描述: 学生列表
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<XsmmModel> 返回类型
	* @throws
	 */
	public List<XsmmModel> fycxXsxxList(XsmmModel model);
	/**
	 * 
	* 方法描述: 根据学号获取学生信息列表
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<XsmmModel> 返回类型
	* @throws
	 */
	public List<XsmmModel> getXsxxList(XsmmModel model) throws Exception;
	
	/**
	 * 根据输入密码批量初始化
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateForMany(XsmmModel model)throws Exception;
	
	/**
	 * 根据身份证号后6位批量初始化
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateForManySFZ(List<XsmmModel> list)throws Exception;
	
	
}
