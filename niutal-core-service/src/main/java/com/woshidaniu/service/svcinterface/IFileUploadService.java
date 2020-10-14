package com.woshidaniu.service.svcinterface;

import java.io.File;
import java.util.List;


/**
 * 文件上传
 * @author Penghui.Qu
 *
 */
public interface IFileUploadService {

	
	/**
	 * 批量文件上传
	 * @param rootPaht
	 * @param files
	 * @param fileNames
	 * @param fileContentTypes
	 * @throws Exception
	 */
	public void uploadFile(String rootPaht, List<File> files, List<String> fileNames, List<String> fileContentTypes) throws Exception;
	
	
	
	/**
	 * 单个文件上传
	 * @param rootPaht
	 * @param file
	 * @param fileName
	 * @param fileContentType
	 * @throws Exception
	 */
	public File uploadFile(String rootPaht, File file, String fileName, String fileContentType) throws Exception;
}
