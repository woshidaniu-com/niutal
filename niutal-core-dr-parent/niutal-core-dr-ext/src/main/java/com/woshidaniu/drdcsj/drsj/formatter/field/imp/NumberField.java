/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import org.apache.commons.lang3.math.NumberUtils;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;

public class NumberField implements IField {

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		return NumberUtils.isNumber(value);
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不是数字";
	}

	@Override
	public String toString() {
		return "NumberField@"+this.hashCode();
	}
}
