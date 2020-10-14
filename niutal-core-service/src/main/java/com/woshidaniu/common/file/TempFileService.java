/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.file;

import java.io.File;

/**
 * @author 康康
 * 临时文件服务
 */
public interface TempFileService {

	/**
	 * 得到临时文件
	 * @param moduleDir 模块名称
	 * @param name 文件名称
	 * @return
	 */
	File getTempFile(String moduleDir,String name);
	
	/**
	 * 创建临时文件
	 * @param moduleDir 模块名称
	 * @param suffix 文件后缀
	 * @return
	 */
	File createTempFile(String moduleDir,String suffix);

	/**
	 * @param moduleDir 模块名称
	 * @param tempFileNameGenerator 临时文件名称构建器
	 * @return
	 */
	File createTempFile(String moduleDir,TempFileNameGenerator tempFileNameGenerator);
}
