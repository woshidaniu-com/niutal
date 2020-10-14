package com.woshidaniu.filemgr.provider.def;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.woshidaniu.filemgr.api.FileObject;
import com.woshidaniu.filemgr.api.FileServ;
import com.woshidaniu.filemgr.dao.entities.FileInfoModel;
import com.woshidaniu.io.utils.FileCopyUtils;
/**
 *  #文件存储方式：<br/>
 *	#FileServ.LOCAL：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常<br/>
 */
public class LocalFileServProvider extends FileServProviderAdapter {
	
	@Override
	public FileObject output(File file) throws Exception {
		return this.output(file, file.getName());
	}
	
	@Override
	public FileObject output(File file, String fileName) throws Exception {
		
		//使用文件路径生成器生成文件路径，便于后期扩展
		String outputPath = getFilepathProvider().generate(fileName, String.valueOf(getSequence().nextId()));
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(outputPath);
		//文件物理存储名称
		String outputName = FilenameUtils.getName(outputPath);
		
		//检查目录
		File targetDir = this.getTargetDir(outputDir);
		if(!targetDir.exists()){
			targetDir.setReadable(true);
			targetDir.setWritable(true);
			targetDir.mkdirs();
		}
		//上传文件
		File newFile = new File(targetDir, outputName);
		FileUtils.copyFile(file, newFile);
		
		//构建文件信息model
		FileInfoModel info = new FileInfoModel();
		
		//文件原始名称
		info.setName(fileName);
		//文件存储服务类型
		info.setServ(FileServ.LOCAL.getName());
		//文件存储目录
		info.setDir(outputDir);
		//文件物理存储路径
		info.setPath(outputPath);
		//文件大小
		info.setSize(String.valueOf(newFile.length()));
		
		//存储文件信息
		String uid = getFileInfoService().insertFileInfo(info);
		//返回文件描述对象
		return new FileObject(uid, info.getServ(), info.getName(), info.getSize(), info.getTime(), info.getPath());
			
	}
	
	@Override
	public FileObject output(byte[] bytes, String fileName) throws Exception {
		
		//使用文件路径生成器生成文件路径，便于后期扩展
		String outputPath = getFilepathProvider().generate(fileName, String.valueOf(getSequence().nextId()));
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(outputPath);
		//文件物理存储名称
		String outputName = FilenameUtils.getName(outputPath);
		
		//检查目录
		File targetDir = this.getTargetDir(outputDir);
		if(!targetDir.exists()){
			targetDir.setReadable(true);
			targetDir.setWritable(true);
			targetDir.mkdirs();
		}
		//上传文件
		File newFile = new File(targetDir, outputName);
		FileCopyUtils.copy(bytes, newFile);
		
		//构建文件信息model
		FileInfoModel info = new FileInfoModel();
		
		//文件原始名称
		info.setName(fileName);
		//文件存储服务类型
		info.setServ(FileServ.LOCAL.getName());
		//文件存储目录
		info.setDir(outputDir);
		//文件物理存储路径
		info.setPath(outputPath);
		//文件大小
		info.setSize(String.valueOf(newFile.length()));
		
		//存储文件信息
		String uid = getFileInfoService().insertFileInfo(info);
		//返回文件描述对象
		return new FileObject(uid, info.getServ(), info.getName(), info.getSize(), info.getTime(), info.getPath());
		
	}
	
	@Override
	public File file(String uid) throws Exception {
		
		//首先获取文件信息
		FileInfoModel info = getFileInfoService().getFileInfo(uid);
		//检查对应的文件信息是否存在
		if(info == null){
			throw new FileNotFoundException("Unable to retrieve the file information from database .");
		}
		
		if(!FileServ.LOCAL.getName().equals(info.getServ())){
			throw new FileNotFoundException("Unable to retrieve the file from local file directory .");
		}
		
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(info.getPath());
		//文件物理存储名称
		String outputName = FilenameUtils.getName(info.getPath());
		//以文件原始名输出
		String tmpFileName = String.format("%s（%s）.%s", FilenameUtils.getBaseName(info.getName()), uid ,FilenameUtils.getExtension(info.getName()));
		File localFile = new File(getTmpDir(), tmpFileName);
		
		//检查目录
		File targetDir = this.getTargetDir(outputDir);
		if(!targetDir.exists()){
             throw new FileNotFoundException("Unable to found directory " + targetDir);
		}
		//检查文件
		File targetFile = new File(targetDir, outputName);
		if(!targetFile.exists()){
			throw new FileNotFoundException("Unable to found File " + targetFile);
		}
		//下载文件
		FileUtils.copyFile(targetFile, localFile);
		
		return localFile;
	}
	
	@Override
	public void copyLarge(String uid, OutputStream output) throws Exception {
		
		//首先获取文件信息
		FileInfoModel info = getFileInfoService().getFileInfo(uid);
		//检查对应的文件信息是否存在
		if(info == null){
			throw new FileNotFoundException("Unable to retrieve the file information from database .");
		}
		
		if(!FileServ.LOCAL.getName().equals(info.getServ())){
			throw new FileNotFoundException("Unable to retrieve the file from local file directory .");
		}
		
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(info.getPath());
		//文件物理存储名称
		String outputName = FilenameUtils.getName(info.getPath());
		
		//检查目录
		File targetDir = this.getTargetDir(outputDir);
		if(!targetDir.exists()){
             throw new FileNotFoundException("Unable to found directory " + targetDir);
		}
		//检查文件
		File targetFile = new File(targetDir, outputName);
		if(!targetFile.exists()){
			throw new FileNotFoundException("Unable to found File " + targetFile);
		}
		//下载文件
		FileUtils.copyFile(targetFile, output);
		
	}
	
	@Override
	public boolean delete(String uid) throws Exception{

		//首先获取文件信息
		FileInfoModel info = getFileInfoService().getFileInfo(uid);
		//检查对应的文件信息是否存在
		if(info == null){
			throw new FileNotFoundException("Unable to retrieve the file information from database .");
		}
		
		if(!FileServ.LOCAL.getName().equals(info.getServ())){
			throw new FileNotFoundException("Unable to retrieve the file from local file directory .");
		}
		
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(info.getPath());
		//文件物理存储名称
		String outputName = FilenameUtils.getName(info.getPath());
		
		//检查目录
		File targetDir = getTargetDir(outputDir);
		if(!targetDir.exists()){
             throw new FileNotFoundException("Unable to found directory " + targetDir);
		}
		//检查文件
		File targetFile = new File(targetDir, outputName);
		if(!targetFile.exists()){
			throw new FileNotFoundException("Unable to found File " + targetFile);
		}
		//删除文件
		targetFile.delete();
		
		//删除文件信息
		getFileInfoService().deleteFileInfo(uid);
		
		return true;
	}
	
}
