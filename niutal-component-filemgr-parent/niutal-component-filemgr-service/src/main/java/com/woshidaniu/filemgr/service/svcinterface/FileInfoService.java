package com.woshidaniu.filemgr.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.filemgr.dao.entities.FileInfoModel;


/**
 * 
 *@类名称	: FileInfoService.java
 *@类描述	： 文件信息Service接口
 *@创建人	：kangzhidong
 *@创建时间	：2017年7月17日 下午5:18:20
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface FileInfoService extends BaseService<FileInfoModel> {

	/**
	 * 增加一条文件信息
	 */
	public String insertFileInfo(FileInfoModel model);
	
	/**
	 * 删除一条文件信息
	 */
	public boolean deleteFileInfo(String uid);
	
	/**
	 * 更新一条文件信息
	 */
	public boolean updateFileInfo(String uid, String filename);
	
	/**
	 * 获取一条文件信息
	 */
	public FileInfoModel getFileInfo(String uid);
	
	/**
	 * 获取用户ID,指定目录总是
	 */
	public int getFileCount(String ukey, String parent);
	
	/**
	 *  获取用户ID,指定目录下所属的所有文件信息
	 */
	public List<FileInfoModel> listFiles(String ukey, String parent);
	
}
