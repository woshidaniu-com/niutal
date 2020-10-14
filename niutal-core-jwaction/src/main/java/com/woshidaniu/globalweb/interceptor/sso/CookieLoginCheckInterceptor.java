package com.woshidaniu.globalweb.interceptor.sso;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.ZFtalParameter;
import com.woshidaniu.common.ZFtalParameters;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.LoginType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.log.YhLog;
import com.woshidaniu.entities.LoginModel;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.struts2.utils.LocaleUtils;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import com.woshidaniu.web.WebContext;

@SuppressWarnings("serial")
public class CookieLoginCheckInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG = LoggerFactory.getLogger(CookieLoginCheckInterceptor.class);
	public ILoginService loginService;

	public ILoginService getLoginService() {
		if (this.loginService == null) {
			this.loginService = (ILoginService) ServiceFactory.getService("loginService");
		}
		return this.loginService;
	}

	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		User user = WebContext.getUser();
		String path = ServletActionContext.getRequest().getServletPath();
		
		LOG.info("path ： " + path);
		
		//系统登入
		if (path.startsWith("/xtgl/login_cxCheckYh.html")) {
			
			LOG.info("Cookie SSO Login Check.");
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("status", Result.SUCCESS);
			resultMap.put("dlCount", 0);
			invocation.getStack().set(Result.DATA, resultMap);
			
			LOG.info("Cookie SSO Login Check Success.");
			
			return Result.DATA;
		}
		else if (path.startsWith("/xtgl/login_login.html")) {
			
			LOG.info("Cookie SSO Login .");
			String yhm = request.getParameter("yhm");
			String mm = request.getParameter("mm");
			//验证用户名和密码是否已填
			if (BlankUtils.isBlank(yhm) || BlankUtils.isBlank(mm)) {
				LOG.info("Cookie SSO Login [yhm or mm is empty ].");
				return Result.RD_LOGIN_OUT;
			}
			//SSO登录
			String token = CookieSsoUtils.login(yhm, mm, response);
			if (StringUtils.isNotEmpty(token)) {
				String uid = CookieSsoUtils.getUidByToken(token);
				LOG.info("Cookie SSO UID get Success.");
				if (StringUtils.isNotEmpty(uid)) {
					// 查询user对象
					LoginModel model = new LoginModel();
					model.setYhm(uid);
					user = getLoginService().cxYhxxWithoutPwd(model);
					//查询用户新
					if ((user == null) || ("".equals(user))) {
						LOG.info("用户暂时未同步至本系统。");
				    	invocation.getStack().set(Result.MESSAGE, "用户暂时未同步至本系统。" );
						return Result.EX_WARN;
					}else{
						handleSSOLogin(uid, user, request, response, invocation.getStack());
						//重定向到系统主页
						response.sendRedirect( request.getContextPath() + ZFtalParameters.getGlobalString(ZFtalParameter.LOGIN_HOMEPAGE_URL) + "?_t=" + new Date().getTime() );
						return null;
					}
				}
			}
			//如果没有SSO登录，则继续本系统的登录逻辑
			return invocation.invoke();
		}
		//系统登出
		else if (path.startsWith("/xtgl/login_logout.html")) {
			LOG.info("Cookie SSO Logout .");
			String uid = CookieSsoUtils.getUidByTokenInCookie(request,response);
			if (StringUtils.isNotEmpty(uid)) {
				CookieSsoUtils.logout(uid);
			}
			//继续本系统的登出逻辑
			return invocation.invoke();
		}
		//非用户登入登出的路径访问
		else if (user == null && "1".equals(request.getParameter("sso"))) {
			//获取cookie中的账号信息
			String uid = CookieSsoUtils.getUidByTokenInCookie(request, response);
			LOG.info("Get Uid By Token In Cookie ：uid = " + uid);
			if (StringUtils.isNotEmpty(uid)) {
				// 查询user对象
				LoginModel model = new LoginModel();
				model.setYhm(uid);
				user = getLoginService().cxYhxxWithoutPwd(model);
				if ((user == null) || ("".equals(user))) {
					LOG.info("用户暂时未同步至本系统。");
			    	invocation.getStack().set(Result.MESSAGE, "用户暂时未同步至本系统。" );
					return Result.EX_WARN;
				}else{
					handleSSOLogin(uid, user, request, response, invocation.getStack());
				}
			}
		}
		return invocation.invoke();
	}
	
	protected void handleSSOLogin(String uid,User user,HttpServletRequest request,HttpServletResponse response,ValueStack stack) throws IOException {
		
		HttpSession session = request.getSession();
		// 会话作废前取出,原Locale
		Locale locale = LocaleUtils.getSessionLocale(session);
		// 创建新的会话后，将原Locale放到会话中
		LocaleUtils.setSessionLocale(session, locale, stack);
		
		String name = CookieSsoUtils.getNameByUid(uid);
		session.setAttribute("mmkey", "mmkeyable");
		session.setAttribute("ZJU_SSO_UID", uid);
		session.setAttribute("NAME", name);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		
		
		// 登录成功;记录登录方式标记；1：页面登录；2：单点登录；3：票据登录（通过握手秘钥等参数认证登录）
		session.setAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY), LoginType.TICKIT.getKey());

		// 在线人数统计对象，目前只能统计单个容器，多个容器后期需要进行回话共享改造
		YhLog yhlog = new YhLog(user.getYhm());
		session.setAttribute("yhLog", yhlog);

		// 把用户加到session里
		session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY), user);
		// 设置登录用户的登录角色信息，在拦截器中会用于判断角色切换后的权限判断
		session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_PRE_KEY), user.getJsdm());
		session.setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_KEY), user.getJsdm());
		
		// 设置时间戳参数
		stack.setParameter("nowTime", new Date().getTime());
		
		//省去sessionUserkey参数
		request.setAttribute(ZFtalParameters.getGlobalString(ZFtalParameter.REQUEST_FUNC_USERKEY) , user.getYhm());
		
	}

}