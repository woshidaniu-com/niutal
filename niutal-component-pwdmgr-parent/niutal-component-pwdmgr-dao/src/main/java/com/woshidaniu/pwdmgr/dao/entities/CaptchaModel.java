package com.woshidaniu.pwdmgr.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * @类名称 ： CaptchaModel.java
 * @类描述 ：验证码校验表，绑定时存储验证信息，验证后立即物理删除
 * @创建人 ：kangzhidong
 * @创建时间 ：2017年4月10日 上午11:21:21
 * @修改人 ：
 * @修改时间 ：
 * @版本号 :v1.0
 */
@SuppressWarnings("serial")
public class CaptchaModel extends ModelBase {

	/**
	 * 验证码表ID，用于数据删除
	 */
	protected String captcha_id;

	/**
	 * 该验证码关联外部UUID;用于业务逻辑关联查询
	 */
	protected String out_uuid;

	/**
	 * 验证码
	 */
	protected String captcha;
	/**
	 * 发送时间
	 */
	protected String out_time;

	public String getCaptcha_id() {
		return captcha_id;
	}

	public void setCaptcha_id(String captcha_id) {
		this.captcha_id = captcha_id;
	}

	public String getOut_uuid() {
		return out_uuid;
	}

	public void setOut_uuid(String out_uuid) {
		this.out_uuid = out_uuid;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getOut_time() {
		return out_time;
	}

	public void setOut_time(String out_time) {
		this.out_time = out_time;
	}

	@Override
	public String toString() {
		return "Captcha [id=" + captcha_id + ", out_uuid=" + out_uuid + ", captcha=" + captcha + ", out_time="
				+ out_time + "]";
	}

}
