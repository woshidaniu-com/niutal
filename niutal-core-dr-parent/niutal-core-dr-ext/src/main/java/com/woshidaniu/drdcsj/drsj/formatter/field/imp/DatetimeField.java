/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import java.text.DateFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.DateFormatFactory;
/**
 * @author 		：康康（1571）
 * @description	： 日期时间字段
 */
public class DatetimeField implements IField {
	
	private static final Logger log = LoggerFactory.getLogger(DatetimeField.class);
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private String pattern;
	protected DateFormat dateFormat;
	
	public DatetimeField(String pattern) {
		this.pattern = pattern;
		this.dateFormat = DateFormatFactory.getDateFormat(this.pattern);
	}
	
	public DatetimeField() {
		this.pattern = DEFAULT_PATTERN;
		this.dateFormat = DateFormatFactory.getDateFormat(this.pattern);
	}

	@Override
	public synchronized boolean check(DrlpzModel drlpzModel, String value) {
		if(StringUtils.isBlank(this.pattern)){
			return false;
		}
		try {
			dateFormat.parse(value);
		} catch (ParseException e) {
			log.error("转换日期格式异常,源文本:{},目标格式:{}",value,pattern);
			return false;
		}
		return true;
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不符合[" + pattern + "]格式";
	}

	@Override
	public String toString() {
		return "DatetimeField [pattern=" + pattern + "]@"+this.hashCode();
	}
}
