/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.constant;

import com.woshidaniu.util.base.MessageUtil;

/**
 *@类名称:BaseConstant.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：Nov 27, 2015 9:48:05 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */

public class BaseConstant {

	public static String DEFAULT_XXDM = "00000";
	
	/**
	 * 学校代码
	 */
	public static String XXDM = MessageUtil.getText("system.xxdm");
	public static String XXMC = MessageUtil.getText("system.xxmc");
	/**
	 * #文件存储方式：
	 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
	 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
	 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
	 */
	public static String UPLOAD_TO = MessageUtil.getText("system.uploadTo");
	
	/**
	 * 指定上传文件外部存储路径
	 */
	public static String UPLOAD_DIR = MessageUtil.getText("system.uploadDir");
	
	/**
	 * 临时文件路径
	 */
	public static String TEMP_PATH = MessageUtil.getText("niutal.tempPath");
	/**
	 * 系统名称
	 */
	public static String SYSTEM_TITLE = MessageUtil.getText("system.title");
	/**
	 * 系统功能模块代码，该值觉得当前运行系统会显示的菜单
	 */
	public static String SYSTEM_GNMKDM = MessageUtil.getText("system.xtgnmkdm");
	
	
	
}
