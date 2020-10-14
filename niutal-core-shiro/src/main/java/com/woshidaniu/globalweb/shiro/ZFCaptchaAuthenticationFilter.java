/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.shiro;

import org.apache.shiro.session.Session;

import com.woshidaniu.shiro.IncorrectCaptchaException;
import com.woshidaniu.shiro.token.CaptchaAuthenticationToken;

public class ZFCaptchaAuthenticationFilter extends DefaultAuthenticationFilter{

	@Override
	protected void validateCaptcha(Session session, CaptchaAuthenticationToken token) {
		
		Object result = session.getAttribute("com.woshidaniu.zfdun.zfcaptcha.servlet.VerifyFilter.VERIFY_RESULT_SESSION_KEY");
		if (isValidateCaptcha() && !"true".equals(result)){
			throw new IncorrectCaptchaException("Captcha validation failed!");
		}
	}
}
