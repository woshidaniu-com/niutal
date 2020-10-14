/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.EmailFromModel;
import com.woshidaniu.entities.EmailSettingModel;

/**
 * 
 *@类名称:IEmailSettingDao.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Dec 28, 2015 9:49:33 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IEmailSettingDao extends BaseDao<EmailFromModel>{
	
	public List<EmailSettingModel> getEmailSettingList(); 
	
	/**
	 * 
	 *@描述		：获取允许的邮箱后缀集合
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 21, 201612:03:57 PM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getEmailPostfixList();
	
}
