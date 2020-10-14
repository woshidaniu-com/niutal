package com.woshidaniu.filemgr.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.filemgr.dao.entities.FileInfoModel;

/**
 * 
 *@类名称	: FileInfoDao.java
 *@类描述	： 文件信息Dao接口
 *@创建人	：kangzhidong
 *@创建时间	：2017年7月17日 下午5:17:58
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Repository
public interface FileInfoDao extends BaseDao<FileInfoModel> {
	
	/**
	 * 增加一条文件信息
	 */
	public int insertFileInfo(FileInfoModel model);
	
	/**
	 * 删除一条文件信息
	 */
	public int deleteFileInfo(@Param("uid") String uid);

	/**
	 * 更新一条文件信息
	 */
	public int updateFileInfo(@Param("uid") String uid,@Param("filename") String filename);
	
	/**
	 * 获取一个文件的UID
	 */
	public String getFileGuid();
	
	/**
	 * 获取一条文件信息
	 */
	public FileInfoModel getFileInfo(@Param("uid") String uid);
	
	/**
	 * 获取用户ID,指定目录总是
	 */
	public int getFileCount(@Param("ukey") String ukey,@Param("dir") String parent);
	
	/**
	 *  获取用户ID,指定目录下所属的所有文件信息
	 */
	public List<FileInfoModel> listFiles(@Param("ukey") String ukey,@Param("dir") String parent);
	
}
