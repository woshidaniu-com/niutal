package com.woshidaniu.editor.ueditor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.ueditor.provider.ProviderManager;
import com.woshidaniu.editor.ueditor.provider.FTPBinaryUploadProvider;
import com.woshidaniu.editor.ueditor.provider.FTPFileManagerProvider;
import com.woshidaniu.editor.ueditor.provider.SMBBinaryUploadProvider;
import com.woshidaniu.editor.ueditor.provider.SMBFileManagerProvider;

/**
 * 
 *@类名称		： UEditorProviderInitializedListener.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Jan 11, 2017 8:24:00 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
public class UEditorProviderInitializedListener implements ServletContextListener {
	
	protected static Logger LOG = LoggerFactory.getLogger(UEditorProviderInitializedListener.class);
	
	public void contextInitialized(ServletContextEvent event) {
		try {

			LOG.info("开始加载classpath目录下的 *.properties资源文件!");
			
			ProviderManager.register(new FTPBinaryUploadProvider());
			ProviderManager.register(new FTPFileManagerProvider());
			
			LOG.info("开始加载classpath目录下的 *.properties资源文件!");
			
			ProviderManager.register(new SMBBinaryUploadProvider());
			ProviderManager.register(new SMBFileManagerProvider());

			LOG.info("资源文件加载成功!");
			
		} catch (Exception e) {
			LOG.error("资源文件加载失败",e);
		}
		
		
	}

	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
}
