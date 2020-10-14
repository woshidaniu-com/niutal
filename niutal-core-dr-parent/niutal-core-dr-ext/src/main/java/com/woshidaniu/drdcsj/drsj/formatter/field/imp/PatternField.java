/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.formatter.field.imp;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.field.IField;
import com.woshidaniu.drdcsj.drsj.utils.PatternFactory;

/**
 * @author 		：康康（1571）
 * @description	： 正则表达式字段，缓存验证过的字符串，重复利用，提高效率
 */
public class PatternField implements IField {
	
	private static final Logger log = LoggerFactory.getLogger(PatternField.class);
	
	private final Set<String> cache = new HashSet<String>();

	private String pattern;
	
	private Pattern regexPattern;
	
	private String errorMessage;

	public PatternField(String pattern) {
		if(StringUtils.contains(pattern, "#")){
			this.pattern = StringUtils.split(pattern, "#")[0];
			this.errorMessage = StringUtils.split(pattern, "#")[1];
		}else{
			this.pattern = pattern;
		}
		this.regexPattern = PatternFactory.compile(this.pattern);
	}

	@Override
	public synchronized boolean check(DrlpzModel drlpzModel, String value) {
		if(cache.contains(value)) {
			return true;
		}
		Matcher m = this.regexPattern.matcher(value);
		boolean b = m.matches();
		if(b) {
			cache.add(value);
		}
		return b;
	}

	@Override
	public String getErrorMessage(DrlpzModel drlpzModel, String value) {
		if(StringUtils.isNotEmpty(errorMessage)){
			return "[" + drlpzModel.getDrlmc() + "]" + errorMessage;
		}else{
			return "[" + drlpzModel.getDrlmc() + "]的值不合法";
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return "PatternField [pattern=" + pattern + ", regexPattern=" + regexPattern + ", errorMessage=" + errorMessage	+ "]@"+this.hashCode();
	}
}
