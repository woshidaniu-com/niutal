package com.woshidaniu.filemgr.patch.provider.def;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.woshidaniu.filemgr.api.FileObject;
import com.woshidaniu.filemgr.api.FileServProvider;
import com.woshidaniu.filemgr.patch.service.FileService;

@Deprecated
public class PatchLocalFileServProvider implements FileServProvider  {

	@Autowired
	@Qualifier("localFileService")
	private FileService fileService;
	
	
	@Override
	public FileObject output(File file) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public FileObject output(File file, String fileName) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public FileObject output(byte[] bytes, String fileName) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public FileObject info(String uid) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public File file(String fileInfo) throws Exception {
		return getFileService().download(fileInfo);
	}

	@Override
	public void copyLarge(String uid, OutputStream output) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public boolean delete(String uid) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public InputStream input(String uid) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public boolean existsDir(String ukey, String parent) throws Exception {
		throw new IOException("该方法不支持");
	}

	@Override
	public List<FileObject> listFiles(String ukey, String parent) throws Exception {
		throw new IOException("该方法不支持");
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
}
