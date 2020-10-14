/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.file.impl;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.woshidaniu.common.file.TempFileNameGenerator;

/**
 * @author 康康
 *  默认临时文件构造器
 */
@Component("defaultTempFileService")
public final class DefaultTempFileServiceImpl extends AutoCleanableTempFileService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	public void initService() {
		this.start();
	}
	
	@PreDestroy
	public void destoryService() {
		this.stop();
	}

	@Override
	public File createTempFile(String moduleDir, String suffix){
		if(moduleDir == null || "".equals(moduleDir.trim())) {
			throw new IllegalArgumentException("moduleDir can not be empty");
		}
		if(suffix == null || "".equals(suffix.trim())) {
			throw new IllegalArgumentException("suffix can not be empty");
		}
		TempFileNameGenerator tempFileNameGenerator = new UuidTempFileNameGenerator(suffix);
		return this.createTempFile(moduleDir, tempFileNameGenerator);
	}
	
	@Override
	public File createTempFile(String moduleDir, TempFileNameGenerator tempFileNameGenerator) {
		String newFileName = tempFileNameGenerator.getName();
		File moduleDirFile = new File(this.getTempRootDir(),moduleDir);
		if(!moduleDirFile.exists()) {
			moduleDirFile.mkdir();
		}
		File newFile = new File(moduleDirFile,newFileName);
		if(newFile.exists()) {
			log.warn("you create file[{}] exist already , delete and create it ",newFile);
			newFile.delete();
		}
		try {
			newFile.createNewFile();
			
			this.createCount.incrementAndGet();
			
		} catch (IOException e) {
			log.error("创建文件异常{}",newFile,e);
			throw new IllegalStateException(e);
		}
		return newFile;
	}

	@Override
	public File getTempFile(String moduleDir, String name) {
		File moduleDirFile = new File(this.getTempRootDir(),moduleDir);
		if(!moduleDirFile.exists()) {
			moduleDirFile.mkdir();
		}
		File tempFile = new File(moduleDirFile,name);
		if(!tempFile.exists()) {
			log.warn("tempFile [{}] not exist ",tempFile);
		}
		return tempFile;
	}
}
