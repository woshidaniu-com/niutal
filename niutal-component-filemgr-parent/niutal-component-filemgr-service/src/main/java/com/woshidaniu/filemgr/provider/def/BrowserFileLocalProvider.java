package com.woshidaniu.filemgr.provider.def;

import java.io.File;

import com.woshidaniu.filemgr.api.BrowserProvider;
import com.woshidaniu.io.utils.DirectoryUtils;

public class BrowserFileLocalProvider implements BrowserProvider {

	@Override
	public File get(String browserType) throws Exception {
		
		String wabappPath = System.getProperty("user.dir").replace("bin", "webapps");
		String filePath = "";
		if ("chrome".equals(browserType)) {
			filePath = wabappPath + "/browserFile/ChromeSetup.rar";
		} else if ("firefox".equals(browserType)) {
			filePath = wabappPath + "/browserFile/FirefoxSetup.rar";
		} else if ("safari".equals(browserType)) {
			filePath = wabappPath + "/browserFile/SafariSetup.rar";
		} else if ("ie9".equals(browserType)) {
			filePath = wabappPath + "/browserFile/IE9Setup.rar";
		} else if ("ie10".equals(browserType)) {
			filePath = wabappPath + "/browserFile/IE10Setup.rar";
		} else if ("ie11".equals(browserType)) {
			filePath = wabappPath + "/browserFile/IE11Setup.rar";
		}
		// 以流的形式下载文件。
		return new File(DirectoryUtils.getResolvePath(filePath));
	}
	
}
