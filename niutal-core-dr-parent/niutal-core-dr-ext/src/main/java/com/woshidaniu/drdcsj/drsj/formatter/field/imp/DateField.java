/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.DateFormatFactory;

/**
 * @author 		：康康（1571）
 * @description	： 日期字段，带缓存日期，因为年月日这样的日期，范围毕竟有限,且重复率比较高
 */
public class DateField implements IField {
	
	private static final Logger log = LoggerFactory.getLogger(DateField.class);
	
	private static final Set<String> CACHE = new HashSet<String>();
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	
	protected String pattern;
	protected DateFormat dateFormat;

	public DateField(String pattern) {
		this.pattern = pattern;
		this.dateFormat = DateFormatFactory.getDateFormat(this.pattern);
	}
	
	public DateField() {
		this.pattern = DEFAULT_PATTERN;
		this.dateFormat = DateFormatFactory.getDateFormat(this.pattern);
	}

	@Override
	public synchronized boolean check(DrlpzModel drlpzModel, String value) {
		if(StringUtils.isBlank(value)) {
			return false;
		}
		synchronized (CACHE) {
			if(CACHE.contains(value)) {
				log.debug("命中缓存");
				return true;
			}else {
				try {
					this.dateFormat.parse(value);
					CACHE.add(value);
					return true;
				} catch (ParseException e) {
					log.error("转换日期格式异常,源文本:{},目标格式:{}",value,pattern);
					return false;
				}
			}
		}
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		return "[" + drlpzModel.getDrlmc() + "]的值不符合[" + pattern + "]格式";
	}

	@Override
	public String toString() {
		return "DateField [pattern=" + pattern + "]@"+this.hashCode();
	}

}
