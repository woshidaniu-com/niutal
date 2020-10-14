/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.utils;

import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.formatter.field.imp.DateField;

/**
 * @author 		：康康（1571）
 * 日期格式化器工厂，缓存已经创建的对象
 */
public class DateFormatFactory {
	
	private static final Logger log = LoggerFactory.getLogger(DateField.class);

	/**
	 * 缓存创建的DateFormat对象
	 */
	private static final Map<String,DateFormat> cacheDateFormatMapper = new HashMap<String,DateFormat>();

	public synchronized static DateFormat getDateFormat(String pattern) {
		
		DateFormat dateFormat = cacheDateFormatMapper.get(pattern);
		
		if(dateFormat == null) {
			dateFormat = new ThreadSafeSimpleDateFormat(pattern);				
			cacheDateFormatMapper.put(pattern, dateFormat);
			if(log.isDebugEnabled()) {
				log.debug("模式{}没有命中缓存,新建格式化器并放入缓存",pattern,dateFormat);
			}
		}else {
			if(log.isDebugEnabled()) {
				log.debug("模式{}命中缓存,获得格式化器{}",pattern,dateFormat);
			}
		}
		return dateFormat;
	}
	
	private static class ThreadSafeSimpleDateFormat extends SimpleDateFormat{

		private static final long serialVersionUID = -2242489411655155686L;

		public ThreadSafeSimpleDateFormat(String pattern) {
			super(pattern);
		}

		@Override
		public synchronized StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
			return super.format(date, toAppendTo, pos);
		}

		@Override
		public synchronized AttributedCharacterIterator formatToCharacterIterator(Object obj) {
			return super.formatToCharacterIterator(obj);
		}

		@Override
		public synchronized Date parse(String text, ParsePosition pos) {
			return super.parse(text, pos);
		}
	}
}
