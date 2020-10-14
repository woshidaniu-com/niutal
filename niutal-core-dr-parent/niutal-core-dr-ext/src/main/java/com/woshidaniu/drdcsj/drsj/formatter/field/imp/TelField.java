/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.PatternFactory;

/**
 * 电话号码字段验证
 */
public class TelField implements IField {
	
	private static Pattern pattern =  PatternFactory.getTelPattern();

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不合法";
	}

	@Override
	public String toString() {
		return "TelField@"+this.hashCode();
	}
}
