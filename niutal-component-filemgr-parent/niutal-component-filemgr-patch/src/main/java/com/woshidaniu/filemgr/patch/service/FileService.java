package com.woshidaniu.filemgr.patch.service;

import java.io.File;
import java.io.IOException;

import com.woshidaniu.filemgr.patch.exception.UploadException;

/**
 * 文件服务接口
 * @author Administrator
 *
 */
@Deprecated
public interface FileService {

	String fileTypeSplit = ".";
	String serverTypeSplit = ":";
	 
	enum ServerType{
		FTP,
		LOCAL
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @return fileInfo 
	 * 格式 LOCAL:${date}:${fileName} 或  FTP:${date}:${fileName}
	 * @throws IOException 
	 */
	String upload(File file) throws IOException,UploadException;
	
	
	/**
	 * 文件上传
	 * @param file
	 * @param dir 上传文件目录
	 * @return fileName 
	 * @throws IOException 
	 */
	String upload(File file , String dir) throws IOException,UploadException;
	
	
	/**
	 * 文件上传
	 * @param file
	 * @param dir 上传文件目录
	 * @param reName 是否重命名
	 * @return fileName 
	 * @throws IOException 
	 */
	String upload(File file , String dir , boolean reName) throws IOException,UploadException;
	
	
	
	/**
	 * 文件下载
	 * @param fileInfo 
	 * @return
	 * @throws IOException 
	 */
	File download(String fileInfo) throws IOException;
	
	
	
	
	/**
	 * 文件删除
	 * @param fileInfo
	 * @return
	 * @throws IOException 
	 */
	boolean deleteFile(String fileInfo) throws IOException;
}
