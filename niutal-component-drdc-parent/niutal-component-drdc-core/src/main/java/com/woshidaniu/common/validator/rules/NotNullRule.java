package com.woshidaniu.common.validator.rules;


import org.apache.commons.lang3.StringUtils;

/**
 * 不为空验证的规则。用于判断对象是否为空。
 * <li>格式验证</li>
 * @author Jiangdong.Yi
 * 
 */
public class NotNullRule implements IValidateRule {
	private String validateInfo;
	private final String defaultMessage = "不能为空！";
	public NotNullRule() {

	}

	public NotNullRule(String message) {
		validateInfo = message;
	}

	@Override
	public String getValidateInfo() {
		return StringUtils.defaultIfEmpty(this.validateInfo, defaultMessage);
	}

	@Override
	public boolean validate(Object value) {
		return value != null;
	}
}
