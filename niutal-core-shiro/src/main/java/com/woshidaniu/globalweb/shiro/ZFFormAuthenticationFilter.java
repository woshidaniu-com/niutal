/**
 * 
 */
package com.woshidaniu.globalweb.shiro;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.GlobalString;
import com.woshidaniu.common.log.BusinessLogModel;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.service.common.RSALoginService;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.shiro.IncorrectCaptchaException;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.utils.WebRequestUtils;

/**
 * 
 * @类名 ZFFormAuthenticationFilter.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2015 2015年11月16日 上午11:12:13
 * @功能描述 TODO
 *
 */
public class ZFFormAuthenticationFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_YHM_PARAM = "yhm";
	
	public static final String DEFAULT_MM_PARAM = "mm";
	
	public static final String DEFAULT_YZM_PARAM = "yzm";
	
	public static final String SESSION_KAPTCHA_KEY = "KAPTCHA_SESSION_KEY";
	
	private String yzmParam = DEFAULT_YZM_PARAM;

	private static final Logger logger = LoggerFactory.getLogger(ZFFormAuthenticationFilter.class);  
	
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String userName = getUsername(request);
		
		RSALoginService rsaLoginService = new RSALoginService();
		String password = rsaLoginService.decryptParameter(getPassword(request), (HttpServletRequest)request);
		rsaLoginService.removePrivateKey((HttpServletRequest)request);
		String validateCode = getYzm(request);
		String host = getHost(request);
		ZFUsernamePasswordToken token = 
			new ZFUsernamePasswordToken(userName, 
					StringUtils.isNull(password) ? null : password.toCharArray(),
										host,
										validateCode);
		
		return token;
	}
	
	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		ZFUsernamePasswordToken token = (ZFUsernamePasswordToken) createToken(request, response);
		try {
			Subject subject = getSubject(request, response);
			
			//如果当前用户已登录，直接跳转
			//防止重复登录
			if(subject.isAuthenticated()){
				logger.info("User has already been Authenticated!");
				return onLoginSuccess(token, subject, request, response);
			}
			
			String isOpenKaptcha  = MessageUtil.getText("isOpenKaptcha");
			if(StringUtils.isNull(isOpenKaptcha) || Boolean.valueOf(isOpenKaptcha)){
				//校验验证码
				doValidateCode((HttpServletRequest) request, token);
			}
			
			/**
			 * 验证用户信息
			 */
			newAndChangeSession(subject, token, subject.getSession());
			
			//登录成功后获取当前用户的IP地址
			String ipAddress = WebRequestUtils.getRemoteAddr((HttpServletRequest) request);
			subject.getSession().setAttribute(GlobalString.WEB_SESSION_USER_IP, ipAddress);
			//登录成功后获取当前用户的IP地址
			
			logger.debug("Login successful ["+token.getHost()+", "+token.getUsername()+"].");
			
			beforeOnLoginSuccess(token, subject, request, response);
			
			return onLoginSuccess(token, subject, request, response);
		
		} catch (AuthenticationException e) {
			
			logger.debug("Login failed ["+token.getHost()+", "+token.getUsername()+"].");
			
			beforeOnLoginFailure(token, e, request, response);
			
			return onLoginFailure(token, e, request, response);
		}
	}
	
	/**
	 * 登录失败前操作
	 * @param token
	 * @param e
	 * @param request
	 * @param response
	 */
	protected void beforeOnLoginFailure(ZFUsernamePasswordToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		
		String czmk = MessageUtil.getTextOnly("log.login");
		String ywmk = MessageUtil.getTextOnly("log.login.cx");
		String czms = MessageUtil.getTextOnly("log.login.ms");
		String czlx = BusinessType.LOGIN.getKey();
		
		BusinessLogModel log = new BusinessLogModel();
		log.setCzlx(czlx);
		log.setCzmk(czmk);
		log.setCzms(czms + "[登录失败,出现异常：" +e.getMessage() + "]");
		log.setYwmc(ywmk);
		log.setCzr(token.getPrincipal().toString());
		log.setCzrq(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
		log.setIpdz(WebRequestUtils.getRemoteAddr((HttpServletRequest) request));
		log.setZjm(WebRequestUtils.getRemoteAddr((HttpServletRequest) request));
		LogEngineImpl.getInstance().log(log);
		
	}

	/**
	 * 成功登录前操作
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 */
	protected void beforeOnLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response){
		String czmk = MessageUtil.getTextOnly("log.login");
		String ywmk = MessageUtil.getTextOnly("log.login.cx");
		String czms = MessageUtil.getTextOnly("log.login.ms");
		String czlx = BusinessType.LOGIN.getKey();
		
		BusinessLogModel log = new BusinessLogModel();
		log.setCzlx(czlx);
		log.setCzmk(czmk);
		log.setCzms(czms);
		log.setYwmc(ywmk);
		log.setCzrq(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
		log.setCzr(token.getPrincipal().toString());
		log.setIpdz(WebRequestUtils.getRemoteAddr((HttpServletRequest) request));
		log.setZjm(WebRequestUtils.getRemoteAddr((HttpServletRequest) request));
		LogEngineImpl.getInstance().log(log);
	}
	
	
	/**
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Session newAndChangeSession(Subject subject, AuthenticationToken token, Session oldSession){
		
		//this will create a new session by default in applications that allow session state:
		subject.login(token);

		//retain Session attributes to put in the new session after login:
		Map attributes = new LinkedHashMap();
		
		Collection<Object> keys = oldSession.getAttributeKeys();
		
		for( Object key : keys) {
			Object value = oldSession.getAttribute(key);
			if (value != null) {
				attributes.put(key, value);
			}
		}
		oldSession.stop();	
		//restore the attributes:
		Session newSession = subject.getSession();
		
		for( Object key : attributes.keySet() ) {
			newSession.setAttribute(key, attributes.get(key));
		}
		return newSession;
	}
	
	/**
	 * 
	 * @param request
	 * @param token
	 */
	protected void doValidateCode(HttpServletRequest request, AuthenticationToken token){

		ZFUsernamePasswordToken zfToken = (ZFUsernamePasswordToken) token;

		String gc = getGenerateCode(request);
		
		if(StringUtils.isBlank(gc) || !StringUtils.equalsIgnoreCase(gc, zfToken.getYzm())){
			throw new IncorrectCaptchaException(MessageUtil.getText("login.info.error"));
		}
	}
	
	/**
	 * 获取生成的验证码
	 * 默认是从session中取
	 * @param request
	 * @return
	 */
	protected String getGenerateCode(HttpServletRequest request){
		HttpSession session = request.getSession();
		return session == null ? null : (String)session.getAttribute(SESSION_KAPTCHA_KEY);
	}

	@Override
	protected String getPassword(ServletRequest request) {
		return request.getParameter(DEFAULT_MM_PARAM);
	}

	@Override
	protected String getUsername(ServletRequest request) {
		return request.getParameter(DEFAULT_YHM_PARAM);
	}

	protected String getYzm(ServletRequest request) {
		return WebUtils.getCleanParam(request, getYzmParam());
	}

	public String getYzmParam() {
		return yzmParam;
	}

	public void setYzmParam(String yzmParam) {
		this.yzmParam = yzmParam;
	}

}
