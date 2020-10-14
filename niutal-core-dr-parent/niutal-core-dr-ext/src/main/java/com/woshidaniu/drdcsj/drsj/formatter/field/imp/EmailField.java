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
 * Email字段
 */
public class EmailField implements IField {
	
	private static Pattern pattern =  PatternFactory.getEmailPattern();

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		//isEmail
		Matcher m = pattern.matcher(value);     
		return m.matches();
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String valu) {
		return "[" + drlpzModel.getDrlmc() + "]的值不合法";
	}

	@Override
	public String toString() {
		return "EmailField@"+this.hashCode();
	}
}
