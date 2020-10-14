package com.woshidaniu.filemgr.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * @类名称 : FileInfoModel.java
 * @类描述 ： 文件信息对象Model
 * @创建人 ：kangzhidong
 * @创建时间 ：2017年7月17日 下午4:56:01
 * @修改人 ：
 * @修改时间 ：
 * @版本号 :v1.0
 */
@SuppressWarnings("serial")
public class FileInfoModel extends ModelBase {

	/**
	 * 用户唯一ID：用于关联当前文件上传者ID
	 */
	protected String ukey;
	/**
	 * 文件唯一ID：用于获取文件
	 */
	protected String uid;
	/**
	 * 文件存储服务类型：ftp,samba,local
	 */
	protected String serv;
	/**
	 * 文件所在目录
	 */
	protected String dir;
	/**
	 * 文件原始名称：用于页面回显
	 */
	protected String name;
	/**
	 * 文件大小：单位：字节
	 */
	protected String size;
	/**
	 * 文件上传时间；格式：YYYY-MM-DD hh24:ss:mi
	 */
	protected String time;
	/**
	 * 服务器存储路径：可作为文件管理功能的目录结构数据来源
	 */
	protected String path;

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getServ() {
		return serv;
	}

	public void setServ(String serv) {
		this.serv = serv;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
