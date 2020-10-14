/**
 * 
 */
package com.woshidaniu.globalweb.shiro.ext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.security.algorithm.DesBase64Codec;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：外部系统登录授权过滤器<br>
 *   :
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年7月29日下午4:50:46
 */
public class OutAccountAuthFilter extends AuthenticatingFilter{

	/**
	 * 解密参数使用
	 */
	protected static DesBase64Codec dbEncrypt = new DesBase64Codec();
	
	protected String errorUrl = "/err/403.jsp";
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		//加密类型
		String jmlx = WebUtils.getCleanParam(httpRequest, "jmlx");
		String param = WebUtils.getCleanParam(httpRequest, "param");
		String yhm = null;
		String remoteHost = httpRequest.getRemoteAddr();
		
		//=============解析用户信息============//
		if(StringUtils.equals("mw", jmlx)){
			yhm = WebUtils.getCleanParam(httpRequest, "yhm");
		}else{
			String d_param = dbEncrypt.decrypt(param.getBytes());
			String[] paramArr = d_param.split("&");
			for (int i = 0; i < paramArr.length; i++) {
				if(paramArr != null){
					String[] pair = paramArr[i].split("=");
					if(pair == null || pair.length < 2 ){
						continue;
					}
					if(StringUtils.isNoneBlank(pair[0]) && StringUtils.equals("yhm",pair[0].trim())){
						yhm = pair[1].trim();
						break;
					}
				}
			}
		}
		//=============解析用户信息============//
		if(StringUtils.isBlank(yhm)){
			return null;
		}
		
		OutAccountLoginToken token = new OutAccountLoginToken();
		token.setUserName(yhm);
		token.setHost(remoteHost);
		return token;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		 try {
			return executeLogin(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		//登录成功，继续后续执行
		return true;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		//登录失败，跳转到错误页面，通知用户异常信息
		try {
			Map<String,String> param = new HashMap<String, String>();
			if(token != null){
				param.put("yhm", ((OutAccountLoginToken) token).getUserName());
			}
			WebUtils.issueRedirect(request, response, errorUrl, param);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}
	
}
