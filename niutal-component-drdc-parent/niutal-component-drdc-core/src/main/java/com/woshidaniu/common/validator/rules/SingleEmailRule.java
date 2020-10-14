package com.woshidaniu.common.validator.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 验证是否为单个邮箱的规则 <li>属于格式验证</li>
 * 
 * @author Jiangdong.Yi
 * 
 */
public class SingleEmailRule implements IValidateRule {
	private String validateInfo;
	private final String defaultMessage = "邮箱格式不正确！";

	public SingleEmailRule() {

	}

	public SingleEmailRule(String message) {
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
		String validateValue = value.toString();

		// String check = "^(\\w+[-_.]?)+@((\\w+[-_]?)+\\.)+[a-zA-Z]{2,}$";
		String check = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

		// String check =
		// "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(validateValue);
		return matcher.matches();
	}
}
