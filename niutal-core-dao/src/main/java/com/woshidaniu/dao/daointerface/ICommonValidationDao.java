package com.woshidaniu.dao.daointerface;

import org.springframework.stereotype.Repository;

import com.woshidaniu.dao.entities.ValidationModel;

/**
 * 
 *@类名称:ICommonValidationDao.java
 *@类描述：公共验证方法dao接口
 *@创建人：xiaokang
 *@创建时间：2014-6-17 下午08:31:19
 *@版本号:v1.0
 */
@Repository("commonValidationDao")
public interface ICommonValidationDao {

	/**
	 * 
	 *@描述：基于绑定元素的唯一性验证
	 *@创建人:xiaokang
	 *@创建时间:2014-8-5下午07:10:49
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public int getCount(ValidationModel model) ;
	
	/**
	 * 
	 *@描述：不限定元素的多个组合唯一性验证
	 *@创建人:xiaokang
	 *@创建时间:2014-8-5下午07:11:13
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public int getMultiCount(ValidationModel model) ;
	
}
