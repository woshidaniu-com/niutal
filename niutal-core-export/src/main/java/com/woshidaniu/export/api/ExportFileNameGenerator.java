/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.api;

/**
 * 导出文件名称构造器
 * @author 		：康康（1571）
 */
public interface ExportFileNameGenerator {

	/**
	 * @description	： 构造文件名称
	 * @param context
	 * @return
	 */
	String generateFileName(ExportFileNameGeneratorContext context);
	
}
