package com.woshidaniu.pwdmgr.provider;

import javax.annotation.Resource;

import com.woshidaniu.pwdmgr.api.model.BindTime;
import com.woshidaniu.pwdmgr.api.provider.DatetimeProvider;
import com.woshidaniu.pwdmgr.dao.daointerface.CaptchaDao;

/**
 * 
 *@类名称		： OracleDatabaseTimeProvider.java
 *@类描述		：Oracle数据库时间提供者实现，统一在一个数据库情况下的时间
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 上午10:20:38
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class OracleDatabaseTimeProvider implements DatetimeProvider {
	
	@Resource
	protected CaptchaDao captchaDao;

	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public BindTime dateTime(String format,int effectTime) {
		return new BindTime(getCaptchaDao().getDatetime(), format, effectTime);
	}
	
	public CaptchaDao getCaptchaDao() {
		return captchaDao;
	}

	public void setCaptchaDao(CaptchaDao captchaDao) {
		this.captchaDao = captchaDao;
	}
	
}
