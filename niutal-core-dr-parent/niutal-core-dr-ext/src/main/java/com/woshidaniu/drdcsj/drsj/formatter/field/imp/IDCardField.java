/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.VerifyIdCard;

/**
 * @author 		：康康（1571）
 * 身份证字段
 */
public class IDCardField implements IField {

	@Override
	public boolean check(DrlpzModel drlpzModel, String value) {
		return VerifyIdCard.verify(StringUtils.upperCase(value));
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不是有效身份证";
	}

	@Override
	public String toString() {
		return "IDCardField@"+this.hashCode();
	}
}

