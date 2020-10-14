package com.woshidaniu.common.validator.rules;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符长度验证规则。
 * <li>正对String</li>
 * <li>默认最小长度为0，最大长度为20</li>
 * <li>属于数据格式范围验证</li>
 * 
 * @author Jiangdong.Yi
 * 
 */
public class StringLengthRule implements IValidateRule {
	private String validateInfo;
	private Integer min=0;
	private Integer max=20;
	private final String defaultMessage = "字符长度不在"+min+"-"+max+"范围内！";
	
	
	public StringLengthRule(Integer min, Integer max) {
		this.min = min;
		this.max = max;
		this.validateInfo = "字符长度不在"+min+"-"+max+"范围内!";
	}

	public StringLengthRule(Integer min, Integer max,String message) {
		this.min = min;
		this.max = max;
		this.validateInfo = message;
	}
	
	public StringLengthRule() {
		
	}
	
	public StringLengthRule(String message) {
		this.validateInfo = message;
	}

	@Override
	public String getValidateInfo() {
		return StringUtils.defaultIfEmpty(this.validateInfo, defaultMessage);
	}

	@Override
	public boolean validate(Object value) {
		if(value == null){
			return false;
		}
		if(value.toString().length() < min){
			return false;
		}else if(value.toString().length() > max){
			return false;
		}
		return true;
		
	}

	public void setValidateInfo(String validateInfo) {
		this.validateInfo = validateInfo;
	}
	
	 /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * 
     * @param value
     *            指定的字符串
     * @return 字符串的长度
     */
    private int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
