package com.woshidaniu.filemgr.api;

import java.io.File;
/**
 * 
 *@类名称	: BrowserProvider.java
 *@类描述	： 浏览器文件提供者
 *@创建人	：kangzhidong
 *@创建时间	：2017年7月27日 下午5:59:44
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface BrowserProvider {

	public File get(String browserType) throws Exception ;
	
}
