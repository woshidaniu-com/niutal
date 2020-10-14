package com.woshidaniu.globalweb.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Initializable;
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
import com.woshidaniu.shiro.filter.BaseAuthenticationFilter;
import com.woshidaniu.shiro.token.DefaultAuthenticationToken;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.utils.WebRequestUtils;

/**
 * 默认表单登录验证拦截器
 * 
 * @author xiaokang[1036]
 * 基础功能实现
 * @author zhidong[1571]
 * 优化代码
 */
public class DefaultAuthenticationFilter extends BaseAuthenticationFilter implements Initializable{
	
	private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationFilter.class);
	//预先创建长度为0的空char数组，用于填充没有输入密码的情况
	private static final char[] EMPTY_CHAR_ARRAY = new char[] {};
	//默认用户名参数名称
	public static final String DEFAULT_YHM_PARAM = "yhm";
	//默认密码参数
	public static final String DEFAULT_MM_PARAM = "mm";
	
	public static final String DEFAULT_YZM_PARAM = "yzm";
	
	private String yzmParam = DEFAULT_YZM_PARAM;
	//登录成功后是否重定向到之前保存的请求地址
	private boolean redirectSavedRequestAfterLoginSuccess = false;
	//记录日志的必要信息
	private String czmk ="";
	private String ywmk ="";
	private String czms ="";
	private String czlx = BusinessType.LOGIN.getKey();
	
	public DefaultAuthenticationFilter() {
		super();
		this.czmk = MessageUtil.getTextOnly("log.login");
		this.ywmk = MessageUtil.getTextOnly("log.login.cx");
		this.czms = MessageUtil.getTextOnly("log.login.ms");
		
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,ServletResponse response) throws Exception {
		
		Session oldSession = subject.getSession();
		oldSession.setAttribute(GlobalString.WEB_SESSION_USER_IP, getHost(request));
		
		//防止session劫持
		newSession(subject,oldSession);
		
		beforeOnLoginSuccess(token, subject, request, response);
		
		if(this.redirectSavedRequestAfterLoginSuccess) {//重定向到saved request
			WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
		}else {//重定向到真正的首页
			WebUtils.issueRedirect(request, response, getSuccessUrl());
		}
		return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,ServletResponse response) {
		beforeOnLoginFailure(token, e, request, response);
        setFailureAttribute(request, e);
        return true;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request,ServletResponse response) {
		String userName = getUsername(request);
		
		RSALoginService rsaLoginService = new RSALoginService();
		String password = rsaLoginService.decryptParameter(getPassword(request), (HttpServletRequest)request);
		rsaLoginService.removePrivateKey((HttpServletRequest)request);
		String validateCode = getYzm(request);
		String host = getHost(request);
		
		DefaultAuthenticationToken token = new DefaultAuthenticationToken();
		token.setCaptcha(validateCode);
		token.setHost(host);
		
		//防止用户未输入用户名和密码就刷新登录页面，导致password为null,这样后续的处理，在String类里面有可能出现空指针异常
		if(StringUtils.isNull(password)) {
			token.setPassword(EMPTY_CHAR_ARRAY);
		}else {
			token.setPassword(password.toCharArray());
		}
		token.setUsername(userName);
		return token;
	}
	
	/**
	 * 登录失败前操作
	 * @param token
	 * @param e
	 * @param request
	 * @param response
	 */
	protected void beforeOnLoginFailure(AuthenticationToken token,AuthenticationException e, ServletRequest request,ServletResponse response) {
		
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
	protected void beforeOnLoginSuccess(AuthenticationToken token, Subject subject,ServletRequest request, ServletResponse response){
		
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
	
	protected String getPassword(ServletRequest request) {
		return request.getParameter(DEFAULT_MM_PARAM);
	}

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

	@Override
	public void init() throws ShiroException {
		String isOpenKaptcha  = MessageUtil.getText("isOpenKaptcha");
		if(StringUtils.isNull(isOpenKaptcha) || Boolean.valueOf(isOpenKaptcha)){
			setValidateCaptcha(true);
		}else{
			setValidateCaptcha(false);
		}
	}
	
	@Override
	public boolean isValidateCaptcha() {
		String isOpenKaptcha  = MessageUtil.getText("isOpenKaptcha");
		if(StringUtils.isNull(isOpenKaptcha) || Boolean.valueOf(isOpenKaptcha)){
			return true;
		}
		return false;
	}

	public void setRedirectSavedRequestAfterLoginSuccess(boolean redirectSavedRequestAfterLoginSuccess) {
		this.redirectSavedRequestAfterLoginSuccess = redirectSavedRequestAfterLoginSuccess;
	}
}