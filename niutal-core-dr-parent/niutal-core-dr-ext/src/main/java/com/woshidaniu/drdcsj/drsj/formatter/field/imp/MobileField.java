/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.PatternFactory;

public class MobileField implements IField {
	
	private static Pattern pattern =  PatternFactory.getMobilePattern();

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不是有效手机号码";
	}

	@Override
	public String toString() {
		return "MobileField@"+this.hashCode();
	}
}
