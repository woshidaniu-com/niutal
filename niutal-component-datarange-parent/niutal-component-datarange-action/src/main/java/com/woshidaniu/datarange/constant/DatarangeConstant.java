package com.woshidaniu.datarange.constant;

public abstract class DatarangeConstant {

	/**
	 * 用于加密文件路径的RSA公钥
	 */
	public static final String RSA_PUBLIC_KEY = "datarange.rsa.public_key";
	/**
	 * 用于解密文件路径的RSA私钥
	 */
	public static final String RSA_PRIVATE_KEY = "datarange.rsa.private_key";
	/**
	 * 使用FTP服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
	 */
	public static final String FTP_PREFIX = "datarange.ftp.prefix";
	/**
	 * 使用文件共享服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
	 */
	public static final String SMB_PREFIX = "datarange.smb.prefix";
	/**
	 * 使用应用服务器本身作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
	 */
	public static final String SERVLET_PREFIX = "datarange.servlet.prefix";
	/**
	 * datarange判断一个类型是图片的依据
	 */
	public static final String IMAGE_TYPES = "datarange.imageTypes";
	/**
	 * datarange可上传和浏览的目录
	 */
	public static final String DIR_TYPES = "datarange.dirTypes";
	/**
	 * 可上传文件大小限制；默认 10M，单位为：KB,MB,GB,TB;注意这里配置的上传大小限制不能大于Strut2本身的上传文件大小限制，否则没有意义 
	 */
	public static final String MAX_SIZE = "datarange.maxsize";
	
	/**
	 * 非法的目录访问！
	 */
	public static final String ILLEGAL_VISIT = "datarange.illegalVisit";
	/**
	 * 访问目录不存在或不是目录！
	 */
	public static final String UNDEFINED_VISIT = "datarange.undefinedVisit";
	/**
	 * 目录访问异常！原因【{0}】
	 */
	public static final String VISIT_EXCEPTION = "datarange.visitException";
	/**
	 * 请选择文件。
	 */
	public static final String INVALID_REQUEST = "datarange.invalidRequest";
	/**
	 * 上传目录不存在。
	 */
	public static final String UNDEFINED_DIR = "datarange.undefinedDir";
	/**
	 * 目录名不被允许。
	 */
	public static final String INVALID_DIR = "datarange.invalidDir";
	/**
	 * 上传文件扩展名是不允许的扩展名。\n只允许{0}格式。
	 */
	public static final String INVALID_EXT = "datarange.invalidExt";
	/**
	 * 上传文件扩展名是不允许的扩展名。\n只允许{0}格式。
	 */
	public static final String INVALID_SIZE = "datarange.invalidSize";
	/**
	 * 上传目录不存在。
	 */
	public static final String NO_DIR = "datarange.noDir";
	/**
	 * 上传目录没有写权限。
	 */
	public static final String NO_WRITE = "datarange.noWrite";
	/**
	 * 文件上传异常！原因【{0}】
	 */
	public static final String UPLOAD_EXCEPTION = "datarange.uploadException";
	/**
	 * 文件名称参数前缀
	 */
	public static final String FILENAME_PREFIX = "&filename=";	
}
