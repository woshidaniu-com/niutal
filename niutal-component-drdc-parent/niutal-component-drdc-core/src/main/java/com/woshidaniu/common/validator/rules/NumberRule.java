package com.woshidaniu.common.validator.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 验证字符串是数字的规则 <li>格式验证</li>
 * 
 * @author Jiangdong.Yi
 * 
 */
public class NumberRule implements IValidateRule {
	private String validateInfo;
	private final String defaultMessage = "只能输入数字！";

	public NumberRule() {

	}

	public NumberRule(String message) {
		validateInfo = message;
	}

	@Override
	public String getValidateInfo() {
		return StringUtils.defaultIfEmpty(this.validateInfo, defaultMessage);
	}

	@Override
	public boolean validate(Object value) {
		if (value == null) {
			return false;
		}
		String check = "[0-9]*";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(String.valueOf(value));
		return matcher.matches();
	}
}
