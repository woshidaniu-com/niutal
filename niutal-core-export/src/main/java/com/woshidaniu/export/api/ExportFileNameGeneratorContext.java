/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.api;

/**
 * 导出文件名称构造器上下文
 * @author 		：康康（1571）
 */
public interface ExportFileNameGeneratorContext {

	/**
	 * @description	： 获得文件名称后缀
	 * @return
	 */
	String getSuffix();
	
	/**
	 * @description	： 获得导出编号
	 * @return
	 */
	String getDcclbh();
	
	/**
	 * @description	： 获得导出名称
	 * @return
	 */
	String getDcclmc();
	
	/**
	 * @description	： 获得偏好id
	 * @return
	 */
	String getPhid();
	
	/**
	 * @description	： 获得偏好名称
	 * @return
	 */
	String getPhmc();
}
