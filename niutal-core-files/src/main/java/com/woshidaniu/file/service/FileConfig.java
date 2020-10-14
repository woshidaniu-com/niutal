package com.woshidaniu.file.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * 文件服务配置
 * @author Administrator
 *
 */
public class FileConfig {

	private String localDir;
	private String ftpUrl;
	
	private int ftpPort;
	private String ftpUser;
	private String ftpPassword;
	private String[] allowTypes;
	private long maxSize;
	
	private FileConfig(){
		
		Properties properties = new Properties();
		
		try {
			
			String configFile = FileConfig.class.getResource("/fileServer.properties").getFile();
			properties.load(new FileInputStream(configFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.maxSize = Long.valueOf((String) properties.get("allow.maxSize"));
		this.localDir = (String) properties.get("local.dir");
		this.ftpUrl = (String) properties.get("ftp.url");
		this.ftpPort = Integer.valueOf((String) properties.get("ftp.port"));
		this.ftpUser = (String) properties.get("ftp.user");
		this.ftpPassword = (String) properties.get("ftp.password");
		this.allowTypes = ((String) properties.get("allow.types")).split(",");
	}
	
	private static class SingletonFactory{
		private static FileConfig config = new FileConfig();
		
	}
	
	public static FileConfig getInstance(){
		return SingletonFactory.config;
	}

	public String getLocalDir() {
		return localDir;
	}

	public String getFtpUser() {
		return ftpUser;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public String[] getAllowTypes() {
		return allowTypes;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	
}
