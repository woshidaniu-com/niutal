package com.woshidaniu.license.admin;

import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：license admin api
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年6月20日下午1:37:55
 */
@Service("licenseAdminOps")
public class LicenseAdminOps {
	
	private static final Logger log = LoggerFactory.getLogger(LicenseAdminOps.class);
	
	public static final String osName = System.getProperty("os.name").toLowerCase();
	
	public static final boolean isWinPlatform = isWindows();
	
	public static final boolean isLinuxPlatform = isLinux();
	
	public static final String osbit = "";
	
	public static final String jrebit = System.getProperty("sun.arch.data.model");
	
	public static final boolean isWindows(){
		return osName.indexOf("windows") >= 0;
	}
	
	public static final boolean isLinux(){
		return osName.indexOf("linux") >= 0;
	}
	
	
	static{
		
		String classpath = null;
		try {
			classpath = LicenseAdminOps.class.getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		StringBuilder path = new StringBuilder();
		if (isLinuxPlatform) {
			path.append(classpath).append("libniutal-license-admin-linux-").append(jrebit).append(".so");
		}
		
		if(isWinPlatform){
			path.append(classpath.substring(1)).append("libniutal-license-admin-win-").append(jrebit).append(".dll");
		}
		
		if(path.length() == 0){
			throw new RuntimeException("动态链接库没有找到");
		}
		
		if(log.isDebugEnabled()){
			log.debug("LicenseAdmin lib path is {}. " , path);
		}
		
		System.load(path.toString());
	}
	
	/**
	 * license文件临时保存目录
	 */
	public String tempFilePath = System.getProperty("java.io.tmpdir");
	
	public String getTempFilePath() {
		return tempFilePath;
	}

	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}
	
	/**
	 * 
	 * <p>方法说明：生成授权文件，返回加密后文件内容<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月20日下午1:41:50<p>
	 */
	public native String generateLicenseFile(String authid,
											 String authuser,
											 String authusercode,
											 String authdate,
											 String startdate,
											 String expiredate,
											 int usage,
											 int usagecount,
											 int alert,
											 String devmode,
											 String productName);
	
	
	
	/**
	 * 
	 * <p>方法说明：生成license文件<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日上午11:47:23<p>
	 */
	public String genFile(String authid,
			 String authuser,
			 String authusercode,
			 String authdate,
			 String startdate,
			 String expiredate,
			 int usage,
			 int usagecount,
			 int alert,
			 String devmode,
			 String productName) throws LicenseAdminOpsException{
		
		if(authid == null){
			return null;
		}
		if(authuser == null){
			return null;
		}
		if(authusercode == null){
			return null;
		}
		if(authdate == null){
			return null;
		}
		if(startdate == null){
			return null;
		}
		if(expiredate == null){
			return null;
		}
		if(productName == null){
			return null;
		}
		try {
			String _authuser = new Base64().encodeAsString(authuser.getBytes("utf-8"));
			String _productname = new Base64().encodeAsString(productName.getBytes("utf-8"));
			String _generateLicenseFile = generateLicenseFile(authid, _authuser, authusercode, authdate, startdate, expiredate, usage, usagecount, alert, devmode, _productname);
			if(log.isDebugEnabled()){
				log.debug("授权文件内容：{}" , _generateLicenseFile);
			}
			return _generateLicenseFile;
		} catch (Exception e) {
			throw new LicenseAdminOpsException(e);
		}
		
	}

}
