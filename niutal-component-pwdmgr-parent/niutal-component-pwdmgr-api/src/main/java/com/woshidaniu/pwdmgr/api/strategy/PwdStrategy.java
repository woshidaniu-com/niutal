package com.woshidaniu.pwdmgr.api.strategy;

public final class PwdStrategy {

	/**
	 * 默认：default
	 */
	public static final String DEFAULT_STRATEGY = "default";
	/**
	 * 邮件：email
	 */
	public static final String PWD_RETAKE_BY_EMAIL = "email";
	/**
	 * 手机验证码：phone
	 */
	public static final String PWD_RETAKE_BY_PHONE = "phone";
	/**
	 * 一卡通：one-card
	 */
	public static final String PWD_RETAKE_BY_ONECARD = "one-card";
	/**
	 * 动态口令：One-time password （otp）
	 */
	public static final String PWD_RETAKE_BY_OTP = "otp";
	/**
	 * 密保问题： Security question （sq）
	 */
	public static final String PWD_RETAKE_BY_SQ = "sq";
	/**
	 * 申述： Appeal
	 */
	public static final String PWD_RETAKE_BY_APPEAL = "appeal";
	
	
}
