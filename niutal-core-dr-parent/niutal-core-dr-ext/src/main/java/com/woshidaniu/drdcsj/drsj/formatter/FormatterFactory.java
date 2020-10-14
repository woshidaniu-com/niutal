/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter;

/**
 * @description	： 格式化方法对象工厂
 * @author 		：康康（1571）
 */
public interface FormatterFactory {

	/**
	 * @description	： 获得格式化器
	 * @param lsjgsh 配置的字符串
	 * @return
	 */
	ImportFormatter getFormatter(String lsjgsh);
}
