package com.woshidaniu.globalweb.controller;

import java.security.interfaces.RSAPublicKey;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.woshidaniu.basicutils.ArrayUtils;
import com.woshidaniu.common.GlobalString;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.globalweb.api.LoginControllerPageSelector;
import com.woshidaniu.globalweb.api.impl.DefaultLoginControllerPageSelector;
import com.woshidaniu.service.common.RSALoginService;
import com.woshidaniu.shiro.IncorrectCaptchaException;
import com.woshidaniu.shiro.InvalidAccountException;
import com.woshidaniu.shiro.InvalidStateException;
import com.woshidaniu.shiro.SubjectUtils;
import com.woshidaniu.shiro.authenticator.ModularRealmAuthenticationException;
import com.woshidaniu.shiro.realm.AccountRealm;
import com.woshidaniu.shiro.realm.DefaultAccountRealm;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.context.WebContext;

import net.sf.json.JSONObject;

/**
 * 
 * <p>
 * <h3>niutal框架
 * <h3>说明：用户登录 / 切换角色
 * <p>
 * 
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月16日下午3:59:59
 */
@Controller
@RequestMapping(value = "/xtgl/login")
public class LoginController extends BaseController {

	private RSALoginService rsaLoginService = new RSALoginService();
	
	//private static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";
	private static final String DEFAULT_ERROR_INSTANCE_KEY_ATTRIBUTE_NAME = "shiroLoginFailureException";

	private static final String INDEX_PAGE = "/xtgl/index/initMenu.zf";
	
	@Autowired(required=false)
	private LoginControllerPageSelector loginControllerPageSelector = new DefaultLoginControllerPageSelector();

	/**
	 * 
	 * <p>
	 * 方法说明：获取公钥
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
	 * <p>
	 * <p>
	 * 时间：2016年9月1日上午11:30:27
	 * <p>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPublicKey")
	@ResponseBody
	public JSONObject getPublicKey(HttpServletRequest request) {
		
		RSAPublicKey publicKey = this.rsaLoginService.generateKey(request);

		byte[] modulus = publicKey.getModulus().toByteArray();
		byte[] exponent = publicKey.getPublicExponent().toByteArray();

		JSONObject json = new JSONObject();
		json.put("modulus", Base64.encodeBase64String(modulus));
		json.put("exponent", Base64.encodeBase64String(exponent));
		return json;
	}

	/**
	 * 
	 * <p>
	 * 方法说明：用户登录
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
	 * <p>
	 * <p>
	 * 时间：2017年3月16日下午3:59:37
	 * <p>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/slogin")
	public String slogin(HttpServletRequest request) {
		boolean authenticated = SubjectUtils.isAuthenticated();

		/**
		 * 如果用户已登录，直接转发到首页
		 */
		if (authenticated) {
			return "redirect:" + INDEX_PAGE;
		}

		HttpSession session = request.getSession();
		session.setAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY, null);

//		String ERROR_VALUE = (String) request.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
//
//		/**
//		 * 验证码错误返回失败
//		 */
//		if (StringUtils.equals(IncorrectCaptchaException.class.getName(), ERROR_VALUE)) {
//			request.setAttribute("message", MessageUtil.getText("login.info.error"));
//			/**
//			 * 账户或密码错误返回失败
//			 */
//		} else if (StringUtils.equals(InvalidAccountException.class.getName(), ERROR_VALUE)) {
//			request.setAttribute("message", MessageUtil.getText("login.info.failed"));
//			/**
//			 * 账户没有启用返回失败
//			 */
//		} else if (StringUtils.equals(InvalidStateException.class.getName(), ERROR_VALUE)) {
//			request.setAttribute("message", MessageUtil.getText("login.user.forbid"));
//			/**
//			 * 用户账户暂时被锁定，原因是密码重试超过指定次数
//			 */
//		} else if (StringUtils.equals(ExcessiveAttemptsException.class.getName(), ERROR_VALUE)) {
//			request.setAttribute("message", MessageUtil.getText("login.user.lock"));
//			/**
//			 * 如果是多个realm处理产生的异常，需要告知用户详细原因
//			 */
//		} else if (StringUtils.equals(ModularRealmAuthenticationException.class.getName(), ERROR_VALUE)) {
//			ModularRealmAuthenticationException authenticationException = (ModularRealmAuthenticationException) request
//					.getAttribute(DEFAULT_ERROR_INSTANCE_KEY_ATTRIBUTE_NAME);
//
//			List<AuthenticationException> authenticationExceptions = authenticationException.getExceptions();
//
//			request.setAttribute("message", MessageUtil.getText("login.user.lock"));
//		}
		Object authenticationException = request.getAttribute(DEFAULT_ERROR_INSTANCE_KEY_ATTRIBUTE_NAME);
		if(authenticationException != null){
			request.setAttribute("message",
					getAuthenticationFailedMessages((AuthenticationException) authenticationException));
		}
		String kick = request.getParameter("kickout");
		if (StringUtils.equals("1", kick)) {
			request.setAttribute("message", MessageUtil.getText("login.user.kick"));
		}

		return this.loginControllerPageSelector.selectLoginPage(request);
	}

	/**
	 * 获取认证异常提示信息
	 * 
	 * @param ex
	 * @return
	 */
	protected Object[] getAuthenticationFailedMessages(AuthenticationException ex) {
		if (ex instanceof IncorrectCaptchaException) {
			return new Object[] { MessageUtil.getText("login.info.error") };
		}
		if (ex instanceof InvalidAccountException) {
			return new Object[] { MessageUtil.getText("login.info.failed") };
		}
		if (ex instanceof InvalidStateException) {
			return new Object[] { MessageUtil.getText("login.user.forbid") };
		}
		if (ex instanceof ExcessiveAttemptsException) {
			return new Object[] { MessageUtil.getText("login.user.lock") };
		}
		if (ex instanceof ModularRealmAuthenticationException) {
			ModularRealmAuthenticationException exx = (ModularRealmAuthenticationException) ex;
			Map<Class<?>, AuthenticationException> exceptions = exx.getExceptions();
			Object[] errorMessages = new Object[]{};
			Iterator<Class<?>> iterator = exceptions.keySet().iterator();
			while(iterator.hasNext()){
				AuthenticationException authenticationException = exceptions.get(iterator.next());
				errorMessages = ArrayUtils.addAll(errorMessages, getAuthenticationFailedMessages(authenticationException));
			}
			return errorMessages;
		}
		return new Object[]{MessageUtil.getText("login.failed.unknown")};
	}

	/**
	 * 
	 * <p>
	 * 方法说明：切换角色
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
	 * <p>
	 * <p>
	 * 时间：2016年9月5日上午8:55:38
	 * <p>
	 * 
	 * @param jsdm
	 * @return
	 */
	@RequestMapping(value = "/switchRole")
	public String switchRole(String jsdm) {

		User currentUser = this.getUser();

		try {

			HttpSession session = WebContext.getSession();

			if (StringUtils.isNotBlank(jsdm) && (!StringUtils.equals(jsdm, currentUser.getJsdm()))) {
				// 切换当前的角色信息
				currentUser.setJsdm(jsdm);

				// 刷新shiro缓存
				AccountRealm shiroRealm = ServiceFactory.getService(DefaultAccountRealm.class);
				shiroRealm.clearAuthorizationCache();
				// 刷新shiro缓存
				// 删除用户数据范围标识
				session.removeAttribute(GlobalString.SESSION_YHJSSJFWZ_LIST);
			}
		} catch (Exception e) {
			logException(e);
		}

		return "redirect:" + INDEX_PAGE;
	}

}
