package com.woshidaniu.common;

import java.util.Locale;

public enum ZFtalParameter {

	/**登录页面地址*/
	LOGIN_FORWARD_URL("login.forward.url","/xtgl/dl_loginForward.html"),
	/**登录成功后跳转的主页地址*/
	LOGIN_HOMEPAGE_URL("login.homepage.url","/xtgl/index_initMenu.html"),
	/**票据登录（通过握手秘钥等参数认证登录）中的token失效时间；可使用单位 (s:秒钟,m:分钟,h:小时,d:天)；如10m表示10分钟*/
	LOGIN_TICKET_TOKEN_PERIOD("login.ticket.token_period","10m"),
	/**票据登录（通过握手秘钥等参数认证登录）中的时间戳允许时差范围；可使用单位 (s:秒钟,m:分钟,h:小时,d:天)；如5m表示5分钟*/
	LOGIN_TICKET_TIMESTAMP_PERIOD("login.ticket.timestamp_period","1m"),
	
	REQUEST_FUNC_USERKEY("request.func.userKey","su");
	
	protected String name;
	protected String defaultValue;

	private ZFtalParameter(String name,String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public String getDefault() {
		return defaultValue;
	}

	static ZFtalParameter valueOfIgnoreCase(String parameter,String defaultValue) {
		ZFtalParameter parm = valueOf(parameter.toUpperCase(Locale.ENGLISH).trim());
		parm.defaultValue = defaultValue;
		return parm;
	}
	
}
