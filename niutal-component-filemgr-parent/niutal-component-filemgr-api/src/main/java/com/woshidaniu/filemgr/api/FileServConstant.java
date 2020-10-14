package com.woshidaniu.filemgr.api;

public abstract class FileServConstant {

	//获取文件存储路径
	public static String DEFAULT_WEB_LOCALDIR = "tmpdir";
	//获取请求过滤前缀
	public static String DEFAULT_WEB_REQUESTPREFIX = "/ftp/";
	//获取是否缓存FTP文件到本地存储路径
	public static boolean DEFAULT_WEB_CACHELOCAL = false;
	//获取共享文件在本地缓存的时间;默认10分钟
	public static long DEFAULT_WEB_CACHEEXPIRY = 10 * 60 * 1000;
	
	//获取文件存储路径
	public static String WEB_LOCALDIR_KEY = "filemgr.file.tmpdir";
	//获取请求过滤前缀
	public static String WEB_REQUESTPREFIX_KEY = "filemgr.file.requestPrefix";
	//获取是否缓存FTP文件到本地存储路径
	public static String WEB_CACHELOCAL_KEY = "filemgr.file.cacheLocal";
	//获取共享文件在本地缓存的时间;默认10分钟
	public static String WEB_CACHEEXPIRY_KEY = "filemgr.file.cacheExpiry";
	//异常信息重定向路径
	public static String WEB_REDIRECTURL_KEY = "filemgr.file.redirectURL";
	
	
	/**
	 * 文件删除成功。
	 */
	public static final String FILE_DEL_SUCCESS = "filemgr.file.del.success";
	/**
	 * 文件删除失败。
	 */
	public static final String FILE_DEL_FAILED = "filemgr.file.del.fail";
	/**
	 * 文件删除异常。
	 */
	public static final String FILE_DEL_ERROR = "filemgr.file.del.error";
	/**
	 * 文件更新成功。
	 */
	public static final String FILE_UPDATE_SUCCESS = "filemgr.file.upload.success";
	/**
	 * 文件更新失败。
	 */
	public static final String FILE_UPDATE_FAILED = "filemgr.file.upload.fail";
	/**
	 * 文件更新异常。
	 */
	public static final String FILE_UPDATE_ERROR = "filemgr.file.upload.error";
	/**
	 * 文件下载异常。
	 */
	public static final String FILE_DOWNLOAD_ERROR = "filemgr.file.download.error";
	/**
	 * 文件唯一ID（uid）不能为空。
	 */
	public static final String FILE_UID_REQUIRED = "filemgr.file.uid.required";
	/**
	 * 文件名不能为空。
	 */
	public static final String FILE_NAME_REQUIRED = "filemgr.file.name.required";
			
	
}
