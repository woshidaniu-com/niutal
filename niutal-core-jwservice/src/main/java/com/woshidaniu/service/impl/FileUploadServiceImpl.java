package com.woshidaniu.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.compress.ZipUtils;
import com.woshidaniu.ftpclient.client.IFTPClient;
import com.woshidaniu.io.utils.FileCopyUtils;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.service.svcinterface.IFileUploadService;
import com.woshidaniu.smbclient.client.ISMBClient;
import com.woshidaniu.util.file.DirectoryUtils;
import com.woshidaniu.web.context.WebContext;

/**
 * 
 *@类名称		： FileUploadServiceImpl.java
 *@类描述		：通用文件上传，下载实现 
 *@创建人		：kangzhidong
 *@创建时间	：Jun 7, 2016 11:16:08 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Service
@Deprecated
public class FileUploadServiceImpl implements IFileUploadService {

	/**
	 * 文件共享客户端
	 */
	@Resource
	private ISMBClient smbClient;
	/**
	 * FTP文件服务客户端
	 */
	@Resource
	private IFTPClient ftpClient;
	
	/**
	 * @see {@link package com.woshidaniu.service.svcinterface.IFileUploadService#uploadFile(String, List, List, List)}
	 */
	public void uploadFile(String rootDir, List<File> files, List<String> fileNames) throws Exception {
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至FTP服务器
			for (int i = 0,j = files.size() ; i < j; i++){
				//上传文件
				getFtpClient().upload(files.get(i), rootDir, fileNames.get(i) );
			}
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至文件共享服务器
			//getSmbClient().upload(localFile ,"attached\\admin\\image\\2016-06-01","tst.mp4");
			for (int i = 0,j = files.size() ; i < j; i++){
				//上传文件
				getSmbClient().upload(files.get(i), rootDir, fileNames.get(i) );
			}
		}else{
			//检查目录
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			if(!targetDir.exists()){
				targetDir.setReadable(true);
				targetDir.setWritable(true);
				targetDir.mkdirs();
			}
			for (int i = 0,j = files.size() ; i < j; i++){
				//上传文件
				File newFile = renameFile(targetDir, fileNames.get(i));
				FileUtils.copyFile(files.get(i), newFile);
			}
		}
	}
	
	
	/**
	 * //唯一ID
	 *	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	 *	//重命名新的文件名
	 *	String newFileName = uuid + FilenameUtils.getFullExtension(fileName);
	 */
	public void uploadFile(File file, String rootDir, String fileName) throws Exception {
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至FTP服务器
			getFtpClient().upload(file, rootDir , fileName);
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至文件共享服务器
			//getSmbClient().upload(localFile ,"attached\\admin\\image\\2016-06-01","tst.mp4");
			getSmbClient().upload(file, rootDir, fileName );
		}else{
			//检查目录
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			if(!targetDir.exists()){
				targetDir.setReadable(true);
				targetDir.setWritable(true);
				targetDir.mkdirs();
			}
			//上传文件
			File newFile = new File(targetDir, fileName);
			FileUtils.copyFile(file, newFile);
		}
	}
	
	@Override
	public void uploadFile(byte[] bytes, String rootDir, String fileName) throws Exception {
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至FTP服务器
			getFtpClient().upload(bytes, rootDir , fileName);
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至文件共享服务器
			//getSmbClient().upload(localFile ,"attached\\admin\\image\\2016-06-01","tst.mp4");
			getSmbClient().upload(bytes, rootDir, fileName );
		}else{
			//检查目录
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			if(!targetDir.exists()){
				targetDir.setReadable(true);
				targetDir.setWritable(true);
				targetDir.mkdirs();
			}
			//上传文件
			File newFile = new File(targetDir, fileName);
			FileCopyUtils.copy(bytes, newFile);
		}
		
	}


	@Override
	public void uploadFile(InputStream input, String rootDir, String fileName)
			throws Exception {
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至FTP服务器
			getFtpClient().upload(input, rootDir , fileName);
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//上传文件至文件共享服务器
			//getSmbClient().upload(localFile ,"attached\\admin\\image\\2016-06-01","tst.mp4");
			getSmbClient().upload(input, rootDir, fileName );
		}else{
			//检查目录
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			if(!targetDir.exists()){
				targetDir.setReadable(true);
				targetDir.setWritable(true);
				targetDir.mkdirs();
			}
			//上传文件
			File newFile = new File(targetDir, fileName);
			FileUtils.copyInputStreamToFile(input, newFile);
		}
		
	}
	
	
	/**
	 * 创建文件
	 * @param uploadDir
	 * @param fileName
	 * @return
	 */
	private File renameFile(File uploadDir,String fileName){
		File newFile = new File(uploadDir, fileName);
		int i = 1;
		while (newFile.exists()){
			String tempName = FilenameUtils.getBaseName(fileName);
			StringBuilder newName = new StringBuilder();
			newName.append(tempName)
				   .append("(")
				   .append(i)
				   .append(")")
				   .append(FilenameUtils.getFullExtension(fileName));
			newFile = new File(uploadDir, newName.toString());
			i++;
		}
		return newFile;
	}


	@Override
	public void downloadFile(String rootDir, List<String> fileNames, File destDir) throws Exception {
		if(!destDir.isDirectory()){
			throw new IOException("destDir is not a directory .");
		}
		if(!destDir.exists()){
			throw new FileNotFoundException(" the dest directory is not found .");
		}
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从FTP服务器下载文件
			for (int i = 0,j = fileNames.size() ; i < j; i++){
				//下载文件
				File destFile = new File(destDir,fileNames.get(i));
				getFtpClient().downloadToFile(rootDir , fileNames.get(i) , destFile);
			}
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从文件共享服务器下载文件
			for (int i = 0,j = fileNames.size() ; i < j; i++){
				//下载文件
				File destFile = new File(destDir,fileNames.get(i));
				getSmbClient().downloadToFile(rootDir , fileNames.get(i) , destFile);
			}
		}else{
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			for (int i = 0,j = fileNames.size() ; i < j; i++){
				//检查文件
				File srcFile = new File(targetDir,fileNames.get(i));
				File destFile = new File(destDir,fileNames.get(i));
				FileUtils.copyFile(srcFile, destFile);
			}
		}
	}


	@Override
	public void downloadFile(String rootDir, List<String> fileNames, OutputStream output) throws Exception {
		try {
			//临时目录
			File tempDir = new File(WebContext.getServletContext().getRealPath(BaseConstant.TEMP_PATH));
			//文件集合
			Collection<File> tempFiles = new ArrayList<File>();
			/**
			 * #文件存储方式：
			 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
			 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
			 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
			 */
			if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
				//从FTP服务器下载文件
				for (int i = 0,j = fileNames.size() ; i < j; i++){
					//下载文件
					File newFile = renameFile(tempDir, fileNames.get(i));
					getFtpClient().downloadToFile(rootDir , fileNames.get(i) , newFile);
					tempFiles.add(newFile);
				}
				ZipUtils.compressFiles(tempFiles, output , tempDir + File.separator );
			}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
				//从文件共享服务器下载文件
				for (int i = 0,j = fileNames.size() ; i < j; i++){
					//下载文件
					File newFile = renameFile(tempDir, fileNames.get(i));
					getSmbClient().downloadToFile(rootDir , fileNames.get(i) , newFile);
					tempFiles.add(newFile);
				}
				ZipUtils.compressFiles(tempFiles, output , tempDir + File.separator );
			}else{
				File targetDir = DirectoryUtils.getTargetDir(rootDir);
				for (int i = 0,j = fileNames.size() ; i < j; i++){
					//检查文件
					File newFile = new File(targetDir,fileNames.get(i));
					if(!newFile.exists()){
						continue;
					}
					tempFiles.add(newFile);
				}
				ZipUtils.compressFiles(tempFiles, output);
			}
		} finally {
			//关闭输出流
        	IOUtils.closeQuietly(output);
		}
	}


	@Override
	public void downloadFile(String rootDir, List<String> fileNames, ServletResponse response) throws Exception {
		//临时目录
		File tempDir = new File(WebContext.getServletContext().getRealPath(BaseConstant.TEMP_PATH));
		//文件集合
		Collection<File> tempFiles = new ArrayList<File>();
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从FTP服务器下载文件
			for (int i = 0,j = fileNames.size() ; i < j; i++){
				//下载文件
				File newFile = renameFile(tempDir, fileNames.get(i));
				getFtpClient().downloadToFile(rootDir , fileNames.get(i) , newFile);
				tempFiles.add(newFile);
			}
			ZipUtils.compressFiles(tempFiles, response.getOutputStream(), tempDir + File.separator );
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从文件共享服务器下载文件
			for (int i = 0,j = fileNames.size() ; i < j; i++){
				//下载文件
				File newFile = renameFile(tempDir, fileNames.get(i));
				getSmbClient().downloadToFile(rootDir , fileNames.get(i) , newFile);
				tempFiles.add(newFile);
			}
			ZipUtils.compressFiles(tempFiles, response.getOutputStream(), tempDir + File.separator );
		}else{
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			for (int i = 0,j = fileNames.size() ; i < j; i++){
				//检查文件
				File newFile = new File(targetDir,fileNames.get(i));
				if(!newFile.exists()){
					continue;
				}
				tempFiles.add(newFile);
			}
			ZipUtils.compressFiles(tempFiles, response.getOutputStream() );
		}
	}


	@Override
	public void downloadFile(String rootDir, String fileName, File destFile) throws Exception {
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从FTP服务器下载文件
			getFtpClient().downloadToFile(rootDir , fileName, destFile);
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从文件共享服务器下载文件
			getSmbClient().downloadToFile(rootDir , fileName, destFile);
		}else{
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			//检查文件
			File downloadFile = new File(targetDir,fileName);
			if(!downloadFile.exists()){
				return;
			}
			//下载文件
			FileUtils.copyFile(downloadFile, destFile);
		}
	}


	@Override
	public void downloadFile(String rootDir, String fileName, OutputStream output) throws Exception {
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从FTP服务器下载文件
			getFtpClient().downloadToStream(rootDir , fileName, output);
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从文件共享服务器下载文件
			getSmbClient().downloadToStream(rootDir , fileName, output);
		}else{
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			//检查文件
			File downloadFile = new File(targetDir,fileName);
			if(!downloadFile.exists()){
				return;
			}
			//下载文件
			FileUtils.copyFile(downloadFile, output);
		}
		
	}


	@Override
	public void downloadFile(String rootDir, String fileName, ServletResponse response) throws Exception {
		/**
		 * #文件存储方式：
		 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从FTP服务器下载文件
			getFtpClient().downloadToResponse(rootDir , fileName, response);
		}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
			//从文件共享服务器下载文件
			getSmbClient().downloadToResponse(rootDir , fileName, response);
		}else{
			File targetDir = DirectoryUtils.getTargetDir(rootDir);
			//检查文件
			File downloadFile = new File(targetDir,fileName);
			if(!downloadFile.exists()){
				return;
			}
			//下载文件
			FileUtils.copyFile(downloadFile, response.getOutputStream());
		}
		
	}


	public ISMBClient getSmbClient() {
		return smbClient;
	}


	public void setSmbClient(ISMBClient smbClient) {
		this.smbClient = smbClient;
	}


	public IFTPClient getFtpClient() {
		return ftpClient;
	}


	public void setFtpClient(IFTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
	
}
