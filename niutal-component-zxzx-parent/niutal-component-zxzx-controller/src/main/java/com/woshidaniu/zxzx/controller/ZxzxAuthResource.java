/**
 * 
 */
package com.woshidaniu.zxzx.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.zxzx.auth.ZxzxAuthIncorrectCAPTCHAException;
import com.woshidaniu.zxzx.auth.ZxzxAuthInvalidStateException;
import com.woshidaniu.zxzx.auth.ZxzxAuthToken;
import com.woshidaniu.zxzx.auth.ZxzxAuthentication;
import com.woshidaniu.zxzx.auth.impl.DefaultZxzxAuthTokenImpl;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.controller.ZxzxAuthResouece.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月20日下午8:40:24
 */
@Controller
@RequestMapping("/zxzx/web")
public class ZxzxAuthResource extends AbstractZxzxWebResource {
	
	private static final Logger log = LoggerFactory.getLogger(ZxzxAuthResource.class);

	// 用于校验用户登录
	@Autowired
	protected ZxzxAuthentication zxzxAuthentication;

	public static final String REQ_USER_NAME_PARAM_KEY = "userName";
	public static final String REQ_PASSWORD_PARAM_KEY = "password";
	public static final String REQ_CAPTCHA_PARAM_KEY = "CAPTCHA";

	protected String getRequestUserName(HttpServletRequest request) {
		return StringUtils.trim(request.getParameter(REQ_USER_NAME_PARAM_KEY));
	}

	protected String getRequestPassword(HttpServletRequest request) {
		return StringUtils.trim(request.getParameter(REQ_PASSWORD_PARAM_KEY));
	}

	protected String getRequestCAPTCHA(HttpServletRequest request) {
		return StringUtils.trim(request.getParameter(REQ_CAPTCHA_PARAM_KEY));
	}

	@ResponseBody
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public Object doAuth(HttpServletRequest request) {
		
		String username = getRequestUserName(request);
		String password = getRequestPassword(request);
		ZxzxAuthToken token = new DefaultZxzxAuthTokenImpl(username, password,getRequestCAPTCHA(request));

		Exception ex = null;

		ObjectNode stateNode = getObjectMapper().createObjectNode();

		try {
			zxzxAuthentication.login(token);
		} catch (Exception e) {
			log.error("在线咨询模块用户登录[{}]异常",username,e);
			ex = e;
		}

		if (ex == null) {
			stateNode.put("state", STATE_VALID);
			return stateNode;
		}

		if (ex instanceof ZxzxAuthInvalidStateException) {
			stateNode.put("state", STATE_INVALID);
			return stateNode;
		}

		if (ex instanceof ZxzxAuthIncorrectCAPTCHAException) {
			stateNode.put("state", STATE_CHPTCHA_INVALID);
			return stateNode;
		}

		stateNode.put("state", STATE_UNKNOWN);
		return stateNode;
	}

	// 登录验证成功
	static final int STATE_VALID = 1;

	// 登录验证失败
	static final int STATE_INVALID = 0;

	// 登录验证码验证失败
	static final int STATE_CHPTCHA_INVALID = 2;

	// 登录验证码验证失败
	static final int STATE_UNKNOWN = 9;
}
