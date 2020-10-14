package com.woshidaniu.service.common;

import com.woshidaniu.entities.ValidationModel;

/**
 * 
 *@类名称:CommonValidationService.java
 *@类描述：公共验证方法service接口
 *@创建人：kangzhidong
 *@创建时间：2014-6-17 下午08:28:40
 *@版本号:v1.0
 */
public interface ICommonValidationService {
	
	
	/**
	 * 
	 *@描述：唯一约束验证
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-17下午08:29:39
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public boolean unique(ValidationModel model) ;
	
	/**
	 * 
	 *@描述：是否身份证
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-1下午01:54:27
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String isIDCard(ValidationModel model) ;
	
	/**
	 * 
	 *@描述：验证邮箱根域名是否存在
	 *@创建人:zfankai
	 *@创建时间:2016-1-7上午09:15:37
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public boolean validEmail(ValidationModel model);
	
}
