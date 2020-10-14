package com.woshidaniu.filemgr.provider.def;

import java.io.File;

import javax.annotation.Resource;

import com.woshidaniu.filemgr.api.BrowserProvider;
import com.woshidaniu.filemgr.api.FileServProvider;

public class BrowserFileFTPProvider implements BrowserProvider {

	@Resource(name = "ftpFileServProvider")
	protected FileServProvider fileServProvider;
	
	@Override
	public File get(String browserType) throws Exception {
		
		
		/*try {
			String wabappPath  =  System.getProperty("user.dir").replace("bin", "webapps");
			String filePath    = "";
			if("chrome".equals(browserType)){
				filePath  = wabappPath+"/browserFile/ChromeSetup.rar";
				fileName = "ChromeSetup.rar";
			}else if("firefox".equals(browserType)){
				filePath  = wabappPath+"/browserFile/FirefoxSetup.rar";
				fileName = "FirefoxSetup.rar";
			}else if("safari".equals(browserType)){
				filePath  = wabappPath+"/browserFile/SafariSetup.rar";
				fileName = "SafariSetup.rar";
			}else if("ie9".equals(browserType)){
				filePath  = wabappPath+"/browserFile/IE9Setup.rar";
				fileName = "IE9Setup.rar";
			}else if("ie10".equals(browserType)){
				filePath  = wabappPath+"/browserFile/IE10Setup.rar";
				fileName = "IE10Setup.rar";
			}else if("ie11".equals(browserType)){
				filePath  = wabappPath+"/browserFile/IE11Setup.rar";
				fileName = "IE11Setup.rar";
			}
			// 以流的形式下载文件。
	        file = new File(DirectoryUtils.getResolvePath(filePath));
	        if(!file.exists()){
	        	return Result.EX_BROWSER;
	        }
			// 以流的形式下载文件。
	        fis = new BufferedInputStream(new FileInputStream(file));
	        bytes = new byte[fis.available()];
		} catch (Exception e) {
			logException(e);
			return Result.EX_BROWSER;
		} finally {
			IOUtils.closeQuietly(fis);
	    }*/
		
		return getFileServProvider().file(browserType);
	}

	public FileServProvider getFileServProvider() {
		return fileServProvider;
	}

	public void setFileServProvider(FileServProvider fileServProvider) {
		this.fileServProvider = fileServProvider;
	}
	
}
