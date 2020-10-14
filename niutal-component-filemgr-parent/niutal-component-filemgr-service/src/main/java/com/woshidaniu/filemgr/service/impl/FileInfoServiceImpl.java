package com.woshidaniu.filemgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.filemgr.dao.daointerface.FileInfoDao;
import com.woshidaniu.filemgr.dao.entities.FileInfoModel;
import com.woshidaniu.filemgr.service.svcinterface.FileInfoService;
import com.woshidaniu.web.WebContext;

/**
 * 
 * @类名称 : FileInfoServiceImpl.java
 * @类描述 ：文件信息Service接口实现
 * @创建人 ：kangzhidong
 * @创建时间 ：2017年7月17日 下午5:18:36
 * @修改人 ：
 * @修改时间 ：
 * @版本号 :v1.0
 */
@Service("fileInfoService")
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfoModel, FileInfoDao> implements FileInfoService {

	@Autowired
	protected FileInfoDao fileInfoDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(fileInfoDao);
	}

	@Override
	public String insertFileInfo(FileInfoModel model) {
		
		/**
		 * 用户唯一ID：用于关联当前文件上传者ID
		 */
		String ukey = WebContext.getUser().getYhm();
		/**
		 * 文件唯一ID：用于获取文件
		 */
		String uid = getFileInfoDao().getFileGuid();
		
		model.setUkey(ukey);
		model.setUid(uid);
		
		getFileInfoDao().insertFileInfo(model);
		
		return uid;
	}

	@Override
	public boolean deleteFileInfo(String uid) {
		getFileInfoDao().deleteFileInfo(uid);
		return true;
	}
	
	@Override
	public boolean updateFileInfo(String uid, String filename) {
		getFileInfoDao().updateFileInfo(uid, filename);
		return true;
	}

	@Override
	public FileInfoModel getFileInfo(String uuid) {
		return getFileInfoDao().getFileInfo(uuid);
	}

	@Override
	public int getFileCount(String ukey, String parent) {
		return getFileInfoDao().getFileCount(ukey, parent);
	}
	
	@Override
	public List<FileInfoModel> listFiles(String ukey, String parent) {
		return getFileInfoDao().listFiles(ukey, parent);
	}
	
	public FileInfoDao getFileInfoDao() {
		return fileInfoDao;
	}

	public void setFileInfoDao(FileInfoDao fileInfoDao) {
		this.fileInfoDao = fileInfoDao;
	}
	
}
