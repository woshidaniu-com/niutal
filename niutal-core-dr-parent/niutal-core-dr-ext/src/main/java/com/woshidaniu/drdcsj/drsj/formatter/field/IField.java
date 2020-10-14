/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;

/**
 * @author 982
 * 字段格式接口
 */
public interface IField {
	/**
	 * 格式检测
	 * @param drlpzModel 导入列配置信息
	 * @param value 当前列值
	 * @param param 格式化配置参数
	 * @return 是否是合法格式
	 */
	public boolean check(DrlpzModel drlpzModel, String value);
	/**
	 * 获取错误信息  
	 * @param drlpzModel 导入列配置信息
	 * @param value 当前列值
	 * @param param 格式化配置参数
	 * @return 错误信息
	 */
	public String getErrorMessage(DrlpzModel drlpzModel, String value);
}
