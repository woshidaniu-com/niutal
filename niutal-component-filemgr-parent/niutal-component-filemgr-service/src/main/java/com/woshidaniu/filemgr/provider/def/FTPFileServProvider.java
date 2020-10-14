package com.woshidaniu.filemgr.provider.def;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.woshidaniu.filemgr.api.FileObject;
import com.woshidaniu.filemgr.api.FileServ;
import com.woshidaniu.filemgr.dao.entities.FileInfoModel;
import com.woshidaniu.ftpclient.client.IFTPClient;
/**
 *  #文件存储方式：<br/>
 *	#FileServ.FTP：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数<br/>
 */
public class FTPFileServProvider extends FileServProviderAdapter {

	/**
	 * FTP文件服务客户端
	 */
	@Resource
	protected IFTPClient ftpClient;
	
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
		
		//上传文件至FTP服务器
		getFtpClient().upload(file, outputDir , outputName);
		
		//构建文件信息model
		FileInfoModel info = new FileInfoModel();
		
		//文件原始名称
		info.setName(fileName);
		//文件存储服务类型
		info.setServ(FileServ.FTP.getName());
		//文件存储目录
		info.setDir(outputDir);
		//文件物理存储路径
		info.setPath(outputPath);
		//文件大小
		info.setSize(String.valueOf(file.length()));
		
		//存储文件信息
		String uid = getFileInfoService().insertFileInfo(info );
		
		//如果需要缓存，则异步缓存文件
		doCache(file, outputDir, outputName);
		
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
		
		//上传文件至FTP服务器
		getFtpClient().upload(bytes, outputDir , outputName);
		
		//构建文件信息model
		FileInfoModel info = new FileInfoModel();
		
		//文件原始名称
		info.setName(fileName);
		//文件存储服务类型
		info.setServ(FileServ.FTP.getName());
		//文件存储目录
		info.setDir(outputDir);
		//文件物理存储路径
		info.setPath(outputPath);
		//文件大小
		info.setSize(String.valueOf(bytes.length));
		
		//存储文件信息
		String uid = getFileInfoService().insertFileInfo(info);
		
		//如果需要缓存，则异步缓存文件
		doCache(bytes, outputDir, outputName);
				
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
		
		if(!FileServ.FTP.getName().equals(info.getServ())){
			throw new FileNotFoundException("Unable to retrieve the file from FTP file service .");
		}
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(info.getPath());
		//文件物理存储名称
		String outputName = FilenameUtils.getName(info.getPath());
		//以文件原始名输出
		String tmpFileName = String.format("%s（%s）.%s", FilenameUtils.getBaseName(info.getName()), uid ,FilenameUtils.getExtension(info.getName()));
		File localFile = new File(getTmpDir(), tmpFileName);
		
		//已经缓存的文件
		File cache = getCache(outputDir, outputName);
		if(cache != null && cache.exists()){
			return cache;
		}
		
		//从FTP服务器下载文件
		getFtpClient().downloadToFile(outputDir , outputName, localFile);
		
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
		
		if(!FileServ.FTP.getName().equals(info.getServ())){
			throw new FileNotFoundException("Unable to retrieve the file from FTP file service .");
		}
		
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(info.getPath());
		//文件物理存储名称
		String outputName = FilenameUtils.getName(info.getPath());
		
		//已经缓存的文件
		File cache = getCache(outputDir, outputName);
		if(cache != null && cache.exists()){
			FileUtils.copyFile(cache, output);
			return;
		}
		
		//从FTP服务器下载文件
		getFtpClient().downloadToStream(outputDir , outputName, output);
		
	}
	
	@Override
	public boolean delete(String uid) throws Exception{

		//首先获取文件信息
		FileInfoModel info = getFileInfoService().getFileInfo(uid);
		//检查对应的文件信息是否存在
		if(info == null){
			throw new FileNotFoundException("Unable to retrieve the file information from database .");
		}
		
		if(!FileServ.FTP.getName().equals(info.getServ())){
			throw new FileNotFoundException("Unable to retrieve the file from FTP file service .");
		}
		
		//文件物理存储目录（相对）
		String outputDir = FilenameUtils.getFullPathNoEndSeparator(info.getPath());
		//文件物理存储名称
		String outputName = FilenameUtils.getName(info.getPath());
		
		//如果有本地缓存，则删除缓存文件
		delCache(outputDir, outputName);
		
		//从FTP服务器删除文件
		getFtpClient().remove(outputDir, outputName);
		
		//删除文件信息
		getFileInfoService().deleteFileInfo(uid);
		
		return true;
	}
	
	public IFTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(IFTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}
	
}
