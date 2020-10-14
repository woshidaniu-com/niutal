package com.woshidaniu.pwdmgr.provider;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import com.woshidaniu.basicutils.RandomUtils;
import com.woshidaniu.pwdmgr.api.model.BindCaptcha;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.BindTime;
import com.woshidaniu.pwdmgr.api.provider.CaptchaProvider;
import com.woshidaniu.pwdmgr.dao.daointerface.CaptchaDao;
import com.woshidaniu.pwdmgr.dao.entities.CaptchaModel;

/**
 * 
 *@类名称		： OracleStorageCaptchaProvider.java
 *@类描述		：基于Oracle数据库存储的验证码服务提供实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 上午11:02:40
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class OracleStorageCaptchaProvider implements CaptchaProvider {

	protected String format = "yyyy-MM-dd HH:mm:ss";
	protected SimpleDateFormat sdf = new SimpleDateFormat(format);
	protected int effectTime = 1000 * 30;
	
	@Resource
	protected CaptchaDao captchaDao;

	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public BindCaptcha input(BindData data) {
		
		CaptchaModel model = new CaptchaModel();
		
		
		getCaptchaDao().getModel(model);
		
		
		return null;
	}

	@Override
	public String valid(BindData data,BindCaptcha captcha) {
		if(captcha == null){
			return "-1";
		}
		//当前数据库时间
		BindTime now =  new BindTime(getCaptchaDao().getDatetime() , getFormat(), getEffectTime());
		//验证码发送时间
		BindTime send = new BindTime(captcha.getTimestamp() , getFormat(), getEffectTime());
		//验证码对象不为空且发送时间在有效时间内
		if(send.compareTo(now) > 0){
			return "1";
		}
		//删除数据库中的过期值
		CaptchaModel model = new CaptchaModel();
		getCaptchaDao().delete(model);
		return "0";
	}

	@Override
	public BindCaptcha gen(BindData data) {
		return new BindCaptcha(getCaptchaDao().getUUID(), RandomUtils.genRandomNum(6), getCaptchaDao().getDatetime());
	}

	@Override
	public boolean store(BindData data, BindCaptcha captcha) {
		
		CaptchaModel model = new CaptchaModel();
		
		
		
		
		getCaptchaDao().insert(model);
		return true;
	}
	
	@Override
	public boolean evict(BindData data) {
		
		CaptchaModel model = new CaptchaModel();
		
		model.setCaptcha_id(data.getUuid());
		
		getCaptchaDao().delete(model);
		return true;
	}

	public CaptchaDao getCaptchaDao() {
		return captchaDao;
	}

	public void setCaptchaDao(CaptchaDao captchaDao) {
		this.captchaDao = captchaDao;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
		this.sdf = new SimpleDateFormat(format);
	}

	public int getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(int effectTime) {
		this.effectTime = effectTime;
	}
	
}
