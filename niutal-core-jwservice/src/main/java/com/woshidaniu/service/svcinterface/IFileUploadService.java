package com.woshidaniu.service.svcinterface;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletResponse;


/**
 * 
 *@类名称		： IFileUploadService.java
 *@类描述		：通用文件上传，下载实现 
 *@创建人		：kangzhidong
 *@创建时间	：Jun 7, 2016 11:19:56 AM
 *@修改人		：
 *@修改描述	： 该接口将作废；今后将采用统一的文件管理模块提供的接口进行文件相关处理；原有代码建议进行调整，不调整也不影响使用；
 *				引用独立的文件管理模块；
 *@see com.woshidaniu.filemgr.api.FileServProvider
 *@版本号	:v1.0
 *<pre>
 *&lt;dependency&gt;
 *	&lt;groupId>${project.groupId}&lt;/groupId&gt;
 *	&lt;artifactId&gt;niutal-component-filemgr-controller&lt;/artifactId&gt;
 *	&lt;version&gt;${niutal.version}&lt;/version&gt;
 *&lt;/dependency&gt;
 *&lt;dependency&gt;
 *	&lt;groupId&gt;${project.groupId}&lt;/groupId&gt;
 *	&lt;artifactId&gt;niutal-component-filemgr-web&lt;/artifactId&gt;
 *	&lt;version&gt;${niutal.version}&lt;/version&gt; 
 *	&lt;type&gt;war&lt;/type&gt;
 * &lt;/dependency&gt;
 *</pre>
 */
@Deprecated
public interface IFileUploadService {

	
	/**
	 * 
	 *@描述		：批量文件上传
	 *@创建人		: kangzhidong
	 *@创建时间	: Jun 7, 201610:59:11 AM
	 *@param rootDir
	 *@param files
	 *@param fileNames
	 *@param fileContentTypes
	 *@throws Exception
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void uploadFile(String rootDir, List<File> files, List<String> fileNames) throws Exception;
	
	/**
	 * 
	 *@描述		：单个文件上传
	 *@创建人		: kangzhidong
	 *@创建时间	: Jun 7, 201610:59:04 AM
	 *@param rootDir
	 *@param file
	 *@param fileName
	 *@param fileContentType
	 *@throws Exception
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void uploadFile(File file, String rootDir, String fileName) throws Exception;
	public void uploadFile(byte[] bytes, String rootDir,String fileName) throws Exception;
	public void uploadFile(InputStream input, String rootDir,String fileName) throws Exception;
	
	/**
	 * 
	 *@描述		：批量文件下载
	 *@创建人		: kangzhidong
	 *@创建时间	: Jun 7, 201611:10:17 AM
	 *@param rootDir
	 *@param fileNames
	 *@param destDir
	 *@throws Exception
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void downloadFile(String rootDir, List<String> fileNames, File destDir) throws Exception;
	public void downloadFile(String rootDir, List<String> fileNames, OutputStream output) throws Exception;
	public void downloadFile(String rootDir, List<String> fileNames, ServletResponse response) throws Exception;
	
	/**
	 * 
	 *@描述		：单个文件下载
	 *@创建人		: kangzhidong
	 *@创建时间	: Jun 7, 201611:09:03 AM
	 *@param rootDir
	 *@param fileName
	 *@param destFile
	 *@throws Exception
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void downloadFile(String rootDir, String fileName, File destFile) throws Exception;
	public void downloadFile(String rootDir, String fileName, OutputStream output) throws Exception;
	public void downloadFile(String rootDir, String fileName, ServletResponse response) throws Exception;
	
	
}
