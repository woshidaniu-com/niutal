package com.woshidaniu.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.woshidaniu.service.svcinterface.IFileUploadService;


/**
 * 文件上传实现 
 * @author Penghui.Qu
 *
 */
@Service("fileUploadService")
public class FileUploadServiceImpl implements IFileUploadService {

	
	/**
	 * @see {@link package com.woshidaniu.service.svcinterface.IFileUploadService#uploadFile(String, List, List, List)}
	 */
	public void uploadFile(String rootPath, List<File> files, List<String> fileNames,
			List<String> fileContentTypes) throws Exception {
		
		File filePath = new File(rootPath);
		
		if (!filePath.exists()){
			filePath.mkdir();
		}
		
		for (int i = 0,j = files.size() ; i < j; i++){
			File newFile = createFile(filePath, fileNames.get(i));
			FileUtils.copyFile(files.get(i), newFile);
		}
	}
	
	
	/**
	 * @see {@link package com.woshidaniu.service.svcinterface.IFileUploadService#uploadFile(String, File, String, String)}
	 */
	public File uploadFile(String rootPath, File file, String fileName,
			String fileContentType) throws Exception {
		
		File filePath = new File(rootPath);
		
		if (!filePath.exists()){
			filePath.mkdir();
		}
		//File newFile = createFile(filePath, fileName);
		File newFile = new File(filePath, System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf(".")));
		FileUtils.copyFile(file, newFile);
		
		return newFile;
		
	}
	
	
	/**
	 * 创建文件
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	private File createFile(File filePath,String fileName){
		File newFile = new File(filePath, fileName);
		int splitIndex = fileName.lastIndexOf(".");
		int i = 1;
		
		while (newFile.exists()){
			String tempName = fileName.substring(0,splitIndex);
			StringBuilder newName = new StringBuilder();
			newName.append(tempName)
				   .append("(")
				   .append(i)
				   .append(")")
				   .append(fileName.substring(splitIndex));
			newFile = new File(filePath, newName.toString());
			i++;
		}
		return newFile;
	}


	
	

}
