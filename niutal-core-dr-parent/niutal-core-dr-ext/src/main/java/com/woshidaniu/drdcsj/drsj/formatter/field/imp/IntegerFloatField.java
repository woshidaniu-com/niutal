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
 * @author 		：康康（1571）
 * 整数字段
 */
public class IntegerFloatField implements IField {
	
	private static final String reg = "^[1-9]\\d*|[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
	
	private static Pattern pattern =  PatternFactory.compile(reg);

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		//isIntegerFloat
		Matcher m = pattern.matcher(value);     
		return m.matches();
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String valu) {
		return "[" + drlpzModel.getDrlmc() + "]的值不是正整数或小数";
	}

	@Override
	public String toString() {
		return "IntegerFloatField@"+this.hashCode();
	}
}
