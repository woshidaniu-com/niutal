package com.woshidaniu.file.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.woshidaniu.file.exception.UploadException;

/**
 * 本地文件服务 
 * @author Administrator
 *
 */
@Service("localFileService")
public class LocalFileService implements FileService {

	
	/* (non-Javadoc)
	 * @see com.woshidaniu.file.FileService#upload(java.io.File)
	 */
	@Override
	public String upload(File file) throws IOException, UploadException {
		String date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
		return localUpload(file, date,true);
	}
	

	/* (non-Javadoc)
	 * @see com.woshidaniu.file.service.FileService#download(java.lang.String)
	 */
	@Override
	public File download(String fileInfo) throws IOException {
		return getFile(fileInfo);
	}


	private File getFile(String fileInfo) throws IOException {
		if (!fileInfo.startsWith(ServerType.LOCAL.toString())){
			throw new IOException("文件类型不匹配");
		}
		
		String[] info = fileInfo.split(serverTypeSplit);
		String absolutePath=FileConfig.getInstance().getLocalDir()+File.separator+info[1]+File.separator+info[2];
		File file = new File(absolutePath);
		
		if (!file.exists()){
			throw new FileNotFoundException("目标文件不存在或已删除");
		}
		return file;
	}


	/* (non-Javadoc)
	 * @see com.woshidaniu.file.service.FileService#deleteFile(java.lang.String)
	 */
	@Override
	public boolean deleteFile(String fileInfo) throws IOException {
		File file = getFile(fileInfo);
		return file.delete();
	}


	@Override
	public String upload(File file, String dir) throws IOException, UploadException {
		return localUpload(file, dir,true);
	}


	private String localUpload(File file, String dir,boolean reName) throws UploadException, IOException {
		String type = file.getName().substring(file.getName().lastIndexOf(fileTypeSplit)+1);
		
		if (!ArrayUtils.contains(FileConfig.getInstance().getAllowTypes(), type)){
			throw new UploadException("上传的文件类型不被允许！");
		}
		
		long size = file.length()/1024;//文件长度(KB)
		long maxSize = FileConfig.getInstance().getMaxSize();
		if (size > maxSize){
			throw new UploadException(String.format("上传的文件大小超过限制:%sKB！", maxSize));
		}
		
		String target = FileConfig.getInstance().getLocalDir();
		File targetDir = new File(target);
		
		if (!targetDir.exists()){
			targetDir.mkdirs();
		}
		
		String fileName = reName ? System.currentTimeMillis()+fileTypeSplit+type : file.getName();
		File targetFile = new File(targetDir, dir+File.separator+fileName);
		FileUtils.copyFile(file, targetFile);
		//返回字符串格式 LOCAL:${dir}:${fileName}
		return StringUtils.join(new String[]{ServerType.LOCAL.toString(),serverTypeSplit,dir,serverTypeSplit,targetFile.getName()});
	}


	@Override
	public String upload(File file, String dir, boolean reName) throws IOException, UploadException {
		return localUpload(file, dir,reName);
	}



}
