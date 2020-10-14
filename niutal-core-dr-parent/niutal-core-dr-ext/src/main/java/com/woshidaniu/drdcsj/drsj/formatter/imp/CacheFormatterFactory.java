/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.imp;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.formatter.FormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;

/**
 * @description	：
 * 
 * 缓存的 格式化器工厂,一次导入会产生一个CacheFormatterFactory对象,
 * 如果按行处理,有很多行,必定会存在new大量的重复对象,这里缓存哪些对象,避免重复new对象导致浪费大量时间
 * 注意：这个对象的生命周期是一次导入开始到导入结束
 * 
 * @author 康康（1571）
 */
public class CacheFormatterFactory implements FormatterFactory{
	
	private static final Logger log = LoggerFactory.getLogger(CacheFormatterFactory.class);
	
	private Map<String,ImportFormatter> cachedFormatter = new HashMap<String,ImportFormatter>(16);
	
	private ImportFormatter returnOriginValueFormatter = new ReturnOriginValueFormatter();
	
	private FormatterFactory orginalFormatterFactory;
	
	public CacheFormatterFactory(FormatterFactory orginalFormatterFactory) {
		this.orginalFormatterFactory = orginalFormatterFactory;
	}

	@Override
	public ImportFormatter getFormatter(String lsjgsh) {
		
		if(StringUtils.isBlank(lsjgsh)) {
			return returnOriginValueFormatter;
		}
		ImportFormatter result = cachedFormatter.get(lsjgsh);
		
		if(result == null) {
			result = this.orginalFormatterFactory.getFormatter(lsjgsh);
			if(log.isTraceEnabled()) {
				log.trace("new one formatter[{}] by [{}] ",result,lsjgsh);				
			}
			cachedFormatter.put(lsjgsh, result);
		}else {
			if(log.isTraceEnabled()) {
				log.trace("get cached formatter[{}] by [{}] ",result,lsjgsh);				
			}
		}
		return result;
	}
}
