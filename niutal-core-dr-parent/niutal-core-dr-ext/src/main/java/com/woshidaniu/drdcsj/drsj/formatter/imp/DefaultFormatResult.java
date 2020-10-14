/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;

/**
 * @description	： 默认格式化结果
 * @author 		：康康（1571）
 */
public class DefaultFormatResult implements FormatResult{

	private String result;
	private String error;
	
	public DefaultFormatResult(String result) {
		this.result = result;
	}
	
	public DefaultFormatResult(String result, String error) {
		this.result = result;
		this.error = error;
	}

	@Override
	public boolean hasError() {
		return StringUtils.isNotEmpty(error);
	}

	@Override
	public String getResult() {
		return this.result;
	}

	@Override
	public String getError() {
		return this.error;
	}
}
