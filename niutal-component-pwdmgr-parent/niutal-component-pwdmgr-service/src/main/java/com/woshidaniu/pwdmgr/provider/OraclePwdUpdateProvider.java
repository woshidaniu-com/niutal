package com.woshidaniu.pwdmgr.provider;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.pwdmgr.api.PwdMgrKey;
import com.woshidaniu.pwdmgr.api.model.BindCaptcha;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.ResultData;
import com.woshidaniu.pwdmgr.api.provider.CaptchaProvider;
import com.woshidaniu.pwdmgr.api.provider.PwdUpdateProvider;
import com.woshidaniu.pwdmgr.service.svcinterface.UserAccountService;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.service.common.IPasswordStrengthCheckService;

public class OraclePwdUpdateProvider implements PwdUpdateProvider {
	
	@Resource
	protected UserAccountService accountService;
	
	@Resource
	private IPasswordStrengthCheckService passwordStrengthCheckService;
	
	/**
	 * 验证码生成服务提供者
	 */
	protected CaptchaProvider captchaProvider;
	
	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	@Override
	public Object get(BindData data) {
		return null;
	}

	@Override
	public ResultData update(BindData data) {
		
		/*
		 * 密码是否应该加密传输,数据库中密码不能明文保存
		 */
		if (StringUtils.isBlank(data.getUsername())) {
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_USERNAME_REQUIRED );
		}
		
		String newPassword = data.getMap().get("newpwd");
		if (StringUtils.isBlank(newPassword)) {
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_NEWPWD_REQUIRED );
		}
		
		String repeatPassword = data.getMap().get("repeatpwd");
		if (StringUtils.isBlank(repeatPassword)) {
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_REPEATPWD_REQUIRED );
		}

		// 检查密码强度
		if(!passwordStrengthCheckService.check(repeatPassword)){
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_REPEATPWD_INVALID );
		}
		
		//提取、验证 验证码
		BindCaptcha captcha = getCaptchaProvider().input(data);
		//-1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
		if(! "1".equals(getCaptchaProvider().valid(data, captcha))){
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_CAPTCHA_INVALID );
		}
		
		String uuid = data.getMap().get(PwdMgrKey.UUID);
		String captcha_code = data.getMap().get(PwdMgrKey.CAPTCHA);
		//对比提交的验证码和存储的验证码
		if(!captcha.getCaptcha().equals(captcha_code) || !captcha.getUuid().equals(uuid)){
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_CAPTCHA_INVALID );
		}
		
		//查询账号信息
		BaseMap accountBefor = getAccountService().getUserAccount(data);
		if (null == accountBefor) {
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_FAIL );
		}
		
		try {
			
			//修改密码
			accountBefor.put("newpassword", MD5Codec.encrypt(newPassword));
			getAccountService().updateAccount(accountBefor);
			
			//清除验证码;防止复用
			getCaptchaProvider().evict(data);
			
			return ResultData.to(PwdMgrKey.SUCCESS, PwdMgrKey.PWD_UPDATE_SUCCESS );
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_UPDATE_ERROR );
		}
		
	}

	public UserAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(UserAccountService accountService) {
		this.accountService = accountService;
	}

	public CaptchaProvider getCaptchaProvider() {
		return captchaProvider;
	}

	public void setCaptchaProvider(CaptchaProvider captchaProvider) {
		this.captchaProvider = captchaProvider;
	}
}
