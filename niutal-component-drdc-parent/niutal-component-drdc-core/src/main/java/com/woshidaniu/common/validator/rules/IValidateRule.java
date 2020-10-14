package com.woshidaniu.common.validator.rules;

/**
 * 验证规则的接口。所有的具体验证规则都实现自这个接口。
 * 
 * @author Jiangdong.Yi
 * 
 */
public interface IValidateRule {
	/**
	 * 进行具体规则的验证逻辑。
	 * 
	 * @param value
	 *            具体的验证内容。
	 * @return true：验证通过；false：验证不通过
	 */
	public boolean validate(Object value);

	/**
	 * 获取验证不通过时的报错信息。如果用户指定的验证信息为空，则返回默认的报错信息。
	 * 
	 * @return 返回验证的报错信息
	 */
	public String getValidateInfo();
}
