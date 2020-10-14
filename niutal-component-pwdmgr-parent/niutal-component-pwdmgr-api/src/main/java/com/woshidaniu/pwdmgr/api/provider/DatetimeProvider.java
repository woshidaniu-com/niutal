package com.woshidaniu.pwdmgr.api.provider;

import com.woshidaniu.pwdmgr.api.model.BindTime;
/**
 * 
 *@类名称		： DatetimeProvider.java
 *@类描述		：日期时间提供接口
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 上午10:10:09
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface DatetimeProvider{

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
	
	public BindTime dateTime(String format,int effectTime);
	
}
