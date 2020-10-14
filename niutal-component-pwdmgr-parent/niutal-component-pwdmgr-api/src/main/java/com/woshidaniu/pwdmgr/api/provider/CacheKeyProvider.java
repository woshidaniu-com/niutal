package com.woshidaniu.pwdmgr.api.provider;

import com.woshidaniu.pwdmgr.api.model.BindData;

public interface CacheKeyProvider {

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
	
	public String genKey(String prefix, BindData data);
	
}
