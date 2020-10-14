/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import java.util.regex.Pattern;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.PatternFactory;

/**
 * @author 		：康康（1571）
 * 浮点数字字段
 */
public class FloatField implements IField {
	
	private static final String reg = "^[-\\+]?[.\\d]*$";
	
	private static Pattern pattern =  PatternFactory.compile(reg);

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		//isFloat
		return pattern.matcher(value).matches();
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不是浮点类型";
	}

	@Override
	public String toString() {
		return "FloatField@"+this.hashCode();
	}
}
