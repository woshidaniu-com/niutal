package com.woshidaniu.pwdmgr.api.provider;

import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.ResultData;

/**
 * 
 *@类名称		： PwdUpdateProvider.java
 *@类描述		： 密码修改服务接口
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 下午3:46:33
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface PwdUpdateProvider {
	
	/**
	 * 
	 *@描述		：服务提供者名称
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月13日下午12:19:06
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String name();
	
	public Object get(BindData data);
	
	public ResultData update(BindData data);
	
}
