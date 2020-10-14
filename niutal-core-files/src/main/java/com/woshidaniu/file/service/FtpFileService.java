package com.woshidaniu.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import com.woshidaniu.file.exception.UploadException;

/**
 * 
 * Ftp文件服务
 * @author Administrator
 *
 */
@Service("ftpFileService")
public class FtpFileService implements FileService {

	/* (non-Javadoc)
	 * @see com.woshidaniu.file.FileService#upload(java.io.File)
	 */
	@Override
	public String upload(File file) throws IOException, UploadException {
		String date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
		return ftpUpload(file, date,true);
	}

	//打开连接
	private void openFtpConnection(FTPClient ftpClient) throws IOException{
		FileConfig config = FileConfig.getInstance();
		ftpClient.connect(config.getFtpUrl(),config.getFtpPort()); 
		ftpClient.login(config.getFtpUser(),config.getFtpPassword()); 
       
		 //是否成功登录FTP服务器
        int replyCode = ftpClient.getReplyCode();
        if(!FTPReply.isPositiveCompletion(replyCode)){
        	throw new IOException("ftp服务器登录失败！");
        }
	}
	
	//关闭连接
	private void closeFtpConnection(FTPClient ftpClient){
		if(ftpClient.isConnected()) {
	        try {
	        	ftpClient.logout();  
	        	ftpClient.disconnect();
	        } catch(IOException e) {
	          e.printStackTrace();
	        }
        }
	}


	/* (non-Javadoc)
	 * @see com.woshidaniu.file.service.FileService#download(java.lang.String)
	 */
	@Override
	public File download(String fileInfo) throws IOException {
		FTPClient ftpClient = new FTPClient(); 
		File resultFile = null;
		OutputStream os = null;
		
		try {
			openFtpConnection(ftpClient);
			
			FTPFile targetFile = getFTPFile(fileInfo, ftpClient);
			
			resultFile = new File(System.getProperty("java.io.tmpdir"),targetFile.getName());
	        os = new FileOutputStream(resultFile);
	        ftpClient.retrieveFile(targetFile.getName(), os);
		} finally{
			closeFtpConnection(ftpClient);
			if (os != null){
				os.close();
			}
		}
		return resultFile;
	}

	//查找ftp目录文件
	private FTPFile getFTPFile(String fileInfo, FTPClient ftpClient)
			throws IOException, FileNotFoundException {
		if (!fileInfo.startsWith(ServerType.FTP.toString())){
			throw new IOException("文件类型不匹配");
		}
		
		String[] info = fileInfo.split(serverTypeSplit);
		ftpClient.changeWorkingDirectory(info[1]);

		FTPFile[] files = ftpClient.listFiles();	
		FTPFile targetFile = null;
		for (FTPFile file : files){
			 if(file.getName().equalsIgnoreCase(info[2])){
				 targetFile = file;
		         break;
		     }
		}
		
		if (targetFile == null){
			throw new FileNotFoundException("目标文件不存在或已删除");
		}
		return targetFile;
	}


	/* (non-Javadoc)
	 * @see com.woshidaniu.file.service.FileService#deleteFile(java.lang.String)
	 */
	@Override
	public boolean deleteFile(String fileInfo) throws IOException {
		FTPClient ftpClient = new FTPClient(); 
		
		try {
			openFtpConnection(ftpClient);
			FTPFile targetFile = getFTPFile(fileInfo, ftpClient);
			return ftpClient.deleteFile(targetFile.getName());
		} finally{
			closeFtpConnection(ftpClient);
		}
	}

	@Override
	public String upload(File file, String dir) throws IOException, UploadException {
		return ftpUpload(file, dir,true);
	}

	private String ftpUpload(File file, String dir , boolean reName) throws UploadException {
		String type = file.getName().substring(file.getName().lastIndexOf(fileTypeSplit)+1);
		
		if (!ArrayUtils.contains(FileConfig.getInstance().getAllowTypes(), type)){
			throw new UploadException("上传的文件类型不被允许！");
		}
		
		long size = file.length()/1024;//文件长度(KB)
		long maxSize = FileConfig.getInstance().getMaxSize();
		if (size > maxSize){
			throw new UploadException(String.format("上传的文件大小超过限制:%sKB！", maxSize));
		}
		
		FTPClient ftpClient = new FTPClient(); 

		try {
			//建立ftp连接
			openFtpConnection(ftpClient);
			
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
	        ftpClient.makeDirectory(dir);
	        ftpClient.changeWorkingDirectory(dir);
	        String fileName = reName ? System.currentTimeMillis()+fileTypeSplit+type : file.getName();
	        boolean status = ftpClient.storeFile(fileName, new FileInputStream(file));  
	        
	        if (!status){
	        	throw new UploadException("上传失败，请检查ftp服务器配置！");
	        }
	        
	        //返回字符串格式 FTP:${fileName}
	        return StringUtils.join(new String[]{ServerType.FTP.toString(),serverTypeSplit,dir,serverTypeSplit,fileName});
		} catch(IOException ex){
			throw new UploadException("ftp服务器连接失败！");
		} finally{
			closeFtpConnection(ftpClient);
		}
	}

	@Override
	public String upload(File file, String dir, boolean reName) throws IOException, UploadException {
		return ftpUpload(file, dir,reName);
	}

	
}
