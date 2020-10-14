/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;

/**
 * @description	： 字段格式化器
 */
public class FieldFormatter implements ImportFormatter{
	
	private IField field;
	
	public FieldFormatter(IField field) {
		this.field = field;
	}
	
	@Override
	public FormatResult format(DrlpzModel drlpzModel, String value) {
		// 空不做处理
		if (StringUtils.isBlank(value) || field == null) {
			return new DefaultFormatResult(value);
		}else {
			boolean right = field.check(drlpzModel, value);
			if(right) {
				return new DefaultFormatResult(value);
			}else {
				String error = field.getErrorMessage(drlpzModel, value);
				return new DefaultFormatResult(value,error);
			}
		}
	}

	@Override
	public String toString() {
		return "FieldFormatter [field=" + field + "]@"+this.hashCode();
	}
}
