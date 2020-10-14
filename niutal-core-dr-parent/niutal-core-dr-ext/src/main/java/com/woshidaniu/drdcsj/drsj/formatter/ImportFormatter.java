/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;

/**
 * @description	： 格式化结果
 * @author 		：康康（1571）
 */
public interface ImportFormatter {

	/**
	 * 格式化数据
	 * @param drlpzModel 列配置信息
	 * @param value 格式化数据
	 * @return 格式化结果
	 */
	FormatResult format(DrlpzModel drlpzModel, String value);
}
