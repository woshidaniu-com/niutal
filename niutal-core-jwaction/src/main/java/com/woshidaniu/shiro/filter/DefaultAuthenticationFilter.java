package com.woshidaniu.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Initializable;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

import com.woshidaniu.basicutils.StrengthUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.GlobalString;
import com.woshidaniu.common.log.LoginType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.service.common.RSALoginService;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.shiro.IncorrectCaptchaException;
import com.woshidaniu.shiro.token.CaptchaAuthenticationToken;
import com.woshidaniu.shiro.token.DefaultAuthenticationToken;
import com.woshidaniu.shiro.token.ZfSsoToken;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：自定义shiro过滤器
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]</a>
 * @version 2017年6月7日下午5:37:51
 */
public class DefaultAuthenticationFilter extends BaseAuthenticationFilter implements Initializable{

	public static final String DEFAULT_YHM_PARAM = "yhm";
	
	public static final String DEFAULT_MM_PARAM = "mm";
	
	public static final String DEFAULT_YZM_PARAM = "yzm";
	
	private String yzmParam = DEFAULT_YZM_PARAM;
	
	private static final String LOGIN_FAIL_KEY = "dlCount";
	
	protected  static final transient Logger log = LoggerFactory.getLogger(DefaultAuthenticationFilter.class);

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		subject.getSession().setAttribute(GlobalString.WEB_SESSION_USER_IP, getHost(request));
		beforeOnLoginSuccess(token, subject, request, response);
		issueSuccessRedirect(request, response);
		return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		beforeOnLoginFailure(token, e, request, response);
        setFailureAttribute(request, e);
        return true;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String userName = getUsername(request);
		
		RSALoginService rsaLoginService = new RSALoginService();
		String password = rsaLoginService.decryptParameter(getPassword(request), (HttpServletRequest)request);
		rsaLoginService.removePrivateKey((HttpServletRequest)request);
		String validateCode = getYzm(request);
		String host = getHost(request);
		
		DefaultAuthenticationToken token = new DefaultAuthenticationToken();
		token.setCaptcha(validateCode);
		token.setHost(host);
		token.setPassword(StringUtils.isNull(password) ? null : password.toCharArray());
		token.setUsername(userName);
		// 密码强度计算
		if (!StringUtils.isNull(password)) {
			token.setStrength(StrengthUtils.getStrength(password));
		}
		
		return token;
	}
	
	
	@Validated
	protected void validateCaptcha(Session session, CaptchaAuthenticationToken token) {
		boolean validation = true;
		Integer count = (Integer) session.getAttribute(LOGIN_FAIL_KEY);
		
		if (count != null && count > 2){
			validation = validateCaptcha((String)session.getAttribute(getSessoionCaptchaKey()), token.getCaptcha());
		}
		
		if(!validation){
			throw new IncorrectCaptchaException("Captcha validation failed!");
		}
	}
	
	protected void beforeOnLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		
		HttpSession session = ((HttpServletRequest)request).getSession(); 
		Integer count = (Integer) session.getAttribute(LOGIN_FAIL_KEY);
		
		if (count == null){
			count = 1;
			session.setAttribute(LOGIN_FAIL_KEY, count);
		} else {
			session.setAttribute(LOGIN_FAIL_KEY, count + 1 );
		}
		
		if (log.isDebugEnabled()){
			log.debug(String.format("登录失败次数：%d", count));
		}
	}

	protected void beforeOnLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response){
		HttpSession session = ((HttpServletRequest)request).getSession(); 
		
		// 登录成功;记录登录方式标记；1：页面登录；2：单点登录；3：票据登录（通过握手秘钥等参数认证登录）
		// session.setAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY), LoginType.INNER.getKey());
		if (token instanceof ZfSsoToken){
			session.setAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY), LoginType.SSO.getKey());
		} else {
			session.setAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY), LoginType.INNER.getKey());
		}
		
		if (log.isDebugEnabled()){
			log.debug(String.format("登录token：%s", token.getClass()));
		}
		
		session.removeAttribute(LOGIN_FAIL_KEY);
		// 设置用户信息到session中
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		
		//密码强度
		DefaultAuthenticationToken authcToken = (DefaultAuthenticationToken) token;
		user.setYhmmdj(String.valueOf(authcToken.getStrength()));
		
		session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY), user);
		// 设置登录用户的登录角色信息，在拦截器中会用于判断角色切换后的权限判断
		session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_PRE_KEY), user.getJsdm());
		session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_KEY), user.getJsdm());
		LogEngineImpl.getInstance().login(user,"系统管理", "用户登录","用户登录成功");
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
		
	}

	
}
