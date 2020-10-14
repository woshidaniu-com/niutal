/**
 * 
 */
package com.woshidaniu.zxzx.auth.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.zxzx.auth.AuthStateResolver;
import com.woshidaniu.zxzx.auth.ZxzxAuthIncorrectCAPTCHAException;
import com.woshidaniu.zxzx.auth.ZxzxAuthInvalidStateException;
import com.woshidaniu.zxzx.auth.ZxzxAuthToken;
import com.woshidaniu.zxzx.auth.ZxzxAuthentication;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.auth.impl.DefaultZxzxAuthTokenValidatorImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月21日上午9:14:19
 */
@Service
public class DefaultZxzxAuthenticationImpl implements ZxzxAuthentication {

	public static final String DEFAULT_SESSION_CAPTCHA_KEY = "KAPTCHA_SESSION_KEY";

	protected String sessoionCaptchaKey = DEFAULT_SESSION_CAPTCHA_KEY;

	@Autowired
	protected AuthStateResolver authStateResolver;

	@Override
	public boolean isAuthenticated() {
		return authStateResolver.isAuthenticated();
	}

	@Override
	public void login(ZxzxAuthToken token) {
		validateCaptcha(WebContext.getSession(), token);
		boolean state = authStateResolver.authenticate(token.getUserName(), token.getPassword());
		if (!state) {
			throw new ZxzxAuthInvalidStateException("UserName or Password incorrect!");
		}
	}

	@Override
	public String getPrinciple() {
		return authStateResolver.getPrinciple();
	}

	protected void validateCaptcha(HttpSession session, ZxzxAuthToken token) {
		if (!validateCaptcha((String) session.getAttribute(getSessoionCaptchaKey()), token.getCAPTCHA())) {
			throw new ZxzxAuthIncorrectCAPTCHAException("Captcha validation failed!");
		}
	}

	public String getSessoionCaptchaKey() {
		return sessoionCaptchaKey;
	}

	protected boolean validateCaptcha(String request, String token) {
		if (StringUtils.isNull(request)) {
			return false;
		}
		return StringUtils.equalsIgnoreCase(request, token);
	}

	public AuthStateResolver getAuthStateResolver() {
		return authStateResolver;
	}

	public void setAuthStateResolver(AuthStateResolver authStateResolver) {
		this.authStateResolver = authStateResolver;
	}

	public void setSessoionCaptchaKey(String sessoionCaptchaKey) {
		this.sessoionCaptchaKey = sessoionCaptchaKey;
	}

}
