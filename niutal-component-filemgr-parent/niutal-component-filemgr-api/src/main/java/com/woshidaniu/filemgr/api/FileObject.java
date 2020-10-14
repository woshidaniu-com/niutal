package com.woshidaniu.filemgr.api;

public class FileObject {

	/**
	 * 文件唯一ID：用于获取文件
	 */
	protected String uid;
	/**
	 * 文件存储服务类型：ftp,samba,local
	 */
	protected String serv;
	/**
	 * 是否目录
	 */
	protected boolean dir;
	/**
	 * 文件类型
	 */
	protected String type;
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

	public FileObject() {
	}
	
	public FileObject(String uid, String serv, String name, String size, String time,
			String path) {
		this.uid = uid;
		this.serv = serv;
		this.name = name;
		this.size = size;
		this.time = time;
		this.path = path;
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

	public boolean isDir() {
		return dir;
	}

	public void setDir(boolean dir) {
		this.dir = dir;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
