package com.woshidaniu.editor.api.model;

public abstract class KindeditorConstant {

	/**
	 * 用于加密文件路径的RSA公钥
	 */
	public static final String RSA_PUBLIC_KEY = "kindeditor.rsa.public_key";
	/**
	 * 用于解密文件路径的RSA私钥
	 */
	public static final String RSA_PRIVATE_KEY = "kindeditor.rsa.private_key";
	/**
	 * 使用FTP服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
	 */
	public static final String FTP_PREFIX = "kindeditor.ftp.prefix";
	/**
	 * 使用文件共享服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
	 */
	public static final String SMB_PREFIX = "kindeditor.smb.prefix";
	/**
	 * 使用应用服务器本身作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
	 */
	public static final String SERVLET_PREFIX = "kindeditor.servlet.prefix";
	/**
	 * kindeditor判断一个类型是图片的依据
	 */
	public static final String IMAGE_TYPES = "kindeditor.imageTypes";
	/**
	 * kindeditor可上传和浏览的目录
	 */
	public static final String DIR_TYPES = "kindeditor.dirTypes";
	/**
	 * 可上传文件大小限制；默认 10M，单位为：KB,MB,GB,TB;注意这里配置的上传大小限制不能大于Strut2本身的上传文件大小限制，否则没有意义 
	 */
	public static final String MAX_SIZE = "kindeditor.maxsize";
	
	/**
	 * 非法的目录访问！
	 */
	public static final String FILEMGR_KINDEDITOR_ILLEGAL_VISIT = "filemgr.kindeditor.illegalVisit";
	/**
	 * 访问目录不存在或不是目录！
	 */
	public static final String FILEMGR_KINDEDITOR_UNDEFINED_VISIT = "filemgr.kindeditor.undefinedVisit";
	/**
	 * 目录访问异常！原因【{0}】
	 */
	public static final String FILEMGR_KINDEDITOR_VISIT_EXCEPTION = "filemgr.kindeditor.visitException";
	/**
	 * 请选择文件。
	 */
	public static final String FILEMGR_KINDEDITOR_INVALID_REQUEST = "filemgr.kindeditor.invalidRequest";
	/**
	 * 上传目录不存在。
	 */
	public static final String FILEMGR_KINDEDITOR_UNDEFINED_DIR = "filemgr.kindeditor.undefinedDir";
	/**
	 * 目录名不被允许。
	 */
	public static final String FILEMGR_KINDEDITOR_INVALID_DIR = "filemgr.kindeditor.invalidDir";
	/**
	 * 上传文件扩展名是不允许的扩展名。\n只允许{0}格式。
	 */
	public static final String FILEMGR_KINDEDITOR_INVALID_EXT = "filemgr.kindeditor.invalidExt";
	/**
	 * 上传文件扩展名是不允许的扩展名。\n只允许{0}格式。
	 */
	public static final String FILEMGR_KINDEDITOR_INVALID_SIZE = "filemgr.kindeditor.invalidSize";
	/**
	 * 上传目录不存在。
	 */
	public static final String FILEMGR_KINDEDITOR_NO_DIR = "filemgr.kindeditor.noDir";
	/**
	 * 上传目录没有写权限。
	 */
	public static final String FILEMGR_KINDEDITOR_NO_WRITE = "filemgr.kindeditor.noWrite";
	/**
	 * 文件上传异常！原因【{0}】
	 */
	public static final String FILEMGR_KINDEDITOR_UPLOAD_EXCEPTION = "filemgr.kindeditor.uploadException";
	/**
	 * 文件唯一ID（uid）不能为空。
	 */
	public static final String FILEMGR_KINDEDITOR_UID_REQUIRED = "filemgr.kindeditor.uid.required";
	/**
	 * 文件名称参数前缀
	 */
	public static final String FILENAME_PREFIX = "&filename=";	
	
}
