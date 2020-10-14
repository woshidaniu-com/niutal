package com.woshidaniu.pwdmgr.api.provider.def;

import com.woshidaniu.pwdmgr.api.model.BindTime;
import com.woshidaniu.pwdmgr.api.provider.DatetimeProvider;
/**
 * 
 *@类名称		： DefaultDatetimeProvider.java
 *@类描述		：默认的日期时间提供接口；获取应用程序的系统时间，该时间有可能因为系统设置原因与其他系统时间不同
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 上午10:10:35
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class DefaultDatetimeProvider implements DatetimeProvider {
	
	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public BindTime dateTime(String format,int effectTime) {
		return new BindTime(String.valueOf(System.currentTimeMillis()), format, effectTime);
	}
	
}
