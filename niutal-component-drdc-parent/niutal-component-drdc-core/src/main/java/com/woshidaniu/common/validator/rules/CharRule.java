package com.woshidaniu.common.validator.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 验证字符串是字母的规则 <li>格式验证</li>
 * 
 * @author Jiangdong.Yi
 * 
 */
public class CharRule implements IValidateRule {
	private String validateInfo;
	private final String defaultMessage = "只能输入字母！";

	public CharRule() {

	}

	public CharRule(String message) {
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
		String check = "[a-zA-Z]+";
		String checkValue=value.toString();
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(checkValue);
		return matcher.matches();
	}

}
