package com.woshidaniu.editor;

import java.util.Locale;

import com.woshidaniu.ftpclient.FTPConstants;
import com.woshidaniu.smbclient.SMBConstants;

public enum EditorParameter {

	/**获取文件存储路径*/
	FTPCLIEN_LOCALDIR("ftpclient.web.tmpdir", FTPConstants.DEFAULT_FTPCLIEN_WEB_LOCALDIR ),
	/**获取请求过滤前缀*/
	FTPCLIEN_REQUESTPREFIX("ftpclient.web.requestPrefix", FTPConstants.DEFAULT_FTPCLIEN_WEB_REQUESTPREFIX),
	/**获取是否缓存FTP文件到本地存储路径*/
	FTPCLIEN_CACHELOCAL("ftpclient.web.cacheLocal", "false"),
	/**获取FTP文件在本地缓存的时间;默认10分钟 ;10 * 60 * 1000*/
	FTPCLIEN_CACHEEXPIRY("ftpclient.web.cacheExpiry", "600000"),
	/**FTP异常信息重定向路径*/
	FTPCLIEN_REDIRECTURL("ftpclient.web.redirectURL",""),
	
	/**获取文件存储路径*/
	SMBCLIEN_LOCALDIR("smbclient.web.tmpdir", SMBConstants.DEFAULT_SMBCLIEN_WEB_LOCALDIR ),
	/**获取请求过滤前缀*/
	SMBCLIEN_REQUESTPREFIX("smbclient.web.requestPrefix", SMBConstants.DEFAULT_SMBCLIEN_WEB_REQUESTPREFIX),
	/**获取是否缓存SMB文件到本地存储路径*/
	SMBCLIEN_CACHELOCAL("smbclient.web.cacheLocal", "false"),
	/**获取共享文件在本地缓存的时间;默认10分钟 ;10 * 60 * 1000*/
	SMBCLIEN_CACHEEXPIRY("smbclient.web.cacheExpiry", "600000"),
	/**异常信息重定向路径*/
	SMBCLIEN_REDIRECTURL("smbclient.web.redirectURL","");
	
	protected String name;
	protected String defaultValue;

	private EditorParameter(String name,String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public String getDefault() {
		return defaultValue;
	}

	static EditorParameter valueOfIgnoreCase(String parameter,String defaultValue) {
		EditorParameter parm = valueOf(parameter.toUpperCase(Locale.ENGLISH).trim());
		parm.defaultValue = defaultValue;
		return parm;
	}
	
}
