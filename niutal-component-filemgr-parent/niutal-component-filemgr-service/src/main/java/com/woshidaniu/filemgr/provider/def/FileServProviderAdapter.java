package com.woshidaniu.filemgr.provider.def;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.uid.Sequence;
import com.woshidaniu.filemgr.api.FileObject;
import com.woshidaniu.filemgr.api.FileObjectPredicate;
import com.woshidaniu.filemgr.api.FileServProvider;
import com.woshidaniu.filemgr.api.FilepathProvider;
import com.woshidaniu.filemgr.api.def.DateFilepathProvider;
import com.woshidaniu.filemgr.dao.entities.FileInfoModel;
import com.woshidaniu.filemgr.service.svcinterface.FileInfoService;
import com.woshidaniu.io.utils.DirectoryUtils;
import com.woshidaniu.io.utils.FileCopyUtils;
import com.woshidaniu.web.context.WebContext;

public abstract class FileServProviderAdapter implements FileServProvider, InitializingBean {

	protected static Logger LOG = LoggerFactory.getLogger(FileServProviderAdapter.class);
	
	//高性能ID生成器
	@Resource(name = "sequence")
	protected Sequence sequence = null;
	
	//文件路径生成器
	@Resource(name = "filepathProvider")
	protected FilepathProvider filepathProvider = null;
	
	//是否使用绝对路径
	@Value("#{filemgrProps['filemgr.serv.useAbsolutePath']}")
	private String useAbsolutePath = "false";
	
	@Value("#{filemgrProps['filemgr.serv.root']}")
	protected String localDir;
	
	//是否缓存远程存储服务的文件到本地
	@Value("#{filemgrProps['filemgr.serv.cacheable']}")
	protected boolean cacheable = false;
	
	//缓存的有效期；单位：毫秒
	@Value("#{filemgrProps['filemgr.serv.cache.expiry']}")
	protected long cacheExpiry = -1;
	
	/**
	 * 文件信息服务接口
	 */
	@Resource
	protected FileInfoService fileInfoService;
	
	protected static ConcurrentMap<String, FileObjectPredicate> COMPLIED_PREDICATE = new ConcurrentHashMap<String, FileObjectPredicate>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if( sequence == null){
			sequence = new Sequence();
		}
		
		if( filepathProvider == null){
			filepathProvider = new DateFilepathProvider();
		}
		
	}
	
	@Override
	public InputStream input(String uid)  throws Exception{
		ByteArrayOutputStream output = null;
		try {
			//构建字节输出流
			output = new ByteArrayOutputStream();
			//从文件服务下文件到内存对象流
			this.copyLarge(uid, output);
			//有数据写入
			if( output.size() > 0 ){
				ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
				return IOUtils.toBufferedInputStream(input);
			}
			return null;
		} finally {
			//关闭输出流
			IOUtils.closeQuietly(output);
		}
	}
	
	@Override
	public FileObject info(String uid) throws Exception {
		//首先获取文件信息
		FileInfoModel info = getFileInfoService().getFileInfo(uid);
		//检查对应的文件信息是否存在
		if(info == null){
			throw new FileNotFoundException("Unable to retrieve the file information from database .");
		}

		return new FileObject(info.getUid(), info.getServ(), info.getName(), info.getSize(), info.getTime(), info.getPath());
	}
	
	protected File getTmpDir() throws IOException {
		//获取IO临时目录
		File tmpDir = new File(SystemUtils.getJavaIoTmpDir(), "filemgr");
		if (!tmpDir.exists()) {
			tmpDir.setReadable(true);
			tmpDir.setWritable(true);
            if (!tmpDir.mkdirs()) {
            	 if (!tmpDir.isDirectory()) {
                     final String message =  "Unable to create directory " + tmpDir;
                     throw new IOException(message);
                 }
            }
        }
		return tmpDir;
	}
	
	protected File getTargetDir(String targetDir) throws IOException {
		File directory = getRootDir();
		//添加模块路径
		if (!BlankUtils.isBlank(targetDir)) {
			directory = new File(directory , targetDir);
		}
		return directory;
	}
	
	protected File getRootDir() throws IOException {
		
		String localDir = this.getLocalDir();
		File directory = null;
		
		if("true".equals(this.useAbsolutePath)) {
			directory = new File(localDir);
			if(!directory.exists()) {
				directory.mkdirs();
			}
		}else {
			//从配置文件找到指定的外部存储路径配置
			if(!StringUtils.isEmpty(getLocalDir())){
				//未找到指定的外部目录，使用本地程序根目录
				String directoryPath = WebContext.getServletContext().getRealPath(DirectoryUtils.getResolvePath(localDir));
				//.getContextPath()
				directory = DirectoryUtils.getExistDir(directoryPath);
			}else{
				//判断指定的路径是否存在
				directory = SystemUtils.getUserDir();
			}
		}
		return directory;
	}
	
	protected void delCache(String outputDir, String outputName) {
		//启用本地缓存
		if(isCacheable()){
			try {
				//检查目录
				File targetDir = this.getTargetDir(outputDir);
				if(targetDir.exists()){
					//文件缓存
					File localFile = new File(targetDir, outputName);
					if(localFile.exists()){
						localFile.delete();
					}
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	protected void doCache(File file, String outputDir, String outputName) {
		//启用本地缓存
		if(isCacheable()){
			try {
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
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	protected void doCache(byte[] bytes, String outputDir, String outputName) {
		//启用本地缓存
		if(isCacheable()){
			try {
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
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	protected File getCache(String outputDir, String outputName){
		//启用本地缓存
		if(isCacheable()){
			try {
				//检查目录
				File targetDir = this.getTargetDir(outputDir);
				if(!targetDir.exists()){
				     return null;
				}
				//文件缓存
				File localFile = new File(targetDir, outputName);
				//判断是否是存在的文件，且未过期
				if(localFile.exists() && (System.currentTimeMillis() - localFile.lastModified() < cacheExpiry)){
					return localFile;
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
		return null;
	}
	
	protected FileObjectPredicate getPredicate(String dir) {
		if (StringUtils.isNotEmpty(dir)) {
			FileObjectPredicate ret = COMPLIED_PREDICATE.get(dir);
			if (ret != null) {
				return ret;
			}
			ret = new FileObjectPredicate(dir);
			FileObjectPredicate existing = COMPLIED_PREDICATE.putIfAbsent(dir, ret);
			if (existing != null) {
				ret = existing;
			}
			return ret;
		}
		return null;
	}
	
	@Override
	public boolean existsDir(String ukey, String parent) throws Exception{
		return getFileInfoService().getFileCount(ukey, parent) > 0;
	}
	
	@Override
	public List<FileObject> listFiles(String ukey, String parent) throws Exception{
		//创建空集合对象
		List<FileObject> infoTree = new ArrayList<FileObject>();
		//首先获取文件信息
		List<FileInfoModel> infoList = getFileInfoService().listFiles(ukey , parent);
		//检查对应的文件信息是否存在
		if(infoList == null){
			return infoTree;
		}
		
		FileObject tmp = null;
		for (FileInfoModel infoModel : infoList) {
			
			tmp = new FileObject();
			tmp.setServ(infoModel.getServ());
			tmp.setName(infoModel.getName());
			
			// 没有UID表示是文件目录查询操作
			if( StringUtils.isEmpty(infoModel.getUid()) ){
				
				tmp.setDir(true);
				tmp.setType("");
				
			} else {
				
				tmp.setDir(false);
				tmp.setType(FilenameUtils.getExtension(infoModel.getName()));
				tmp.setUid(infoModel.getUid());
			}
			
			tmp.setSize(infoModel.getSize());
			tmp.setTime(infoModel.getTime());
			tmp.setPath(infoModel.getPath());
			
			infoTree.add(tmp);
		}
		
		return infoTree;
	}
	
	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public FilepathProvider getFilepathProvider() {
		return filepathProvider;
	}

	public void setFilepathProvider(FilepathProvider filepathProvider) {
		this.filepathProvider = filepathProvider;
	}
	
	public FileInfoService getFileInfoService() {
		return fileInfoService;
	}

	public void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}

	public String getLocalDir() {
		return localDir;
	}

	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public long getCacheExpiry() {
		return cacheExpiry;
	}

	public void setCacheExpiry(long cacheExpiry) {
		this.cacheExpiry = cacheExpiry;
	}
	
}
