/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter;

/**
 * @description	： 格式化结果
 * @author 		：康康（1571）
 */
public interface FormatResult {

	/**
	 * @description	： 是否存在错误
	 * @return
	 */
	boolean hasError();
	
	/**
	 * @description	： 获得格式化结果
	 * @return
	 */
	String getResult();
	
	/**
	 * @description	： 获得错误信息
	 * @return
	 */
	String getError();
}
